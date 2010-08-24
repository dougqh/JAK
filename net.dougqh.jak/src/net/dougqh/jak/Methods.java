package net.dougqh.jak;

import java.lang.reflect.Type;

import net.dougqh.jak.Attribute.Deferred;

final class Methods {
	private final ConstantPool constantPool;
	private final ByteStream out;
	
	private int count = 0;
	
	private Attributes methodAttributes = null;
	
	Methods( final ConstantPool constantPool ) {
		this.constantPool = constantPool;
		this.out = new ByteStream( 1024 );
	}
	
	final JavaCoreCodeWriter createMethod(
		final int flags,
		final Type returnType,
		final String name,
		final FormalArguments arguments,
		final Type[] exceptionTypes,
		final Object defaultValue,
		final Locals locals,
		final Stack stack )
	{
		this.finishMethod();
		++this.count;
		
		int nameIndex = this.constantPool.addUtf8( name );
		int descriptorIndex = this.constantPool.addMethodDescriptor(
			returnType,
			arguments );
		
		this.out.u2( flags ).u2( nameIndex ).u2( descriptorIndex );
		
		boolean needsCode =
			! JavaFlagsBuilder.isAbstract( flags ) &&
			! JavaFlagsBuilder.isNative( flags );
		if ( needsCode ) {
			this.methodAttributes = new Attributes( 512 );
		} else {
			this.methodAttributes = new Attributes( 64 );
		}
		
		this.methodAttributes.add( new ExceptionsAttribute( this.constantPool, exceptionTypes ) );
		this.methodAttributes.add( new SignatureAttribute( this.constantPool, returnType, arguments ) );
		
		if ( needsCode ) {			
			CodeAttribute codeAttribute = new CodeAttribute( this.constantPool, locals, stack );
			this.methodAttributes.add( codeAttribute );
			return codeAttribute.getCodeWriter();
		} else {
			return null;
		}
	}
	
	private final void finishMethod() {
		if ( this.methodAttributes != null ) {
			this.methodAttributes.write( this.out );

			this.methodAttributes = null;
		}
	}
	
	final void prepareForWrite() {
		this.finishMethod();
	}

	final void write( final ByteStream out ) {
		out.u2( this.count );
		this.out.writeTo( out );
	}
	
	private static final class ExceptionsAttribute extends Attribute {
		static final String ID = "Exceptions";
		
		private final Type[] exceptionTypes;
		
		ExceptionsAttribute(
			final ConstantPool constantPool,
			final Type[] exceptionTypes )
		{
			super( constantPool, ID );
			
			this.exceptionTypes = exceptionTypes;
		}
		
		@Override
		final boolean isEmpty() {
			return ( this.exceptionTypes.length == 0 );
		}
		
		@Override
		final int length() {
			//2 bytes per entry - plus 2 bytes for the count
			return 2 * this.exceptionTypes.length + 2;
		}
		
		@Override
		final void writeBody( final ByteStream out ) {
			out.u2( this.exceptionTypes.length );
			for ( Type exceptionType : this.exceptionTypes ) {
				out.u2( this.constantPool.addClassInfo( exceptionType ) );
			}
		}
	}
	
	private static final class SignatureAttribute extends FixedLengthAttribute {
		static final String ID = "Signature";
		
		private final Integer index;
		
		SignatureAttribute(
			final ConstantPool constantPool,
			final Type returnType,
			final FormalArguments args )
		{
			super( constantPool, ID, 2 );
			
			this.index = this.constantPool.addGenericMethodDescriptor(
				returnType,
				args );
		}
		
		@Override
		final boolean isEmpty() {
			return ( this.index == null );
		}
		
		@Override
		final void writeBody( final ByteStream out ) {
			out.u2( this.index );
		}
	}
	
	private static final class CodeAttribute
		extends Attribute
		implements Deferred
	{
		static final String ID = "Code";
		
		private final JavaCoreCodeWriterImpl codeWriter;
		
		CodeAttribute(
			final ConstantPool constantPool,
			final Locals locals,
			final Stack stack )
		{
			super( constantPool, ID );
			
			this.codeWriter = new JavaCoreCodeWriterImpl( constantPool, locals, stack );
		}
		
		@Override
		final boolean isEmpty() {
			return false;
		}
		
		@Override
		final int length() {
			return this.codeWriter.length();
		}
		
		@Override
		final void writeBody( final ByteStream out ) {
			this.codeWriter.write( out );
		}
		
		final JavaCoreCodeWriter getCodeWriter() {
			return this.codeWriter;
		}
	}
	
//	private static final class AnnotationDefaultAttribute
//		extends Attribute
//	{
//		static final String ID = "AnnotationDefault";
//		
//		private final Object defaultValue;
//		
//		AnnotationDefaultAttribute(
//			final ConstantPool constantPool,
//			final Object defaultValue )
//		{
//			super( constantPool, ID );
//			this.defaultValue = defaultValue;
//		}
//		
//		@Override
//		final boolean isEmpty() {
//			return ( this.defaultValue == null );
//		}
//	}
}
