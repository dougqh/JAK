package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import net.dougqh.jak.JakContext;
import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.JavaMethodSignature;
import net.dougqh.jak.assembler.JakAsm;
import net.dougqh.jak.assembler.JakCodeWriter;
import net.dougqh.jak.assembler.JakCondition;
import net.dougqh.jak.assembler.JakExpression;
import net.dougqh.jak.assembler.JakMacro;
import net.dougqh.jak.assembler.TypeResolver;
import net.dougqh.jak.jvm.JvmOperationProcessor.ExceptionHandler;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;
import net.dougqh.jak.jvm.annotations.JvmOp;
import net.dougqh.jak.jvm.annotations.Symbol;
import net.dougqh.jak.jvm.annotations.SyntheticOp;
import net.dougqh.jak.jvm.annotations.WrapOp;
import net.dougqh.jak.jvm.assembler.macros.ArrayFor;
import net.dougqh.jak.jvm.assembler.macros.DoWhile;
import net.dougqh.jak.jvm.assembler.macros.IfConstruct;
import net.dougqh.jak.jvm.assembler.macros.IterableFor;
import net.dougqh.jak.jvm.assembler.macros.Synchronized;
import net.dougqh.jak.jvm.assembler.macros.Ternary;
import net.dougqh.jak.jvm.assembler.macros.TryConstruct;
import net.dougqh.jak.jvm.assembler.macros.While;
import net.dougqh.jak.jvm.assembler.macros.stmt;
import net.dougqh.jak.jvm.operations.*;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.ArgList;
import net.dougqh.jak.types.Primitive;
import net.dougqh.jak.types.Reference;
import net.dougqh.java.meta.types.JavaTypes;
import static net.dougqh.jak.Jak.*;

import static net.dougqh.jak.assembler.JakAsm.*;

/**
 * Abstract base of JvmCodeWriter and JakMacro.
 */
/*
 * Most convenience methods should be implemented here.
 */
public abstract class JvmCodeWriter implements JakCodeWriter {
	private static final String TYPE = "TYPE";
	
	//Sub-writers need to share state with the surrounding writer.
	//To accomplish this, a CodeWritingState object is shared between 
	//the two objects.
	protected final class CodeWritingState {
		private Scope varScope;
		private Scope labelScope;
		
		private boolean trapReturn = false;
		private Class<?> returnType = null;
		
		protected CodeWritingState() {
			this.varScope = new Scope();
			this.labelScope = new Scope();
		}
		
		protected CodeWritingState( final CodeWritingState sharedState ) {
			this.varScope = sharedState.varScope;
			this.labelScope = sharedState.labelScope;
			
			this.trapReturn = sharedState.trapReturn;
			this.returnType = sharedState.returnType;
		}
	}
	
	private final JakContext context = new JakContext() {
		@Override
		public final Type localType( final String name ) {
			return JvmCodeWriter.this.typeOf( name );
		}
		
		@Override
		public final Type thisType() {
			return JvmCodeWriter.this.thisType();
		}
	};

	private JvmMacro underConstructionMacro = null;
	
	protected abstract CodeWritingState sharedState();
	
	public abstract JvmCoreCodeWriter coreWriter();
	
	protected final ConstantPool constantPool() {
		return this.coreWriter().context().constantPool;
	}
	
	protected final Type thisType() {
		return this.coreWriter().context().thisType;
	}
	
	protected final Type superType() {
		return this.coreWriter().context().superType;
	}
	
	protected final TypeResolver resolver() {
		return this.coreWriter().context().resolver;
	}
	
	protected final Scope varScope( final boolean writing ) {
		if ( writing ) {
			this.coreWriter().prepare();
		}
		
		return this.sharedState().varScope;
	}
	
	protected final Scope labelScope( final boolean writing ) {
		if ( writing ) {
			this.coreWriter().prepare();
		}
		
		return this.sharedState().labelScope;
	}

	@Override
	@SyntheticOp
	public final JvmCodeWriter startScope() {
		this.sharedState().varScope = this.varScope( true ).startScope();
		return this;
	}
	
	@Override
	@SyntheticOp
	public final JvmCodeWriter endScope() {
		Scope varScope = this.varScope( false );
		for ( Map.Entry< String, Integer> entry: varScope.entrySet() ) {
			this.localsMonitor().undeclare( entry.getValue() );
		}
		
		this.sharedState().varScope = varScope.endScope();
		return this;
	}
	
	protected final Type typeOf( final String var ) {
		return this.localsMonitor().typeOf( this.varScope( false ).get( var ) );
	}
	
	@Override
	@SyntheticOp
	public final JvmCodeWriter declare( final Type type, final @Symbol String var ) {
		int slot = this.localsMonitor().declare( type );
		this.varScope( true ).set( var, slot );
		return this;
	}
	
	public final JvmCodeWriter declare( final JakExpression expr, final @Symbol String var ) {
		this.declare( expr.type( this.context() ), var );
		this.expr( expr );
		this.store( expr, var );
		return this;
	}
	
