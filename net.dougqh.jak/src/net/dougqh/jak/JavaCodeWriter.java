package net.dougqh.jak;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;

import net.dougqh.jak.JavaCoreCodeWriter.ExceptionHandler;
import net.dougqh.jak.JavaCoreCodeWriter.Jump;
import net.dougqh.jak.annotations.Op;
import net.dougqh.jak.annotations.SyntheticOp;
import net.dougqh.jak.annotations.WrapOp;
import net.dougqh.jak.operations.*;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.ArgList;
import net.dougqh.jak.types.Primitive;
import net.dougqh.jak.types.Reference;
import net.dougqh.java.meta.types.JavaTypes;

public final class JavaCodeWriter {
	private static final String TYPE = "TYPE";
	
	private final TypeWriter typeWriter;
	private final JavaCoreCodeWriter coreWriter;

	private final HashMap< String, Integer > localVars = new HashMap< String, Integer >( 8 );
	private final HashMap< String, Integer > labels = new HashMap< String, Integer>( 4 );
	
	JavaCodeWriter(
		final TypeWriter typeWriter,
		final boolean isStatic,
		final FormalArguments arguments,
		final JavaCoreCodeWriter coreWriter )
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
			curArgPos += JavaCoreCodeWriterImpl.size( var.getType() );
		}

		this.coreWriter = coreWriter;
	}
	
	public final JavaCoreCodeWriter coreWriter() {
		return this.coreWriter;
	}

	@Op( Nop.class )
	public final JavaCodeWriter nop() {
		this.coreWriter.nop();
		return this;
	}

	@SyntheticOp( stackResultTypes=Class.class )
	public final JavaCodeWriter aconst( final Class< ? > aClass ) {
		if ( aClass.equals( void.class ) ) {
			return this.getstatic(
				Void.class,
				JavaAssembler.field( Class.class, TYPE ) );
		} else if ( aClass.equals( boolean.class ) ) {
			return this.getstatic(
				Boolean.class,
				JavaAssembler.field( Class.class, TYPE ) );
		} else if ( aClass.equals( byte.class ) ) {
			return this.getstatic(
				Byte.class,
				JavaAssembler.field( Class.class, TYPE ) );
		} else if ( aClass.equals( char.class ) ) {
			return this.getstatic(
				Character.class,
				JavaAssembler.field( Class.class, TYPE ) );
		} else if ( aClass.equals( short.class ) ) {
			return this.getstatic(
				Short.class,
				JavaAssembler.field( Class.class, TYPE ) );
		} else if ( aClass.equals( int.class ) ) {
			return this.getstatic(
				Integer.class,
				JavaAssembler.field( Class.class, TYPE ) );
		} else if ( aClass.equals( long.class ) ) {
			return this.getstatic(
				Long.class,
				JavaAssembler.field( Class.class, TYPE ) );
		} else if ( aClass.equals( float.class ) ) {
			return this.getstatic(
				Float.class,
				JavaAssembler.field( Class.class, TYPE ) );
		} else if ( aClass.equals( double.class ) ) {
			return this.getstatic(
				Double.class,
				JavaAssembler.field( Class.class, TYPE ) );
		} else {
			return this.ldc( aClass );
		}
	}

	@Op( Aconst_null.class )
	public final JavaCodeWriter aconst_null() {
		this.coreWriter.aconst_null();
		return this;
	}

	@SyntheticOp( stackResultTypes=boolean.class )
	public final JavaCodeWriter iconst( final boolean value ) {
		if ( value ) {
			return this.iconst_1();
		} else {
			return this.iconst_0();
		}
	}

	@SyntheticOp( stackResultTypes=byte.class )
	public final JavaCodeWriter iconst( final byte value ) {
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

	@SyntheticOp( stackResultTypes=short.class )
	public final JavaCodeWriter iconst( final short value ) {
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

	@SyntheticOp( stackResultTypes=char.class )
	public final JavaCodeWriter iconst( final char value ) {
		return this.iconst( (int)value );
	}

	@SyntheticOp( stackResultTypes=int.class )
	public final JavaCodeWriter iconst( final int value ) {
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

	@Op( Iconst_m1.class )
	public final JavaCodeWriter iconst_m1() {
		this.coreWriter.iconst_m1();
		return this;
	}

	@Op( Iconst_0.class )
	public final JavaCodeWriter iconst_0() {
		this.coreWriter.iconst_0();
		return this;
	}

	@Op( Iconst_1.class )
	public final JavaCodeWriter iconst_1() {
		this.coreWriter.iconst_1();
		return this;
	}

	@Op( Iconst_2.class )
	public final JavaCodeWriter iconst_2() {
		this.coreWriter.iconst_2();
		return this;
	}

	@Op( Iconst_3.class )
	public final JavaCodeWriter iconst_3() {
		this.coreWriter.iconst_3();
		return this;
	}

	@Op( Iconst_4.class )
	public final JavaCodeWriter iconst_4() {
		this.coreWriter.iconst_4();
		return this;
	}

	@Op( Iconst_5.class )
	public final JavaCodeWriter iconst_5() {
		this.coreWriter.iconst_5();
		return this;
	}

	@SyntheticOp( stackResultTypes=long.class )
	public final JavaCodeWriter lconst( final long value ) {
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

	@Op( Lconst_0.class )
	public final JavaCodeWriter lconst_0() {
		this.coreWriter.lconst_0();
		return this;
	}

	@Op( Lconst_1.class )
	public final JavaCodeWriter lconst_1() {
		this.coreWriter.lconst_1();
		return this;
	}

	@SyntheticOp( stackResultTypes=float.class )
	public final JavaCodeWriter fconst( final float value ) {
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

	@Op( Fconst_0.class )
	public final JavaCodeWriter fconst_0() {
		this.coreWriter.fconst_0();
		return this;
	}

	@Op( Fconst_1.class )
	public final JavaCodeWriter fconst_1() {
		this.coreWriter.fconst_1();
		return this;
	}

	@Op( Fconst_2.class )
	public final JavaCodeWriter fconst_2() {
		this.coreWriter.fconst_2();
		return this;
	}

	@SyntheticOp( stackResultTypes=double.class )
	public final JavaCodeWriter dconst( final double value ) {
		if ( value == 0D ) {
			return this.dconst_0();
		} else if ( value == 1D ) {
			return this.dconst_1();
		} else {
			return this.ldc2_w( value );
		}
	}

	@Op( Dconst_0.class )
	public final JavaCodeWriter dconst_0() {
		this.coreWriter.dconst_0();
		return this;
	}

	@Op( Dconst_1.class )
	public final JavaCodeWriter dconst_1() {
		this.coreWriter.dconst_1();
		return this;
	}

	@Op( Bipush.class )
	public final JavaCodeWriter bipush( final byte value ) {
		this.coreWriter.bipush( value );
		return this;
	}

	@Op( Sipush.class )
	public final JavaCodeWriter sipush( final short value ) {
		this.coreWriter.sipush( value );
		return this;
	}

	@WrapOp( value=Ldc.class, stackResultTypes=Class.class )
	public final JavaCodeWriter ldc( final Class< ? > aClass ) {
		return this.smartLdc( this.constantPool().addClassInfo( aClass ) );
	}

	@WrapOp( value=Ldc.class, stackResultTypes=int.class )
	public final JavaCodeWriter ldc( final int value ) {
		return this.smartLdc( this.constantPool().addIntegerConstant( value ) );
	}

	@WrapOp( value=Ldc.class, stackResultTypes=float.class )
	public final JavaCodeWriter ldc( final float value ) {
		return this.smartLdc( this.constantPool().addFloatConstant( value ) );
	}

	@WrapOp( value=Ldc.class, stackResultTypes=String.class )
	public final JavaCodeWriter ldc( final CharSequence value ) {
		return this.smartLdc( this.constantPool().addStringConstant( value ) );
	}

	final JavaCodeWriter smartLdc( final ConstantEntry entry ) {
		if ( isSingleByte( entry ) ) {
			this.ldc_( entry );
		} else {
			this.ldc_w( entry );
		}
		return this;
	}

	final JavaCodeWriter ldc_( final ConstantEntry entry ) {
		this.coreWriter.ldc( entry );
		return this;
	}

	final JavaCodeWriter ldc_w( final ConstantEntry entry ) {
		this.coreWriter.ldc_w( entry );
		return this;
	}

	@WrapOp( value=Ldc2_w.class, stackResultTypes=long.class )
	public final JavaCodeWriter ldc2_w( final long value ) {
		this.coreWriter.ldc2_w( this.constantPool().addLongConstant( value ) );
		return this;
	}

	@WrapOp( value=Ldc2_w.class, stackResultTypes=double.class )
	public final JavaCodeWriter ldc2_w( final double value ) {
		this.coreWriter.ldc2_w( this.constantPool().addDoubleConstant( value ) );
		return this;
	}
	
	@WrapOp( Iload.class )
	public final JavaCodeWriter iload( final String var ) {
		return this.iload( this.getOrReserveVarSlot( var, int.class ) );
	}

	@WrapOp( Iload.class )
	public final JavaCodeWriter iload( final int slot ) {
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

	@Op( Iload_0.class )
	public final JavaCodeWriter iload_0() {
		this.coreWriter.iload_0();
		return this;
	}

	@Op( Iload_1.class )
	public final JavaCodeWriter iload_1() {
		this.coreWriter.iload_1();
		return this;
	}

	@Op( Iload_2.class )
	public final JavaCodeWriter iload_2() {
		this.coreWriter.iload_2();
		return this;
	}

	@Op( Iload_3.class )
	public final JavaCodeWriter iload_3() {
		this.coreWriter.iload_3();
		return this;
	}

	@Op( Lload.class )
	public final JavaCodeWriter lload( final String var ) {
		return this.lload( this.getOrReserveVarSlot( var, long.class ) );
	}

	@WrapOp( Lload.class )
	public final JavaCodeWriter lload( final int slot ) {
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

	@Op( Lload_0.class )
	public final JavaCodeWriter lload_0() {
		this.coreWriter.lload_0();
		return this;
	}

	@Op( Lload_1.class )
	public final JavaCodeWriter lload_1() {
		this.coreWriter.lload_1();
		return this;
	}

	@Op( Lload_2.class )
	public final JavaCodeWriter lload_2() {
		this.coreWriter.lload_2();
		return this;
	}

	@Op( Lload_3.class )
	public final JavaCodeWriter lload_3() {
		this.coreWriter.lload_3();
		return this;
	}
	
	@Op( Fload.class )
	public final JavaCodeWriter fload( final String var ) {
		return this.fload( this.getOrReserveVarSlot( var, float.class ) );
	}

	@WrapOp( Fload.class )
	public final JavaCodeWriter fload( final int slot ) {
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

	@Op( Fload_0.class )
	public final JavaCodeWriter fload_0() {
		this.coreWriter.fload_0();
		return this;
	}

	@Op( Fload_1.class )
	public final JavaCodeWriter fload_1() {
		this.coreWriter.fload_1();
		return this;
	}

	@Op( Fload_2.class )
	public final JavaCodeWriter fload_2() {
		this.coreWriter.fload_2();
		return this;
	}

	@Op( Fload_3.class )
	public final JavaCodeWriter fload_3() {
		this.coreWriter.fload_3();
		return this;
	}

	@WrapOp( Dload.class )
	public final JavaCodeWriter dload( final String var ) {
		return this.dload( this.getOrReserveVarSlot( var, double.class ) );
	}

	@WrapOp( Dload.class )
	public final JavaCodeWriter dload( final int slot ) {
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

	@Op( Dload_0.class )
	public final JavaCodeWriter dload_0() {
		this.coreWriter.dload_0();
		return this;
	}

	@Op( Dload_1.class )
	public final JavaCodeWriter dload_1() {
		this.coreWriter.dload_1();
		return this;
	}

	@Op( Dload_2.class )
	public final JavaCodeWriter dload_2() {
		this.coreWriter.dload_2();
		return this;
	}

	@Op( Dload_3.class )
	public final JavaCodeWriter dload_3() {
		this.coreWriter.dload_3();
		return this;
	}

	@Op( Aload.class )
	public final JavaCodeWriter aload( final String var ) {
		return this.aload( this.getOrReserveVarSlot( var, Reference.class ) );
	}

	@WrapOp( Aload.class )
	public final JavaCodeWriter aload( final int slot ) {
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

	@WrapOp( Aload_0.class )
	public final JavaCodeWriter this_() {
		return this.aload_0();
	}

	@Op( Aload_0.class )
	public final JavaCodeWriter aload_0() {
		this.coreWriter.aload_0();
		return this;
	}

	@Op( Aload_1.class )
	public final JavaCodeWriter aload_1() {
		this.coreWriter.aload_1();
		return this;
	}

	@Op( Aload_2.class )
	public final JavaCodeWriter aload_2() {
		this.coreWriter.aload_2();
		return this;
	}

	@Op( Aload_3.class )
	public final JavaCodeWriter aload_3() {
		this.coreWriter.aload_3();
		return this;
	}

	@Op( Iaload.class )
	public final JavaCodeWriter iaload() {
		this.coreWriter.iaload();
		return this;
	}

	@Op( Laload.class )
	public final JavaCodeWriter laload() {
		this.coreWriter.laload();
		return this;
	}

	@Op( Faload.class )
	public final JavaCodeWriter faload() {
		this.coreWriter.faload();
		return this;
	}

	@Op( Daload.class )
	public final JavaCodeWriter daload() {
		this.coreWriter.daload();
		return this;
	}

	@Op( Aaload.class )
	public final JavaCodeWriter aaload() {
		this.coreWriter.aaload();
		return this;
	}

	@Op( Baload.class )
	public final JavaCodeWriter baload() {
		this.coreWriter.baload();
		return this;
	}

	@Op( Caload.class )
	public final JavaCodeWriter caload() {
		this.coreWriter.caload();
		return this;
	}

	@Op( Saload.class )
	public final JavaCodeWriter saload() {
		this.coreWriter.saload();
		return this;
	}

	@Op( Istore.class )
	public final JavaCodeWriter istore( final String var ) {
		return this.istore(this.getOrReserveVarSlot( var, int.class ) );
	}

	@WrapOp( Istore.class )
	public final JavaCodeWriter istore( final int index ) {
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

	@Op( Istore_0.class )
	public final JavaCodeWriter istore_0() {
		this.coreWriter.istore_0();
		return this;
	}

	@Op( Istore_1.class )
	public final JavaCodeWriter istore_1() {
		this.coreWriter.istore_1();
		return this;
	}

	@Op( Istore_2.class )
	public final JavaCodeWriter istore_2() {
		this.coreWriter.istore_2();
		return this;
	}

	@Op( Istore_3.class )
	public final JavaCodeWriter istore_3() {
		this.coreWriter.istore_3();
		return this;
	}

	@WrapOp( Lstore.class )
	public final JavaCodeWriter lstore( final String var ) {
		return this.lstore( this.getOrReserveVarSlot( var, long.class ) );
	}

	@WrapOp( Lstore.class )
	public final JavaCodeWriter lstore( final int slot ) {
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

	@Op( Lstore_0.class )
	public final JavaCodeWriter lstore_0() {
		this.coreWriter.lstore_0();
		return this;
	}

	@Op( Lstore_1.class )
	public final JavaCodeWriter lstore_1() {
		this.coreWriter.lstore_1();
		return this;
	}

	@Op( Lstore_2.class )
	public final JavaCodeWriter lstore_2() {
		this.coreWriter.lstore_2();
		return this;
	}

	@Op( Lstore_3.class )
	public final JavaCodeWriter lstore_3() {
		this.coreWriter.lstore_3();
		return this;
	}

	@WrapOp( Fstore.class )
	public final JavaCodeWriter fstore( final String var ) {
		return this.fstore( this.getOrReserveVarSlot( var, float.class ) );
	}

	@WrapOp( Fstore.class )
	public final JavaCodeWriter fstore( final int slot ) {
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

	@Op( Fstore_0.class )
	public final JavaCodeWriter fstore_0() {
		this.coreWriter.fstore_0();
		return this;
	}
	
	@Op( Fstore_1.class )
	public final JavaCodeWriter fstore_1() {
		this.coreWriter.fstore_1();
		return this;
	}

	@Op( Fstore_2.class )
	public final JavaCodeWriter fstore_2() {
		this.coreWriter.fstore_2();
		return this;
	}

	@Op( Fstore_3.class )
	public final JavaCodeWriter fstore_3() {
		this.coreWriter.fstore_3();
		return this;
	}

	@WrapOp( Dstore.class )
	public final JavaCodeWriter dstore( final String var ) {
		return this.dstore( this.getOrReserveVarSlot( var, double.class ) );
	}

	@WrapOp( Dstore.class )
	public final JavaCodeWriter dstore( final int slot ) {
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

	@Op( Dstore_0.class )
	public final JavaCodeWriter dstore_0() {
		this.coreWriter.dstore_0();
		return this;
	}

	@Op( Dstore_1.class )
	public final JavaCodeWriter dstore_1() {
		this.coreWriter.dstore_1();
		return this;
	}

	@Op( Dstore_2.class )
	public final JavaCodeWriter dstore_2() {
		this.coreWriter.dstore_2();
		return this;
	}

	@Op( Dstore_3.class )
	public final JavaCodeWriter dstore_3() {
		this.coreWriter.dstore_3();
		return this;
	}

	@WrapOp( Astore.class )
	public final JavaCodeWriter astore( final String var ) {
		return this.astore( this.getOrReserveVarSlot( var, Reference.class ) );
	}

	@WrapOp( Astore.class )
	public final JavaCodeWriter astore( final int slot ) {
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

	@Op( Astore_0.class )
	public final JavaCodeWriter astore_0() {
		this.coreWriter.astore_0();
		return this;
	}

	@Op( Astore_1.class )
	public final JavaCodeWriter astore_1() {
		this.coreWriter.astore_1();
		return this;
	}

	@Op( Astore_2.class )
	public final JavaCodeWriter astore_2() {
		this.coreWriter.astore_2();
		return this;
	}

	@Op( Astore_3.class )
	public final JavaCodeWriter astore_3() {
		this.coreWriter.astore_3();
		return this;
	}

	@Op( Iastore.class )
	public final JavaCodeWriter iastore() {
		this.coreWriter.iastore();
		return this;
	}

	@Op( Lastore.class )
	public final JavaCodeWriter lastore() {
		this.coreWriter.lastore();
		return this;
	}

	@Op( Fastore.class )
	public final JavaCodeWriter fastore() {
		this.coreWriter.fastore();
		return this;
	}

	@Op( Dastore.class )
	public final JavaCodeWriter dastore() {
		this.coreWriter.dastore();
		return this;
	}

	@Op( Aastore.class )
	public final JavaCodeWriter aastore() {
		this.coreWriter.aastore();
		return this;
	}

	@Op( Bastore.class )
	public final JavaCodeWriter bastore() {
		this.coreWriter.bastore();
		return this;
	}

	@Op( Castore.class )
	public final JavaCodeWriter castore() {
		this.coreWriter.castore();
		return this;
	}

	@Op( Sastore.class )
	public final JavaCodeWriter sastore() {
		this.coreWriter.sastore();
		return this;
	}
	
	@Op( Pop.class )
	public final JavaCodeWriter pop() {
		this.coreWriter.pop();
		return this;
	}

	@Op( Pop2.class )
	public final JavaCodeWriter pop2() {
		this.coreWriter.pop2();
		return this;
	}

	@Op( Dup.class )
	public final JavaCodeWriter dup() {
		this.coreWriter.dup();
		return this;
	}

	@Op( Dup_x1.class )
	public final JavaCodeWriter dup_x1() {
		this.coreWriter.dup_x1();
		return this;
	}

	@Op( Dup_x2.class )
	public final JavaCodeWriter dup_x2() {
		this.coreWriter.dup_x2();
		return this;
	}

	@Op( Dup2.class )
	public final JavaCodeWriter dup2() {
		this.coreWriter.dup2();
		return this;
	}

	@Op( Dup2_x1.class )
	public final JavaCodeWriter dup2_x1() {
		this.coreWriter.dup2_x1();
		return this;
	}

	@Op( Dup2_x2.class )
	public final JavaCodeWriter dup2_x2() {
		this.coreWriter.dup2_x2();
		return this;
	}

	@Op( Swap.class )
	public final JavaCodeWriter swap() {
		this.coreWriter.swap();
		return this;
	}

	@Op( Iadd.class )
	public final JavaCodeWriter iadd() {
		this.coreWriter.iadd();
		return this;
	}

	@Op( Ladd.class )
	public final JavaCodeWriter ladd() {
		this.coreWriter.ladd();
		return this;
	}

	@Op( Fadd.class )
	public final JavaCodeWriter fadd() {
		this.coreWriter.fadd();
		return this;
	}

	@Op( Dadd.class )
	public final JavaCodeWriter dadd() {
		this.coreWriter.dadd();
		return this;
	}

	@Op( Isub.class )
	public final JavaCodeWriter isub() {
		this.coreWriter.isub();
		return this;
	}

	@Op( Lsub.class )
	public final JavaCodeWriter lsub() {
		this.coreWriter.lsub();
		return this;
	}

	@Op( Fsub.class )
	public final JavaCodeWriter fsub() {
		this.coreWriter.fsub();
		return this;
	}

	@Op( Dsub.class )
	public final JavaCodeWriter dsub() {
		this.coreWriter.dsub();
		return this;
	}

	@Op( Imul.class )
	public final JavaCodeWriter imul() {
		this.coreWriter.imul();
		return this;
	}

	@Op( Lmul.class )
	public final JavaCodeWriter lmul() {
		this.coreWriter.lmul();
		return this;
	}

	@Op( Fmul.class )
	public final JavaCodeWriter fmul() {
		this.coreWriter.fmul();
		return this;
	}

	@Op( Dmul.class )
	public final JavaCodeWriter dmul() {
		this.coreWriter.dmul();
		return this;
	}

	@Op( Idiv.class )
	public final JavaCodeWriter idiv() {
		this.coreWriter.idiv();
		return this;
	}

	@Op( Ldiv.class )
	public final JavaCodeWriter ldiv() {
		this.coreWriter.ldiv();
		return this;
	}

	@Op( Fdiv.class )
	public final JavaCodeWriter fdiv() {
		this.coreWriter.fdiv();
		return this;
	}

	@Op( Ddiv.class )
	public final JavaCodeWriter ddiv() {
		this.coreWriter.ddiv();
		return this;
	}

	@Op( Irem.class )
	public final JavaCodeWriter irem() {
		this.coreWriter.irem();
		return this;
	}

	@Op( Lrem.class )
	public final JavaCodeWriter lrem() {
		this.coreWriter.lrem();
		return this;
	}

	@Op( Frem.class )
	public final JavaCodeWriter frem() {
		this.coreWriter.frem();
		return this;
	}

	@Op( Drem.class )
	public final JavaCodeWriter drem() {
		this.coreWriter.drem();
		return this;
	}

	@Op( Ineg.class )
	public final JavaCodeWriter ineg() {
		this.coreWriter.ineg();
		return this;
	}

	@Op( Lneg.class )
	public final JavaCodeWriter lneg() {
		this.coreWriter.lneg();
		return this;
	}

	@Op( Fneg.class )
	public final JavaCodeWriter fneg() {
		this.coreWriter.fneg();
		return this;
	}

	@Op( Dneg.class )
	public final JavaCodeWriter dneg() {
		this.coreWriter.dneg();
		return this;
	}

	@Op( Ishl.class )
	public final JavaCodeWriter ishl() {
		this.coreWriter.ishl();
		return this;
	}

	@Op( Lshl.class )
	public final JavaCodeWriter lshl() {
		this.coreWriter.lshl();
		return this;
	}

	@Op( Ishr.class )
	public final JavaCodeWriter ishr() {
		this.coreWriter.ishr();
		return this;
	}

	@Op( Lshr.class )
	public final JavaCodeWriter lshr() {
		this.coreWriter.lshr();
		return this;
	}

	@Op( Iushr.class )
	public final JavaCodeWriter iushr() {
		this.coreWriter.iushr();
		return this;
	}

	@Op( Lushr.class )
	public final JavaCodeWriter lushr() {
		this.coreWriter.lushr();
		return this;
	}

	@Op( Iand.class )
	public final JavaCodeWriter iand() {
		this.coreWriter.iand();
		return this;
	}

	@Op( Land.class )
	public final JavaCodeWriter land() {
		this.coreWriter.land();
		return this;
	}

	@Op( Ior.class )
	public final JavaCodeWriter ior() {
		this.coreWriter.ior();
		return this;
	}

	@Op( Lor.class )
	public final JavaCodeWriter lor() {
		this.coreWriter.lor();
		return this;
	}

	@Op( Ixor.class )
	public final JavaCodeWriter ixor() {
		this.coreWriter.ixor();
		return this;
	}

	@Op( Lxor.class )
	public final JavaCodeWriter lxor() {
		this.coreWriter.lxor();
		return this;
	}

	@WrapOp( Iinc.class )
	public final JavaCodeWriter iinc( final String var ) {
		return this.iinc( var, 1 );
	}

	@WrapOp( Iinc.class )
	public final JavaCodeWriter iinc( final String var, final int amount ) {
		return this.iinc( this.getOrReserveVarSlot( var, int.class ), amount );
	}

	@WrapOp( Iinc.class )
	public final JavaCodeWriter iinc( final int slot ) {
		return this.iinc( slot, 1 );
	}

	@Op( Iinc.class )
	public final JavaCodeWriter iinc( final int slot, final int amount ) {
		this.coreWriter.iinc( slot, amount );
		return this;
	}

	@Op( I2l.class )
	public final JavaCodeWriter i2l() {
		this.coreWriter.i2l();
		return this;
	}

	@Op( I2f.class )
	public final JavaCodeWriter i2f() {
		this.coreWriter.i2f();
		return this;
	}

	@Op( I2d.class )
	public final JavaCodeWriter i2d() {
		this.coreWriter.i2d();
		return this;
	}

	@Op( L2i.class )
	public final JavaCodeWriter l2i() {
		this.coreWriter.l2i();
		return this;
	}

	@Op( L2f.class )
	public final JavaCodeWriter l2f() {
		this.coreWriter.l2f();
		return this;
	}

	@Op( L2d.class )
	public final JavaCodeWriter l2d() {
		this.coreWriter.l2d();
		return this;
	}

	@Op( F2i.class )
	public final JavaCodeWriter f2i() {
		this.coreWriter.f2i();
		return this;
	}

	@Op( F2l.class )
	public final JavaCodeWriter f2l() {
		this.coreWriter.f2l();
		return this;
	}

	@Op( F2d.class )
	public final JavaCodeWriter f2d() {
		this.coreWriter.f2d();
		return this;
	}

	@Op( D2i.class )
	public final JavaCodeWriter d2i() {
		this.coreWriter.d2i();
		return this;
	}

	@Op( D2l.class )
	public final JavaCodeWriter d2l() {
		this.coreWriter.d2l();
		return this;
	}

	@Op( D2f.class )
	public final JavaCodeWriter d2f() {
		this.coreWriter.d2f();
		return this;
	}

	@Op( I2b.class )
	public final JavaCodeWriter i2b() {
		this.coreWriter.i2b();
		return this;
	}

	@Op( I2c.class )
	public final JavaCodeWriter i2c() {
		this.coreWriter.i2c();
		return this;
	}

	@Op( I2s.class )
	public final JavaCodeWriter i2s() {
		this.coreWriter.i2s();
		return this;
	}

	@Op( Lcmp.class )
	public final JavaCodeWriter lcmp() {
		this.coreWriter.lcmp();
		return this;
	}

	@Op( Fcmpl.class )
	public final JavaCodeWriter fcmpl() {
		this.coreWriter.fcmpl();
		return this;
	}

	@Op( Fcmpg.class )
	public final JavaCodeWriter fcmpg() {
		this.coreWriter.fcmpg();
		return this;
	}

	@Op( Dcmpl.class )
	public final JavaCodeWriter dcmpl() {
		this.coreWriter.dcmpl();
		return this;
	}

	@Op( Dcmpg.class )
	public final JavaCodeWriter dcmpg() {
		this.coreWriter.dcmpg();
		return this;
	}

	@WrapOp( Ifeq.class )
	public final JavaCodeWriter ifeq( final String label ) {
		this.coreWriter.ifeq( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( Ifne.class )
	public final JavaCodeWriter ifne( final String label ) {
		this.coreWriter.ifne( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( Iflt.class )
	public final JavaCodeWriter iflt( final String label ) {
		this.coreWriter.iflt( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( Ifgt.class )
	public final JavaCodeWriter ifgt( final String label ) {
		this.coreWriter.ifgt( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( Ifge.class )
	public final JavaCodeWriter ifge( final String label ) {
		this.coreWriter.ifge( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( Ifle.class )
	public final JavaCodeWriter ifle( final String label ) {
		this.coreWriter.ifle( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( If_icmpeq.class )
	public final JavaCodeWriter if_icmpeq( final String label ) {
		this.coreWriter.if_icmpeq( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( If_icmpne.class )
	public final JavaCodeWriter if_icmpne( final String label ) {
		this.coreWriter.if_icmpne( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( If_icmplt.class )
	public final JavaCodeWriter if_icmplt( final String label ) {
		this.coreWriter.if_icmplt( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( If_icmpgt.class )
	public final JavaCodeWriter if_icmpgt( final String label ) {
		this.coreWriter.if_icmpgt( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( If_icmpge.class )
	public final JavaCodeWriter if_icmpge( final String label ) {
		this.coreWriter.if_icmpge( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( If_icmple.class )
	public final JavaCodeWriter if_icmple( final String label ) {
		this.coreWriter.if_icmple( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( If_acmpeq.class )
	public final JavaCodeWriter if_acmpeq( final String label ) {
		this.coreWriter.if_acmpeq( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( If_acmpne.class )
	public final JavaCodeWriter if_acmpne( final String label ) {
		this.coreWriter.if_acmpne( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( Goto.class )
	public final JavaCodeWriter goto_( final String label ) {
		this.coreWriter.goto_( this.jumpTo( label ) );
		return this;
	}

	final JavaCodeWriter jsr() {
		// TODO: jsr
		return null;
	}

	final JavaCodeWriter ret() {
		// TODO: ret
		return null;
	}

	final JavaCodeWriter tableswitch() {
		// TODO: tableswitch
		return null;
	}

	final JavaCodeWriter lookupswitch() {
		// TODO: lookupswitch
		return null;
	}

	@Op( Ireturn.class )
	public final JavaCodeWriter ireturn() {
		this.coreWriter.ireturn();
		return this;
	}

	@Op( Lreturn.class )
	public final JavaCodeWriter lreturn() {
		this.coreWriter.lreturn();
		return this;
	}

	@Op( Freturn.class )
	public final JavaCodeWriter freturn() {
		this.coreWriter.freturn();
		return this;
	}

	@Op( Dreturn.class )
	public final JavaCodeWriter dreturn() {
		this.coreWriter.dreturn();
		return this;
	}

	@Op( Areturn.class )
	public final JavaCodeWriter areturn() {
		this.coreWriter.areturn();
		return this;
	}

	@Op( Return.class )
	public final JavaCodeWriter return_() {
		this.coreWriter.return_();
		return this;
	}

	@WrapOp( Getstatic.class )
	public final JavaCodeWriter this_getstatic( final JavaFieldDescriptor field ) {
		return this.getstatic( this.thisType(), field );
	}

	@WrapOp( Getstatic.class )
	public final JavaCodeWriter getstatic(
		final Type targetType,
		final String fieldName )
	{
		return this.getstatic( getField( targetType, fieldName ) );
	}

	@Op( Getstatic.class )
	public final JavaCodeWriter getstatic(
		final Type targetType,
		final JavaFieldDescriptor field )
	{
		this.coreWriter.getstatic( targetType, field );
		return this;
	}

	@WrapOp( Getstatic.class )
	public final JavaCodeWriter getstatic( final Field field ) {
		this.coreWriter.getstatic(
			field.getDeclaringClass(),
			JavaAssembler.field( field ) );
		return this;
	}

	@WrapOp( Putstatic.class )
	public final JavaCodeWriter putstatic(
		final Type targetType,
		final String fieldName )
	{
		return this.putstatic( getField( targetType, fieldName ) );
	}

	@WrapOp( Putstatic.class )
	public final JavaCodeWriter this_putstatic( final JavaFieldDescriptor field ) {
		return this.putstatic( this.thisType(), field );
	}

	@Op( Putstatic.class )
	public final JavaCodeWriter putstatic(
		final Type targetType,
		final JavaFieldDescriptor field )
	{
		this.coreWriter.putstatic( targetType, field );
		return this;
	}

	@WrapOp( Putstatic.class )
	public final JavaCodeWriter putstatic( final Field field ) {
		this.coreWriter.putstatic(
			field.getDeclaringClass(),
			JavaAssembler.field( field ) );
		return this;
	}

	@WrapOp( Getfield.class )
	public final JavaCodeWriter getfield(
		final Type targetType,
		final String fieldName )
	{
		return this.getfield( getField( targetType, fieldName ) );
	}

	@WrapOp( Getfield.class )
	public final JavaCodeWriter this_getfield( final JavaFieldDescriptor field ) {
		return this.getfield( this.thisType(), field );
	}

	@Op( Getfield.class )
	public final JavaCodeWriter getfield(
		final Type targetType,
		final JavaFieldDescriptor field )
	{
		this.coreWriter.getfield( targetType, field );
		return this;
	}

	@WrapOp( Getfield.class )
	public final JavaCodeWriter getfield( final Field field ) {
		this.coreWriter.getfield(
			field.getDeclaringClass(),
			JavaAssembler.field( field ) );
		
		return this;
	}

	@WrapOp( Putfield.class )
	public final JavaCodeWriter putfield(
		final Type targetType,
		final String fieldName )
	{
		return this.putfield( getField( targetType, fieldName ) );
	}

	@WrapOp( Putfield.class )
	public final JavaCodeWriter this_putfield( final JavaFieldDescriptor field ) {
		return this.putfield( this.thisType(), field );
	}

	@Op( Putfield.class )
	public final JavaCodeWriter putfield(
		final Type targetType,
		final JavaFieldDescriptor field )
	{
		this.coreWriter.putfield(
			targetType,
			JavaAssembler.field( field.getType(), field.getName() ) );
		return this;
	}

	@WrapOp( Putfield.class )
	public final JavaCodeWriter putfield( final Field field ) {
		this.coreWriter.putfield(
			field.getDeclaringClass(),
			JavaAssembler.field( field.getType(), field.getName() ) );
		return this;
	}

	@WrapOp( Getfield.class )
	private static final Field getField(
		final Type targetType,
		final String fieldName )
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
	public final JavaCodeWriter invoke(
		final Type targetType,
		final String methodName,
		final Type... args )
	{
		return this.invoke( targetType, JavaAssembler.method( methodName, args ) );
	}

	@SyntheticOp( stackOperandTypes={ Any.class, ArgList.class }, stackResultTypes=Any.class )
	public final JavaCodeWriter invoke(
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
	public final JavaCodeWriter invoke( final Method method ) {
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
	
	@WrapOp( Invokevirtual.class )
	public final JavaCodeWriter invokevirtual( final Method method ) {
		return this.invokevirtual(
			method.getDeclaringClass(),
			JavaAssembler.method( method ) );
	}
	
	@Op( Invokeinterface.class )
	public final JavaCodeWriter invokeinterface( final Method method ) {
		return this.invokeinterface(
			method.getDeclaringClass(),
			JavaAssembler.method( method ) );
	}

	@Op( Invokespecial.class )
	public final JavaCodeWriter invokespecial( final Method method ) {
		return this.invokespecial(
			method.getDeclaringClass(),
			JavaAssembler.method( method ) );
	}
	
	@Op( Invokestatic.class )
	public final JavaCodeWriter invokestatic( final Method method ) {
		return this.invokestatic(
			method.getDeclaringClass(),
			JavaAssembler.method( method ) );
	}

	@Op( Invokevirtual.class )
	public final JavaCodeWriter invokevirtual(
		final Type targetType,
		final JavaMethodSignature signature )
	{
		Method method = findMethod( targetType, signature );
		if (Modifier.isStatic( method.getModifiers() ) ) {
			throw new IllegalStateException( "Not a virtual method" );
		}
		return this.invokevirtual( method );
	}

	@WrapOp( Invokevirtual.class )
	public final JavaCodeWriter this_invokevirtual(
		final JavaMethodDescriptor method )
	{
		return this.invokevirtual( this.thisType(), method );
	}

	@Op( Invokevirtual.class )
	public final JavaCodeWriter invokevirtual(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.coreWriter.invokevirtual( targetType, method );
		return this;
	}

	@WrapOp( Invokevirtual.class )
	public final JavaCodeWriter this_invokevirtual(
		final Type returnType,
		final String methodName,
		final Type... argumentTypes )
	{
		return this.invokevirtual(
			this.thisType(),
			returnType,
			methodName,
			argumentTypes );
	}

	@WrapOp( Invokevirtual.class )
	public final JavaCodeWriter invokevirtual(
		final Type targetType,
		final Type returnType,
		final String methodName,
		final Type... argumentTypes )
	{
		this.coreWriter.invokevirtual(
			targetType,
			JavaAssembler.method(
				returnType,
				methodName,
				argumentTypes ) );
		return this;
	}

	@WrapOp( Invokespecial.class )
	public final JavaCodeWriter this_invokespecial(
		final JavaMethodDescriptor method )
	{
		return this.invokespecial( this.thisType(), method );
	}

	@WrapOp( Invokespecial.class )
	public final JavaCodeWriter super_invokespecial(
		final JavaMethodDescriptor method )
	{
		return this.invokespecial( this.superType(), method );
	}

	@Op( Invokespecial.class )
	public final JavaCodeWriter invokespecial(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.coreWriter.invokespecial( targetType, method );
		return this;
	}

	@WrapOp( Invokespecial.class )
	public final JavaCodeWriter this_invokespecial(
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

	@WrapOp( Invokespecial.class )
	public final JavaCodeWriter super_invokespecial(
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

	@WrapOp( Invokespecial.class )
	public final JavaCodeWriter invokespecial(
		final Type targetType,
		final Type returnType,
		final String methodName,
		final Type... argumentTypes )
	{
		return this.invokespecial(
			targetType,
			JavaAssembler.method( returnType, methodName, argumentTypes ) );
	}

	@Op( Invokestatic.class )
	public final JavaCodeWriter invokestatic(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.coreWriter.invokestatic( targetType, method );
		return this;
	}

	@WrapOp( Invokestatic.class )
	public final JavaCodeWriter invokestatic(
		final Type targetType,
		final JavaMethodSignature builder )
	{
		Method method = findMethod( targetType, builder );
		if ( ! Modifier.isStatic( method.getModifiers() ) ) {
			throw new IllegalStateException( "Not a static method" );
		}
		return this.invokevirtual( method );
	}

	@WrapOp( Invokeinterface.class )
	public final JavaCodeWriter this_invokeinterface(
		final Type returnType,
		final String methodName,
		final Type... argumentTypes )
	{
		return this.invokeinterface(
			this.thisType(),
			returnType,
			methodName,
			argumentTypes );
	}

	@WrapOp( Invokeinterface.class )
	public final JavaCodeWriter invokeinterface(
		final Type targetType,
		final Type returnType,
		final String methodName,
		final Type... argumentTypes )
	{
		return this.invokeinterface(
			targetType,
			JavaAssembler.method( returnType, methodName, argumentTypes ) );
	}

	@WrapOp( Invokeinterface.class )
	public final JavaCodeWriter this_invokeinterface(
		final JavaMethodDescriptor method )
	{
		return this.invokeinterface( this.thisType(), method );
	}

	@Op( Invokeinterface.class )
	public final JavaCodeWriter invokeinterface(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.coreWriter.invokeinterface( targetType, method );
		return this;
	}

	@Op( New.class )
	public final JavaCodeWriter new_( final Type type ) {
		this.coreWriter.new_( type );
		return this;
	}

	@Op( Newarray.class )
	public final JavaCodeWriter newarray( final Type componentType ) {
		this.coreWriter.newarray( componentType );
		return this;
	}

	@SyntheticOp( stackOperandTypes=boolean[].class )
	public final JavaCodeWriter barray( final boolean... array ) {
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

	@SyntheticOp( stackOperandTypes=boolean[].class )
	public final JavaCodeWriter barray( final byte... array ) {
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
	public final JavaCodeWriter carray( final char... array ) {
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
	public final JavaCodeWriter sarray( final short... array ) {
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
	public final JavaCodeWriter iarray( final int... array ) {
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
	public final JavaCodeWriter farray( final float... array ) {
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
	public final JavaCodeWriter larray( final long... array ) {
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
	public final JavaCodeWriter darray( final double... array ) {
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

	@Op( Anewarray.class )
	public final JavaCodeWriter anewarray( final Type componentType ) {
		this.coreWriter.anewarray( componentType );
		return this;
	}
	
	@Op( Arraylength.class )
	public final JavaCodeWriter arraylength() {
		this.coreWriter.arraylength();
		return this;
	}

	@Op( Athrow.class )
	public final JavaCodeWriter athrow() {
		this.coreWriter.athrow();
		return this;
	}

	@Op( Checkcast.class )
	public final JavaCodeWriter checkcast( final Type type ) {
		this.coreWriter.checkcast( type );
		return this;
	}
	
	@Op( Instanceof.class )
	public final JavaCodeWriter instanceof_( final Type type ) {
		this.coreWriter.instanceof_( type );
		return this;
	}

	@Op( Monitorenter.class )
	public final JavaCodeWriter monitorenter() {
		this.coreWriter.monitorenter();
		return this;
	}

	@Op( Monitorexit.class )
	public final JavaCodeWriter monitorexit() {
		this.coreWriter.monitorexit();
		return this;
	}

	final JavaCodeWriter wide() {
		// TODO: wide
		return null;
	}

	@Op( Multianewarray.class )
	public final JavaCodeWriter multianewarray(
		final Type arrayType,
		final int numDimensions )
	{
		this.coreWriter.multianewarray( arrayType, numDimensions );
		return this;
	}

	@WrapOp( Ifnull.class )
	public final JavaCodeWriter ifnull( final String label ) {
		this.coreWriter.ifnull( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( Ifnonnull.class )
	public final JavaCodeWriter ifnonnull( final String label ) {
		this.coreWriter.ifnonnull( this.jumpTo( label ) );
		return this;
	}

	final JavaCodeWriter goto_w() {
		// TODO: goto_w
		return null;
	}

	final JavaCodeWriter jsr_w() {
		// TODO: jsr_w
		return null;
	}

	@SyntheticOp( stackOperandTypes=boolean.class, stackResultTypes=boolean.class )
	public final JavaCodeWriter inot() {
		this.coreWriter.ifeq( this.jumpRelative( +7 ) ).
			iconst_0().
			goto_( this.jumpRelative( +4 ) ).
			iconst_1();
		
		return this;
	}

	@SyntheticOp( stackOperandTypes=Primitive.class, stackResultTypes=Reference.class )
	public final JavaCodeWriter box( final Class< ? > primitiveClass ) {
		if ( JavaTypes.isObjectType( primitiveClass ) ) {
			return this;
		}

		Class< ? > boxClass = JavaTypes.getObjectType( primitiveClass );

		return this.invokestatic(
			boxClass,
			JavaAssembler.method( boxClass, "valueOf", primitiveClass ) );
	}

	@SyntheticOp( stackOperandTypes=Reference.class, stackResultTypes=Primitive.class )
	public final JavaCodeWriter unbox( final Type boxClass ) {
		if (JavaTypes.isPrimitiveType( boxClass ) ) {
			return this;
		}

		Class<?> primitiveClass = JavaTypes.getPrimitiveType( boxClass );
		String methodName = primitiveClass.getCanonicalName() + "Value";
		
		return this.invokevirtual(
			boxClass,
			JavaAssembler.method( primitiveClass, methodName ) );
	}

	@SyntheticOp
	public final JavaCodeWriter label( final String labelName ) {
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
	public final JavaCodeWriter catch_(
		final String startLabel,
		final String endLabel,
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
	public final JavaCodeWriter exception(
		final String startLabel,
		final String endLabel,
		final Class<? extends Throwable> throwableClass,
		final String handlerLabel )
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
				return JavaCodeWriter.this.labels.get( label );
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
			int newSlot = this.coreWriter.locals().addLocal( int.class );
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
			return JavaCodeWriter.this.getLabelPos( this.startLabel );
		}

		@Override
		public final int endPos() {
			return JavaCodeWriter.this.getLabelPos( this.endLabel );
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
			return JavaCodeWriter.this.getLabelPos( this.startLabel );
		}

		@Override
		public final int endPos() {
			return JavaCodeWriter.this.getLabelPos( this.endLabel );
		}

		@Override
		public final Class<? extends Throwable> throwableClass() {
			return this.throwableClass;
		}

		@Override
		public final int handlerPos() {
			return JavaCodeWriter.this.getLabelPos( this.handlerLabel );
		}
	}
}
