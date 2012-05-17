package net.dougqh.jak.jvm.assembler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;

import net.dougqh.jak.Flags;
import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.JavaVariable;
import net.dougqh.jak.JavaVersion;
import net.dougqh.jak.TypeDescriptor;
import net.dougqh.jak.assembler.JakTypeResolver;
import net.dougqh.jak.jvm.assembler.Attribute.DeferredAttribute;
import net.dougqh.java.meta.types.JavaTypeVariable;
import net.dougqh.java.meta.types.JavaTypes;

final class TypeWriter {
	private static final int AVG_CLASS_SIZE = 2048;

	private final TypeWriterGroup writerGroup;
	private final JavaVersion version;
	
	private final String className;
	private final ConstantPool constantPool;
	private final int flags;
	private final Type parentType;
	private final Interfaces interfaces;
	private final Fields fields;
	private final Methods methods;
	private final Attributes attributes;
	
	private final ConstantEntry classNameEntry;
	private final ConstantEntry parentClassNameEntry;
	
	private InnerClassesAttribute innerClasses = null;
	private JakConfiguration config = new JakConfiguration();
	
	private final JakTypeResolver resolver = new JakTypeResolver() {
		@Override
		protected final Type resolve( final JavaTypeVariable typeVar ) {
			//TODO: Add support when generic class declaration support is added.
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected final Type thisType() {
			return TypeWriter.this.thisType();
		}
		
		@Override
		protected final Type superType() {
			return TypeWriter.this.superType();
		}
	};
	
	TypeWriter(
		final TypeWriterGroup writerGroup,
		final TypeDescriptor typeDescriptor,
		final int additionalFlags )
	{
		this.writerGroup = writerGroup;
		this.version = typeDescriptor.version();
		
		this.className = typeDescriptor.name();
		this.parentType = typeDescriptor.parentType();

		this.constantPool = new ConstantPool();
		this.classNameEntry = this.constantPool.addClassInfo( this.className );
		this.parentClassNameEntry = this.constantPool.addClassInfo( this.parentType );
		
		if ( this.version.getSuperFlag() ) {
			this.flags = typeDescriptor.flags() | additionalFlags | Flags.SUPER;
		} else {
			this.flags = typeDescriptor.flags() | additionalFlags;
		}
		
		this.interfaces = new Interfaces( this.constantPool );
		this.fields = new Fields( this.constantPool );
		this.methods = new Methods( this.constantPool );
		this.attributes = new Attributes( 128 );
		
		for ( Type interfaceType : typeDescriptor.interfaceTypes() ) {
			this.interfaces.add( JavaTypes.getRawClass( interfaceType ) );
		}
		
		this.attributes.add(
			new SignatureAttribute(
				this.constantPool,
				typeDescriptor.parentType(),
				typeDescriptor.interfaceTypes() ) );
	}
	
	final void initConfig( final JakConfiguration config ) {
		this.config = config;
	}
	
	final ConstantPool constantPool() {
		return this.constantPool;
	}
	
	final Type thisType() {
		return JavaTypes.objectTypeName( this.className );
	}
	
	final Type superType() {
		return this.parentType;
	}
	
	final Type resolve( final Type type ) {
		return this.resolver.resolve( type );
	}
	
	final void define( final JavaField field ) {
		this.define( field, null );
	}
	
	final void define( final JavaField field, final Object value ) {
		this.fields.add(
			field.getFlags(),
			field.getType(),
			field.getName(),
			value );
	}
	
	final JvmClassWriter defineClass(
		final TypeDescriptor typeDescriptor,
		final int additionalFlags )
	{
		TypeDescriptor innerTypeDescriptor = typeDescriptor.innerType( this.className, additionalFlags );
		
		this.addInnerClass( innerTypeDescriptor );
		
		JvmClassWriter classWriter = this.writerGroup.createClassWriter( innerTypeDescriptor );
		classWriter.typeWriter().addOuterClass( this );
		
		return classWriter;
	}
	
	final JvmInterfaceWriter defineInterface(
		final TypeDescriptor typeDescriptor,
		final int additionalFlags )
	{
		TypeDescriptor innerTypeDescriptor = typeDescriptor.innerType( this.className, additionalFlags );
		
		this.addInnerClass( innerTypeDescriptor );
		
		JvmInterfaceWriter interfaceWriter =
			this.writerGroup.createInterfaceWriter( innerTypeDescriptor );
		interfaceWriter.typeWriter().addOuterClass( this );
		return interfaceWriter;
	}
	
	final JvmAnnotationWriter defineAnnotation(
		final TypeDescriptor typeDescriptor,
		final int additionalFlags )
	{
		TypeDescriptor innerTypeDescriptor = typeDescriptor.innerType( this.className, additionalFlags );
		
		this.addInnerClass( innerTypeDescriptor );
		
		JvmAnnotationWriter annotationWriter = 
			this.writerGroup.createAnnotationWriter( innerTypeDescriptor );
		annotationWriter.typeWriter().addOuterClass( this );
		return annotationWriter;
	}
	
	final void addInnerClass( final TypeDescriptor typeBuilder ) {
		if ( this.innerClasses == null ) {
			this.innerClasses = new InnerClassesAttribute( this.constantPool );
			this.attributes.add( this.innerClasses );
		}
		this.innerClasses.addInnerClass( typeBuilder );
	}
	
	final void addOuterClass( final TypeWriter outerWriter ) {
		if ( this.innerClasses == null ) {
			this.innerClasses = new InnerClassesAttribute( this.constantPool );
			this.attributes.add( this.innerClasses );
		}
		this.innerClasses.addOuterClass( outerWriter );
	}
	
	final JvmCoreCodeWriter define(
		final JavaMethodDescriptor method,
		final int additionalFlags,
		final Object defaultValue )
	{
		JvmLocals locals = this.config.configure( new DefaultLocals() );
		if ( ! method.isStatic() ) {
			locals.declare( this.thisType() );
		}
		for ( JavaVariable var : method.arguments() ) {
			locals.declare( var.getType() );
		}

		JvmStack stack = this.config.configure( new DefaultJvmStack() );		
		
		JvmCoreCodeWriterImpl writer = this.methods.createMethod(
			method.getFlags() | additionalFlags,
			method.getTypeVars(),
			method.getReturnType(),
			method.getName(),
			method.arguments(),
			method.exceptions(),
			defaultValue,
			locals,
			stack );
		
		if ( writer != null ) {
			JvmCoreCodeWriter wrapperWriter = this.config.configure( writer );
			writer.initWrapper( wrapperWriter );
			return wrapperWriter;
		} else {
			return null;
		}
	}
	
	final byte[] getBytes() {
		JvmOutputStream out = new JvmOutputStream( AVG_CLASS_SIZE );
		this.write( out );
		return out.toByteArray();
	}
	
	private final void write( final JvmOutputStream out ) {
		//DQH - Both methods and attributes are lazily evaluated under 
		//certain circumstances, so it is necessary to prepareForWrite()
		//which will finalize the contents of the constant pool before 
		//writing the constant pool.
		this.methods.prepareForWrite();
		this.attributes.prepareForWrite();
		
		out.u2( 0xCAFE ).u2( 0xBABE );
		out.u2( this.version.getMinor() ).u2( this.version.getMajor() );
		this.constantPool.write( out );
		out.u2( this.flags );
		out.u2( this.classNameEntry );
		out.u2( this.parentClassNameEntry );
		this.interfaces.write( out );
		this.fields.write( out );
		this.methods.write( out );
		this.attributes.write( out );
	}
	
	final Class< ? > load() {
		return this.writerGroup.load( this.className );
	}
	
	protected final Package definePackage(
		final TypeWriterGroup.DynamicClassLoader classLoader )
	{
		return classLoader.definePackage( this.packageName() );
	}
	
	protected final Class< ? > defineType(
		final TypeWriterGroup.DynamicClassLoader classLoader )
	{
		JvmOutputStream out = new JvmOutputStream( AVG_CLASS_SIZE );
		this.write( out );
		return out.defineType( classLoader, this.className );
	}
	
	final String packageName() {
		int dotPos = this.className.lastIndexOf( '.' );
		if ( dotPos == -1 ) {
			return null;
		} else {
			return this.className.substring( 0, dotPos );
		}
	}
	
	final String name() {
		return this.className;
	}
	
	final String qualifyName( final String shortName ) {
		return this.className + '$' + shortName;
	}
	
//	public final void writeTo(
//		final ProcessingEnvironment env,
//		final Element... originatingElements )
//		throws IOException
//	{
//		this.writeTo( env.getFiler(), originatingElements );
//	}

//	public final void writeTo(
//		final Filer filer,
//		final Element... originatingElements )
//		throws IOException
//	{
//		JavaFileObject classFile = filer.createClassFile(
//			this.className,
//			originatingElements );
//		OutputStream out = classFile.openOutputStream();
//		try {
//			this.writeTo( out );
//		} finally {
//			out.close();
//		}
//	}
	
	final void writeTo( final File classDir )
		throws IOException
	{
		File classFile = new File(
			classDir,
			this.className.replace( '.', '/' ) + ".class" );
		
		File packageDir = classFile.getParentFile();
		boolean success = packageDir.mkdirs();
		if ( ! success && ! packageDir.exists() ) {
			throw new IOException( "Unable to create directory " + packageDir );
		}
		
		FileOutputStream out = new FileOutputStream( classFile );
		try {
			this.writeTo( out );
		} finally {
			out.close();
		}
	}
	
	final void writeTo( final OutputStream out )
		throws IOException
	{
		//TODO: Make this more efficient
		out.write( this.getBytes() );
	}
	
	protected final Type parentType() {
		return this.parentType;
	}
	
	protected final String simpleName() {
		int dotPos = this.className.lastIndexOf( '.' );
		if ( dotPos == -1 ) {
			return this.className;
		} else {
			return this.className.substring( dotPos + 1 );
		}
	}
	
	private static final class SignatureAttribute extends FixedLengthAttribute {
		static final String ID = net.dougqh.jak.core.Attributes.SIGNATURE;
		
		private final ConstantEntry entry;
		
		SignatureAttribute(
			final ConstantPool constantPool,
			final Type parentType,
			final Type[] interfaceTypes )
		{
			super( constantPool, ID, 2 );
			
			this.entry = this.constantPool.addGenericClassDescriptor(
				parentType,
				interfaceTypes );
		}
		
		@Override
		final boolean isEmpty() {
			return ( this.entry == null );
		}
		
		@Override
		final void writeBody( final JvmOutputStream out ) {
			out.u2( this.entry );
		}
	}
	
	private final class InnerClassesAttribute
		extends Attribute
		implements DeferredAttribute
	{
		static final String ID = net.dougqh.jak.core.Attributes.INNER_CLASSES;
		
		private final JvmOutputStream out;
		private int count;
		
		InnerClassesAttribute( final ConstantPool constantPool ) {
			super( constantPool, ID );
			
			this.out = new JvmOutputStream( 32 );
			this.count = 0;
		}
		
		@Override
		final boolean isEmpty() {
			return ( this.count == 0 );
		}
		
		@Override
		final int length() {
			//u2 for count + data length
			return 2 + this.out.length();
		}
		
		private final void addInnerClass( final TypeDescriptor innerTypeDescriptor ) {
			++this.count;

			this.out.u2( this.constantPool.addClassInfo( innerTypeDescriptor.name() ) );
			this.out.u2( TypeWriter.this.classNameEntry );
			this.out.u2( this.constantPool.addUtf8( innerTypeDescriptor.name() ) );
			this.out.u2( innerTypeDescriptor.flags() );
		}
		
		private final void addOuterClass( final TypeWriter outerWriter ) {
			++this.count;
			
			this.out.u2( TypeWriter.this.classNameEntry );
			this.out.u2( this.constantPool.addClassInfo( outerWriter.className ) );
			this.out.u2( this.constantPool.addUtf8( TypeWriter.this.simpleName() ) );
			this.out.u2( TypeWriter.this.flags );
		}
		
		@Override
		final void writeBody( final JvmOutputStream out ) {
			out.u2( this.count );
			this.out.writeTo( out );
		}
	}
}