	@SyntheticOp
	public final JvmCodeWriter ideclare( final JakExpression expr, final @Symbol String var ) {
		return this.ideclare( var ).expr( expr ).istore( var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter ideclare( final @Symbol String var ) {
		return this.declare( int.class, var );
	}

	@SyntheticOp
	public final JvmCodeWriter ideclare( final boolean value, final @Symbol String var ) {
		return this.ideclare( var ).istore( value, var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter ideclare( final int value, final @Symbol String var ) {
		return this.ideclare( var ).istore( value, var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter fdeclare( final @Symbol String var ) {
		return this.declare( float.class, var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter fdeclare( final JakExpression expr, final @Symbol String var ) {
		return this.fdeclare( var ).fstore( expr, var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter fdeclare( final float value, final @Symbol String var ) {
		return this.fdeclare( var ).fstore( value, var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter ldeclare( final @Symbol String var ) {
		return this.declare( long.class, var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter ldeclare( final JakExpression expr, final @Symbol String var ) {
		return this.ldeclare( var ).lstore( expr, var );
	}

	@SyntheticOp
	public final JvmCodeWriter ldeclare( final long value, final @Symbol String var ) {
		return this.ldeclare( var ).lstore( value, var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter ddeclare( final @Symbol String var ) {
		return this.declare( double.class, var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter ddeclare( final JakExpression expr, final @Symbol String var ) {
		return this.ddeclare( var ).ddeclare( expr, var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter ddeclare( final double value, final @Symbol String var ) {
		return this.ddeclare( var ).dstore( value, var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter adeclare( final @Symbol String var ) {
		return this.declare( Reference.class, var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter adeclare( final JakExpression expr, final @Symbol String var ) {
		return this.adeclare( var ).astore( expr, var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter adeclare_null( final @Symbol String var ) {
		return this.adeclare( var ).astore_null( var );
	}
	
	@Override
	@SyntheticOp( repl=false )
	public final JvmCodeWriter alias(
		final @Symbol String var,
		final @Symbol String existingVar )
	{
		this.varScope( true ).alias( var, existingVar );
		return this;
	}
	
	private final JakContext context() {
		return this.context;
	}
	
	protected final JvmCodeWriter expr( final JakExpression expr ) {
		expr.accept( new JvmExpressionVisitor( this.context() ) {
			@Override
			protected void this_() {
				JvmCodeWriter.this.this_();
			}
			
			@Override
			protected final void var( final String name, final Type type ) {
				JvmCodeWriter.this.load( type, name );
			}
			
			@Override
			protected final void null_( final Type type ) {
				JvmCodeWriter.this.aconst_null();
			}
			
			@Override
			protected final void const_( final String value ) {
				JvmCodeWriter.this.ldc( value );
			}
			
			@Override
			protected final void const_( final Type aClass ) {
				JvmCodeWriter.this.ldc( aClass );
			}
			
			@Override
			protected final void const_( final int value ) {
				JvmCodeWriter.this.iconst( value );
			}
			
			@Override
			protected final void const_( final long value ) {
				JvmCodeWriter.this.lconst( value );
			}
			
			@Override
			protected final void const_( final double value ) {
				JvmCodeWriter.this.dconst( value );
			}
			
			@Override
			protected final void const_( final float value ) {
				JvmCodeWriter.this.fconst( value );
			}
			
			@Override
			protected void arbitrary( final JvmExpression<?> expression ) {
				expression.eval( JvmCodeWriter.this );
			}
		} );
		return this;
	}
	
	protected final JvmCodeWriter expr( final JakCondition cond ) {
		return this.ternary( cond, JakAsm.true_(), JakAsm.false_() );
	}
	
	@Override
	public final JvmCodeWriter scope( final JakMacro macro ) {
		this.startScope();
		try {
			return this.macro( macro );
		} finally {
			this.endScope();
		}
	}

	@Override
	public final JvmCodeWriter macro( final JakMacro macro ) {
		if ( macro instanceof JvmMacro ) {
			final JvmMacro jvmMacro = (JvmMacro)macro;
			
			if ( jvmMacro.defer() ) {
				final CaptureCodeWriter captureWriter = new CaptureCodeWriter();
				
				this.coreWriter().defer( new JvmCoreCodeWriter.DeferredWrite() {
					@Override
					public final void write(
						final JvmCoreCodeWriter coreWriter,
						final boolean terminal )
					{
						captureWriter.init( coreWriter );
						jvmMacro.write( captureWriter, terminal );
					}
				} );
			} else {
				jvmMacro.write( this, false );
			}
		}
		return this;
	}

	@JvmOp( nop.class )
	public final JvmCodeWriter nop() {
		this.coreWriter().nop();
		return this;
	}

	@SyntheticOp( stackResultTypes=Class.class )
	public final JvmCodeWriter aconst( final Class< ? > aClass ) {
		if ( aClass.equals( void.class ) ) {
			return this.getstatic(
				Void.class,
				field( Class.class, TYPE ) );
		} else if ( aClass.equals( boolean.class ) ) {
			return this.getstatic(
				Boolean.class,
				field( Class.class, TYPE ) );
		} else if ( aClass.equals( byte.class ) ) {
			return this.getstatic(
				Byte.class,
				field( Class.class, TYPE ) );
		} else if ( aClass.equals( char.class ) ) {
			return this.getstatic(
				Character.class,
				field( Class.class, TYPE ) );
		} else if ( aClass.equals( short.class ) ) {
			return this.getstatic(
				Short.class,
				field( Class.class, TYPE ) );
		} else if ( aClass.equals( int.class ) ) {
			return this.getstatic(
				Integer.class,
				field( Class.class, TYPE ) );
		} else if ( aClass.equals( long.class ) ) {
			return this.getstatic(
				Long.class,
				field( Class.class, TYPE ) );
		} else if ( aClass.equals( float.class ) ) {
			return this.getstatic(
				Float.class,
				field( Class.class, TYPE ) );
		} else if ( aClass.equals( double.class ) ) {
			return this.getstatic(
				Double.class,
				field( Class.class, TYPE ) );
		} else {
			return this.ldc( aClass );
		}
	}

	@JvmOp( aconst_null.class )
	public final JvmCodeWriter aconst_null() {
		this.coreWriter().aconst_null();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter true_() {
		return this.iconst( true );
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter false_() {
		return this.iconst( false );
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
		ldc.normalize( this.coreWriter(), value );
		return this;
	}

	@JvmOp( iconst_m1.class )
	public final JvmCodeWriter iconst_m1() {
		this.coreWriter().iconst_m1();
		return this;
	}

	@JvmOp( iconst_0.class )
	public final JvmCodeWriter iconst_0() {
		this.coreWriter().iconst_0();
		return this;
	}

	@JvmOp( iconst_1.class )
	public final JvmCodeWriter iconst_1() {
		this.coreWriter().iconst_1();
		return this;
	}

	@JvmOp( iconst_2.class )
	public final JvmCodeWriter iconst_2() {
		this.coreWriter().iconst_2();
		return this;
	}

	@JvmOp( iconst_3.class )
	public final JvmCodeWriter iconst_3() {
		this.coreWriter().iconst_3();
		return this;
	}

	@JvmOp( iconst_4.class )
	public final JvmCodeWriter iconst_4() {
		this.coreWriter().iconst_4();
		return this;
	}

	@JvmOp( iconst_5.class )
	public final JvmCodeWriter iconst_5() {
		this.coreWriter().iconst_5();
		return this;
	}

	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter lconst( final long value ) {
		ldc2_w.normalize( this.coreWriter(), value );
		return this;
	}

	@JvmOp( lconst_0.class )
	public final JvmCodeWriter lconst_0() {
		this.coreWriter().lconst_0();
		return this;
	}

	@JvmOp( lconst_1.class )
	public final JvmCodeWriter lconst_1() {
		this.coreWriter().lconst_1();
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
		this.coreWriter().fconst_0();
		return this;
	}

	@JvmOp( fconst_1.class )
	public final JvmCodeWriter fconst_1() {
		this.coreWriter().fconst_1();
		return this;
	}

	@JvmOp( fconst_2.class )
	public final JvmCodeWriter fconst_2() {
		this.coreWriter().fconst_2();
		return this;
	}

	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter dconst( final double value ) {
		ldc2_w.normalize( this.coreWriter(), value );
		return this;
	}

	@JvmOp( dconst_0.class )
	public final JvmCodeWriter dconst_0() {
		this.coreWriter().dconst_0();
		return this;
	}

	@JvmOp( dconst_1.class )
	public final JvmCodeWriter dconst_1() {
		this.coreWriter().dconst_1();
		return this;
	}

	@JvmOp( bipush.class )
	public final JvmCodeWriter bipush( final byte value ) {
		this.coreWriter().bipush( value );
		return this;
	}

	@JvmOp( sipush.class )
	public final JvmCodeWriter sipush( final short value ) {
		this.coreWriter().sipush( value );
		return this;
	}

	@WrapOp( value=ldc.class, stackResultTypes=int.class )
	public final JvmCodeWriter ldc( final int value ) {
		this.coreWriter().ldc( value );
		return this;
	}

	@WrapOp( value=ldc.class, stackResultTypes=float.class )
	public final JvmCodeWriter ldc( final float value ) {
		this.coreWriter().ldc( value );
		return this;
	}

	@WrapOp( value=ldc.class, stackResultTypes=String.class )
	public final JvmCodeWriter ldc( final CharSequence value ) {
		this.coreWriter().ldc( value.toString() );
		return this;
	}
	
	@WrapOp( value=ldc.class, stackResultTypes=Type.class )
	public final JvmCodeWriter ldc( final Type value ) {
		this.coreWriter().ldc( value );
		return this;
	}

	@WrapOp( value=ldc2_w.class, stackResultTypes=long.class )
	public final JvmCodeWriter ldc2_w( final long value ) {
		this.coreWriter().ldc2_w( value );
		return this;
	}

	@WrapOp( value=ldc2_w.class, stackResultTypes=double.class )
	public final JvmCodeWriter ldc2_w( final double value ) {
		this.coreWriter().ldc2_w( value );
		return this;
	}

	@SyntheticOp( repl=false )
	public final JvmCodeWriter load( final Type type, final int slot ) {
		Class<?> aClass = this.slotType( type );
		if ( aClass.equals( int.class ) ) {
			return this.iload( slot );
		} else if ( aClass.equals( long.class ) ) {
			return this.lload( slot );
		} else if ( aClass.equals( float.class ) ) {
			return this.fload( slot );
		} else if ( aClass.equals( double.class ) ) {
			return this.dload( slot );
		} else {
			return this.aload( slot );
		}
	}
	
	@SyntheticOp( repl=false )
	public final JvmCodeWriter load( final Type type, final String var ) {
		Class<?> aClass = this.slotType( type );
		if ( aClass.equals( int.class ) ) {
			return this.iload( var );
		} else if ( aClass.equals( long.class ) ) {
			return this.lload( var );
		} else if ( aClass.equals( float.class ) ) {
			return this.fload( var );
		} else if ( aClass.equals( double.class ) ) {
			return this.dload( var );
		} else {
			return this.aload( var );
		}
	}
	
	@WrapOp( iload.class )
	public final JvmCodeWriter iload( final String var ) {
		return this.iload( this.getVarSlot( var ) );
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
			this.coreWriter().iload( slot );
			return this;
		}
	}

	@JvmOp( iload_0.class )
	public final JvmCodeWriter iload_0() {
		this.coreWriter().iload_0();
		return this;
	}

	@JvmOp( iload_1.class )
	public final JvmCodeWriter iload_1() {
		this.coreWriter().iload_1();
		return this;
	}

	@JvmOp( iload_2.class )
	public final JvmCodeWriter iload_2() {
		this.coreWriter().iload_2();
		return this;
	}

	@JvmOp( iload_3.class )
	public final JvmCodeWriter iload_3() {
		this.coreWriter().iload_3();
		return this;
	}

	@JvmOp( lload.class )
	public final JvmCodeWriter lload( final @Symbol String var ) {
		return this.lload( this.getVarSlot( var ) );
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
			this.coreWriter().lload( slot );
			return this;
		}
	}

	@JvmOp( lload_0.class )
	public final JvmCodeWriter lload_0() {
		this.coreWriter().lload_0();
		return this;
	}

	@JvmOp( lload_1.class )
	public final JvmCodeWriter lload_1() {
		this.coreWriter().lload_1();
		return this;
	}

	@JvmOp( lload_2.class )
	public final JvmCodeWriter lload_2() {
		this.coreWriter().lload_2();
		return this;
	}

	@JvmOp( lload_3.class )
	public final JvmCodeWriter lload_3() {
		this.coreWriter().lload_3();
		return this;
	}
	
	@JvmOp( fload.class )
	public final JvmCodeWriter fload( final @Symbol String var ) {
		return this.fload( this.getVarSlot( var ) );
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
			this.coreWriter().fload( slot );
			return this;
		}
	}

	@JvmOp( fload_0.class )
	public final JvmCodeWriter fload_0() {
		this.coreWriter().fload_0();
		return this;
	}

	@JvmOp( fload_1.class )
	public final JvmCodeWriter fload_1() {
		this.coreWriter().fload_1();
		return this;
	}

	@JvmOp( fload_2.class )
	public final JvmCodeWriter fload_2() {
		this.coreWriter().fload_2();
		return this;
	}

	@JvmOp( fload_3.class )
	public final JvmCodeWriter fload_3() {
		this.coreWriter().fload_3();
		return this;
	}

	@WrapOp( dload.class )
	public final JvmCodeWriter dload( final @Symbol String var ) {
		return this.dload( this.getVarSlot( var ) );
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
			this.coreWriter().dload( slot );
			return this;
		}
	}

	@JvmOp( dload_0.class )
	public final JvmCodeWriter dload_0() {
		this.coreWriter().dload_0();
		return this;
	}

	@JvmOp( dload_1.class )
	public final JvmCodeWriter dload_1() {
		this.coreWriter().dload_1();
		return this;
	}

	@JvmOp( dload_2.class )
	public final JvmCodeWriter dload_2() {
		this.coreWriter().dload_2();
		return this;
	}

	@JvmOp( dload_3.class )
	public final JvmCodeWriter dload_3() {
		this.coreWriter().dload_3();
		return this;
	}

	@JvmOp( aload.class )
	public final JvmCodeWriter aload( final @Symbol String var ) {
		return this.aload( this.getVarSlot( var ) );
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
			this.coreWriter().aload( slot );
			return this;
		}
	}

	@WrapOp( aload_0.class )
	public final JvmCodeWriter this_() {
		return this.aload_0();
	}

	@JvmOp( aload_0.class )
	public final JvmCodeWriter aload_0() {
		this.coreWriter().aload_0();
		return this;
	}

	@JvmOp( aload_1.class )
	public final JvmCodeWriter aload_1() {
		this.coreWriter().aload_1();
		return this;
	}

	@JvmOp( aload_2.class )
	public final JvmCodeWriter aload_2() {
		this.coreWriter().aload_2();
		return this;
	}

	@JvmOp( aload_3.class )
	public final JvmCodeWriter aload_3() {
		this.coreWriter().aload_3();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=Object.class )
	public final JvmCodeWriter arrayload( final Type elementType ) {
		if ( elementType.equals( boolean.class ) ) {
			return this.baload();
		} else if ( elementType.equals( byte.class ) ) {
			return this.baload();
		} else if ( elementType.equals( char.class ) ) {
			return this.caload();
		} else if ( elementType.equals( short.class ) ) {
			return this.saload();
		} else if ( elementType.equals( int.class ) ) {
			return this.iaload();
		} else if ( elementType.equals( long.class ) ) {
			return this.laload();
		} else if ( elementType.equals( float.class ) ) {
			return this.faload();
		} else if ( elementType.equals( double.class ) ) {
			return this.daload();
		} else {
			return this.aaload();
		}
	}

	@JvmOp( iaload.class )
	public final JvmCodeWriter iaload() {
		this.coreWriter().iaload();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter iaload(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.aload( arrayVar ).iconst( index ).iaload();
	}
	

	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter iaload(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.aload( arrayVar ).iload( indexVar ).iaload();
	}

	@JvmOp( laload.class )
	public final JvmCodeWriter laload() {
		this.coreWriter().laload();
		return this;
	}
	

	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter laload(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.aload( arrayVar ).iconst( index ).laload();
	}

	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter laload(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.aload( arrayVar ).iload( indexVar ).laload();
	}

	@JvmOp( faload.class )
	public final JvmCodeWriter faload() {
		this.coreWriter().faload();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter faload(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.aload( arrayVar ).iconst( index ).faload();
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter faload(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.aload( arrayVar ).iload( indexVar ).faload();
	}

	@JvmOp( daload.class )
	public final JvmCodeWriter daload() {
		this.coreWriter().daload();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter daload(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.aload( arrayVar ).iconst( index ).daload();
	}
	
	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter daload(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.aload( arrayVar ).iload( indexVar ).daload();
	}

	@JvmOp( aaload.class )
	public final JvmCodeWriter aaload() {
		this.coreWriter().aaload();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=Reference.class )
	public final JvmCodeWriter aaload(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.aload( arrayVar ).iconst( index ).aaload();
	}
	
	@SyntheticOp( stackResultTypes=Reference.class )
	public final JvmCodeWriter aaload(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.aload( arrayVar ).iload( indexVar ).aaload();
	}

	@JvmOp( baload.class )
	public final JvmCodeWriter baload() {
		this.coreWriter().baload();
		return this;
	}

	@SyntheticOp( stackResultTypes=byte.class )
	public final JvmCodeWriter baload(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.aload( arrayVar ).iconst( index ).baload();
	}
	
	@SyntheticOp( stackResultTypes=byte.class )
	public final JvmCodeWriter baload(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.aload( arrayVar ).iload( indexVar ).baload();
	}

	@JvmOp( caload.class )
	public final JvmCodeWriter caload() {
		this.coreWriter().caload();
		return this;
	}

	@SyntheticOp( stackResultTypes=char.class )
	public final JvmCodeWriter caload(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.aload( arrayVar ).iconst( index ).caload();
	}
	
	@SyntheticOp( stackResultTypes=char.class )
	public final JvmCodeWriter caload(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.aload( arrayVar ).iload( indexVar ).caload();
	}
	
	@JvmOp( saload.class )
	public final JvmCodeWriter saload() {
		this.coreWriter().saload();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=short.class )
	public final JvmCodeWriter saload(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.aload( arrayVar ).iconst( index ).saload();
	}
	
	@SyntheticOp( stackResultTypes=short.class )
	public final JvmCodeWriter saload(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.aload( arrayVar ).iload( indexVar ).saload();
	}
	
	@SyntheticOp( repl=false )
	public final JvmCodeWriter store( final Type type, final int slot ) {
		Class<?> slotType = this.slotType( type );
		if ( slotType.equals( int.class ) ) {
			return this.istore( slot );
		} else if ( slotType.equals( long.class ) ) {
			return this.lstore( slot );
		} else if ( slotType.equals( float.class ) ) {
			return this.fstore( slot );
		} else if ( slotType.equals( double.class ) ) {
			return this.dstore( slot );
		} else {
			return this.astore( slot );
		}
	}
	
	@SyntheticOp
	public final JvmCodeWriter store( final JakExpression expr, final String var ) {
		return this.expr( expr ).store( expr.type( this.context() ), var );
	}

	@SyntheticOp( repl=false )
	public final JvmCodeWriter store( final Type type, final String var ) {
		Class<?> slotType = this.slotType( type );
		if ( slotType.equals( int.class ) ) {
			return this.istore( var );
		} else if ( slotType.equals( long.class ) ) {
			return this.lstore( var );
		} else if ( slotType.equals( float.class ) ) {
			return this.fstore( var );
		} else if ( slotType.equals( double.class ) ) {
			return this.dstore( var );
		} else {
			return this.astore( var );
		}
	}

	@WrapOp( istore.class )
	public final JvmCodeWriter istore( final @Symbol String var ) {
		return this.istore( this.getVarSlot( var ) );
	}
	
	@SyntheticOp
	public final JvmCodeWriter istore( final JakExpression expr, final @Symbol String var ) {
		return this.expr( expr ).istore( var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter istore( final boolean value, final String var ) {
		return this.iconst( value ).istore( var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter istore( final byte value, final String var ) {
		return this.iconst( value ).istore( var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter istore( final char value, final String var ) {
		return this.iconst( value ).istore( var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter istore( final short value, final String var ) {
		return this.iconst( value ).istore( var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter istore( final int value, final String var ) {
		return this.iconst( value ).istore( var );
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
			this.coreWriter().istore( index );
			return this;
		}
	}

	@JvmOp( istore_0.class )
	public final JvmCodeWriter istore_0() {
		this.coreWriter().istore_0();
		return this;
	}

	@JvmOp( istore_1.class )
	public final JvmCodeWriter istore_1() {
		this.coreWriter().istore_1();
		return this;
	}

	@JvmOp( istore_2.class )
	public final JvmCodeWriter istore_2() {
		this.coreWriter().istore_2();
		return this;
	}

	@JvmOp( istore_3.class )
	public final JvmCodeWriter istore_3() {
		this.coreWriter().istore_3();
		return this;
	}

	@WrapOp( lstore.class )
	public final JvmCodeWriter lstore( final @Symbol String var ) {
		return this.lstore( this.getVarSlot( var ) );
	}
	
	@SyntheticOp
	public final JvmCodeWriter lstore( final JakExpression expr, final @Symbol String var ) {
		return this.expr( expr ).lstore( var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter lstore( final long value, final @Symbol String var ) {
		return this.lconst( value ).lstore( var );
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
			this.coreWriter().lstore( slot );
			return this;
		}
	}

	@JvmOp( lstore_0.class )
	public final JvmCodeWriter lstore_0() {
		this.coreWriter().lstore_0();
		return this;
	}

	@JvmOp( lstore_1.class )
	public final JvmCodeWriter lstore_1() {
		this.coreWriter().lstore_1();
		return this;
	}

	@JvmOp( lstore_2.class )
	public final JvmCodeWriter lstore_2() {
		this.coreWriter().lstore_2();
		return this;
	}

	@JvmOp( lstore_3.class )
	public final JvmCodeWriter lstore_3() {
		this.coreWriter().lstore_3();
		return this;
	}

	@WrapOp( fstore.class )
	public final JvmCodeWriter fstore( final @Symbol String var ) {
		return this.fstore( this.getVarSlot( var ) );
	}
	
	@WrapOp( fstore.class )
	public final JvmCodeWriter fstore( final float value, final @Symbol String var ) {
		return this.fconst( value ).fstore( var );
	}

	@SyntheticOp
	public final JvmCodeWriter fstore( final JakExpression expr, final @Symbol String var ) {
		return this.expr( expr ).fstore( var );
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
			this.coreWriter().fstore( slot );
			return this;
		}
	}

	@JvmOp( fstore_0.class )
	public final JvmCodeWriter fstore_0() {
		this.coreWriter().fstore_0();
		return this;
	}
	
	@JvmOp( fstore_1.class )
	public final JvmCodeWriter fstore_1() {
		this.coreWriter().fstore_1();
		return this;
	}

	@JvmOp( fstore_2.class )
	public final JvmCodeWriter fstore_2() {
		this.coreWriter().fstore_2();
		return this;
	}

	@JvmOp( fstore_3.class )
	public final JvmCodeWriter fstore_3() {
		this.coreWriter().fstore_3();
		return this;
	}

	@WrapOp( dstore.class )
	public final JvmCodeWriter dstore( final @Symbol String var ) {
		return this.dstore( this.getVarSlot( var ) );
	}
	
	@SyntheticOp
	public final JvmCodeWriter dstore( final double value, final @Symbol String var ) {
		return this.dconst( value ).dstore( var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter dstore( final JakExpression expr, final @Symbol String var ) {
		return this.expr( expr ).dstore( var );
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
			this.coreWriter().dstore( slot );
			return this;
		}
	}

	@JvmOp( dstore_0.class )
	public final JvmCodeWriter dstore_0() {
		this.coreWriter().dstore_0();
		return this;
	}

	@JvmOp( dstore_1.class )
	public final JvmCodeWriter dstore_1() {
		this.coreWriter().dstore_1();
		return this;
	}

	@JvmOp( dstore_2.class )
	public final JvmCodeWriter dstore_2() {
		this.coreWriter().dstore_2();
		return this;
	}

	@JvmOp( dstore_3.class )
	public final JvmCodeWriter dstore_3() {
		this.coreWriter().dstore_3();
		return this;
	}

	@WrapOp( astore.class )
	public final JvmCodeWriter astore( final @Symbol String var ) {
		return this.astore( this.getVarSlot( var ) );
	}
	
	@SyntheticOp
	public final JvmCodeWriter astore( final JakExpression expr, final @Symbol String var ) {
		return this.expr( expr ).astore( var );
	}
	
	@SyntheticOp
	public final JvmCodeWriter astore_null( final @Symbol String var ) {
		this.aconst_null().astore( var );
		return this;
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
			this.coreWriter().astore( slot );
			return this;
		}
	}

	@JvmOp( astore_0.class )
	public final JvmCodeWriter astore_0() {
		this.coreWriter().astore_0();
		return this;
	}

	@JvmOp( astore_1.class )
	public final JvmCodeWriter astore_1() {
		this.coreWriter().astore_1();
		return this;
	}

	@JvmOp( astore_2.class )
	public final JvmCodeWriter astore_2() {
		this.coreWriter().astore_2();
		return this;
	}

	@JvmOp( astore_3.class )
	public final JvmCodeWriter astore_3() {
		this.coreWriter().astore_3();
		return this;
	}
	
	public final JvmCodeWriter arraystore( final Type elementType ) {
		if ( elementType.equals( boolean.class ) ) {
			return this.bastore();
		} else if ( elementType.equals( byte.class ) ) {
			return this.bastore();
		} else if ( elementType.equals( char.class ) ) {
			return this.castore();
		} else if ( elementType.equals( short.class ) ) {
			return this.sastore();
		} else if ( elementType.equals( int.class ) ) {
			return this.iastore();
		} else if ( elementType.equals( long.class ) ) {
			return this.lastore();
		} else if ( elementType.equals( float.class ) ) {
			return this.fastore();
		} else if ( elementType.equals( double.class ) ) {
			return this.dastore();
		} else {
			return this.aastore();
		}
	}

	@JvmOp( iastore.class )
	public final JvmCodeWriter iastore() {
		this.coreWriter().iastore();
		return this;
	}
	
	private final JvmCodeWriter start_arraystore(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.aload( arrayVar ).iconst( index );
	}
	
	private final JvmCodeWriter start_arraystore(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.aload( arrayVar ).iload( indexVar );
	}
	
	@SyntheticOp
	public final JvmCodeWriter start_iastore(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.start_arraystore( arrayVar, index );
	}

	@SyntheticOp
	public final JvmCodeWriter start_iastore(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.start_arraystore( arrayVar, indexVar );
	}
	
	@JvmOp( lastore.class )
	public final JvmCodeWriter lastore() {
		this.coreWriter().lastore();
		return this;
	}
	
	@SyntheticOp
	public final JvmCodeWriter start_lastore(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.start_arraystore( arrayVar, index );
	}

	@SyntheticOp
	public final JvmCodeWriter lastore(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.start_arraystore( arrayVar, indexVar );
	}

	@JvmOp( fastore.class )
	public final JvmCodeWriter fastore() {
		this.coreWriter().fastore();
		return this;
	}
	
	@SyntheticOp
	public final JvmCodeWriter start_fastore(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.start_arraystore( arrayVar, index );
	}

	@SyntheticOp
	public final JvmCodeWriter start_fastore(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.start_arraystore( arrayVar, indexVar );

	}

	@JvmOp( dastore.class )
	public final JvmCodeWriter dastore() {
		this.coreWriter().dastore();
		return this;
	}

	@SyntheticOp
	public final JvmCodeWriter start_dastore(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.start_arraystore( arrayVar, index );
	}

	@SyntheticOp
	public final JvmCodeWriter start_dastore(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.start_arraystore( arrayVar, indexVar );
	}
	
	@JvmOp( aastore.class )
	public final JvmCodeWriter aastore() {
		this.coreWriter().aastore();
		return this;
	}
	
	@SyntheticOp
	public final JvmCodeWriter start_aastore(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.start_arraystore( arrayVar, index );
	}

	@SyntheticOp
	public final JvmCodeWriter start_aastore(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.start_arraystore( arrayVar, indexVar );
	}

	@JvmOp( bastore.class )
	public final JvmCodeWriter bastore() {
		this.coreWriter().bastore();
		return this;
	}
	
	@SyntheticOp
	public final JvmCodeWriter start_bastore(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.start_arraystore( arrayVar, index );
	}

	@SyntheticOp( stackOperandTypes=byte.class )
	public final JvmCodeWriter start_bastore(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.start_arraystore( arrayVar, indexVar );
	}
	
	@JvmOp( castore.class )
	public final JvmCodeWriter castore() {
		this.coreWriter().castore();
		return this;
	}

	@SyntheticOp
	public final JvmCodeWriter start_castore(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.start_arraystore( arrayVar, index );
	}

	@SyntheticOp
	public final JvmCodeWriter start_castore(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.start_arraystore( arrayVar, indexVar );
	}

	@JvmOp( sastore.class )
	public final JvmCodeWriter sastore() {
		this.coreWriter().sastore();
		return this;
	}
	
	@SyntheticOp
	public final JvmCodeWriter start_sastore(
		final @Symbol String arrayVar,
		final int index )
	{
		return this.start_arraystore( arrayVar, index );
	}

	@SyntheticOp
	public final JvmCodeWriter start_sastore(
		final @Symbol String arrayVar,
		final @Symbol String indexVar )
	{
		return this.start_arraystore( arrayVar, indexVar );
	}
	
	@JvmOp( pop.class )
	public final JvmCodeWriter pop() {
		this.coreWriter().pop();
		return this;
	}

	@JvmOp( pop2.class )
	public final JvmCodeWriter pop2() {
		this.coreWriter().pop2();
		return this;
	}

	@JvmOp( dup.class )
	public final JvmCodeWriter dup() {
		this.coreWriter().dup();
		return this;
	}

	@JvmOp( dup_x1.class )
	public final JvmCodeWriter dup_x1() {
		this.coreWriter().dup_x1();
		return this;
	}

	@JvmOp( dup_x2.class )
	public final JvmCodeWriter dup_x2() {
		this.coreWriter().dup_x2();
		return this;
	}

	@JvmOp( dup2.class )
	public final JvmCodeWriter dup2() {
		this.coreWriter().dup2();
		return this;
	}

	@JvmOp( dup2_x1.class )
	public final JvmCodeWriter dup2_x1() {
		this.coreWriter().dup2_x1();
		return this;
	}

	@JvmOp( dup2_x2.class )
	public final JvmCodeWriter dup2_x2() {
		this.coreWriter().dup2_x2();
		return this;
	}

	@JvmOp( swap.class )
	public final JvmCodeWriter swap() {
		this.coreWriter().swap();
		return this;
	}

	@JvmOp( iadd.class )
	public final JvmCodeWriter iadd() {
		this.coreWriter().iadd();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter iadd( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.iload( lhsVar ).iload( rhsVar ).iadd();
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter iadd( final @Symbol String lhsVar, final int rhsConstant ) {
		return this.iload( lhsVar ).iconst( rhsConstant ).iadd();
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter iadd( final int lhsConstant, final @Symbol String rhsVar ) {
		return this.iconst( lhsConstant ).iload( rhsVar ).iadd();
	}

	@JvmOp( ladd.class )
	public final JvmCodeWriter ladd() {
		this.coreWriter().ladd();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter ladd( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.lload( lhsVar ).lload( rhsVar ).ladd();
	}
	
	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter ladd( final @Symbol String lhsVar, final long rhsConstant ) {
		return this.lload( lhsVar ).lconst( rhsConstant ).iadd();
	}
	
	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter ladd( final long lhsConstant, final @Symbol String rhsVar ) {
		return this.lconst( lhsConstant ).lload( rhsVar ).iadd();
	}

	@JvmOp( fadd.class )
	public final JvmCodeWriter fadd() {
		this.coreWriter().fadd();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter fadd( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.fload( lhsVar ).fload( rhsVar ).fadd();
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter fadd( final @Symbol String lhsVar, final float rhsConstant ) {
		return this.fload( lhsVar ).fconst( rhsConstant ).fadd();
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter fadd( final float lhsConstant, final @Symbol String rhsVar ) {
		return this.fconst( lhsConstant ).fload( rhsVar ).fadd();
	}

	@JvmOp( dadd.class )
	public final JvmCodeWriter dadd() {
		this.coreWriter().dadd();
		return this;
	}

	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter dadd( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.dload( lhsVar ).dload( rhsVar ).dadd();
	}
	
	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter dadd( final @Symbol String lhsVar, final double rhsConstant ) {
		return this.dload( lhsVar ).dconst( rhsConstant ).dadd();
	}
	
	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter dadd( final double lhsConstant, final @Symbol String rhsVar ) {
		return this.dconst( lhsConstant ).dload( rhsVar ).dadd();
	}
	
	@JvmOp( isub.class )
	public final JvmCodeWriter isub() {
		this.coreWriter().isub();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter isub( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.iload( lhsVar ).iload( rhsVar ).isub();
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter isub( final @Symbol String lhsVar, final int rhsConstant ) {
		return this.iload( lhsVar ).iconst( rhsConstant ).isub();
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter isub( final int lhsConstant, final @Symbol String rhsVar ) {
		return this.iconst( lhsConstant ).iload( rhsVar ).isub();
	}

	@JvmOp( lsub.class )
	public final JvmCodeWriter lsub() {
		this.coreWriter().lsub();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter lsub( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.lload( lhsVar ).lload( rhsVar ).lsub();
	}

	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter lsub( final @Symbol String lhsVar, final long rhsConstant ) {
		return this.lload( lhsVar ).lconst( rhsConstant ).lsub();
	}
	
	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter lsub( final long lhsConstant, final @Symbol String rhsVar ) {
		return this.lconst( lhsConstant ).lload( rhsVar ).lsub();
	}

	@JvmOp( fsub.class )
	public final JvmCodeWriter fsub() {
		this.coreWriter().fsub();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter fsub( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.fload( lhsVar ).fload( rhsVar ).fsub();
	}

	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter fsub( final @Symbol String lhsVar, final float rhsConstant ) {
		return this.fload( lhsVar ).fconst( rhsConstant ).fsub();
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter fsub( final float lhsConstant, final @Symbol String rhsVar ) {
		return this.fconst( lhsConstant ).fload( rhsVar ).fsub();
	}
	
	@JvmOp( dsub.class )
	public final JvmCodeWriter dsub() {
		this.coreWriter().dsub();
		return this;
	}

	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter dsub( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.dload( lhsVar ).dload( rhsVar ).dsub();
	}
	
	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter dsub( final @Symbol String lhsVar, final double rhsConstant ) {
		return this.dload( lhsVar ).dconst( rhsConstant ).dsub();
	}
	
	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter dsub( final double lhsConstant, final @Symbol String rhsVar ) {
		return this.dconst( lhsConstant ).dload( rhsVar ).dsub();
	}
	
	@JvmOp( imul.class )
	public final JvmCodeWriter imul() {
		this.coreWriter().imul();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter imul( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.iload( lhsVar ).iload( rhsVar ).imul();
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter imul( final @Symbol String lhsVar, final int rhsConstant ) {
		return this.iload( lhsVar ).iconst( rhsConstant ).imul();
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter imul( final int lhsConstant, final @Symbol String rhsVar ) {
		return this.iconst( lhsConstant ).iload( rhsVar ).imul();
	}

	@JvmOp( lmul.class )
	public final JvmCodeWriter lmul() {
		this.coreWriter().lmul();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter lmul( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.lload( lhsVar ).lload( rhsVar ).lmul();
	}
	
	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter lmul( final @Symbol String lhsVar, final long rhsConstant ) {
		return this.lload( lhsVar ).lconst( rhsConstant ).lmul();
	}
	
	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter imul( final long lhsConstant, final @Symbol String rhsVar ) {
		return this.lconst( lhsConstant ).lload( rhsVar ).lmul();
	}
	
	@JvmOp( fmul.class )
	public final JvmCodeWriter fmul() {
		this.coreWriter().fmul();
		return this;
	}

	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter fmul( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.fload( lhsVar ).fload( rhsVar ).fmul();
	}

	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter fmul( final @Symbol String lhsVar, final long rhsConstant ) {
		return this.fload( lhsVar ).fconst( rhsConstant ).fmul();
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter fmul( final long lhsConstant, final @Symbol String rhsVar ) {
		return this.fconst( lhsConstant ).fload( rhsVar ).fmul();
	}
	
	@JvmOp( dmul.class )
	public final JvmCodeWriter dmul() {
		this.coreWriter().dmul();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter dmul( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.dload( lhsVar ).dload( rhsVar ).fmul();
	}

	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter dmul( final @Symbol String lhsVar, final double rhsConstant ) {
		return this.dload( lhsVar ).dconst( rhsConstant ).fmul();
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter dmul( final long lhsConstant, final @Symbol String rhsVar ) {
		return this.dconst( lhsConstant ).dload( rhsVar ).fmul();
	}
	
	@JvmOp( idiv.class )
	public final JvmCodeWriter idiv() {
		this.coreWriter().idiv();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter idiv( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.iload( lhsVar ).iload( rhsVar ).idiv();
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter idiv( final @Symbol String lhsVar, final int rhsConstant ) {
		return this.iload( lhsVar ).iconst( rhsConstant ).idiv();
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter idiv( final int lhsConstant, final @Symbol String rhsVar ) {
		return this.iconst( lhsConstant ).iload( rhsVar ).idiv();
	}

	@JvmOp( ldiv.class )
	public final JvmCodeWriter ldiv() {
		this.coreWriter().ldiv();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter ldiv( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.lload( lhsVar ).lload( rhsVar ).ldiv();
	}

	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter ldiv( final @Symbol String lhsVar, final long rhsConstant ) {
		return this.lload( lhsVar ).lconst( rhsConstant ).ldiv();
	}

	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter ldiv( final long lhsConstant, final @Symbol String rhsVar ) {
		return this.lconst( lhsConstant ).lload( rhsVar ).ldiv();
	}
	
	@JvmOp( fdiv.class )
	public final JvmCodeWriter fdiv() {
		this.coreWriter().fdiv();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter fdiv( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.fload( lhsVar ).fload( rhsVar ).fdiv();
	}

	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter fdiv( final @Symbol String lhsVar, final float rhsConstant ) {
		return this.fload( lhsVar ).fconst( rhsConstant ).fdiv();
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter fdiv( final float lhsConstant , final @Symbol String rhsVar ) {
		return this.fconst( lhsConstant ).fload( rhsVar ).fdiv();
	}
	
	@JvmOp( ddiv.class )
	public final JvmCodeWriter ddiv() {
		this.coreWriter().ddiv();
		return this;
	}

	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter ddiv( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.dload( lhsVar ).dload( rhsVar ).ddiv();
	}

	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter ddiv( final @Symbol String lhsVar, final double rhsConstant ) {
		return this.dload( lhsVar ).dconst( rhsConstant ).ddiv();
	}

	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter ddiv( final double lhsConstant, final @Symbol String rhsVar ) {
		return this.dconst( lhsConstant ).dload( rhsVar ).ddiv();
	}
	
	@JvmOp( irem.class )
	public final JvmCodeWriter irem() {
		this.coreWriter().irem();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter irem( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.iload( lhsVar ).iload( rhsVar ).irem();
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter irem( final @Symbol String lhsVar, final int rhsConstant ) {
		return this.iload( lhsVar ).iconst( rhsConstant ).irem();
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter irem( final int lhsConstant, final @Symbol String rhsVar ) {
		return this.iconst( lhsConstant ).iload( rhsVar ).irem();
	}

	@JvmOp( lrem.class )
	public final JvmCodeWriter lrem() {
		this.coreWriter().lrem();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter lrem( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.lload( lhsVar ).lload( rhsVar ).lrem();
	}

	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter lrem( final @Symbol String lhsVar, final long rhsConstant ) {
		return this.lload( lhsVar ).lconst( rhsConstant ).lrem();
	}
	
	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter lrem( final long lhsConstant, final @Symbol String rhsVar ) {
		return this.lconst( lhsConstant ).lload( rhsVar ).lrem();
	}
	
	@JvmOp( frem.class )
	public final JvmCodeWriter frem() {
		this.coreWriter().frem();
		return this;
	}

	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter frem( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.fload( lhsVar ).fload( rhsVar ).frem();
	}

	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter frem( final @Symbol String lhsVar, final float rhsConstant ) {
		return this.fload( lhsVar ).fconst( rhsConstant ).frem();
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter frem( final long lhsConstant, final @Symbol String rhsVar ) {
		return this.fconst( lhsConstant ).fload( rhsVar ).frem();
	}
	
	@JvmOp( drem.class )
	public final JvmCodeWriter drem() {
		this.coreWriter().drem();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter drem( final @Symbol String lhsVar, final @Symbol String rhsVar ) {
		return this.dload( lhsVar ).dload( rhsVar ).drem();
	}

	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter drem( final @Symbol String lhsVar, final double rhsConstant ) {
		return this.dload( lhsVar ).dconst( rhsConstant ).drem();
	}
	
	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter drem( final double lhsConstant, final @Symbol String rhsVar ) {
		return this.dconst( lhsConstant ).dload( rhsVar ).drem();
	}
	
	@JvmOp( ineg.class )
	public final JvmCodeWriter ineg() {
		this.coreWriter().ineg();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter ineg( final @Symbol String var ) {
		return this.iload( var ).ineg();
	}

	@JvmOp( lneg.class )
	public final JvmCodeWriter lneg() {
		this.coreWriter().lneg();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter lneg( final @Symbol String var ) {
		return this.lload( var ).lneg();
	}

	@JvmOp( fneg.class )
	public final JvmCodeWriter fneg() {
		this.coreWriter().fneg();
		return this;
	}

	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter fneg( final @Symbol String var ) {
		return this.fload( var ).fneg();
	}
	
	@JvmOp( dneg.class )
	public final JvmCodeWriter dneg() {
		this.coreWriter().dneg();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter dneg( final @Symbol String var ) {
		this.dload( var ).dneg();
		return this;
	}

	@JvmOp( ishl.class )
	public final JvmCodeWriter ishl() {
		this.coreWriter().ishl();
		return this;
	}

	@JvmOp( lshl.class )
	public final JvmCodeWriter lshl() {
		this.coreWriter().lshl();
		return this;
	}

	@JvmOp( ishr.class )
	public final JvmCodeWriter ishr() {
		this.coreWriter().ishr();
		return this;
	}

	@JvmOp( lshr.class )
	public final JvmCodeWriter lshr() {
		this.coreWriter().lshr();
		return this;
	}

	@JvmOp( iushr.class )
	public final JvmCodeWriter iushr() {
		this.coreWriter().iushr();
		return this;
	}

	@JvmOp( lushr.class )
	public final JvmCodeWriter lushr() {
		this.coreWriter().lushr();
		return this;
	}

	@JvmOp( iand.class )
	public final JvmCodeWriter iand() {
		this.coreWriter().iand();
		return this;
	}

	@JvmOp( land.class )
	public final JvmCodeWriter land() {
		this.coreWriter().land();
		return this;
	}

	@JvmOp( ior.class )
	public final JvmCodeWriter ior() {
		this.coreWriter().ior();
		return this;
	}

	@JvmOp( lor.class )
	public final JvmCodeWriter lor() {
		this.coreWriter().lor();
		return this;
	}

	@JvmOp( ixor.class )
	public final JvmCodeWriter ixor() {
		this.coreWriter().ixor();
		return this;
	}
	
	@SyntheticOp
	public final JvmCodeWriter ixor(
		final @Symbol String lhsVar,
		final @Symbol String rhsVar )
	{
		return this.iload( lhsVar ).iload( rhsVar ).ixor();
	}
	
	@SyntheticOp
	public final JvmCodeWriter ixor(
		final JakCondition lhsCond,
		final JakCondition rhsCond )
	{
		return this.expr( lhsCond ).expr( rhsCond ).ixor();
	}
	
	@SyntheticOp
	public final JvmCodeWriter ixor(
		final JakExpression lhsExpr,
		final JakExpression rhsExpr )
	{
		return this.expr( lhsExpr ).expr( rhsExpr ).ixor();
	}

	@JvmOp( lxor.class )
	public final JvmCodeWriter lxor() {
		this.coreWriter().lxor();
		return this;
	}
	
	@WrapOp( iinc.class )
	public final JvmCodeWriter idec( final @Symbol String var ) {
		return this.iinc( var, -1 );
	}
	
	@WrapOp( iinc.class )
	public final JvmCodeWriter idec( final int slot ) {
		return this.iinc( slot, -1 );
	}
	
	@WrapOp( iinc.class )
	public final JvmCodeWriter iinc( final @Symbol String var ) {
		return this.iinc( var, 1 );
	}

	@WrapOp( iinc.class )
	public final JvmCodeWriter iinc( final @Symbol String var, final int amount ) {
		return this.iinc( this.getVarSlot( var ), amount );
	}

	@WrapOp( iinc.class )
	public final JvmCodeWriter iinc( final int slot ) {
		return this.iinc( slot, 1 );
	}

	@JvmOp( iinc.class )
	public final JvmCodeWriter iinc( final int slot, final int amount ) {
		this.coreWriter().iinc( slot, amount );
		return this;
	}

	@JvmOp( i2l.class )
	public final JvmCodeWriter i2l() {
		this.coreWriter().i2l();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter i2l( final @Symbol String var ) {
		return this.iload( var ).i2l();
	}

	@JvmOp( i2f.class )
	public final JvmCodeWriter i2f() {
		this.coreWriter().i2f();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter i2f( final @Symbol String var ) {
		return this.iload( var ).i2f();
	}

	@JvmOp( i2d.class )
	public final JvmCodeWriter i2d() {
		this.coreWriter().i2d();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter i2d( final @Symbol String var ) {
		return this.iload( var ).i2d();
	}

	@JvmOp( l2i.class )
	public final JvmCodeWriter l2i() {
		this.coreWriter().l2i();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter l2i( final @Symbol String var ) {
		return this.lload( var ).l2i();
	}

	@JvmOp( l2f.class )
	public final JvmCodeWriter l2f() {
		this.coreWriter().l2f();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter l2f( final @Symbol String var ) {
		return this.lload( var ).l2f();
	}

	@JvmOp( l2d.class )
	public final JvmCodeWriter l2d() {
		this.coreWriter().l2d();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter l2d( final @Symbol String var ) {
		return this.lload( var ).l2d();
	}

	@JvmOp( f2i.class )
	public final JvmCodeWriter f2i() {
		this.coreWriter().f2i();
		return this;
	}

	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter f2i( final @Symbol String var ) {
		return this.fload( var ).f2i();
	}
	
	@JvmOp( f2l.class )
	public final JvmCodeWriter f2l() {
		this.coreWriter().f2l();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter f2l( final @Symbol String var ) {
		return this.fload( var ).f2l();
	}
	
	@JvmOp( f2d.class )
	public final JvmCodeWriter f2d() {
		this.coreWriter().f2d();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=double.class )
	public final JvmCodeWriter f2d( final @Symbol String var ) {
		return this.fload( var ).f2d();
	}

	@JvmOp( d2i.class )
	public final JvmCodeWriter d2i() {
		this.coreWriter().d2i();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter d2i( final @Symbol String var ) {
		return this.dload( var ).d2i();
	}

	@JvmOp( d2l.class )
	public final JvmCodeWriter d2l() {
		this.coreWriter().d2l();
		return this;
	}

	@SyntheticOp( stackResultTypes=long.class )
	public final JvmCodeWriter d2l( final @Symbol String var ) {
		return this.dload( var ).d2l();
	}

	@JvmOp( d2f.class )
	public final JvmCodeWriter d2f() {
		this.coreWriter().d2f();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=float.class )
	public final JvmCodeWriter d2f( final @Symbol String var ) {
		return this.dload( var ).d2f();
	}

	@JvmOp( i2b.class )
	public final JvmCodeWriter i2b() {
		this.coreWriter().i2b();
		return this;
	}

	@SyntheticOp( stackResultTypes=byte.class )
	public final JvmCodeWriter i2b( final @Symbol String var ) {
		return this.iload( var ).i2b();
	}

	@JvmOp( i2c.class )
	public final JvmCodeWriter i2c() {
		this.coreWriter().i2c();
		return this;
	}

	@SyntheticOp( stackResultTypes=char.class )
	public final JvmCodeWriter i2c( final @Symbol String var ) {
		return this.iload( var ).i2c();
	}
	
	@JvmOp( i2s.class )
	public final JvmCodeWriter i2s() {
		this.coreWriter().i2s();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=short.class )
	public final JvmCodeWriter i2s( final @Symbol String var ) {
		return this.iload( var ).i2s();
	}

	@JvmOp( lcmp.class )
	public final JvmCodeWriter lcmp() {
		this.coreWriter().lcmp();
		return this;
	}
	
	@SyntheticOp( stackOperandTypes=int.class )
	public final JvmCodeWriter lcmp( final JakExpression lhsExpr, final JakExpression rhsExpr ) {
		return this.expr( lhsExpr ).expr( rhsExpr ).lcmp();
	}

	@JvmOp( fcmpl.class )
	public final JvmCodeWriter fcmpl() {
		this.coreWriter().fcmpl();
		return this;
	}

	@JvmOp( fcmpg.class )
	public final JvmCodeWriter fcmpg() {
		this.coreWriter().fcmpg();
		return this;
	}

	@JvmOp( dcmpl.class )
	public final JvmCodeWriter dcmpl() {
		this.coreWriter().dcmpl();
		return this;
	}

	@JvmOp( dcmpg.class )
	public final JvmCodeWriter dcmpg() {
		this.coreWriter().dcmpg();
		return this;
	}
	
	private final IfConstruct ifUnderConstruction() {
		if ( this.underConstructionMacro instanceof IfConstruct ) {
			return (IfConstruct)this.underConstructionMacro;
		} else {
			throw new IllegalStateException( "No if under construction" );
		}
	}
	
	public final JvmCodeWriter if_( final JakCondition condition, final stmt stmt ) {
		return this.startConstruction( new IfConstruct( condition, stmt ) );
	}
	
	public final JvmCodeWriter if_( final JakExpression expr, final stmt stmt ) {
		return this.if_( ne( expr, const_( 0 ) ), stmt );
	}
	
	public final JvmCodeWriter ifnonnull( final JakExpression expr, final stmt stmt ) {
		return this.if_( ne( expr, null_() ), stmt );
	}
	
	public final JvmCodeWriter ifnull( final JakExpression expr, final stmt stmt ) {
		return this.if_( eq( expr, null_() ), stmt );
	}
	
	public final JvmCodeWriter elseif( final JakCondition condition, final stmt stmt ) {
		this.ifUnderConstruction().addElseIf( condition, stmt );
		return this;
	}

	public final JvmCodeWriter elseif( final JakExpression expr, final stmt stmt ) {
		return this.elseif( ne( expr, const_( 0 ) ), stmt );
	}
	
	public final JvmCodeWriter elseif_nonnull( final JakExpression expr, final stmt stmt ) {
		return this.elseif( ne( expr, null_() ), stmt );
	}
	
	public final JvmCodeWriter elseif_null( final JakExpression expr, final stmt stmt ) {
		return this.elseif( eq( expr, null_() ), stmt );
	}
	
	public final JvmCodeWriter else_( final stmt stmt ) {
		this.ifUnderConstruction().addElse( stmt );
		return this;
	}
	
	public final JvmCodeWriter ternary(
		final JakCondition cond, 
		final JakExpression trueExpr,
		final JakExpression falseExpr )
	{
		return this.expr( new Ternary( cond, trueExpr, falseExpr ) );
	}
	
	@Override
	public final JvmCodeWriter if_(
		final JakCondition condition,
		final @Symbol String label )
	{
		condition.accept( new JvmConditionVisitor( this.context() ) {
			@Override
			protected final void and( final JakCondition lhs, final JakCondition rhs ) {
				JvmCodeWriter.this.startLabelScope();
				try {
					//TODO: optimize?
					JvmCodeWriter.this.
						if_( JakAsm.not( lhs ), "andFalse" ).
						if_( JakAsm.not( rhs ), "andFalse" ).
						true_().
						goto_( "endAnd" ).
						label( "andFalse" ).
						false_().
						label( "endAnd" );
						ifne( label );
				} finally {
					JvmCodeWriter.this.endLabelScope();
				}
			}
			
			@Override
			protected final void or( final JakCondition lhs, final JakCondition rhs ) {
				JvmCodeWriter.this.startLabelScope();
				try {
					//TODO: optimize?
					JvmCodeWriter.this.
						if_( lhs, "orTrue" ).
						if_( rhs, "orTrue" ).
						false_().
						goto_( "endOr" ).
						label( "orTrue" ).
						true_().
						label( "endOr" );
						ifne( label );
				} finally {
					JvmCodeWriter.this.endLabelScope();
				}
			}
			
			@Override
			protected final void xor( final JakCondition lhs, final JakCondition rhs ) {
				JvmCodeWriter.this.
					ixor( lhs, rhs ).
					ifne( label );
			}
			
			@Override
			protected void not( final JakCondition cond ) {
				JvmCodeWriter.this.expr( cond );
				JvmCodeWriter.this.ifeq( label );
			}

			@Override
			protected final void ieq( final JakExpression lhs, final JakExpression rhs ) {
				if ( isZero( rhs ) ) {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.ifeq( label );
				} else if ( isZero( lhs ) ) {
					JvmCodeWriter.this.expr( rhs );
					JvmCodeWriter.this.ifeq( label );
				} else {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.expr( rhs );
					JvmCodeWriter.this.if_icmpeq( label );
				}
			}
			
			@Override
			protected final void ine( final JakExpression lhs, final JakExpression rhs ) {
				if ( isZero( rhs ) ) {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.ifne( label );
				} else if ( isZero( lhs ) ) {
					JvmCodeWriter.this.expr( rhs );
					JvmCodeWriter.this.ifne( label );
				} else {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.expr( rhs );
					JvmCodeWriter.this.if_icmpne( label );
				}
			}
			
			@Override
			protected final void ilt( final JakExpression lhs, final JakExpression rhs ) {
				if ( isZero( rhs ) ) {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.iflt( label );
				} else {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.expr( rhs );
					JvmCodeWriter.this.if_icmplt( label );
				}
			}
			
			@Override
			protected final void ile( final JakExpression lhs, final JakExpression rhs ) {
				if ( isZero( rhs ) ) {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.ifle( label );
				} else {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.expr( rhs );
					JvmCodeWriter.this.if_icmple( label );
				}
			}
			
			@Override
			protected final void igt( final JakExpression lhs, final JakExpression rhs ) {
				if ( isZero( rhs ) ) {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.ifgt( label );
				} else {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.expr( rhs );
					JvmCodeWriter.this.if_icmpgt( label );
				}
			}
			
			@Override
			protected final void ige( final JakExpression lhs, final JakExpression rhs ) {
				if ( isZero( rhs ) ) {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.ifge( label );
				} else {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.expr( rhs );
					JvmCodeWriter.this.if_icmpge( label );
				}
			}
			
			@Override
			protected final void leq( final JakExpression lhs, final JakExpression rhs ) {
				JvmCodeWriter.this.lcmp( lhs, rhs );
				JvmCodeWriter.this.ifeq( label );
			}
			
			@Override
			protected final void lne( final JakExpression lhs, final JakExpression rhs ) {
				JvmCodeWriter.this.lcmp( lhs, rhs );
				JvmCodeWriter.this.ifne( label );
			}
			
			@Override
			protected final void llt( final JakExpression lhs, final JakExpression rhs ) {
				JvmCodeWriter.this.lcmp( lhs, rhs );
				JvmCodeWriter.this.iflt( label );
			}
			
			@Override
			protected final void lle( final JakExpression lhs, final JakExpression rhs ) {
				JvmCodeWriter.this.lcmp( lhs, rhs );
				JvmCodeWriter.this.ifle( label );
			}

			@Override
			protected final void lgt( final JakExpression lhs, final JakExpression rhs ) {
				JvmCodeWriter.this.lcmp( lhs, rhs );
				JvmCodeWriter.this.ifgt( label );
			}
			
			@Override
			protected final void lge( final JakExpression lhs, final JakExpression rhs ) {
				JvmCodeWriter.this.lcmp( lhs, rhs );
				JvmCodeWriter.this.ifge( label );
			}
			
			@Override
			protected void aeq( final JakExpression lhs, final JakExpression rhs ) {
				if ( isNull( rhs ) ) {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.ifnull( label );
				} else {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.expr( rhs );
					JvmCodeWriter.this.if_acmpeq( label );
				}
			}
			
			@Override
			protected void ane( final JakExpression lhs, final JakExpression rhs ) {
				if ( isNull( rhs ) ) {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.ifnonnull( label );
				} else {
					JvmCodeWriter.this.expr( lhs );
					JvmCodeWriter.this.expr( rhs );
					JvmCodeWriter.this.if_acmpne( label );
				}
			}
		});
		
		return this;
	}
	
	@SyntheticOp
	public final JvmCodeWriter if_( final @Symbol String label ) {
		return this.ifne( label );
	}
	
	@SyntheticOp
	public final JvmCodeWriter ifnot( final @Symbol String label ) {
		return this.ifeq( label );
	}

	@WrapOp( ifeq.class )
	public final JvmCodeWriter ifeq( final @Symbol String label ) {
		this.coreWriter().ifeq( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( ifne.class )
	public final JvmCodeWriter ifne( final @Symbol String label ) {
		this.coreWriter().ifne( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( iflt.class )
	public final JvmCodeWriter iflt( final @Symbol String label ) {
		this.coreWriter().iflt( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( ifgt.class )
	public final JvmCodeWriter ifgt( final @Symbol String label ) {
		this.coreWriter().ifgt( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( ifge.class )
	public final JvmCodeWriter ifge( final @Symbol String label ) {
		this.coreWriter().ifge( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( ifle.class )
	public final JvmCodeWriter ifle( final @Symbol String label ) {
		this.coreWriter().ifle( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_icmpeq.class )
	public final JvmCodeWriter if_icmpeq( final @Symbol String label ) {
		this.coreWriter().if_icmpeq( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_icmpne.class )
	public final JvmCodeWriter if_icmpne( final @Symbol String label ) {
		this.coreWriter().if_icmpne( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_icmplt.class )
	public final JvmCodeWriter if_icmplt( final @Symbol String label ) {
		this.coreWriter().if_icmplt( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_icmpgt.class )
	public final JvmCodeWriter if_icmpgt( final @Symbol String label ) {
		this.coreWriter().if_icmpgt( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_icmpge.class )
	public final JvmCodeWriter if_icmpge( final @Symbol String label ) {
		this.coreWriter().if_icmpge( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_icmple.class )
	public final JvmCodeWriter if_icmple( final @Symbol String label ) {
		this.coreWriter().if_icmple( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_acmpeq.class )
	public final JvmCodeWriter if_acmpeq( final @Symbol String label ) {
		this.coreWriter().if_acmpeq( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( if_acmpne.class )
	public final JvmCodeWriter if_acmpne( final @Symbol String label ) {
		this.coreWriter().if_acmpne( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( goto_.class )
	public final JvmCodeWriter goto_( final @Symbol String label ) {
		this.coreWriter().goto_( this.jumpTo( label ) );
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
	
	@SyntheticOp
	public final JvmCodeWriter trapReturn() {
		this.sharedState().trapReturn = true;
		this.startScope();
		return this;
	}
	
	private JvmCodeWriter return_( final Class<?> slotType ) {
		if ( this.sharedState().trapReturn ) {
			this.sharedState().returnType = slotType;
			
			if ( ! slotType.equals( void.class ) ) {
				this.declare( slotType, "returnValue" );
				this.store( slotType, "returnValue" );
			}
		} else {
			this.writeReturn( slotType );
		}
		return this;
	}
	
	private final void writeReturn( final Class<?> slotType ) {
		if ( slotType.equals( void.class ) ) {
			this.coreWriter().return_();
		} else if ( slotType.equals( int.class ) ) {
			this.coreWriter().ireturn();
		} else if ( slotType.equals( long.class ) ) {
			this.coreWriter().lreturn();
		} else if ( slotType.equals( float.class ) ) {
			this.coreWriter().freturn();
		} else if ( slotType.equals( double.class ) ) {
			this.coreWriter().dreturn();
		} else {
			this.coreWriter().areturn();
		}
	}
	
	@SyntheticOp
	public final JvmCodeWriter releaseReturn() {
		boolean trappedReturn = ( this.sharedState().trapReturn && this.sharedState().returnType != null );
		if ( trappedReturn ) {
			Class<?> slotType = this.sharedState().returnType;
			if ( ! slotType.equals( void.class ) ) {
				this.load( slotType, "returnValue" );
			}
			this.writeReturn( slotType );
			
			this.sharedState().trapReturn = false;
			this.sharedState().returnType = null;
		}
		this.endScope();
		return this;
	}

	@JvmOp( ireturn.class )
	public final JvmCodeWriter ireturn() {
		return this.return_( int.class );
	}
	
	@SyntheticOp
	public final JvmCodeWriter ireturn( final JakExpression expr ) {
		return this.expr( expr ).ireturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter ireturn( final JakCondition cond ) {
		return this.expr( cond ).ireturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter ireturn( final boolean value ) {
		return this.iconst( value ).ireturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter ireturn( final char value ) {
		return this.iconst( value ).ireturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter ireturn( final byte value ) {
		return this.iconst( value ).ireturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter ireturn( final short value ) {
		return this.iconst( value ).ireturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter ireturn( final int value ) {
		return this.iconst( value ).ireturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter ireturn( final String var ) {
		return this.iload( var ).ireturn();
	}

	@JvmOp( lreturn.class )
	public final JvmCodeWriter lreturn() {
		return this.return_( long.class );
	}
	
	@SyntheticOp
	public final JvmCodeWriter lreturn( final JakExpression expr ) {
		return this.expr( expr ).lreturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter lreturn( final long value ) {
		return this.lconst( value ).lreturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter lreturn( final String var ) {
		return this.lload( var ).lreturn();
	}

	@JvmOp( freturn.class )
	public final JvmCodeWriter freturn() {
		return this.return_( float.class );
	}
	
	@SyntheticOp
	public final JvmCodeWriter freturn( final JakExpression expr ) {
		return this.expr( expr ).freturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter freturn( final float value ) {
		return this.fconst( value ).freturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter freturn( final String var ) {
		return this.fload( var ).freturn();
	}

	@JvmOp( dreturn.class )
	public final JvmCodeWriter dreturn() {
		return this.return_( double.class );
	}
	
	@SyntheticOp
	public final JvmCodeWriter dreturn( final JakExpression expr ) {
		return this.expr( expr ).dreturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter dreturn( final float value ) {
		return this.dconst( value ).dreturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter dreturn( final String var ) {
		return this.dload( var ).dreturn();
	}

	@JvmOp( areturn.class )
	public final JvmCodeWriter areturn() {
		return this.return_( Object.class );
	}
	
	@SyntheticOp
	public final JvmCodeWriter areturn( final JakExpression expr ) {
		return this.expr( expr ).areturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter areturn_null() {
		return this.aconst_null().areturn();
	}
	
	@SyntheticOp
	public final JvmCodeWriter areturn( final @Symbol String var ) {
		return this.aload( var ).areturn();
	}

	@JvmOp( return_.class )
	public final JvmCodeWriter return_() {
		return this.return_( void.class );
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
		this.coreWriter().getstatic( targetType, field );
		return this;
	}

	@WrapOp( getstatic.class )
	public final JvmCodeWriter getstatic( final Field field ) {
		this.coreWriter().getstatic(
			field.getDeclaringClass(),
			field( field ) );
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
		this.coreWriter().putstatic( targetType, field );
		return this;
	}

	@WrapOp( putstatic.class )
	public final JvmCodeWriter putstatic( final Field field ) {
		this.coreWriter().putstatic(
			field.getDeclaringClass(),
			field( field ) );
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
		this.coreWriter().getfield( targetType, field );
		return this;
	}

	@WrapOp( getfield.class )
	public final JvmCodeWriter getfield( final Field field ) {
		this.coreWriter().getfield(
			field.getDeclaringClass(),
			field( field ) );
		
		return this;
	}

	@WrapOp( putfield.class )
	public final JvmCodeWriter putfield(
		final Type targetType,
		final @Symbol String fieldName )
	{
		return this.putfield( getField( targetType, fieldName ) );
	}

	@JvmOp( putfield.class )
	public final JvmCodeWriter putfield(
		final Type targetType,
		final JavaField field )
	{
		this.coreWriter().putfield(
			targetType,
			field( field.getType(), field.getName() ) );
		return this;
	}
	
	@JvmOp( putfield.class )
	public final JvmCodeWriter putfield(
		final Type targetType,
		final Type fieldType,
		final String fieldName )
	{
		return this.putfield( targetType, field( fieldType, fieldName ) );
	}

	@WrapOp( putfield.class )
	public final JvmCodeWriter putfield( final Field field ) {
		this.coreWriter().putfield(
			field.getDeclaringClass(),
			field( field.getType(), field.getName() ) );
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
		return this.invoke( targetType, method( methodName, args ) );
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
			method( method ) );
	}
	
	@JvmOp( invokeinterface.class )
	public final JvmCodeWriter invokeinterface( final Method method ) {
		return this.invokeinterface(
			method.getDeclaringClass(),
			method( method ) );
	}

	@JvmOp( invokespecial.class )
	public final JvmCodeWriter invokespecial( final Method method ) {
		return this.invokespecial(
			method.getDeclaringClass(),
			method( method ) );
	}
	
	@JvmOp( invokestatic.class )
	public final JvmCodeWriter invokestatic( final Method method ) {
		return this.invokestatic(
			method.getDeclaringClass(),
			method( method ) );
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

	@JvmOp( invokevirtual.class )
	public final JvmCodeWriter invokevirtual(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.coreWriter().invokevirtual( targetType, method );
		return this;
	}

	@WrapOp( invokevirtual.class )
	public final JvmCodeWriter invokevirtual(
		final Type targetType,
		final Type returnType,
		final @Symbol String methodName,
		final Type... argumentTypes )
	{
		this.coreWriter().invokevirtual(
			targetType,
			method(
				returnType,
				methodName,
				argumentTypes ) );
		return this;
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
		this.coreWriter().invokespecial( targetType, method );
		return this;
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
			method( returnType, methodName, argumentTypes ) );
	}

	@JvmOp( invokestatic.class )
	public final JvmCodeWriter invokestatic(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.coreWriter().invokestatic( targetType, method );
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
			method( returnType, methodName, argumentTypes ) );
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
		this.coreWriter().invokeinterface( targetType, method );
		return this;
	}

	@JvmOp( new_.class )
	public final JvmCodeWriter new_( final Type type ) {
		this.coreWriter().new_( type );
		return this;
	}
	
	@SyntheticOp( stackOperandTypes=Reference.class )
	public final JvmCodeWriter new_default_init( final Type type ) {
		this.new_( type );
		this.dup();
		this.invokespecial( type, init() );
		return this;
	}

	@JvmOp( newarray.class )
	public final JvmCodeWriter newarray( final Type componentType ) {
		this.coreWriter().newarray( componentType );
		return this;
	}
	
	@SyntheticOp( stackResultTypes=Any[].class )
	public final JvmCodeWriter newarray( final Type componentType, final int value ) {
		return this.iconst( value ).anewarray( componentType );
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
	
	@SyntheticOp( stackOperandTypes=boolean[].class )
	public final JvmCodeWriter barray( final List< Boolean > booleans ) {
		this.iconst( booleans.size() ).newarray( boolean.class );

		for ( ListIterator< Boolean > iter = booleans.listIterator(); iter.hasNext(); ) {
			int index = iter.nextIndex();
			Boolean value = iter.next();
			
			if ( value ) {
				this.dup().
					iconst( index ).
					iconst( value ).
					fastore();
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
	
	@SyntheticOp( stackOperandTypes=short[].class )
	public final JvmCodeWriter sarray( final List< Short > shorts ) {
		this.iconst( shorts.size() ).newarray( short.class );

		for ( ListIterator< Short > iter = shorts.listIterator(); iter.hasNext(); ) {
			int index = iter.nextIndex();
			Short value = iter.next();
			
			if ( value != 0 ) {
				this.dup().
					iconst( index ).
					iconst( value ).
					iastore();
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
	
	@SyntheticOp( stackOperandTypes=int[].class )
	public final JvmCodeWriter iarray( final List< Integer > ints ) {
		this.iconst( ints.size() ).newarray( int.class );

		for ( ListIterator< Integer > iter = ints.listIterator(); iter.hasNext(); ) {
			int index = iter.nextIndex();
			Integer value = iter.next();
			
			if ( value != 0 ) {
				this.dup().
					iconst( index ).
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

	@SyntheticOp( stackResultTypes=float[].class )
	public final JvmCodeWriter farray( final List< Float > floats ) {
		this.iconst( floats.size() ).newarray( float.class );

		for ( ListIterator< Float > iter = floats.listIterator(); iter.hasNext(); ) {
			int index = iter.nextIndex();
			Float value = iter.next();
			
			if ( value != 0 ) {
				this.dup().
					iconst( index ).
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

	@SyntheticOp( stackResultTypes=long[].class )
	public final JvmCodeWriter larray( final List< Long > longs ) {
		this.iconst( longs.size() ).newarray( long.class );

		for ( ListIterator< Long > iter = longs.listIterator(); iter.hasNext(); ) {
			int index = iter.nextIndex();
			Long value = iter.next();
			
			if ( value != 0 ) {
				this.dup().
					iconst( index ).
					lconst( value ).
					fastore();
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
	
	@SyntheticOp
	public final JvmCodeWriter darray( final List< Double > doubles ) {
		this.iconst( doubles.size() ).newarray( double.class );

		for ( ListIterator< Double > iter = doubles.listIterator(); iter.hasNext(); ) {
			int index = iter.nextIndex();
			Double value = iter.next();
			
			if ( value != 0 ) {
				this.dup().
					iconst( index ).
					dconst( value ).
					fastore();
			}
		}

		return this;
	}

	@JvmOp( anewarray.class )
	public final JvmCodeWriter anewarray( final Type componentType ) {
		this.coreWriter().anewarray( componentType );
		return this;
	}
	
	@SyntheticOp( stackResultTypes=Reference[].class )
	public final JvmCodeWriter anewarray( final Type componentType, final int value ) {
		return this.iconst( value ).anewarray( componentType );
	}
	
	@JvmOp( arraylength.class )
	public final JvmCodeWriter arraylength() {
		this.coreWriter().arraylength();
		return this;
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter arraylength( final String var ) {
		return this.aload( var ).arraylength();
	}

	@JvmOp( athrow.class )
	public final JvmCodeWriter athrow() {
		this.coreWriter().athrow();
		return this;
	}
	
	@SyntheticOp
	public final JvmCodeWriter athrow( final @Symbol String var ) {
		return this.aload( var ).athrow();
	}

	@JvmOp( checkcast.class )
	public final JvmCodeWriter checkcast( final Type type ) {
		this.coreWriter().checkcast( type );
		return this;
	}
	
	@JvmOp( instanceof_.class )
	public final JvmCodeWriter instanceof_( final Type type ) {
		this.coreWriter().instanceof_( type );
		return this;
	}
	
	@SyntheticOp
	public final JvmCodeWriter synchronized_(
		final String var,
		final stmt stmt )
	{
		return this.macro( new Synchronized( var, stmt ) );
	}

	@JvmOp( monitorenter.class )
	public final JvmCodeWriter monitorenter() {
		this.coreWriter().monitorenter();
		return this;
	}
	
	@SyntheticOp
	public final JvmCodeWriter monitorenter( final @Symbol String var ) {
		return this.aload( var ).monitorenter();
	}

	@JvmOp( monitorexit.class )
	public final JvmCodeWriter monitorexit() {
		this.coreWriter().monitorexit();
		return this;
	}
	
	@SyntheticOp
	public final JvmCodeWriter monitorexit( final @Symbol String var ) {
		return this.aload( var ).monitorexit();
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
		this.coreWriter().multianewarray( arrayType, numDimensions );
		return this;
	}

	@WrapOp( ifnull.class )
	public final JvmCodeWriter ifnull( final String label ) {
		this.coreWriter().ifnull( this.jumpTo( label ) );
		return this;
	}

	@WrapOp( ifnonnull.class )
	public final JvmCodeWriter ifnonnull( final String label ) {
		this.coreWriter().ifnonnull( this.jumpTo( label ) );
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
		JvmCoreCodeWriter coreWriter = this.coreWriter();
		
		coreWriter.ifeq( this.jumpRelative( +7 ) );
		coreWriter.iconst_0();
		coreWriter.goto_( this.jumpRelative( +4 ) );
		coreWriter.iconst_1();
		
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
			method( boxClass, "valueOf", primitiveClass ) );
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
			method( primitiveClass, methodName ) );
	}
	
	
	@SyntheticOp( stackOperandTypes=Integer.class, stackResultTypes=int.class )
	public final JvmCodeWriter ibox() {
		return this.box(int.class);
	}
	
	@SyntheticOp( stackOperandTypes=int.class, stackResultTypes=Integer.class )
	public final JvmCodeWriter iunbox() {
		return this.unbox(Integer.class);
	}
	
	@SyntheticOp( stackResultTypes=Integer.class )
	public final JvmCodeWriter ibox( final String var ) {
		return this.iload( var ).ibox();
	}
	
	@SyntheticOp( stackResultTypes=int.class )
	public final JvmCodeWriter iunbox( final String var ) {
		return this.aload( var ).iunbox();
	}
	
	@Override
	@SyntheticOp
	public final JvmCodeWriter startLabelScope() {
		this.sharedState().labelScope = this.labelScope( true ).startScope();
		return this;
	}
	
	@Override
	@SyntheticOp
	public final JvmCodeWriter label( final String labelName ) {
		this.labelScope( true ).set( labelName, this.coreWriter().pos() );
		return this;
	}
	
	@Override
	@SyntheticOp
	public final JvmCodeWriter endLabelScope() {
		this.sharedState().labelScope = this.labelScope( false ).endScope();
		return this;
	}
	
	public final JvmCodeWriter while_( final JakCondition condition, final stmt stmt ) {
		return this.macro( new While( condition, stmt ) );
	}
	
	public final JvmCodeWriter while_( final JakExpression expr, final stmt stmt ) {
		return this.while_( JakAsm.truthy( expr ), stmt );
	}
	
	public final JvmCodeWriter while_( final boolean value, final stmt stmt ) {
		return this.while_( JakAsm.const_( value ), stmt );
	}
	
	private final DoWhile doUnderConstruction() {
		if ( this.underConstructionMacro instanceof DoWhile ) {
			return (DoWhile)this.underConstructionMacro;
		} else {
			throw new IllegalStateException( "No do/while under construction" );
		}
	}
	
	public final JvmCodeWriter do_( final stmt stmt ) {
		return this.startConstruction( new DoWhile( stmt ) );
	}
	
	public final JvmCodeWriter while_( final JakCondition condition ) {
		this.doUnderConstruction().addWhile( condition );
		return this;
	}
	
	public final JvmCodeWriter while_( final JakExpression expression ) {
		return this.while_( JakAsm.truthy( expression ) );
	}
	
	public final JvmCodeWriter while_( final boolean value ) {
		return this.while_( JakAsm.const_( value ) );
	}
	
	public final JvmCodeWriter array_for(
		final Type elementType,
		final String var,
		final String arrayVar,
		final stmt stmt )
	{
		return this.macro( new ArrayFor( elementType, var, arrayVar, stmt ) );
	}
	
	public final JvmCodeWriter iterable_for(
		final Type elementType,
		final String var,
		final String iterableVar,
		final stmt stmt )
	{
		return this.macro( new IterableFor( elementType, var, iterableVar, stmt ) );
	}
	
	private final int getLabelPos( final String labelName ) {
		//TODO: Revisit how this is used -- may need to bind the jump to the 
		//scope object more directly
		Integer pos = this.labelScope( true ).get( labelName );
		if ( pos == null ) {
			throw new IllegalStateException( "Undefined label: " + labelName );
		} else {
			return pos;
		}
	}
	
	private final TryConstruct tryUnderConstruction() {
		if ( this.underConstructionMacro instanceof TryConstruct ) {
			return (TryConstruct)this.underConstructionMacro;
		} else {
			throw new IllegalStateException( "No try/catch/finally under construction" );
		}
	}
	
	public final JvmCodeWriter try_( final stmt stmt ) {
		return this.startConstruction( new TryConstruct( stmt ) );
	}
	
	public final JvmCodeWriter catch_(
		final Type exceptionType, final String var,
		final stmt stmt )
	{
		this.tryUnderConstruction().addCatch( exceptionType, var, stmt );
		return this;
	}
	
	public final JvmCodeWriter finally_( final stmt stmt ) {
		this.tryUnderConstruction().addFinally( stmt );
		return this;
	}

	@SyntheticOp( id="catch" )
	public final JvmCodeWriter catch_(
		final @Symbol String startLabel,
		final @Symbol String endLabel,
		final Type exceptionType )
	{
		Type resolvedType = this.resolver().resolve( exceptionType );
		
		this.coreWriter().handleException(
			new CatchExceptionHandler(
				this.labelScope( false ),
				startLabel,
				endLabel,
				resolvedType,
				this.coreWriter().pos() ) );
		return this;
	}

	@SyntheticOp( id="exception" )
	public final JvmCodeWriter exception(
		final @Symbol String startLabel,
		final @Symbol String endLabel,
		final Class<? extends Throwable> throwableClass,
		final @Symbol String handlerLabel )
	{
		this.coreWriter().handleException(
			new LabelBasedExceptionHandler(
				this.labelScope( false ),
				startLabel,
				endLabel,
				throwableClass,
				handlerLabel ) );
		return this;
	}

	public final boolean isVarDefined( final String var ) {
		return this.varScope( false ).isDefined( var );
	}

	public final boolean isLabelDefined( final String label ) {
		return this.labelScope( false ).isDefined( label );
	}
	
	public final JvmStack stackMonitor() {
		return this.coreWriter().stackMonitor();
	}
	
	public final JvmLocals localsMonitor() {
		return this.coreWriter().localsMonitor();
	}
	
	private final JvmCodeWriter startConstruction( final JvmMacro macro ) {
		final CaptureCodeWriter captureWriter = new CaptureCodeWriter();
		this.coreWriter().defer( new JvmCoreCodeWriter.DeferredWrite() {
			@Override
			public final void write(
				final JvmCoreCodeWriter coreWriter,
				final boolean terminal )
			{
				captureWriter.init( coreWriter );
				macro.write( captureWriter, terminal );
				
				JvmCodeWriter.this.underConstructionMacro = null;
			}
		} );
		
		this.underConstructionMacro = macro;
		
		return this;
	}

	private final Jump jumpTo( final String label ) {
		final Scope labelScope = this.labelScope( true );
		
		return new Jump() {
			@Override
			public final Integer pos() {
				return labelScope.get( label );
			}
			
			@Override
			public final String toString() {
				return label;
			}
		};
	}
	
	private final Jump jumpRelative( final int relativeOffset ) {
		//Relative jump is tied to if<cond> or goto which is 1-byte back from the current position.
		final int pos = this.coreWriter().pos() + relativeOffset;
		return new Jump() {
			@Override
			public final Integer pos() {
				return pos;
			}
			
			@Override
			public final String toString() {
				if ( relativeOffset < 0 ) {
					return String.valueOf(relativeOffset);
				} else {
					return "+" + relativeOffset;
				}
			}
		};
	}

	protected final int getVarSlot( final String varName ) {
		return this.varScope( false ).get( varName );
	}
	
	private final Class<?> slotType( final Type type ) {
		Type resolvedType = this.resolver().resolve( type );
		if ( resolvedType.equals( boolean.class ) ) {
			return int.class;
		} else if ( resolvedType.equals( byte.class ) ) {
			return int.class;
		} else if ( resolvedType.equals( short.class ) ) {
			return int.class;
		} else if ( resolvedType.equals( char.class ) ) {
			return int.class;
		} else if ( resolvedType.equals( int.class ) ) {
			return int.class;
		} else if ( resolvedType.equals( long.class ) ) {
			return long.class;
		} else if ( resolvedType.equals( float.class ) ) {
			return float.class;
		} else if ( resolvedType.equals( double.class ) ) {
			return double.class;
		} else {
			return Reference.class;
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
		private final Scope labelScope;
		private final String startLabel;
		private final String endLabel;
		private final Type exceptionType;
		private final int handlerPos;

		CatchExceptionHandler(
			final Scope labelScope,
			final String startLabel,
			final String endLabel,
			final Type exceptionType,
			final int handlerPos )
		{
			this.labelScope = labelScope;
			this.startLabel = startLabel;
			this.endLabel = endLabel;
			this.exceptionType = exceptionType;
			this.handlerPos = handlerPos;
		}

		@Override
		public final int startPos() {
			return this.labelScope.get( this.startLabel );
		}

		@Override
		public final int endPos() {
			return this.labelScope.get( this.endLabel );
		}
		
		@Override
		public final Type exceptionType() {
			return this.exceptionType;
		}

		@Override
		public final int handlerPos() {
			return this.handlerPos;
		}
	}

	private final class LabelBasedExceptionHandler extends ExceptionHandler {
		private final Scope labelScope;
		private final String startLabel;
		private final String endLabel;
		private final Type exceptionType;
		private final String handlerLabel;

		LabelBasedExceptionHandler(
			final Scope labelScope,
			final String startLabel,
			final String endLabel,
			final Type exceptionType,
			final String handlerLabel)
		{
			this.labelScope = labelScope;
			this.startLabel = startLabel;
			this.endLabel = endLabel;
			this.exceptionType = exceptionType;
			this.handlerLabel = handlerLabel;
		}

		@Override
		public final int startPos() {
			return this.labelScope.get( this.startLabel );
		}

		@Override
		public final int endPos() {
			return this.labelScope.get( this.endLabel );
		}

		@Override
		public final Type exceptionType() {
			return this.exceptionType;
		}

		@Override
		public final int handlerPos() {
			return this.labelScope.get( this.handlerLabel );
		}
	}
	
	static final class Scope {
		private final Scope parentScope;
		private Map<String, Integer> vars = null;
		private Map<String, String> aliases = null;
		
		Scope() {
			this.parentScope = null;
		}
		
		Scope( final Scope parentScope ) {
			this.parentScope = parentScope;
		}
		
		private final String resolveAlias( final String id ) {
			String resolvedId = null;
			if ( this.aliases != null ) {
				resolvedId = this.aliases.get( id );
			}
			if ( resolvedId != null ) {
				return resolvedId;
			} else {
				return id;
			}
		}
		
		public final void alias( final String id, final String existingId ) {
			if ( this.aliases == null ) {
				this.aliases = new HashMap< String, String >( 4 );
			}
			
			this.aliases.put( id, existingId );				
		}
		
		public final boolean isDefined( final String id ) {
			String resolvedId = this.resolveAlias( id );
			
			boolean result = false;
			if ( this.vars != null ) {
				result = this.vars.containsKey( this.resolveAlias( id ) );
			}
			if ( ! result && this.parentScope != null ) {
				result = this.parentScope.isDefined( resolvedId );
			}
			return result;
		}
		
		public final void set( final String id, final int slot ) {
			String resolvedId = this.resolveAlias( id );
			
			if ( this.vars == null ) {
				this.vars = new HashMap< String, Integer >( 8 );
			}
			this.vars.put( resolvedId, slot );
		}
		
		public final Integer get( final String id ) {
			String resolvedId = this.resolveAlias( id );
			
			Integer value = null;
			if ( this.vars != null ) {
				value = this.vars.get( resolvedId );
			}
			if ( value == null && this.parentScope != null ) {
				value = this.parentScope.get( resolvedId );
			}
			return value;
		}
		
		public final Set< Map.Entry< String, Integer > > entrySet() {
			//DQH - Frequent and internal to JvmCodeWriter, so I guess I'll forego the usual immutable wrapper.
			if ( this.vars == null ) {
				return Collections.emptySet();
			} else {
				return this.vars.entrySet();
			}
		}
		
		public final Scope startScope() {
			return new Scope( this );
		}
		
		public final Scope endScope() {
			return this.parentScope;
		}
	}

	private final class CaptureCodeWriter extends JvmCodeWriter {
		private final CodeWritingState sharedState = new CodeWritingState( JvmCodeWriter.this.sharedState() );
		private JvmCoreCodeWriter coreWriter = null;
		
		protected final void init( final JvmCoreCodeWriter coreWriter ) {
			this.coreWriter = coreWriter;
		}
		
		@Override
		public final JvmCoreCodeWriter coreWriter() {
			return this.coreWriter;
		}
		
		@Override
		protected final CodeWritingState sharedState() {
			return this.sharedState;
		}
	}
}
