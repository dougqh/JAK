package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Type;

final class Fields {
	private final JvmOutputStream out = new JvmOutputStream( 512 );
	private int fieldCount = 0;

	private final ConstantPool constantPool;

	Fields( final ConstantPool constantPool ) {
		this.constantPool = constantPool;
	}
	
	final Fields add(
		final int flags,
		final Type type,
		final String name,
		final Object value )
	{
		++this.fieldCount;
		this.out.u2( flags ).
			u2( this.constantPool.addUtf8( name ) ).
			u2( this.constantPool.addFieldDescriptor( type ) );
		
		Attributes attributes = new Attributes( 64 );
		attributes.add( new ConstantValueAttribute( this.constantPool, type, value ) );
		attributes.add( new SignatureAttribute( this.constantPool, type ) );
		attributes.write( this.out );
		
		return this;
	}
	
	final void write( final JvmOutputStream out ) {
		out.u2( this.fieldCount );
		this.out.writeTo( out );
	}
	
	private static final class ConstantValueAttribute extends FixedLengthAttribute {
		static final String ID = net.dougqh.jak.core.Attributes.CONSTANT_VALUE;
		
		private final Type targetType;
		private final Object value;
		
		ConstantValueAttribute(
			final ConstantPool constantPool,
			final Type targetType,
			final Object value )
		{
			super( constantPool, ID, 2 );
			this.targetType = targetType;
			this.value = value;
		}
		
		@Override
		final boolean isEmpty() {
			return ( this.value == null );
		}
		
		@Override
		final void writeBody( final JvmOutputStream out ) {
			out.u2( this.constantPool.addConstant(
				this.targetType,
				this.value ) );
		}
	}
	
	private static final class SignatureAttribute extends FixedLengthAttribute {
		static final String ID = net.dougqh.jak.core.Attributes.SIGNATURE;
		
		private final ConstantEntry entry;
		
		SignatureAttribute(
			final ConstantPool constantPool,
			final Type type )
		{
			super( constantPool, ID, 2 );
			
			this.entry = this.constantPool.addGenericFieldDescriptor( type );
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
}
