package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Category2;

public final class ldc2_w
	extends ConstantOperation
	implements NormalizeableOperation
{
	public static final String ID = "ldc2_w";
	public static final byte CODE = LDC2_W;
	
	public static final ldc2_w prototype() {
		return new ldc2_w( 0 );
	}
	
	private final Object value;
	
	public ldc2_w( final long value ) {
		this.value = value;
	}
	
	public ldc2_w( final double value ) {
		this.value = value;
	}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final Type codeInputType() {
		return Category2.class;
	}
	
	@Override
	public final Type type() {
		return Category2.class;
	}
	
	@Override
	public final <T> T value() {
		return ConstantOperation.<T>as( this.value );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		if ( this.value instanceof Long ) {
			processor.ldc2_w( (Long)value );
		} else if ( this.value instanceof Double ) {
			processor.ldc2_w( (Double)value );
		} else {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public boolean canNormalize() {
		if ( this.value instanceof Long ) {
			long longValue = (Long)this.value;
			return ( longValue == 0L || longValue == 1L );
		} else if ( this.value instanceof Double ) {
			double doubleValue = (Double)this.value;
			return ( doubleValue == 0D || doubleValue == 1D );
		} else {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public void normalize( final JvmOperationProcessor processor ) {
		if ( this.value instanceof Long ) {
			normalize( processor, (Long)this.value );
		} else if ( this.value instanceof Double ) {
			normalize( processor, (Double)this.value );
		} else {
			throw new IllegalStateException();
		}
	}
	
	public static final void normalize(
		final JvmOperationProcessor processor,
		final long value )
	{
		if ( value == 0 ) {
			processor.lconst_0();
		} else if ( value == 1 ) {
			processor.lconst_1();
		} else {
			processor.ldc2_w( value );
		}
	}
	
	public static final void normalize(
		final JvmOperationProcessor processor,
		final double value )
	{
		if ( value == 0D ) {
			processor.dconst_0();
		} else if ( value == 1F ) {
			processor.dconst_1();
		} else {
			processor.ldc2_w( value );
		}
	}
}