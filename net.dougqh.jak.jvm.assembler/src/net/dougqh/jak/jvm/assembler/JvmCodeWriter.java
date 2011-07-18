package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;

import net.dougqh.jak.FormalArguments;
import net.dougqh.jak.Jak;
import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.JavaMethodSignature;
import net.dougqh.jak.JavaVariable;
import net.dougqh.jak.annotations.JvmOp;
import net.dougqh.jak.annotations.Symbol;
import net.dougqh.jak.annotations.SyntheticOp;
import net.dougqh.jak.annotations.WrapOp;
import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter.ExceptionHandler;
import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter.Jump;
import net.dougqh.jak.jvm.operations.*;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.ArgList;
import net.dougqh.jak.types.Primitive;
import net.dougqh.jak.types.Reference;
import net.dougqh.java.meta.types.JavaTypes;

public final class JvmCodeWriter {
	private static final String TYPE = "TYPE";
	
	private final TypeWriter typeWriter;
	private final JvmCoreCodeWriter coreWriter;

	private final HashMap< String, Integer > localVars = new HashMap< String, Integer >( 8 );
	private final HashMap< String, Integer > labels = new HashMap< String, Integer>( 4 );
	
	JvmCodeWriter(
		final TypeWriter typeWriter,
		final boolean isStatic,
		final FormalArguments arguments,
		final JvmCoreCodeWriter coreWriter )
	{
		this.typeWriter = typeWriter;
		
		if ( ! isStatic ) {
			this.localVars.put( "this", 0 );
		}

		int curArgPos = ( isStatic ? 0 : 1 );
		for ( JavaVariable var : arguments ) {
			if ( var.getName() != null ) {
				this.localVars.put( var.getName(), curArgPos );
			}
			curArgPos += JvmCoreCodeWriterImpl.size( var.getType() );
		}

		this.coreWriter = coreWriter;
	}
	
	public final JvmCoreCodeWriter coreWriter() {
		return this.coreWriter;
	}

	@JvmOp( nop.class )
	public final JvmCodeWriter nop() {
		this.coreWriter.nop();
		return this;
	}

	@SyntheticOp( stackResultTypes=Class.class )
	public final JvmCodeWriter aconst( final Class< ? > aClass ) {
		if ( aClass.equals( void.class ) ) {
			return this.getstatic(
				Void.class,
				Jak.field( Class.class, TYPE ) );
		} else if ( aClass.equals( boolean.class ) ) {
			return this.getstatic(
				Boolean.class,
				Jak.field( Class.class, TYPE ) );
		} else if ( aClass.equals( byte.class ) ) {
			return this.getstatic(
				Byte.class,
				Jak.field( Class.class, TYPE ) );
		} else if ( aClass.equals( char.class ) ) {
			return this.getstatic(
				Character.class,
				Jak.field( Class.class, TYPE ) );
		} else if ( aClass.equals( short.class ) ) {
			return this.getstatic(
				Short.class,
				Jak.field( Class.class, TYPE ) );
		} else if ( aClass.equals( int.class ) ) {
			return this.getstatic(
				Integer.class,
				Jak.field( Class.class, TYPE ) );
		} else if ( aClass.equals( long.class ) ) {
			return this.getstatic(
				Long.class,
				Jak.field( Class.class, TYPE ) );
		} else if ( aClass.equals( float.class ) ) {
			return this.getstatic(
				Float.class,
				Jak.field( Class.class, TYPE ) );
		} else if ( aClass.equals( double.class ) ) {
			return this.getstatic(
				Double.class,
				Jak.field( Class.class, TYPE ) );
		} else {
			return this.ldc( aClass );
		}
	}

	@JvmOp( aconst_null.class )
	public final JvmCodeWriter aconst_null() {
		this.coreWriter.aconst_null();
		return this;
	}

	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter iconst( final boolean value ) {
		if ( value ) {
			return this.iconst_1();
		} else {
			return this.iconst_0();
		}
	}

	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter iconst( final byte value ) {
		switch ( value ) {
			case -1:
			return this.iconst_m1();

			case 0:
			return this.iconst_0();

			case 1:
			return this.iconst_1();

			case 2:
			return this.iconst_2();

			case 3:
			return this.iconst_3();

			case 4:
			return this.iconst_4();

			case 5:
			return this.iconst_5();

			default:
			return this.bipush( value );
		}
	}

	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter iconst( final short value ) {
		switch ( value ) {
			case -1:
			return this.iconst_m1();

			case 0:
			return this.iconst_0();

			case 1:
			return this.iconst_1();

			case 2:
			return this.iconst_2();

			case 3:
			return this.iconst_3();

			case 4:
			return this.iconst_4();

			case 5:
			return this.iconst_5();

			default:
			return this.sipush( value );
		}
	}

	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter iconst( final char value ) {
		return this.iconst( (int)value );
	}

	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter iconst( final int value ) {
		if ( value == -1 ) {
			return this.iconst_m1();
		} else if ( value == 0 ) {
			return this.iconst_0();
		} else if ( value == 1 ) {
			return this.iconst_1();
		} else if ( value == 2 ) {
			return this.iconst_2();
		} else if ( value == 3 ) {
			return this.iconst_3();
		} else if ( value == 4 ) {
			return this.iconst_4();
		} else if ( value == 5 ) {
			return this.iconst_5();
		} else if ( value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE ) {
			return this.bipush( (byte)value );
		} else if ( value >= Short.MIN_VALUE && value <= Short.MAX_VALUE ) {
			return this.sipush( (short)value );
		} else {
			return this.ldc( value );
		}
	}

	@JvmOp( iconst_m1.class )
	public final JvmCodeWriter iconst_m1() {
		this.coreWriter.iconst_m1();
		return this;
	}

	@JvmOp( iconst_0.class )
	public final JvmCodeWriter iconst_0() {
		this.coreWriter.iconst_0();
		return this;
	}

	@JvmOp( iconst_1.class )
	public final JvmCodeWriter iconst_1() {
		this.coreWriter.iconst_1();
		return this;
	}

	@JvmOp( iconst_2.class )
	public final JvmCodeWriter iconst_2() {
		this.coreWriter.iconst_2();
		return this;
	}

	@JvmOp( iconst_3.class )
	public final JvmCodeWriter iconst_3() {
		this.coreWriter.iconst_3();
		return this;
	}

	@JvmOp( iconst_4.class )
	public final JvmCodeWriter iconst_4() {
		this.coreWriter.iconst_4();
		return this;
	}

	@JvmOp( iconst_5.class )
	public final JvmCodeWriter iconst_5() {
		this.coreWriter.iconst_5();
		return this;
	}

	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter lconst( final long value ) {
		if ( value == 0 ) {
			return this.lconst_0();
		} else if ( value == 1 ) {
			return this.lconst_1();
		} else if ( value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE ) {
			return this.iconst( (int) value ).i2l();
		} else {
			return this.ldc2_w( value );
		}
	}

	@JvmOp( lconst_0.class )
	public final JvmCodeWriter lconst_0() {
		this.coreWriter.lconst_0();
		return this;
	}

	@JvmOp( lconst_1.class )
	public final JvmCodeWriter lconst_1() {
		this.coreWriter.lconst_1();
		return this;
	}

	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter fconst( final float value ) {
		if ( value == 0F ) {
			return this.fconst_0();
		} else if ( value == 1F ) {
			return this.fconst_1();
		} else if ( value == 2F ) {
			return this.fconst_2();
		} else {
			return this.ldc( value );
		}
	}

