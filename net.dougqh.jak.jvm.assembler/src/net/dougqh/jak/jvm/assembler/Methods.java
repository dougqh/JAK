package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import net.dougqh.jak.Flags;
import net.dougqh.jak.FormalArguments;
import net.dougqh.jak.jvm.JvmLocalsTracker;
import net.dougqh.jak.jvm.JvmStackTracker;
import net.dougqh.jak.jvm.assembler.Attribute.DeferredAttribute;

final class Methods {
	private final WritingContext context;
	private final JvmOutputStream out;
	
	private int count = 0;
	
	private Attributes methodAttributes = null;
	
	Methods( final WritingContext context ) {
		this.context = context;
		this.out = new JvmOutputStream( 1024 );
	}
	
	final JvmCoreCodeWriterImpl createMethod(
		final int flags,
		final TypeVariable<?>[] typeVars,
		final Type returnType,
		final String name,
		final FormalArguments arguments,
		final Type[] exceptionTypes,
		final Object defaultValue,
		final JvmLocalsTracker locals,
		final JvmStackTracker stack )
	{
		this.finishMethod();
		++this.count;
		
		WritingContext methodContext = new WritingContext( this.context, typeVars );
		
		ConstantEntry nameEntry = this.context.constantPool.addUtf8( name );
		ConstantEntry descriptorEntry = this.context.constantPool.addMethodDescriptor(
			methodContext,
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
		
		this.methodAttributes.add(
			new ExceptionsAttribute( methodContext, exceptionTypes ) );
		this.methodAttributes.add(
			new SignatureAttribute( methodContext, typeVars, returnType, arguments ) );
		
		if ( needsCode ) {
			CodeAttribute codeAttribute = new CodeAttribute(
				methodContext,
				this.context.constantPool,
				locals,
				stack );
			
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
		static final String ID = net.dougqh.jak.jvm.Attributes.EXCEPTIONS;
		
		private final Type[] exceptionTypes;
		
		ExceptionsAttribute(
			final WritingContext context,
			final Type[] exceptionTypes )
		{
			super( context, ID );
			
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
		static final String ID = net.dougqh.jak.jvm.Attributes.SIGNATURE;
		
		private final ConstantEntry entry;
		
		SignatureAttribute(
			final WritingContext context,
			final TypeVariable<?>[] typeVars,
			final Type returnType,
			final FormalArguments args )
		{
			super( context, ID, 2 );
			
			this.entry = this.constantPool.addGenericMethodDescriptor( typeVars, returnType, args );
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
		implements DeferredAttribute
	{
		static final String ID = net.dougqh.jak.jvm.Attributes.CODE;
		
		private final JvmCoreCodeWriterImpl codeWriter;
		
		CodeAttribute(
			final WritingContext context,
			final ConstantPool constantPool,
			final JvmLocalsTracker locals,
			final JvmStackTracker stack )
		{
			super( context, ID );
			
			this.codeWriter = new JvmCoreCodeWriterImpl( context, locals, stack );
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
