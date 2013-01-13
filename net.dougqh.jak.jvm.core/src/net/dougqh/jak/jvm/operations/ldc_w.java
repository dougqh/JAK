package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Category1;

public final class ldc_w
	extends ParameterizedConstantOperation
	implements NormalizeableOperation
{
	public static final String ID = "ldc_w";
	public static final byte CODE = LDC_W;
	
	public static final ldc_w prototype() {
		return new ldc_w( 0 );
	}
	
	private final Object value;
	
	public ldc_w( final int value ) {
		this.value = value;
	}

	public ldc_w( final float value ) {
		this.value = value;
	}
	
	public ldc_w( final String value ) {
		this.value = value;
	}
	
	public ldc_w( final Type value ) {
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
		return Category1.class;
	}
	
	@Override
	public final Type type() {
		return Category1.class;
	}
	
	@Override
	public final <T> T value() {
		return ConstantOperation.<T>as( this.value );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		if ( this.value instanceof Integer ) {
			processor.ldc_w( (Integer)this.value );
		} else if ( this.value instanceof Float ) {
			processor.ldc_w( (Float)this.value );
		} else if ( this.value instanceof String ) {
			processor.ldc_w( (String)this.value );
		} else if ( this.value instanceof Type ) {
			processor.ldc_w( (Type)this.value );
		} else {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public final boolean canNormalize() {
		if ( this.value instanceof Integer ) {
			int intValue = (Integer)this.value;
			return ( intValue >= Short.MIN_VALUE && intValue <= Short.MAX_VALUE ); 
		} else if ( this.value instanceof Float ) {
			float floatValue = (Float)this.value;
			return ( floatValue == 0F || floatValue == 1F || floatValue == 2F );
		} else {
			return false;
		}
	}
	
	@Override
	public final void normalize( final JvmOperationProcessor processor ) {
		if ( this.value instanceof Integer ) {
			normalize( processor, (Integer)this.value );
		} else if ( this.value instanceof Float ) {
			normalize( processor, (Float)this.value );
		} else if ( this.value instanceof String ) {
			processor.ldc( (String)this.value );
		} else if ( this.value instanceof Type ) {
			processor.ldc( (Type)this.value );
		} else {
			throw new IllegalStateException();
		}		
	}
	
	public static final void normalize(
		final JvmOperationProcessor processor,
		final int value )
	{
		if ( value == -1 ) {
			processor.iconst_m1();
		} else if ( value == 0 ) {
			processor.iconst_0();
		} else if ( value == 1 ) {
			processor.iconst_1();
		} else if ( value == 2 ) {
			processor.iconst_2();
		} else if ( value == 3 ) {
			processor.iconst_3();
		} else if ( value == 4 ) {
			processor.iconst_4();
		} else if ( value == 5 ) {
			processor.iconst_5();
		} else if ( value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE ) {
			processor.bipush( (byte)value );
		} else if ( value >= Short.MIN_VALUE && value <= Short.MAX_VALUE ) {
			processor.sipush( (short)value );
		} else {
			processor.ldc_w( value );
		}
	}
	
	public static final void normalize(
		final JvmOperationProcessor processor,
		final float value )
	{
		if ( value == 0F ) {
			processor.fconst_0();
		} else if ( value == 1F ) {
			processor.fconst_1();
		} else if ( value == 2F ) {
			processor.fconst_2();
		} else {
			processor.ldc_w( value );
		}
	}
}