	@JvmOp( fconst_0.class )
	public final JvmCodeWriter fconst_0() {
		this.coreWriter.fconst_0();
		return this;
	}

	@JvmOp( fconst_1.class )
	public final JvmCodeWriter fconst_1() {
		this.coreWriter.fconst_1();
		return this;
	}

	@JvmOp( fconst_2.class )
	public final JvmCodeWriter fconst_2() {
		this.coreWriter.fconst_2();
		return this;
	}

	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter dconst( final double value ) {
		if ( value == 0D ) {
			return this.dconst_0();
		} else if ( value == 1D ) {
			return this.dconst_1();
		} else {
			return this.ldc2_w( value );
		}
	}

	@JvmOp( dconst_0.class )
	public final JvmCodeWriter dconst_0() {
		this.coreWriter.dconst_0();
		return this;
	}

	@JvmOp( dconst_1.class )
	public final JvmCodeWriter dconst_1() {
		this.coreWriter.dconst_1();
		return this;
	}

	@JvmOp( bipush.class )
	public final JvmCodeWriter bipush( final byte value ) {
		this.coreWriter.bipush( value );
		return this;
	}

	@JvmOp( sipush.class )
	public final JvmCodeWriter sipush( final short value ) {
		this.coreWriter.sipush( value );
		return this;
	}

	@WrapOp( value=ldc.class, stackResultTypes=Class.class )
	public final JvmCodeWriter ldc( final Class< ? > aClass ) {
		return this.smartLdc( this.constantPool().addClassInfo( aClass ) );
	}

	@WrapOp( value=ldc.class, stackResultTypes=int.class )
	public final JvmCodeWriter ldc( final int value ) {
		return this.smartLdc( this.constantPool().addIntegerConstant( value ) );
	}

	@WrapOp( value=ldc.class, stackResultTypes=float.class )
	public final JvmCodeWriter ldc( final float value ) {
		return this.smartLdc( this.constantPool().addFloatConstant( value ) );
	}

	@WrapOp( value=ldc.class, stackResultTypes=String.class )
	public final JvmCodeWriter ldc( final CharSequence value ) {
		return this.smartLdc( this.constantPool().addStringConstant( value ) );
	}

	final JvmCodeWriter smartLdc( final ConstantEntry entry ) {
		if ( isSingleByte( entry ) ) {
			this.ldc_( entry );
		} else {
			this.ldc_w( entry );
		}
		return this;
	}

	final JvmCodeWriter ldc_( final ConstantEntry entry ) {
		this.coreWriter.ldc( entry );
		return this;
	}

	final JvmCodeWriter ldc_w( final ConstantEntry entry ) {
		this.coreWriter.ldc_w( entry );
		return this;
	}

	@WrapOp( value=ldc2_w.class, stackResultTypes=long.class )
	public final JvmCodeWriter ldc2_w( final long value ) {
		this.coreWriter.ldc2_w( this.constantPool().addLongConstant( value ) );
		return this;
	}

	@WrapOp( value=ldc2_w.class, stackResultTypes=double.class )
	public final JvmCodeWriter ldc2_w( final double value ) {
		this.coreWriter.ldc2_w( this.constantPool().addDoubleConstant( value ) );
		return this;
	}
	
	@WrapOp( iload.class )
	public final JvmCodeWriter iload( final String var ) {
		return this.iload( this.getOrReserveVarSlot( var, int.class ) );
	}

	@WrapOp( iload.class )
	public final JvmCodeWriter iload( final int slot ) {
		switch ( slot ) {
			case 0:
			return this.iload_0();

			case 1:
			return this.iload_1();

			case 2:
			return this.iload_2();

			case 3:
			return this.iload_3();

			default:
			this.coreWriter.iload( slot );
			return this;
		}
	}

	@JvmOp( iload_0.class )
	public final JvmCodeWriter iload_0() {
		this.coreWriter.iload_0();
		return this;
	}

	@JvmOp( iload_1.class )
	public final JvmCodeWriter iload_1() {
		this.coreWriter.iload_1();
		return this;
	}

	@JvmOp( iload_2.class )
	public final JvmCodeWriter iload_2() {
		this.coreWriter.iload_2();
		return this;
	}

	@JvmOp( iload_3.class )
	public final JvmCodeWriter iload_3() {
		this.coreWriter.iload_3();
		return this;
	}

	@JvmOp( lload.class )
	public final JvmCodeWriter lload( final @Symbol String var ) {
		return this.lload( this.getOrReserveVarSlot( var, long.class ) );
	}

	@WrapOp( lload.class )
	public final JvmCodeWriter lload( final int slot ) {
		switch ( slot ) {
			case 0:
			return this.lload_0();

			case 1:
			return this.lload_1();

			case 2:
			return this.lload_2();

			case 3:
			return this.lload_3();

			default:
			this.coreWriter.lload( slot );
			return this;
		}
	}

	@JvmOp( lload_0.class )
	public final JvmCodeWriter lload_0() {
		this.coreWriter.lload_0();
		return this;
	}

	@JvmOp( lload_1.class )
	public final JvmCodeWriter lload_1() {
		this.coreWriter.lload_1();
		return this;
	}

	@JvmOp( lload_2.class )
	public final JvmCodeWriter lload_2() {
		this.coreWriter.lload_2();
		return this;
	}

	@JvmOp( lload_3.class )
	public final JvmCodeWriter lload_3() {
		this.coreWriter.lload_3();
		return this;
	}
	
	@JvmOp( fload.class )
	public final JvmCodeWriter fload( final @Symbol String var ) {
		return this.fload( this.getOrReserveVarSlot( var, float.class ) );
	}

	@WrapOp( fload.class )
	public final JvmCodeWriter fload( final int slot ) {
		switch ( slot ) {
			case 0:
			return this.fload_0();

			case 1:
			return this.fload_1();

			case 2:
			return this.fload_2();

			case 3:
			return this.fload_3();

			default:
			this.coreWriter.fload( slot );
			return this;
		}
	}

	@JvmOp( fload_0.class )
	public final JvmCodeWriter fload_0() {
		this.coreWriter.fload_0();
		return this;
	}

	@JvmOp( fload_1.class )
	public final JvmCodeWriter fload_1() {
		this.coreWriter.fload_1();
		return this;
	}

	@JvmOp( fload_2.class )
	public final JvmCodeWriter fload_2() {
		this.coreWriter.fload_2();
		return this;
	}

	@JvmOp( fload_3.class )
	public final JvmCodeWriter fload_3() {
		this.coreWriter.fload_3();
		return this;
	}

	@WrapOp( dload.class )
	public final JvmCodeWriter dload( final @Symbol String var ) {
		return this.dload( this.getOrReserveVarSlot( var, double.class ) );
	}

	@WrapOp( dload.class )
	public final JvmCodeWriter dload( final int slot ) {
		switch (slot) {
			case 0:
			return this.dload_0();

			case 1:
			return this.dload_1();

			case 2:
			return this.dload_2();

			case 3:
			return this.dload_3();

			default:
			this.coreWriter.dload( slot );
			return this;
		}
	}

	@JvmOp( dload_0.class )
	public final JvmCodeWriter dload_0() {
		this.coreWriter.dload_0();
		return this;
	}

	@JvmOp( dload_1.class )
	public final JvmCodeWriter dload_1() {
		this.coreWriter.dload_1();
		return this;
	}

	@JvmOp( dload_2.class )
	public final JvmCodeWriter dload_2() {
		this.coreWriter.dload_2();
		return this;
	}

