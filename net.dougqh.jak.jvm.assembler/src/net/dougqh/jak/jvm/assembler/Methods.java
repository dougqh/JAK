package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Type;

import net.dougqh.jak.Flags;
import net.dougqh.jak.FormalArguments;
import net.dougqh.jak.jvm.assembler.Attribute.Deferred;

final class Methods {
	private final ConstantPool constantPool;
	private final JvmOutputStream out;
	
	private int count = 0;
	
	private Attributes methodAttributes = null;
	
	Methods( final ConstantPool constantPool ) {
		this.constantPool = constantPool;
		this.out = new JvmOutputStream( 1024 );
	}
	
	final JvmCoreCodeWriterImpl createMethod(
		final int flags,
		final Type returnType,
		final String name,
		final FormalArguments arguments,
		final Type[] exceptionTypes,
		final Object defaultValue,
		final JvmLocals locals,
		final JvmStack stack )
	{
		this.finishMethod();
		++this.count;
		
		ConstantEntry nameEntry = this.constantPool.addUtf8( name );
		ConstantEntry descriptorEntry = this.constantPool.addMethodDescriptor(
			returnType,
			arguments );
		
		this.out.u2( flags ).u2( nameEntry ).u2( descriptorEntry );
		
		boolean needsCode =
			! Flags.isAbstract( flags ) &&
			! Flags.isNative( flags );
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

	final void write( final JvmOutputStream out ) {
		out.u2( this.count );
		this.out.writeTo( out );
	}
	
	private static final class ExceptionsAttribute extends Attribute {
		static final String ID = net.dougqh.jak.core.Attributes.EXCEPTIONS;
		
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
		final void writeBody( final JvmOutputStream out ) {
			out.u2( this.exceptionTypes.length );
			for ( Type exceptionType : this.exceptionTypes ) {
				out.u2( this.constantPool.addClassInfo( exceptionType ) );
			}
		}
	}
	
	private static final class SignatureAttribute extends FixedLengthAttribute {
		static final String ID = net.dougqh.jak.core.Attributes.SIGNATURE;
		
		private final ConstantEntry entry;
		
		SignatureAttribute(
			final ConstantPool constantPool,
			final Type returnType,
			final FormalArguments args )
		{
			super( constantPool, ID, 2 );
			
			this.entry = this.constantPool.addGenericMethodDescriptor(
				returnType,
				args );
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
	
	private static final class CodeAttribute
		extends Attribute
		implements Deferred
	{
		static final String ID = net.dougqh.jak.core.Attributes.CODE;
		
		private final JvmCoreCodeWriterImpl codeWriter;
		
		CodeAttribute(
			final ConstantPool constantPool,
			final JvmLocals locals,
			final JvmStack stack )
		{
			super( constantPool, ID );
			
			this.codeWriter = new JvmCoreCodeWriterImpl( constantPool, locals, stack );
		}
		
		@Override
		final boolean isEmpty() {
			return false;
		}
		
		@Override
		final void prepare() {
			this.codeWriter.prepareWrapperForWrite();
		}
		
		@Override
		final int length() {
			return this.codeWriter.length();
		}
		
		@Override
		final void writeBody( final JvmOutputStream out ) {
			this.codeWriter.write( out );
		}
		
		final JvmCoreCodeWriterImpl getCodeWriter() {
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
