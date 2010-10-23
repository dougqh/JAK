package net.dougqh.jak;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;

import net.dougqh.jak.Attribute.Deferred;
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
			this.flags = typeDescriptor.flags() | additionalFlags | JavaFlagsBuilder.SUPER;
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
	
	final void define( final JavaFieldDescriptor field ) {
		this.define( field, null );
	}
	
	final void define( final JavaFieldDescriptor field, final Object value ) {
		this.fields.add(
			field.flags(),
			field.getType(),
			field.getName(),
			value );
	}
	
	final JavaClassWriter defineClass(
		final TypeDescriptor typeDescriptor,
		final int additionalFlags )
	{
		TypeDescriptor innerTypeDescriptor = typeDescriptor.innerType( this, additionalFlags );
		
		this.addInnerClass( innerTypeDescriptor );
		
		JavaClassWriter classWriter = this.writerGroup.createClassWriter( innerTypeDescriptor );
		classWriter.typeWriter().addOuterClass( this );
		
		return classWriter;
	}
	
	final JavaInterfaceWriter defineInterface(
		final TypeDescriptor typeDescriptor,
		final int additionalFlags )
	{
		TypeDescriptor innerTypeDescriptor = typeDescriptor.innerType( this, additionalFlags );
		
		this.addInnerClass( innerTypeDescriptor );
		
		JavaInterfaceWriter interfaceWriter =
			this.writerGroup.createInterfaceWriter( innerTypeDescriptor );
		interfaceWriter.typeWriter().addOuterClass( this );
		return interfaceWriter;
	}
	
	final JavaAnnotationWriter defineAnnotation(
		final TypeDescriptor typeDescriptor,
		final int additionalFlags )
	{
		TypeDescriptor innerTypeDescriptor = typeDescriptor.innerType( this, additionalFlags );
		
		this.addInnerClass( innerTypeDescriptor );
		
		JavaAnnotationWriter annotationWriter = 
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
	
	final JavaCoreCodeWriter define(
		final JavaMethodDescriptor method,
		final int additionalFlags,
		final Object defaultValue )
	{
		LocalsMonitor locals = this.config.configure( new DefaultLocalsMonitor() );
		if ( ! method.isStatic() ) {
			locals.addParameter( this.thisType() );
		}
		for ( JavaVariable var : method.arguments() ) {
			locals.addParameter( var.getType() );
		}

		StackMonitor stack = this.config.configure( new DefaultStackMonitor() );		
		
		JavaCoreCodeWriterImpl writer = this.methods.createMethod(
			method.flags() | additionalFlags,
			method.getReturnType(),
			method.getName(),
			method.arguments(),
			method.exceptions(),
			defaultValue,
			locals,
			stack );
		
		if ( writer != null ) {
			JavaCoreCodeWriter wrapperWriter = this.config.configure( writer );
			writer.initWrapper( wrapperWriter );
			return wrapperWriter;
		} else {
			return null;
		}
	}
	
	final byte[] getBytes() {
		ByteStream out = new ByteStream( AVG_CLASS_SIZE );
		this.write( out );
		return out.toByteArray();
	}
	
	private final void write( final ByteStream out ) {
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
		return this.load( TypeWriter.class.getClassLoader() );
	}
	
	final Class< ? > load( final ClassLoader classLoader ) {
		return this.writerGroup.load( classLoader, this.className );
	}
	
	protected final Package definePackage(
		final TypeWriterGroup.DynamicClassLoader classLoader )
	{
		return classLoader.definePackage( this.packageName() );
	}
	
	protected final Class< ? > defineType(
		final TypeWriterGroup.DynamicClassLoader classLoader )
	{
		ByteStream out = new ByteStream( AVG_CLASS_SIZE );
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
	
	final void writeTo( final File srcDir )
		throws IOException
	{
		File classFile = new File(
			srcDir,
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
		static final String ID = "Signature";
		
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
		final void writeBody( final ByteStream out ) {
			out.u2( this.entry );
		}
	}
	
	private final class InnerClassesAttribute
		extends Attribute
		implements Deferred
	{
		static final String ID = "InnerClasses";
		
		private final ByteStream out;
		private int count;
		
		InnerClassesAttribute( final ConstantPool constantPool ) {
			super( constantPool, ID );
			
			this.out = new ByteStream( 32 );
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
		final void writeBody( final ByteStream out ) {
			out.u2( this.count );
			this.out.writeTo( out );
		}
	}
}