	@JvmOp( dload_3.class )
	public final JvmCodeWriter dload_3() {
		this.coreWriter.dload_3();
		return this;
	}

	@JvmOp( aload.class )
	public final JvmCodeWriter aload( final @Symbol String var ) {
		return this.aload( this.getOrReserveVarSlot( var, Reference.class ) );
	}

	@WrapOp( aload.class )
	public final JvmCodeWriter aload( final int slot ) {
		switch ( slot ) {
			case 0:
			return this.aload_0();

			case 1:
			return this.aload_1();

			case 2:
			return this.aload_2();

			case 3:
			return this.aload_3();

			default:
			this.coreWriter.aload( slot );
			return this;
		}
	}

	@WrapOp( aload_0.class )
	public final JvmCodeWriter this_() {
		return this.aload_0();
	}

	@JvmOp( aload_0.class )
	public final JvmCodeWriter aload_0() {
		this.coreWriter.aload_0();
		return this;
	}

	@JvmOp( aload_1.class )
	public final JvmCodeWriter aload_1() {
		this.coreWriter.aload_1();
		return this;
	}

	@JvmOp( aload_2.class )
	public final JvmCodeWriter aload_2() {
		this.coreWriter.aload_2();
		return this;
	}

	@JvmOp( aload_3.class )
	public final JvmCodeWriter aload_3() {
		this.coreWriter.aload_3();
		return this;
	}

	@JvmOp( iaload.class )
	public final JvmCodeWriter iaload() {
		this.coreWriter.iaload();
		return this;
	}

	@JvmOp( laload.class )
	public final JvmCodeWriter laload() {
		this.coreWriter.laload();
		return this;
	}

	@JvmOp( faload.class )
	public final JvmCodeWriter faload() {
		this.coreWriter.faload();
		return this;
	}

	@JvmOp( daload.class )
	public final JvmCodeWriter daload() {
		this.coreWriter.daload();
		return this;
	}

	@JvmOp( aaload.class )
	public final JvmCodeWriter aaload() {
		this.coreWriter.aaload();
		return this;
	}

	@JvmOp( baload.class )
	public final JvmCodeWriter baload() {
		this.coreWriter.baload();
		return this;
	}

	@JvmOp( caload.class )
	public final JvmCodeWriter caload() {
		this.coreWriter.caload();
		return this;
	}

	@JvmOp( saload.class )
	public final JvmCodeWriter saload() {
		this.coreWriter.saload();
		return this;
	}

	@JvmOp( istore.class )
	public final JvmCodeWriter istore( final @Symbol String var ) {
		return this.istore(this.getOrReserveVarSlot( var, int.class ) );
	}

	@WrapOp( istore.class )
	public final JvmCodeWriter istore( final int index ) {
		switch ( index ) {
			case 0:
			return this.istore_0();

			case 1:
			return this.istore_1();

			case 2:
			return this.istore_2();

			case 3:
			return this.istore_3();

			default:
			this.coreWriter.istore( index );
			return this;
		}
	}

	@JvmOp( istore_0.class )
	public final JvmCodeWriter istore_0() {
		this.coreWriter.istore_0();
		return this;
	}

	@JvmOp( istore_1.class )
	public final JvmCodeWriter istore_1() {
		this.coreWriter.istore_1();
		return this;
	}

	@JvmOp( istore_2.class )
	public final JvmCodeWriter istore_2() {
		this.coreWriter.istore_2();
		return this;
	}

	@JvmOp( istore_3.class )
	public final JvmCodeWriter istore_3() {
		this.coreWriter.istore_3();
		return this;
	}

	@WrapOp( lstore.class )
	public final JvmCodeWriter lstore( final @Symbol String var ) {
		return this.lstore( this.getOrReserveVarSlot( var, long.class ) );
	}

	@WrapOp( lstore.class )
	public final JvmCodeWriter lstore( final int slot ) {
		switch ( slot ) {
			case 0:
			return this.lstore_0();

			case 1:
			return this.lstore_1();

			case 2:
			return this.lstore_2();

			case 3:
			return this.lstore_3();

			default:
			this.coreWriter.lstore( slot );
			return this;
		}
	}

	@JvmOp( lstore_0.class )
	public final JvmCodeWriter lstore_0() {
		this.coreWriter.lstore_0();
		return this;
	}

	@JvmOp( lstore_1.class )
	public final JvmCodeWriter lstore_1() {
		this.coreWriter.lstore_1();
		return this;
	}

	@JvmOp( lstore_2.class )
	public final JvmCodeWriter lstore_2() {
		this.coreWriter.lstore_2();
		return this;
	}

	@JvmOp( lstore_3.class )
	public final JvmCodeWriter lstore_3() {
		this.coreWriter.lstore_3();
		return this;
	}

	@WrapOp( fstore.class )
	public final JvmCodeWriter fstore( final @Symbol String var ) {
		return this.fstore( this.getOrReserveVarSlot( var, float.class ) );
	}

	@WrapOp( fstore.class )
	public final JvmCodeWriter fstore( final int slot ) {
		switch ( slot ) {
			case 0:
			return this.fstore_0();

			case 1:
			return this.fstore_1();

			case 2:
			return this.fstore_2();

			case 3:
			return this.fstore_3();

			default:
			this.coreWriter.fstore( slot );
			return this;
		}
	}

	@JvmOp( fstore_0.class )
	public final JvmCodeWriter fstore_0() {
		this.coreWriter.fstore_0();
		return this;
	}
	
	@JvmOp( fstore_1.class )
	public final JvmCodeWriter fstore_1() {
		this.coreWriter.fstore_1();
		return this;
	}

	@JvmOp( fstore_2.class )
	public final JvmCodeWriter fstore_2() {
		this.coreWriter.fstore_2();
		return this;
	}

	@JvmOp( fstore_3.class )
	public final JvmCodeWriter fstore_3() {
		this.coreWriter.fstore_3();
		return this;
	}

	@WrapOp( dstore.class )
	public final JvmCodeWriter dstore( final @Symbol String var ) {
		return this.dstore( this.getOrReserveVarSlot( var, double.class ) );
	}

	@WrapOp( dstore.class )
	public final JvmCodeWriter dstore( final int slot ) {
		switch ( slot ) {
			case 0:
			return this.dstore_0();

			case 1:
			return this.dstore_1();

			case 2:
			return this.dstore_2();

			case 3:
			return this.dstore_3();

			default:
			this.coreWriter.dstore( slot );
			return this;
		}
	}

	@JvmOp( dstore_0.class )
	public final JvmCodeWriter dstore_0() {
		this.coreWriter.dstore_0();
		return this;
	}

	@JvmOp( dstore_1.class )
	public final JvmCodeWriter dstore_1() {
		this.coreWriter.dstore_1();
		return this;
	}

	@JvmOp( dstore_2.class )
	public final JvmCodeWriter dstore_2() {
		this.coreWriter.dstore_2();
		return this;
	}

	@JvmOp( dstore_3.class )
	public final JvmCodeWriter dstore_3() {
		this.coreWriter.dstore_3();
		return this;
	}

	@WrapOp( astore.class )
	public final JvmCodeWriter astore( final @Symbol String var ) {
		return this.astore( this.getOrReserveVarSlot( var, Reference.class ) );
	}

	@WrapOp( astore.class )
	public final JvmCodeWriter astore( final int slot ) {
		switch ( slot ) {
			case 0:
			return this.astore_0();

			case 1:
			return this.astore_1();

			case 2:
			return this.astore_2();

			case 3:
			return this.astore_3();

			default:
			this.coreWriter.astore( slot );
			return this;
		}
	}

	@JvmOp( astore_0.class )
	public final JvmCodeWriter astore_0() {
		this.coreWriter.astore_0();
		return this;
	}

	@JvmOp( astore_1.class )
	public final JvmCodeWriter astore_1() {
		this.coreWriter.astore_1();
		return this;
	}

	@JvmOp( astore_2.class )
	public final JvmCodeWriter astore_2() {
		this.coreWriter.astore_2();
		return this;
	}

	@JvmOp( astore_3.class )
	public final JvmCodeWriter astore_3() {
		this.coreWriter.astore_3();
		return this;
	}

	@JvmOp( iastore.class )
	public final JvmCodeWriter iastore() {
		this.coreWriter.iastore();
		return this;
	}

	@JvmOp( lastore.class )
	public final JvmCodeWriter lastore() {
		this.coreWriter.lastore();
		return this;
	}

	@JvmOp( fastore.class )
	public final JvmCodeWriter fastore() {
		this.coreWriter.fastore();
		return this;
	}

	@JvmOp( dastore.class )
	public final JvmCodeWriter dastore() {
		this.coreWriter.dastore();
		return this;
	}

	@JvmOp( aastore.class )
	public final JvmCodeWriter aastore() {
		this.coreWriter.aastore();
		return this;
	}

	@JvmOp( bastore.class )
	public final JvmCodeWriter bastore() {
		this.coreWriter.bastore();
		return this;
	}

	@JvmOp( castore.class )
	public final JvmCodeWriter castore() {
		this.coreWriter.castore();
		return this;
	}

	@JvmOp( sastore.class )
	public final JvmCodeWriter sastore() {
		this.coreWriter.sastore();
		return this;
	}
	
	@JvmOp( pop.class )
	public final JvmCodeWriter pop() {
		this.coreWriter.pop();
		return this;
	}

	@JvmOp( pop2.class )
	public final JvmCodeWriter pop2() {
		this.coreWriter.pop2();
		return this;
	}

	@JvmOp( dup.class )
	public final JvmCodeWriter dup() {
		this.coreWriter.dup();
		return this;
	}

	@JvmOp( dup_x1.class )
	public final JvmCodeWriter dup_x1() {
		this.coreWriter.dup_x1();
		return this;
	}

	@JvmOp( dup_x2.class )
	public final JvmCodeWriter dup_x2() {
		this.coreWriter.dup_x2();
		return this;
	}

	@JvmOp( dup2.class )
	public final JvmCodeWriter dup2() {
		this.coreWriter.dup2();
		return this;
	}

	@JvmOp( dup2_x1.class )
	public final JvmCodeWriter dup2_x1() {
		this.coreWriter.dup2_x1();
		return this;
	}

	@JvmOp( dup2_x2.class )
	public final JvmCodeWriter dup2_x2() {
		this.coreWriter.dup2_x2();
		return this;
	}

	@JvmOp( swap.class )
	public final JvmCodeWriter swap() {
		this.coreWriter.swap();
		return this;
	}

	@JvmOp( iadd.class )
	public final JvmCodeWriter iadd() {
		this.coreWriter.iadd();
		return this;
	}

	@JvmOp( ladd.class )
	public final JvmCodeWriter ladd() {
		this.coreWriter.ladd();
		return this;
	}

	@JvmOp( fadd.class )
	public final JvmCodeWriter fadd() {
		this.coreWriter.fadd();
		return this;
	}

	@JvmOp( dadd.class )
	public final JvmCodeWriter dadd() {
		this.coreWriter.dadd();
		return this;
	}

	@JvmOp( isub.class )
	public final JvmCodeWriter isub() {
		this.coreWriter.isub();
		return this;
	}

	@JvmOp( lsub.class )
	public final JvmCodeWriter lsub() {
		this.coreWriter.lsub();
		return this;
	}

	@JvmOp( fsub.class )
	public final JvmCodeWriter fsub() {
		this.coreWriter.fsub();
		return this;
	}

	@JvmOp( dsub.class )
	public final JvmCodeWriter dsub() {
		this.coreWriter.dsub();
		return this;
	}

	@JvmOp( imul.class )
	public final JvmCodeWriter imul() {
		this.coreWriter.imul();
		return this;
	}

	@JvmOp( lmul.class )
	public final JvmCodeWriter lmul() {
		this.coreWriter.lmul();
		return this;
	}

	@JvmOp( fmul.class )
	public final JvmCodeWriter fmul() {
		this.coreWriter.fmul();
		return this;
	}

	@JvmOp( dmul.class )
	public final JvmCodeWriter dmul() {
		this.coreWriter.dmul();
		return this;
	}

	@JvmOp( idiv.class )
	public final JvmCodeWriter idiv() {
		this.coreWriter.idiv();
		return this;
	}

	@JvmOp( ldiv.class )
	public final JvmCodeWriter ldiv() {
		this.coreWriter.ldiv();
		return this;
	}

	@JvmOp( fdiv.class )
	public final JvmCodeWriter fdiv() {
		this.coreWriter.fdiv();
		return this;
	}

	@JvmOp( ddiv.class )
	public final JvmCodeWriter ddiv() {
		this.coreWriter.ddiv();
		return this;
	}

	@JvmOp( irem.class )
	public final JvmCodeWriter irem() {
		this.coreWriter.irem();
		return this;
	}

	@JvmOp( lrem.class )
	public final JvmCodeWriter lrem() {
		this.coreWriter.lrem();
		return this;
	}

	@JvmOp( frem.class )
	public final JvmCodeWriter frem() {
		this.coreWriter.frem();
		return this;
	}

	@JvmOp( drem.class )
	public final JvmCodeWriter drem() {
		this.coreWriter.drem();
		return this;
	}

	@JvmOp( ineg.class )
	public final JvmCodeWriter ineg() {
		this.coreWriter.ineg();
		return this;
	}

	@JvmOp( lneg.class )
	public final JvmCodeWriter lneg() {
		this.coreWriter.lneg();
		return this;
	}

	@JvmOp( fneg.class )
	public final JvmCodeWriter fneg() {
		this.coreWriter.fneg();
		return this;
	}

	@JvmOp( dneg.class )
	public final JvmCodeWriter dneg() {
		this.coreWriter.dneg();
		return this;
	}

	@JvmOp( ishl.class )
	public final JvmCodeWriter ishl() {
		this.coreWriter.ishl();
		return this;
	}

	@JvmOp( lshl.class )
	public final JvmCodeWriter lshl() {
		this.coreWriter.lshl();
		return this;
	}

	@JvmOp( ishr.class )
	public final JvmCodeWriter ishr() {
		this.coreWriter.ishr();
		return this;
	}

	@JvmOp( lshr.class )
	public final JvmCodeWriter lshr() {
		this.coreWriter.lshr();
		return this;
	}

	@JvmOp( iushr.class )
	public final JvmCodeWriter iushr() {
		this.coreWriter.iushr();
		return this;
	}

	@JvmOp( lushr.class )
	public final JvmCodeWriter lushr() {
		this.coreWriter.lushr();
		return this;
	}

	@JvmOp( iand.class )
	public final JvmCodeWriter iand() {
		this.coreWriter.iand();
		return this;
	}

	@JvmOp( land.class )
	public final JvmCodeWriter land() {
		this.coreWriter.land();
		return this;
	}

	@JvmOp( ior.class )
	public final JvmCodeWriter ior() {
		this.coreWriter.ior();
		return this;
	}

	@JvmOp( lor.class )
	public final JvmCodeWriter lor() {
		this.coreWriter.lor();
		return this;
	}

	@JvmOp( ixor.class )
	public final JvmCodeWriter ixor() {
		this.coreWriter.ixor();
		return this;
	}

	@JvmOp( lxor.class )
	public final JvmCodeWriter lxor() {
		this.coreWriter.lxor();
		return this;
	}

	@WrapOp( iinc.class )
	public final JvmCodeWriter iinc( final @Symbol String var ) {
		return this.iinc( var, 1 );
	}

	@WrapOp( iinc.class )
	public final JvmCodeWriter iinc( final @Symbol String var, final int amount ) {
		return this.iinc( this.getOrReserveVarSlot( var, int.class ), amount );
	}

	@WrapOp( iinc.class )
	public final JvmCodeWriter iinc( final int slot ) {
		return this.iinc( slot, 1 );
	}

	@JvmOp( iinc.class )
	public final JvmCodeWriter iinc( final int slot, final int amount ) {
		this.coreWriter.iinc( slot, amount );
		return this;
	}

	@JvmOp( i2l.class )
	public final JvmCodeWriter i2l() {
		this.coreWriter.i2l();
		return this;
	}

	@JvmOp( i2f.class )
	public final JvmCodeWriter i2f() {
		this.coreWriter.i2f();
		return this;
	}

	@JvmOp( i2d.class )
	public final JvmCodeWriter i2d() {
		this.coreWriter.i2d();
		return this;
	}

	@JvmOp( l2i.class )
	public final JvmCodeWriter l2i() {
		this.coreWriter.l2i();
		return this;
	}

	@JvmOp( l2f.class )
	public final JvmCodeWriter l2f() {
		this.coreWriter.l2f();
		return this;
	}

	@JvmOp( l2d.class )
	public final JvmCodeWriter l2d() {
		this.coreWriter.l2d();
		return this;
	}

	@JvmOp( f2i.class )
	public final JvmCodeWriter f2i() {
		this.coreWriter.f2i();
		return this;
	}

	@JvmOp( f2l.class )
	public final JvmCodeWriter f2l() {
		this.coreWriter.f2l();
		return this;
	}

	@JvmOp( f2d.class )
	public final JvmCodeWriter f2d() {
		this.coreWriter.f2d();
		return this;
	}

	@JvmOp( d2i.class )
	public final JvmCodeWriter d2i() {
		this.coreWriter.d2i();
		return this;
	}

	@JvmOp( d2l.class )
	public final JvmCodeWriter d2l() {
		this.coreWriter.d2l();
		return this;
	}

	@JvmOp( d2f.class )
	public final JvmCodeWriter d2f() {
		this.coreWriter.d2f();
		return this;
	}

	@JvmOp( i2b.class )
	public final JvmCodeWriter i2b() {
		this.coreWriter.i2b();
		return this;
	}

	@JvmOp( i2c.class )
	public final JvmCodeWriter i2c() {
		this.coreWriter.i2c();
		return this;
	}

	@JvmOp( i2s.class )
	public final JvmCodeWriter i2s() {
		this.coreWriter.i2s();
		return this;
	}

	@JvmOp( lcmp.class )
	public final JvmCodeWriter lcmp() {
		this.coreWriter.lcmp();
		return this;
	}

	@JvmOp( fcmpl.class )
	public final JvmCodeWriter fcmpl() {
		this.coreWriter.fcmpl();
		return this;
	}

	@JvmOp( fcmpg.class )
	public final JvmCodeWriter fcmpg() {
		this.coreWriter.fcmpg();
		return this;
	}

	@JvmOp( dcmpl.class )
	public final JvmCodeWriter dcmpl() {
		this.coreWriter.dcmpl();
		return this;
	}

	@JvmOp( dcmpg.class )
	public final JvmCodeWriter dcmpg() {
		this.coreWriter.dcmpg();
		return this;
	}

	@WrapOp( ifeq.class )
	public final JvmCodeWriter ifeq( final @Symbol String label ) {
		this.coreWriter.ifeq( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( ifne.class )
	public final JvmCodeWriter ifne( final @Symbol String label ) {
		this.coreWriter.ifne( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( iflt.class )
	public final JvmCodeWriter iflt( final @Symbol String label ) {
		this.coreWriter.iflt( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( ifgt.class )
	public final JvmCodeWriter ifgt( final @Symbol String label ) {
		this.coreWriter.ifgt( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( ifge.class )
	public final JvmCodeWriter ifge( final @Symbol String label ) {
		this.coreWriter.ifge( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( ifle.class )
	public final JvmCodeWriter ifle( final @Symbol String label ) {
		this.coreWriter.ifle( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_icmpeq.class )
	public final JvmCodeWriter if_icmpeq( final @Symbol String label ) {
		this.coreWriter.if_icmpeq( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_icmpne.class )
	public final JvmCodeWriter if_icmpne( final @Symbol String label ) {
		this.coreWriter.if_icmpne( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_icmplt.class )
	public final JvmCodeWriter if_icmplt( final @Symbol String label ) {
		this.coreWriter.if_icmplt( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_icmpgt.class )
	public final JvmCodeWriter if_icmpgt( final @Symbol String label ) {
		this.coreWriter.if_icmpgt( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_icmpge.class )
	public final JvmCodeWriter if_icmpge( final @Symbol String label ) {
		this.coreWriter.if_icmpge( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_icmple.class )
	public final JvmCodeWriter if_icmple( final @Symbol String label ) {
		this.coreWriter.if_icmple( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_acmpeq.class )
	public final JvmCodeWriter if_acmpeq( final @Symbol String label ) {
		this.coreWriter.if_acmpeq( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_acmpne.class )
	public final JvmCodeWriter if_acmpne( final @Symbol String label ) {
		this.coreWriter.if_acmpne( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( goto_.class )
	public final JvmCodeWriter goto_( final @Symbol String label ) {
		this.coreWriter.goto_( this.jumpTo( label ) );
		return this;
	}

	final JvmCodeWriter jsr() {
		// TODO: jsr
		return null;
	}

	final JvmCodeWriter ret() {
		// TODO: ret
		return null;
	}

	final JvmCodeWriter tableswitch() {
		// TODO: tableswitch
		return null;
	}

	final JvmCodeWriter lookupswitch() {
		// TODO: lookupswitch
		return null;
	}

	@JvmOp( ireturn.class )
	public final JvmCodeWriter ireturn() {
		this.coreWriter.ireturn();
		return this;
	}

	@JvmOp( lreturn.class )
	public final JvmCodeWriter lreturn() {
		this.coreWriter.lreturn();
		return this;
	}

	@JvmOp( freturn.class )
	public final JvmCodeWriter freturn() {
		this.coreWriter.freturn();
		return this;
	}

	@JvmOp( dreturn.class )
	public final JvmCodeWriter dreturn() {
		this.coreWriter.dreturn();
		return this;
	}

	@JvmOp( areturn.class )
	public final JvmCodeWriter areturn() {
		this.coreWriter.areturn();
		return this;
	}

	@JvmOp( return_.class )
	public final JvmCodeWriter return_() {
		this.coreWriter.return_();
		return this;
	}

	@WrapOp( getstatic.class )
	public final JvmCodeWriter self_getstatic( final JavaField field ) {
		return this.getstatic( this.thisType(), field );
	}

	@WrapOp( value=getstatic.class, repl=false )
	public final JvmCodeWriter getstatic(
		final Type targetType,
		final @Symbol String fieldName )
	{
		return this.getstatic( getField( targetType, fieldName ) );
	}

	@JvmOp( getstatic.class )
	public final JvmCodeWriter getstatic(
		final Type targetType,
		final JavaField field )
	{
		this.coreWriter.getstatic( targetType, field );
		return this;
	}

	@WrapOp( getstatic.class )
	public final JvmCodeWriter getstatic( final Field field ) {
		this.coreWriter.getstatic(
			field.getDeclaringClass(),
			Jak.field( field ) );
		return this;
	}

	@WrapOp( putstatic.class )
	public final JvmCodeWriter putstatic(
		final Type targetType,
		final @Symbol String fieldName )
	{
		return this.putstatic( getField( targetType, fieldName ) );
	}

	@WrapOp( putstatic.class )
	public final JvmCodeWriter self_putstatic( final JavaField field ) {
		return this.putstatic( this.thisType(), field );
	}

	@JvmOp( putstatic.class )
	public final JvmCodeWriter putstatic(
		final Type targetType,
		final JavaField field )
	{
		this.coreWriter.putstatic( targetType, field );
		return this;
	}

	@WrapOp( putstatic.class )
	public final JvmCodeWriter putstatic( final Field field ) {
		this.coreWriter.putstatic(
			field.getDeclaringClass(),
			Jak.field( field ) );
		return this;
	}

	@WrapOp( getfield.class )
	public final JvmCodeWriter getfield(
		final Type targetType,
		final @Symbol String fieldName )
	{
		return this.getfield( getField( targetType, fieldName ) );
	}

	@WrapOp( getfield.class )
	public final JvmCodeWriter this_getfield( final JavaField field ) {
		return this.getfield( this.thisType(), field );
	}

	@JvmOp( getfield.class )
	public final JvmCodeWriter getfield(
		final Type targetType,
		final JavaField field )
	{
		this.coreWriter.getfield( targetType, field );
		return this;
	}

	@WrapOp( getfield.class )
	public final JvmCodeWriter getfield( final Field field ) {
		this.coreWriter.getfield(
			field.getDeclaringClass(),
			Jak.field( field ) );
		
		return this;
	}

	@WrapOp( putfield.class )
	public final JvmCodeWriter putfield(
		final Type targetType,
		final @Symbol String fieldName )
	{
		return this.putfield( getField( targetType, fieldName ) );
	}

	@WrapOp( putfield.class )
	public final JvmCodeWriter this_putfield( final JavaField field ) {
		return this.putfield( this.thisType(), field );
	}

	@JvmOp( putfield.class )
	public final JvmCodeWriter putfield(
		final Type targetType,
		final JavaField field )
	{
		this.coreWriter.putfield(
			targetType,
			Jak.field( field.getType(), field.getName() ) );
		return this;
	}

	@WrapOp( putfield.class )
	public final JvmCodeWriter putfield( final Field field ) {
		this.coreWriter.putfield(
			field.getDeclaringClass(),
			Jak.field( field.getType(), field.getName() ) );
		return this;
	}

	@WrapOp( getfield.class )
	private static final Field getField(
		final Type targetType,
		final @Symbol String fieldName )
	{
		Class<?> rawTargetClass = JavaTypes.getRawClass( targetType );
		try {
			return rawTargetClass.getField( fieldName );
		} catch ( SecurityException e ) {
			throw new IllegalArgumentException( e );
		} catch ( NoSuchFieldException e ) {
			throw new IllegalArgumentException( e );
		}
	}

	@SyntheticOp( stackOperandTypes={ Any.class, ArgList.class }, stackResultTypes=Any.class )
	public final JvmCodeWriter invoke(
		final Type targetType,
		final @Symbol String methodName,
		final Type... args )
	{
		return this.invoke( targetType, Jak.method( methodName, args ) );
	}

	@SyntheticOp( stackOperandTypes={ Any.class, ArgList.class }, stackResultTypes=Any.class )
	public final JvmCodeWriter invoke(
		final Type targetType,
		final JavaMethodSignature signature )
	{
		return this.invoke( findMethod( targetType, signature ) );
	}

	private final Method findMethod(
		final Type targetType,
		final JavaMethodSignature signature )
	{
		try {
			Class< ? > aClass = JavaTypes.getRawClass( targetType );
			return aClass.getMethod(
				signature.name(),
				signature.arguments().getRawClasses() );
		} catch ( NoSuchMethodException e ) {
			throw new IllegalArgumentException( e );
		}
	}

	@SyntheticOp( stackOperandTypes={ Any.class, ArgList.class }, stackResultTypes=Any.class )
	public final JvmCodeWriter invoke( final Method method ) {
		if ( isInterface( method ) ) {
			return this.invokeinterface( method );
		} else if ( isStatic( method ) ) {
			return this.invokestatic( method );
		} else if ( isPrivate( method ) ) {
			return this.invokespecial( method );
		} else {
			return this.invokevirtual( method );
		}
	}
	
	@WrapOp( invokevirtual.class )
	public final JvmCodeWriter invokevirtual( final Method method ) {
		return this.invokevirtual(
			method.getDeclaringClass(),
			Jak.method( method ) );
	}
	
	@JvmOp( invokeinterface.class )
	public final JvmCodeWriter invokeinterface( final Method method ) {
		return this.invokeinterface(
			method.getDeclaringClass(),
			Jak.method( method ) );
	}

	@JvmOp( invokespecial.class )
	public final JvmCodeWriter invokespecial( final Method method ) {
		return this.invokespecial(
			method.getDeclaringClass(),
			Jak.method( method ) );
	}
	
	@JvmOp( invokestatic.class )
	public final JvmCodeWriter invokestatic( final Method method ) {
		return this.invokestatic(
			method.getDeclaringClass(),
			Jak.method( method ) );
	}

	@JvmOp( invokevirtual.class )
	public final JvmCodeWriter invokevirtual(
		final Type targetType,
		final JavaMethodSignature signature )
	{
		Method method = findMethod( targetType, signature );
		if (Modifier.isStatic( method.getModifiers() ) ) {
			throw new IllegalStateException( "Not a virtual method" );
		}
		return this.invokevirtual( method );
	}

	@WrapOp( invokevirtual.class )
	public final JvmCodeWriter this_invokevirtual(
		final JavaMethodDescriptor method )
	{
		return this.invokevirtual( this.thisType(), method );
	}

	@JvmOp( invokevirtual.class )
	public final JvmCodeWriter invokevirtual(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.coreWriter.invokevirtual( targetType, method );
		return this;
	}

	@WrapOp( invokevirtual.class )
	public final JvmCodeWriter this_invokevirtual(
		final Type returnType,
		final @Symbol String methodName,
		final Type... argumentTypes )
	{
		return this.invokevirtual(
			this.thisType(),
			returnType,
			methodName,
			argumentTypes );
	}

	@WrapOp( invokevirtual.class )
	public final JvmCodeWriter invokevirtual(
		final Type targetType,
		final Type returnType,
		final @Symbol String methodName,
		final Type... argumentTypes )
	{
		this.coreWriter.invokevirtual(
			targetType,
			Jak.method(
				returnType,
				methodName,
				argumentTypes ) );
		return this;
	}

	@WrapOp( invokespecial.class )
	public final JvmCodeWriter this_invokespecial(
		final JavaMethodDescriptor method )
	{
		return this.invokespecial( this.thisType(), method );
	}

	@WrapOp( invokespecial.class )
	public final JvmCodeWriter super_invokespecial(
		final JavaMethodDescriptor method )
	{
		return this.invokespecial( this.superType(), method );
	}

	@JvmOp( invokespecial.class )
	public final JvmCodeWriter invokespecial(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.coreWriter.invokespecial( targetType, method );
		return this;
	}

	@WrapOp( invokespecial.class )
	public final JvmCodeWriter this_invokespecial(
		final Type returnType,
		final String methodName,
		final Type... argumentTypes )
	{
		return this.invokespecial(
			this.thisType(),
			returnType,
			methodName,
			argumentTypes );
	}

	@WrapOp( invokespecial.class )
	public final JvmCodeWriter super_invokespecial(
		final Type returnType,
		final String methodName,
		final Type... argumentTypes )
	{
		return this.invokespecial(
			this.superType(),
			returnType,
			methodName,
			argumentTypes );
	}

	@WrapOp( invokespecial.class )
	public final JvmCodeWriter invokespecial(
		final Type targetType,
		final Type returnType,
		final @Symbol String methodName,
		final Type... argumentTypes )
	{
		return this.invokespecial(
			targetType,
			Jak.method( returnType, methodName, argumentTypes ) );
	}

	@JvmOp( invokestatic.class )
	public final JvmCodeWriter invokestatic(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.coreWriter.invokestatic( targetType, method );
		return this;
	}

	@WrapOp( invokestatic.class )
	public final JvmCodeWriter invokestatic(
		final Type targetType,
		final JavaMethodSignature builder )
	{
		Method method = findMethod( targetType, builder );
		if ( ! Modifier.isStatic( method.getModifiers() ) ) {
			throw new IllegalStateException( "Not a static method" );
		}
		return this.invokevirtual( method );
	}

	@WrapOp( invokeinterface.class )
	public final JvmCodeWriter this_invokeinterface(
		final Type returnType,
		final @Symbol String methodName,
		final Type... argumentTypes )
	{
		return this.invokeinterface(
			this.thisType(),
			returnType,
			methodName,
			argumentTypes );
	}

	@WrapOp( invokeinterface.class )
	public final JvmCodeWriter invokeinterface(
		final Type targetType,
		final Type returnType,
		final @Symbol String methodName,
		final Type... argumentTypes )
	{
		return this.invokeinterface(
			targetType,
			Jak.method( returnType, methodName, argumentTypes ) );
	}

	@WrapOp( invokeinterface.class )
	public final JvmCodeWriter this_invokeinterface(
		final JavaMethodDescriptor method )
	{
		return this.invokeinterface( this.thisType(), method );
	}

	@JvmOp( invokeinterface.class )
	public final JvmCodeWriter invokeinterface(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.coreWriter.invokeinterface( targetType, method );
		return this;
	}

	@JvmOp( new_.class )
	public final JvmCodeWriter new_( final Type type ) {
		this.coreWriter.new_( type );
		return this;
	}

	@JvmOp( newarray.class )
	public final JvmCodeWriter newarray( final Type componentType ) {
		this.coreWriter.newarray( componentType );
		return this;
	}

	@SyntheticOp( stackOperandTypes=boolean[].class )
	public final JvmCodeWriter barray( final boolean... array ) {
		this.iconst( array.length ).newarray( boolean.class );

		for ( int i = 0; i < array.length; ++i ) {
			boolean value = array[ i ];
			if ( value ) {
				this.dup().
					iconst( i ).
					iconst( value ).
					bastore();
			}
		}

		return this;
	}

	@SyntheticOp( stackOperandTypes=byte[].class )
	public final JvmCodeWriter barray( final byte... array ) {
		this.iconst( array.length ).newarray( byte.class );

		for ( int i = 0; i < array.length; ++i ) {
			byte value = array[ i ];
			if ( value != 0 ) {
				this.dup().
					iconst( i ).
					iconst( value ).
					bastore();
			}
		}

		return this;
	}

	@SyntheticOp( stackResultTypes=char[].class )
	public final JvmCodeWriter carray( final char... array ) {
		this.iconst( array.length ).newarray( char.class );

		for ( int i = 0; i < array.length; ++i ) {
			char value = array[ i ];
			if ( value != 0 ) {
				this.dup().
					iconst( i ).
					iconst( value ).
					castore();
			}
		}

		return this;
	}

	@SyntheticOp( stackResultTypes=short[].class )
	public final JvmCodeWriter sarray( final short... array ) {
		this.iconst( array.length ).newarray( short.class );

		for ( int i = 0; i < array.length; ++i ) {
			short value = array[ i ];
			if ( value != 0 ) {
				this.dup().
					iconst( i ).
					iconst( value ).
					sastore();
			}
		}

		return this;
	}

	@SyntheticOp( stackResultTypes=int[].class )
	public final JvmCodeWriter iarray( final int... array ) {
		this.iconst( array.length ).newarray( int.class );

		for ( int i = 0; i < array.length; ++i ) {
			int value = array[ i ];
			if ( value != 0 ) {
				this.dup().
					iconst( i ).
					iconst( value ).
					iastore();
			}
		}

		return this;
	}

	@SyntheticOp( stackResultTypes=float[].class )
	public final JvmCodeWriter farray( final float... array ) {
		this.iconst( array.length ).newarray( float.class );

		for ( int i = 0; i < array.length; ++i ) {
			float value = array[ i ];
			if ( value != 0F ) {
				this.
					dup().
					iconst( i ).
					fconst( value ).
					fastore();
			}
		}

		return this;
	}

	@SyntheticOp( stackResultTypes=long[].class )
	public final JvmCodeWriter larray( final long... array ) {
		this.iconst( array.length ).newarray( long.class );

		for ( int i = 0; i < array.length; ++i ) {
			long value = array[ i ];
			if ( value != 0L ) {
				this.dup().
					iconst( i ).
					lconst( value ).
					lastore();
			}
		}

		return this;
	}

	@SyntheticOp( stackResultTypes=double[].class )
	public final JvmCodeWriter darray( final double... array ) {
		this.iconst( array.length ).newarray( double.class );

		for ( int i = 0; i < array.length; ++i ) {
			double value = array[ i ];
			if ( value != 0D ) {
				this.dup().
					iconst( i ).
					dconst( value ).
					dastore();
			}
		}

		return this;
	}

	@JvmOp( anewarray.class )
	public final JvmCodeWriter anewarray( final Type componentType ) {
		this.coreWriter.anewarray( componentType );
		return this;
	}
	
	@JvmOp( arraylength.class )
	public final JvmCodeWriter arraylength() {
		this.coreWriter.arraylength();
		return this;
	}

	@JvmOp( athrow.class )
	public final JvmCodeWriter athrow() {
		this.coreWriter.athrow();
		return this;
	}

	@JvmOp( checkcast.class )
	public final JvmCodeWriter checkcast( final Type type ) {
		this.coreWriter.checkcast( type );
		return this;
	}
	
	@JvmOp( instanceof_.class )
	public final JvmCodeWriter instanceof_( final Type type ) {
		this.coreWriter.instanceof_( type );
		return this;
	}

	@JvmOp( monitorenter.class )
	public final JvmCodeWriter monitorenter() {
		this.coreWriter.monitorenter();
		return this;
	}

	@JvmOp( monitorexit.class )
	public final JvmCodeWriter monitorexit() {
		this.coreWriter.monitorexit();
		return this;
	}

	final JvmCodeWriter wide() {
		// TODO: wide
		return null;
	}

	@JvmOp( multianewarray.class )
	public final JvmCodeWriter multianewarray(
		final Type arrayType,
		final int numDimensions )
	{
		this.coreWriter.multianewarray( arrayType, numDimensions );
		return this;
	}

	@WrapOp( ifnull.class )
	public final JvmCodeWriter ifnull( final String label ) {
		this.coreWriter.ifnull( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( ifnonnull.class )
	public final JvmCodeWriter ifnonnull( final String label ) {
		this.coreWriter.ifnonnull( this.jumpTo( label ) );
		return this;
	}

	final JvmCodeWriter goto_w() {
		// TODO: goto_w
		return null;
	}

	final JvmCodeWriter jsr_w() {
		// TODO: jsr_w
		return null;
	}

	@SyntheticOp( stackOperandTypes=boolean.class, stackResultTypes=boolean.class )
	public final JvmCodeWriter inot() {
		this.coreWriter.ifeq( this.jumpRelative( +7 ) ).
			iconst_0().
			goto_( this.jumpRelative( +4 ) ).
			iconst_1();
		
		return this;
	}

	@SyntheticOp( stackOperandTypes=Primitive.class, stackResultTypes=Reference.class )
	public final JvmCodeWriter box( final Class< ? > primitiveClass ) {
		if ( JavaTypes.isObjectType( primitiveClass ) ) {
			return this;
		}

		Class< ? > boxClass = JavaTypes.getObjectType( primitiveClass );

		return this.invokestatic(
			boxClass,
			Jak.method( boxClass, "valueOf", primitiveClass ) );
	}

	@SyntheticOp( stackOperandTypes=Reference.class, stackResultTypes=Primitive.class )
	public final JvmCodeWriter unbox( final Type boxClass ) {
		if (JavaTypes.isPrimitiveType( boxClass ) ) {
			return this;
		}

		Class<?> primitiveClass = JavaTypes.getPrimitiveType( boxClass );
		String methodName = primitiveClass.getCanonicalName() + "Value";
		
		return this.invokevirtual(
			boxClass,
			Jak.method( primitiveClass, methodName ) );
	}

	@SyntheticOp
	public final JvmCodeWriter label( final String labelName ) {
		this.labels.put( labelName, this.coreWriter.pos() );
		return this;
	}
	
	private final int getLabelPos( final String labelName ) {
		Integer pos = this.labels.get( labelName );
		if ( pos == null ) {
			throw new IllegalStateException( "Undefined label: " + labelName );
		} else {
			return pos;
		}
	}

	@SyntheticOp( id="catch" )
	public final JvmCodeWriter catch_(
		final @Symbol String startLabel,
		final @Symbol String endLabel,
		final Class< ? extends Throwable > throwableClass )
	{
		this.coreWriter.handleException(
			new CatchExceptionHandler(
				startLabel,
				endLabel,
				throwableClass,
				this.coreWriter.pos() ) );
		return this;
	}

	@SyntheticOp( id="exception" )
	public final JvmCodeWriter exception(
		final @Symbol String startLabel,
		final @Symbol String endLabel,
		final Class<? extends Throwable> throwableClass,
		final @Symbol String handlerLabel )
	{
		this.coreWriter.handleException(
			new LabelBasedExceptionHandler(
				startLabel,
				endLabel,
				throwableClass,
				handlerLabel ) );
		return this;
	}

	public final boolean isVarDefined( final String var ) {
		return this.localVars.containsKey( var );
	}

	public final boolean isLabelDefined( final String label ) {
		return this.labels.containsKey( label );
	}
	
	public final JvmStack stackMonitor() {
		return this.coreWriter.stackMonitor();
	}
	
	public final JvmLocals localsMonitor() {
		return this.coreWriter.localsMonitor();
	}
	
	private final ConstantPool constantPool() {
		return this.typeWriter.constantPool();
	}

	private final Type thisType() {
		return this.typeWriter.thisType();
	}

	private final Type superType() {
		return this.typeWriter.superType();
	}

	private final Jump jumpTo( final String label ) {
		return new Jump() {
			@Override
			public final Integer pos() {
				return JvmCodeWriter.this.labels.get( label );
			}
			
			@Override
			public final String toString() {
				return "Jump To Label: " + label;
			}
		};
	}
	
	private final Jump jumpRelative( final int relativeOffset ) {
		//Relative jump is tied to if<cond> or goto which is 1-byte back from the current position.
		final int pos = this.coreWriter.pos() + relativeOffset;
		return new Jump() {
			@Override
			public final Integer pos() {
				return pos;
			}
			
			@Override
			public final String toString() {
				return "Relative Jump: " + relativeOffset;
			}
		};
	}

	private final int getOrReserveVarSlot(
		final String varName,
		final Type type )
	{
		Integer existingSlot = this.localVars.get( varName );
		if ( existingSlot == null ) {
			int newSlot = this.coreWriter.localsMonitor().maxLocals();
			this.localVars.put( varName, newSlot );
			return newSlot;
		} else {
			return existingSlot;
		}
	}
	
	private static final boolean isSingleByte( final ConstantEntry entry ) {
		return isSingleByte( entry.index() );
	}

	private static final boolean isSingleByte( final int value ) {
		return ( value < 256 );
	}	

	private static final boolean isInterface( final Method method ) {
		return method.getDeclaringClass().isInterface();
	}

	private static final boolean isStatic( final Method method ) {
		return Modifier.isStatic( method.getModifiers() );
	}

	private static final boolean isPrivate( final Method method ) {
		return Modifier.isPrivate( method.getModifiers() );
	}

	private final class CatchExceptionHandler extends ExceptionHandler {
		private final String startLabel;
		private final String endLabel;
		private final Class<? extends Throwable> throwableClass;
		private final int handlerPos;

		CatchExceptionHandler(
			final String startLabel,
			final String endLabel,
			final Class< ? extends Throwable > throwableClass,
			final int handlerPos )
		{
			this.startLabel = startLabel;
			this.endLabel = endLabel;
			this.throwableClass = throwableClass;
			this.handlerPos = handlerPos;
		}

		@Override
		public final int startPos() {
			return JvmCodeWriter.this.getLabelPos( this.startLabel );
		}

		@Override
		public final int endPos() {
			return JvmCodeWriter.this.getLabelPos( this.endLabel );
		}

		@Override
		public final Class<? extends Throwable> throwableClass() {
			return this.throwableClass;
		}

		@Override
		public final int handlerPos() {
			return this.handlerPos;
		}
	}

	private final class LabelBasedExceptionHandler extends ExceptionHandler {
		private final String startLabel;
		private final String endLabel;
		private final Class< ? extends Throwable > throwableClass;
		private final String handlerLabel;

		LabelBasedExceptionHandler(
			final String startLabel,
			final String endLabel,
			final Class< ? extends Throwable > throwableClass,
			final String handlerLabel)
		{
			this.startLabel = startLabel;
			this.endLabel = endLabel;
			this.throwableClass = throwableClass;
			this.handlerLabel = handlerLabel;
		}

		@Override
		public final int startPos() {
			return JvmCodeWriter.this.getLabelPos( this.startLabel );
		}

		@Override
		public final int endPos() {
			return JvmCodeWriter.this.getLabelPos( this.endLabel );
		}

		@Override
		public final Class<? extends Throwable> throwableClass() {
			return this.throwableClass;
		}

		@Override
		public final int handlerPos() {
			return JvmCodeWriter.this.getLabelPos( this.handlerLabel );
		}
	}
}
