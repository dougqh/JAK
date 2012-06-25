package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import net.dougqh.jak.FormalArguments;
import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.JavaVariable;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.Reference;
import net.dougqh.java.meta.types.JavaTypes;
import static net.dougqh.jak.jvm.operations.JvmOperation.*;

final class JvmCoreCodeWriterImpl implements JvmCoreCodeWriter {	
	private static final byte BOOLEAN_ARRAY = 4;
	private static final byte CHAR_ARRAY = 5;
	private static final byte FLOAT_ARRAY = 6;
	private static final byte DOUBLE_ARRAY = 7;
	private static final byte BYTE_ARRAY = 8;
	private static final byte SHORT_ARRAY = 9;
	private static final byte INT_ARRAY = 10;
	private static final byte LONG_ARRAY = 11;
	
	private final WritingContext context;
	
	private final ConstantPool constantPool;
	private final JvmOutputStream codeOut;
	
	private final JvmLocals locals;
	private final JvmStack stack;
	
	private final ArrayList< ExceptionHandler > handlers = new ArrayList< ExceptionHandler >( 8 );	
	
	private final ArrayList< Byte2Slot > unresolvedSlots = new ArrayList < Byte2Slot >( 4 );
	private final ArrayList< Jump > unresolvedJumps = new ArrayList< Jump >( 4 );
	
	private JvmCoreCodeWriter wrapper = null;
	private DeferredWrite deferredWrite = null;
	
	JvmCoreCodeWriterImpl(
		final WritingContext context,
		final JvmLocals locals,
		final JvmStack stack )
	{
		this.context = context;
		
		this.codeOut = new JvmOutputStream( 128 );
		this.constantPool = context.constantPool;
		
		this.locals = locals;
		this.stack = stack;
	}
	
	@Override
	public final void prepare() {
		if ( this.deferredWrite != null ) {
			DeferredWrite deferredWrite = this.deferredWrite;
			this.deferredWrite = null;
			deferredWrite.write( this, false );
		}
	}
	
	private final void prepareLast() {
		if ( this.deferredWrite != null ) {
			DeferredWrite deferredWrite = this.deferredWrite;
			this.deferredWrite = null;
			deferredWrite.write( this, true );
		}		
	}
	
	public final void defer( final DeferredWrite deferredWrite ) {
		this.prepare();
		
		this.deferredWrite = deferredWrite;
	}
	
	@Override
	public final WritingContext context() {
		return this.context;
	}

	@Override
	public final void nop() {
		this.op( NOP );
	}
	
	@Override
	public final void aconst_null() {
		try {
			this.op( ACONST_NULL );
		} finally {
			this.stack( Reference.class );
		}
	}	
	
	@Override
	public final void iconst_m1() {
		try {
			this.op( ICONST_M1 );			
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iconst_0() {
		try {
			this.op( ICONST_0 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iconst_1() {
		try {
			this.op( ICONST_1 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iconst_2() {
		try {
			this.op( ICONST_2 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iconst_3() {
		try {
			this.op( ICONST_3 );			
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iconst_4() {
		try {
			this.op( ICONST_4 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iconst_5() {
		try {
			this.op( ICONST_5 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void lconst_0() {
		try {
			this.op( LCONST_0 );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final void lconst_1() {
		try {
			this.op( LCONST_1 );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final void fconst_0() {
		try {
			this.op( FCONST_0 );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void fconst_1() {
		try {
			this.op( FCONST_1 );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void fconst_2() {
		try {
			this.op( FCONST_2 );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void dconst_0() {
		try {
			this.op( DCONST_0 );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final void dconst_1() {
		try {
			this.op( DCONST_1 );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final void bipush( final byte value ) {
		try {
			this.op( BIPUSH ).u1( value );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void sipush( final short value ) {
		try {
			this.op( SIPUSH ).u2( value );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void ldc( final float value ) {
		try {
			this.op( LDC ).u1( this.constantPool.addFloatConstant( value ) );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void ldc( final int value ) {
		try {
			this.op( LDC ).u1( this.constantPool.addIntegerConstant( value ) );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void ldc( final String value ) {
		try {
			this.op( LDC ).u1( this.constantPool.addStringConstant( value ) );
		} finally {
			this.stack( String.class );
		}
	}
	
	@Override
	public final void ldc( final Type value ) {
		try {
			this.op( LDC ).u1( this.constantPool.addClassInfo( value ) );
		} finally {
			this.stack( Class.class );
		}		
	}
	
	@Override
	public final void ldc_w( final float value ) {
		try {
			this.op( LDC_W ).u2( this.constantPool.addFloatConstant( value ) );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void ldc_w( final int value ) {
		try {
			this.op( LDC_W ).u2( this.constantPool.addIntegerConstant( value ) );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void ldc_w( final String value ) {
		try {
			this.op( LDC_W ).u2( this.constantPool.addStringConstant( value ) );
		} finally {
			this.stack( String.class );
		}
	}
	
	@Override
	public final void ldc_w( final Type value ) {
		try {
			this.op( LDC_W ).u2( this.constantPool.addClassInfo( value ) );
		} finally {
			this.stack( Class.class );
		}
	}
	
	@Override
	public final void ldc2_w( final double value ) {
		try {
			this.op( LDC2_W ).u2( this.constantPool.addDoubleConstant( value ) );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final void ldc2_w( final long value ) {
		try {
			this.op( LDC2_W ).u2( this.constantPool.addLongConstant( value ) );
		} finally {
			this.stack( double.class );
		}		
	}
	
	@Override
	public final void iload( final Slot slot ) {
		//TODO: implement deferred handling
		this.iload( slot.pos() );
	}
	
	@Override
	public final void iload( final int slot ) {
		try {
			this.op( ILOAD ).u1( slot );
		} finally {
			this.load( slot, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iload_0() {
		try {
			this.op( ILOAD_0 );
		} finally {
			this.load( 0, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iload_1() {
		try {
			this.op( ILOAD_1 );
		} finally {
			this.load( 1, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iload_2() {
		try {
			this.op( ILOAD_2 );
		} finally {
			this.load( 2, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iload_3() {
		try {
			this.op( ILOAD_3 );
		} finally {
			this.load( 3, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final void lload( final Slot slot ) {
		//TODO: implement deferred handling
		this.lload( slot.pos() );
	}

	@Override
	public final void lload( final int slot ) {
		try {
			this.op( LLOAD ).u1( slot );
		} finally {
			this.load( slot, long.class );
			this.stack( long.class );
		}
	}
	
	@Override
	public final void lload_0() {
		try {
			this.op( LLOAD_0 );
		} finally {
			this.load( 0, long.class );
			this.stack( long.class );
		}
	}
	
	@Override
	public final void lload_1() {
		try {
			this.op( LLOAD_1 );
		} finally {
			this.load( 1, long.class );
			this.stack( long.class );
		}
	}
	
	@Override
	public final void lload_2() {
		try {
			this.op( LLOAD_2 );
		} finally {
			this.load( 2, long.class );
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void lload_3() {
		try {
			this.op( LLOAD_3 );
		} finally {
			this.load( 3, long.class );
			this.stack( long.class );
		}
	}	
	
	@Override
	public final void fload( final Slot slot ) {
		//TODO: implement deferred handling
		this.fload( slot.pos() );
	}

	@Override
	public final void fload( final int slot ) {
		try {
			this.op( FLOAD ).u1( slot );
		} finally {
			this.load( slot, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final void fload_0() {
		try {
			this.op( FLOAD_0 );
		} finally {
			this.load( 0, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final void fload_1() {
		try {
			this.op( FLOAD_1 );
		} finally {
			this.load( 1, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final void fload_2() {
		try {
			this.op( FLOAD_2 );	
		} finally {
			this.load( 2, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final void fload_3() {
		try {
			this.op( FLOAD_3 );
		} finally {
			this.load( 3, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final void dload( final Slot slot ) {
		//TODO: implement deferred handling
		this.dload( slot.pos() );
	}

	@Override
	public final void dload( final int slot ) {
		try {
			this.op( DLOAD ).u1( slot );
		} finally {
			this.load( slot, double.class );
			this.stack( double.class );
		}
	}
	
	@Override
	public final void dload_0() {
		try {
			this.op( DLOAD_0 );
		} finally {
			this.load( 0, double.class );
			this.stack( double.class );
		}
	}
	
	@Override
	public final void dload_1() {
		try {
			this.op( DLOAD_1 );
		} finally {
			this.load( 1, double.class );
			this.stack( double.class );
		}
	}
	
	@Override
	public final void dload_2() {
		try {
			this.op( DLOAD_2 );
		} finally {
			this.load( 2, double.class );
			this.stack( double.class );
		}
	}
	
	@Override
	public final void dload_3() {
		try {
			this.op( DLOAD_3 );
		} finally {
			this.load( 3, double.class );
			this.stack( double.class );
		}
	}
	
	@Override
	public final void aload( final Slot slot ) {
		//TODO: implement deferred handling
		this.aload( slot.pos() );
	}
	
	@Override
	public final void aload( final int slot ) {
		try {
			this.op( ALOAD ).u1( slot );
		} finally {
			Type actualType = this.load( slot, Reference.class );
			this.stack( actualType );
		}
	}
	
	@Override
	public final void aload_0() {
		try {
			this.op( ALOAD_0 );
		} finally {
			Type actualType = this.load( 0, Reference.class );
			this.stack( actualType );
		}
	}
	
	@Override
	public final void aload_1() {
		try {
			this.op( ALOAD_1 );
		} finally {
			Type actualType = this.load( 1, Reference.class );
			this.stack( actualType );
		}
	}
	
	@Override
	public final void aload_2() {
		try {
			this.op( ALOAD_2 );
		} finally {
			Type actualType = this.load( 2, Reference.class );
			this.stack( actualType );
		}
	}
	
	@Override
	public final void aload_3() {
		try {
			this.op( ALOAD_3 );
		} finally {
			Type actualType = this.load( 3, Reference.class );
			this.stack( actualType );
		}
	}
	
	@Override
	public final void iaload() {
		this.unstack( int.class );
		this.unstack( int[].class );
		try {
			this.op( IALOAD );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void laload() {
		this.unstack( int.class );
		this.unstack( long[].class );
		try {
			this.op( LALOAD );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final void faload() {
		this.unstack( int.class );
		this.unstack( float[].class );
		try {
			this.op( FALOAD );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void daload() {
		this.unstack( int.class );
		this.unstack( double[].class );
		try {
			this.op( DALOAD );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final void aaload() {
		this.unstack( int.class );
		this.unstack( Reference[].class );
		try {
			this.op( AALOAD );
		} finally {
			this.stack( Reference.class );	
		}
	}
	
	@Override
	public final void baload() {
		this.unstack( int.class );
		this.unstack( byte[].class );
		try {
			this.op( BALOAD );
		} finally {
			this.stack( byte.class );	
		}
	}
	
	@Override
	public final void caload() {
		this.unstack( int.class );
		this.unstack( char[].class );
		try {
			this.op( CALOAD );
		} finally {
			this.stack( char.class );	
		}
	}
	
	@Override
	public final void saload() {
		this.unstack( int.class );
		this.unstack( short[].class );
		try {
			this.op( SALOAD );
		} finally {
			this.stack( short.class );	
		}
	}
	
	@Override
	public final void istore( final Slot slot ) {
		//TODO: implement deferred handling
		this.istore( slot.pos() );
	}
	
	@Override
	public final void istore( final int index ) {
		this.store( index, int.class );
		try {
			this.op( ISTORE ).u1( index );
		} finally {
			this.unstack( int.class );	
		}
	}
	
	@Override
	public final void istore_0() {
		this.store( 0, int.class );
		this.unstack( int.class );
		this.op( ISTORE_0 );
	}
	
	@Override
	public final void istore_1() {
		this.store( 1, int.class );
		this.unstack( int.class );
		this.op( ISTORE_1 );
	}
	
	@Override
	public final void istore_2() {
		this.store( 2, int.class );
		this.unstack( int.class );
		this.op( ISTORE_2 );
	}
	
	@Override
	public final void istore_3() {
		this.store( 3, int.class );
		this.unstack( int.class );
		this.op( ISTORE_3 );
	}
	
	@Override
	public final void lstore( final Slot slot ) {
		//TODO: implement deferred handling
		this.lstore( slot.pos() );
	}
	
	@Override
	public final void lstore( final int slot ) {
		this.store( slot, long.class );
		this.unstack( long.class );
		this.op( LSTORE ).u1( slot );
	}
	
	@Override
	public final void lstore_0() {
		this.store( 0, long.class );
		this.unstack( long.class );
		this.op( LSTORE_0 );
	}
	
	@Override
	public final void lstore_1() {
		this.store( 1, long.class );
		this.unstack( long.class );
		this.op( LSTORE_1 );
	}
	
	@Override
	public final void lstore_2() {
		this.store( 2, long.class );
		this.unstack( long.class );
		this.op( LSTORE_2 );
	}
	
	@Override
	public final void lstore_3() {
		this.store( 3, long.class );
		this.unstack( long.class );
		this.op( LSTORE_3 );
	}
	
	@Override
	public final void fstore( final Slot slot ) {
		//TODO: implement deferred handling
		this.fstore( slot.pos() );
	}
	
	@Override
	public final void fstore( final int slot ) {
		this.store( slot, float.class );
		this.unstack( float.class );
		this.op( FSTORE ).u1( slot );
	}
	
	@Override
	public final void fstore_0() {
		this.store( 0, float.class );
		this.unstack( float.class );
		this.op( FSTORE_0 );
	}
	
	@Override
	public final void fstore_1() {
		this.store( 1, float.class );
		this.unstack( float.class );
		this.op( FSTORE_1 );
	}
	
	@Override
	public final void fstore_2() {
		this.store( 2, float.class );
		this.unstack( float.class );
		this.op( FSTORE_2 );
	}
	
	@Override
	public final void fstore_3() {
		this.store( 3, float.class );
		this.unstack( float.class );
		this.op( FSTORE_3 );
	}

	@Override
	public final void dstore( final Slot slot ) {
		//TODO: implement deferred handling
		this.dstore( slot.pos() );
	}
	
	@Override
	public final void dstore( final int slot ) {
		this.store( slot, double.class );
		this.unstack( double.class );
		this.op( DSTORE ).u1( slot );
	}
	
	@Override
	public final void dstore_0() {
		this.store( 0, double.class );
		this.unstack( double.class );
		this.op( DSTORE_0 );
	}
	
	@Override
	public final void dstore_1() {
		this.store( 1, double.class );
		this.unstack( double.class );
		this.op( DSTORE_1 );
	}
	
	@Override
	public final void dstore_2() {
		this.store( 2, double.class );
		this.unstack( double.class );
		this.op( DSTORE_2 );
	}
	
	@Override
	public final void dstore_3() {
		this.store( 3, double.class );
		this.unstack( double.class );
		this.op( DSTORE_3 );
	}
	
	@Override
	public final void astore( final Slot slot ) {
		//TODO: implement deferred handling
		this.astore( slot.pos() );
	}
	
	@Override
	public final void astore( final int slot ) {
		Type actualType = this.topType( Reference.class );
		this.store( slot, actualType );
		this.unstack( actualType );
		this.op( ASTORE ).u1( slot );
	}
	
	@Override
	public final void astore_0() {
		Type actualType = this.topType( Reference.class );
		this.store( 0, actualType );
		this.unstack( actualType );
		this.op( ASTORE_0 );
	}
	
	@Override
	public final void astore_1() {
		Type actualType = this.topType( Reference.class );
		this.store( 1, actualType );
		this.unstack( actualType );
		this.op( ASTORE_1 );
	}
	
	@Override
	public final void astore_2() {
		Type actualType = this.topType( Reference.class );
		this.store( 2, actualType );
		this.unstack( actualType );
		this.op( ASTORE_2 );
	}
	
	@Override
	public final void astore_3() {
		Type actualType = this.topType( Reference.class );
		this.store( 3, actualType );
		this.unstack( actualType );
		this.op( ASTORE_3 );
	}
	
	@Override
	public final void iastore() {
		this.unstack( int.class );
		this.unstack( int.class );
		this.unstack( int[].class );
		this.op( IASTORE );
	}
	
	@Override
	public final void lastore() {
		this.unstack( long.class );
		this.unstack( int.class );
		this.unstack( long[].class );
		this.op( LASTORE );
	}
	
	@Override
	public final void fastore() {
		this.unstack( float.class );
		this.unstack( int.class );
		this.unstack( float[].class );
		this.op( FASTORE );
	}
	
	@Override
	public final void dastore() {
		this.unstack( double.class );
		this.unstack( int.class );
		this.unstack( double[].class );
		this.op( DASTORE );
	}
	
	@Override
	public final void aastore() {
		this.unstack( Reference.class );
		this.unstack( int.class );
		this.unstack( Reference[].class );
		this.op( AASTORE );
	}
	
	@Override
	public final void bastore() {
		this.unstack( byte.class );
		this.unstack( int.class );
		this.unstack( byte[].class );
		this.op( BASTORE );
	}
	
	@Override
	public final void castore() {
		this.unstack( char.class );
		this.unstack( int.class );
		this.unstack( char[].class );
		this.op( CASTORE );
	}
	
	@Override
	public final void sastore() {
		this.unstack( short.class );
		this.unstack( int.class );
		this.unstack( short[].class );
		this.op( SASTORE );
	}
	
	@Override
	public final void pop() {
		try {
			this.op( POP );
		} finally {
			this.stack.pop();
		}
	}
	
	@Override
	public final void pop2() {
		try {
			this.op( POP2 );
		} finally {
			this.stack.pop2();
		}
	}
	
	@Override
	public final void dup() {
		try {
			this.op( DUP );
		} finally {
			this.stack.dup();
		}
	}
	
	@Override
	public final void dup_x1() {
		try {
			this.op( DUP_X1 );
		} finally {
			this.stack.dup_x1();
		}
	}
	
	@Override
	public final void dup_x2() {
		try {
			this.op( DUP_X2 );
		} finally {
			this.stack.dup_x2();
		}
	}
	
	@Override
	public final void dup2() {
		try {
			this.op( DUP2 );
		} finally {
			this.stack.dup2();
		}
	}
	
	@Override
	public final void dup2_x1() {
		try {
			this.op( DUP2_X1 );
		} finally {
			this.stack.dup2_x1();
		}
	}
	
	@Override
	public final void dup2_x2() {
		try {
			this.op( DUP2_X2 );
		} finally {
			this.stack.dup2_x2();
		}
	}
	
	@Override
	public final void swap() {
		try {
			this.op( SWAP );
		} finally {
			this.stack.swap();
		}
	}
	
	@Override
	public final void iadd() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.op( IADD );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void ladd() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.op( LADD );
		} finally {
			this.stack( long.class );			
		}
	}
	
	@Override
	public final void fadd() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			this.op( FADD );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void dadd() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			this.op( DADD );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final void isub() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.op( ISUB );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lsub() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.op( LSUB );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void fsub() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			this.op( FSUB );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final void dsub() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			this.op( DSUB );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final void imul() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.op( IMUL );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lmul() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.op( LMUL );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final void fmul() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			this.op( FMUL );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final void dmul() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			this.op( DMUL );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final void idiv() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.op( IDIV );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void ldiv() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.op( LDIV );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final void fdiv() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			this.op( FDIV );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void ddiv() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			this.op( DDIV );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final void irem() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.op( IREM );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void lrem() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.op( LREM );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void frem() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			this.op( FREM );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final void drem() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			this.op( DREM );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final void ineg() {
		this.unstack( int.class );
		try {
			this.op( INEG );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lneg() {
		this.unstack( long.class );
		try {
			this.op( LNEG );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void fneg() {
		this.unstack( float.class );
		try {
			this.op( FNEG );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final void dneg() {
		this.unstack( double.class );
		try {
			this.op( DNEG );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final void ishl() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.op( ISHL );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lshl() {
		this.unstack( int.class );
		this.unstack( long.class );
		try {
			this.op( LSHL );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void ishr() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.op( ISHR );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lshr() {
		this.unstack( int.class );
		this.unstack( long.class );
		try {
			this.op( LSHR );
		} finally {
			this.stack( long.class );						
		}
	}
	
	@Override
	public final void iushr() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.op( IUSHR );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lushr() {
		this.unstack( int.class );
		this.unstack( long.class );
		try {
			this.op( LUSHR );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final void iand() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.op( IAND );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void land() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.op( LAND );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void ior() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.op( IOR );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lor() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.op( LOR );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void ixor() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.op( IXOR );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lxor() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.op( LXOR );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void iinc(
		final int slot,
		final int amount )
	{
		//TODO: Bound checks on amount
		
		try {
			this.op( IINC ).u1( slot ).u1( amount );
		} finally {
			this.inc( slot );	
		}
	}
	
	@Override
	public final void i2l() {
		this.unstack( int.class );
		try {
			this.op( I2L );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final void i2f() {
		this.unstack( int.class );
		try {
			this.op( I2F );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final void i2d() {
		this.unstack( int.class );
		try {
			this.op( I2D );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final void l2i() {
		this.unstack( long.class );
		try {
			this.op( L2I );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void l2f() {
		this.unstack( long.class );
		try {
			this.op( L2F );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final void l2d() {
		this.unstack( long.class );
		try {
			this.op( L2D );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final void f2i() {
		this.unstack( float.class );
		try {
			this.op( F2I );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void f2l() {
		this.unstack( float.class );
		try {
			this.op( F2L );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void f2d() {
		this.unstack( float.class );
		try {
			this.op( F2D );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final void d2i() {
		this.unstack( double.class );
		try {
			this.op( D2I );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void d2l() {
		this.unstack( double.class );
		try {
			this.op( D2L );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void d2f() {
		this.unstack( double.class );
		try {
			this.op( D2F );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final void i2b() {
		this.unstack( int.class );
		try {
			this.op( I2B );
		} finally {
			this.stack( byte.class );	
		}
	}
	
	@Override
	public final void i2c() {
		this.unstack( int.class );
		try {
			this.op( I2C );
		} finally {
			this.stack( char.class );			
		}
	}
	
	@Override
	public final void i2s() {
		this.unstack( int.class );
		try {
			this.op( I2S );
		} finally {
			this.stack( short.class );	
		}
	}
	
	@Override
	public final void lcmp() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.op( LCMP );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void fcmpl() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			this.op( FCMPL );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void fcmpg() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			this.op( FCMPG );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void dcmpl() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			this.op( DCMPL );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void dcmpg() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			this.op( DCMPG );
		} finally {
			this.stack( int.class );			
		}
	}
	
	final void jsr() {
		this.op( JSR );
	}
	
	final void ret() {
		this.op( RET );
	}
	
	final void tableswitch() {
		this.op( TABLESWITCH );
	}
	
	final void lookupswitch() {
		this.op( LOOKUPSWITCH );
	}
	
	@Override
	public final void ireturn() {
		this.unstack( int.class );
		this.op( IRETURN );
	}
	
	@Override
	public final void lreturn() {
		this.unstack( long.class );
		this.op( LRETURN );
	}
	
	@Override
	public final void freturn() {
		this.unstack( float.class );
		this.op( FRETURN );
	}
	
	@Override
	public final void dreturn() {
		this.unstack( double.class );
		this.op( DRETURN );
	}
	
	@Override
	public final void areturn() {
		this.unstack( Reference.class );
		this.op( ARETURN );
	}
	
	@Override
	public final void return_() {
		this.op( RETURN );
	}

	@Override
	public final void getstatic(
		final Type targetType,
		final JavaField field )
	{
		try {
			this.op( GETSTATIC ).u2(
				this.constantPool.addFieldReference(
					this.context,
					targetType,
					field.getType(),
					field.getName() ) );
		} finally {
			this.stack( field.getType() );		
		}
	}
	
	@Override
	public final void putstatic(
		final Type targetType,
		final JavaField field )
	{
		this.unstack( field.getType() );
		this.op( PUTSTATIC ).u2(
			this.constantPool.addFieldReference(
				this.context,
				targetType,
				field.getType(),
				field.getName() ) );
	}
	
	@Override
	public final void getfield(
		final Type targetType,
		final JavaField field )
	{
		this.unstack( targetType );
		try {
			this.op( GETFIELD ).u2(
				this.constantPool.addFieldReference(
					this.context,
					targetType,
					field.getType(),
					field.getName() ) );
		} finally {
			this.stack( field.getType() );	
		}
	}
	
	@Override
	public final void putfield(
		final Type targetType,
		final JavaField field )
	{
		this.unstack( targetType );
		this.unstack( field.getType() );
		this.op( PUTFIELD ).u2(
			this.constantPool.addFieldReference(
				this.context,
				targetType,
				field.getType(),
				field.getName() ) );
	}
	
	@Override
	public final void invokevirtual(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.unstack( method.arguments() );
		this.unstack( targetType ); //this
		try {
			this.op( INVOKEVIRTUAL ).u2(
				this.constantPool.addMethodReference(
					this.context,
					targetType,
					method.getReturnType(),
					method.getName(),
					method.arguments() ) );
		} finally {
			this.stack( method.getReturnType() );			
		}
	}
	
	@Override
	public final void invokeinterface(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.unstack( method.arguments() );
		this.unstack( targetType ); //this
		try {
			this.op( INVOKEINTERFACE ).u2(
				this.constantPool.addInterfaceMethodReference(
					this.context,
					targetType,
					method.getReturnType(),
					method.getName(),
					method.arguments() ) ).
				u1( size( method.arguments() ) + 1 ).
				u1( 0 );
		} finally {
			this.stack( method.getReturnType() );	
		}
	}
	
	@Override
	public final void invokespecial(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.unstack( method.arguments() );
		this.unstack( targetType ); //this
		try {
			this.op( INVOKESPECIAL ).u2(
				this.constantPool.addMethodReference(
					this.context,
					targetType,
					method.getReturnType(),
					method.getName(),
					method.arguments() ) );
		} finally {
			this.stack( method.getReturnType() );	
		}
	}
	
	@Override
	public final void invokestatic(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.unstack( method.arguments() );
		try {
			this.op( INVOKESTATIC ).u2(
				this.constantPool.addMethodReference(
					this.context,
					targetType,
					method.getReturnType(),
					method.getName(),
					method.arguments() ) );
		} finally {
			this.stack( method.getReturnType() );	
		}
	}
	
	@Override
	public final void new_( final Type type ) {
		Type resolvedType = this.context().resolver.resolve( type );
		try {
			this.op( NEW ).u2(
				this.constantPool.addClassInfo( JavaTypes.getRawClassName( resolvedType ) ) );
		} finally {
			this.stack( type );			
		}
	}
	
	@Override
	public final void newarray( final Type componentType ) {
		this.unstack( int.class );
		try {
			this.op( NEWARRAY ).u1( getArrayTypeCode( componentType ) );
		} finally {
			this.stack( JavaTypes.array( componentType ) );	
		}
	}
	
	private static final byte getArrayTypeCode( final Type componentType ) {
		Type resolvedType = JavaTypes.getRawClass( componentType );
		if ( resolvedType.equals( boolean.class ) ) {
			return BOOLEAN_ARRAY;
		} else if ( resolvedType.equals( char.class ) ) {
			return CHAR_ARRAY;
		} else if ( resolvedType.equals( float.class ) ) {
			return FLOAT_ARRAY;
		} else if ( resolvedType.equals( double.class ) ) {
			return DOUBLE_ARRAY;
		} else if ( resolvedType.equals( byte.class ) ) {
			return BYTE_ARRAY;
		} else if ( resolvedType.equals( short.class ) ) {
			return SHORT_ARRAY;
		} else if ( resolvedType.equals( int.class ) ) {
			return INT_ARRAY;
		} else if ( resolvedType.equals( long.class ) ) {
			return LONG_ARRAY;
		} else {
			throw new IllegalStateException( "Use anewarray for Object types" );
		}
	}
	
	@Override
	public final void anewarray( final Type componentType ) {
		this.unstack( int.class );
		try {
			this.op( ANEWARRAY ).u2( this.constantPool.addClassInfo( componentType ) );
		} finally {
			this.stack( JavaTypes.array( componentType ) );
		}
	}
	
	@Override
	public final void arraylength() {
		this.unstack( Any[].class );
		try {
			this.op( ARRAYLENGTH );
		} finally {
			this.stack( int.class );			
		}
	}
	
	@Override
	public final void athrow() {
		this.unstack( Throwable.class );
		this.op( ATHROW );
	}
	
	@Override
	public final void checkcast( final Type type ) {
		this.unstack( Reference.class );
		try {
			this.op( CHECKCAST ).u2( this.constantPool.addClassInfo( type ) );
		} finally {
			this.stack( type );			
		}
	}
	
	@Override
	public final void instanceof_( final Type type ) {
		this.unstack( Reference.class );
		try {
			this.op( INSTANCEOF ).u2( this.constantPool.addClassInfo( type ) );
		} finally {
			this.stack( boolean.class );	
		}
	}
	
	@Override
	public final void monitorenter() {
		this.unstack( Reference.class );
		this.op( MONITORENTER );
	}
	
	@Override
	public final void monitorexit() {
		this.unstack( Reference.class );
		this.op( MONITOREXIT );
	}
	
	final void wide() {
		this.op( WIDE );
	}

	@Override
	public final void multianewarray(
		final Type arrayType,
		final int numDimensions )
	{	
		for ( int i = 0; i < numDimensions; ++i ) {
			this.unstack( int.class );
		}
		try {
			this.op( MULTIANEWARRAY ).
				u2( this.constantPool.addClassInfo( arrayType ) ).
				u1( numDimensions );
		} finally {
			this.stack( arrayType );			
		}
	}
	
	@Override
	public final void ifnull( final Jump jump ) {
		this.unstack( Reference.class );
		this.op( IFNULL ).jumpTo( jump );
	}
	
	@Override
	public final void ifnonnull( final Jump jump ) {
		this.unstack( Reference.class );
		this.op( IFNONNULL ).jumpTo( jump );
	}
	
	final void goto_w() {
		this.op( GOTO_W );
	}
	
	final void jsr_w() {
		this.op( JSR_W );
	}

	@Override
	public final void ifeq( final Jump jump ) {
		this.unstack( int.class );
		this.op( IFEQ ).jumpTo( jump );
	}
	
	@Override
	public final void ifne( final Jump jump ) {
		this.unstack( int.class );
		this.op( IFNE ).jumpTo( jump );
	}
	
	@Override
	public final void iflt( final Jump jump ) {
		this.unstack( int.class );
		this.op( IFLT ).jumpTo( jump );
	}
	
	@Override
	public final void ifgt( final Jump jump ) {
		this.unstack( int.class );
		this.op( IFGT ).jumpTo( jump );
	}
	
	@Override
	public final void ifge( final Jump jump ) {
		this.unstack( int.class );
		this.op( IFGE ).jumpTo( jump );
	}
	
	@Override
	public final void ifle( final Jump jump ) {
		this.unstack( int.class );
		this.op( IFLE ).jumpTo( jump );
	}
	
	@Override
	public final void if_icmpeq( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		this.op( IF_ICMPEQ ).jumpTo( jump );
	}
	
	@Override
	public final void if_icmpne( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		this.op( IF_ICMPNE ).jumpTo( jump );
	}
	
	@Override
	public final void if_icmplt( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		this.op( IF_ICMPLT ).jumpTo( jump );
	}
	
	@Override
	public final void if_icmpgt( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		this.op( IF_ICMPGT ).jumpTo( jump );
	}
	
	@Override
	public final void if_icmpge( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		this.op( IF_ICMPGE ).jumpTo( jump );
	}
	
	@Override
	public final void if_icmple( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		this.op( IF_ICMPLE ).jumpTo( jump );
	}
	
	@Override
	public final void if_acmpeq( final Jump jump ) {
		this.unstack( Reference.class );
		this.unstack( Reference.class );
		this.op( IF_ACMPEQ ).jumpTo( jump );
	}
	
	@Override
	public final void if_acmpne( final Jump jump ) {
		this.unstack( Reference.class );
		this.unstack( Reference.class );
		this.op( IF_ACMPNE ).jumpTo( jump );
	}
	
	@Override
	public final void goto_( final Jump jump ) {
		this.op( GOTO ).jumpTo( jump );
	}
	
	private JvmCoreCodeWriter jumpTo( final Jump jump ) {		
		Integer pos = jump.pos();
		if ( pos == null ) {
			this.unresolvedSlots.add( this.codeOut.reserve2Slot() );
			this.unresolvedJumps.add( jump );
		} else {
			//Write position is one byte after the goto or if<cond> instruction
			//to which it is associated.	
			this.codeOut.u2( pos - ( this.pos() - 1 ) );
		}
		return this;
	}
	
	@Override
	public final void handleException( final ExceptionHandler exceptionHandler ) {
		this.handlers.add( exceptionHandler );
	}
	
	@Override
	public final void finish() {
		this.prepareLast();
	}
	
	//DQH - 10-19-2010 - Rather ugly...
	//The wrapping JavaCoreCodeWriter that might be added by a JakMonitor 
	//may need to be flush any pending calls before the method can be written.  
	//To enable this, the JAK core makes the JavaCoreCodeWriterImpl aware of
	//the outermost wrapper.  This allows the JAK core to later prepare the
	//outermost wrapper associated with a JavaCoreCodeWriterImpl that is about
	//to be written.
	//Overall, not very pretty, but effective.
	final void initWrapper( final JvmCoreCodeWriter wrapper ) {
		this.wrapper = wrapper;
	}
	
	final void prepareWrapperForWrite() {
		this.wrapper.finish();
	}
	
	private final void inc( final int slot ) {
		this.locals.inc( slot );
	}
	
	private final Type load( final int slot, final Type expectedType ) {
		Type actualType = this.locals.typeOf( slot );
		Type estimatedType = actualType != null ? actualType : expectedType;
		this.locals.load( slot, estimatedType );
		return estimatedType;
	}
	
	private final void store( final int slot, final Type type ) {
		this.locals.store( slot, type );
	}
	
	private final void stack( final Type type ) {
		if ( ! type.equals( void.class) ) {
			this.stack.stack( type );
		}
	}
	
	private final void unstack( final FormalArguments args ) {
		Type[] types = args.getTypes();
		for ( int i = types.length - 1; i >= 0; --i ) {
			this.unstack( types[ i ] );
		}
	}
	
	private final Type topType( final Type expectedType ) {
		return this.stack.topType( expectedType );
	}
	
	private final void unstack( final Type type ) {
		this.prepare();
		
		if ( ! type.equals( void.class ) ) {
			this.stack.unstack( type );
		}
	}
	
	private final JvmCoreCodeWriterImpl op( final byte opCode ) {
		this.prepare();
		
		this.codeOut.u1( opCode );
		return this;
	}
	
	private final JvmCoreCodeWriterImpl u1( final int value ) {
		this.codeOut.u1( value );
		return this;
	}

	private final JvmCoreCodeWriterImpl u1( final ConstantEntry entry ) {
		this.codeOut.u1( entry );
		return this;
	}
	
	private final JvmCoreCodeWriterImpl u2( final int value ) {
		this.codeOut.u2( value );
		return this;
	}
	
	private final JvmCoreCodeWriterImpl u2( final ConstantEntry entry ) {
		this.codeOut.u2( entry );
		return this;
	}
	
	static final int size( final FormalArguments arguments ) {
		int size = 0;
		for ( JavaVariable variable : arguments ) {
			size += size( variable.getType() );
		}
		return size;
	}
	
	static final int size( final Type type ) {
		if ( type.equals( long.class ) ) {
			return 2;
		} else if ( type.equals( double.class ) ) {
			return 2;
		} else {
			return 1;
		}	
	}

	final int length() {
		return 2 + 2 + 4 + this.codeOut.length() + 2 + this.handlers.size() * 8 + 2;
	}
	
	final void write( final JvmOutputStream out ) {
		Iterator< Byte2Slot > slotIter = this.unresolvedSlots.iterator();
		Iterator< Jump > jumpIter = this.unresolvedJumps.iterator();
		
		while ( slotIter.hasNext() ) {
			Byte2Slot slot = slotIter.next();
			Jump jump = jumpIter.next();
			if ( jump.pos() == null ) {
				throw new IllegalStateException( "Unresolved jump: " + jump );
			}
			
			//Offset of slot is one byte after the goto or if<cond> instruction
			//to which it is associated.
			slot.u2( jump.pos() - ( slot.offset() - 1 ) );
		}
		
		out.u2( this.maxStack() );
		out.u2( this.maxLocals() );
		
		out.u4( this.codeOut.length() );
		this.codeOut.writeTo( out );
	
		out.u2( this.handlers.size() );
		for ( ExceptionHandler handler : this.handlers ) {
			ConstantEntry exceptionEntry =  this.constantPool.addClassInfo( handler.exceptionType() );
			out.u2( handler.startPos() ).
				u2( handler.endPos() ).
				u2( handler.handlerPos() ).
				u2( exceptionEntry );
		}
		
		Attributes.writeEmpty( out );
	}
	
	private final int maxStack() {
		if ( this.handlers.isEmpty() ) {
			return this.stack.maxStack();
		} else {
			//DQH - Not sure if this is necessary.
			//In most cases, operations that could raise 
			//an Exception would also pop an item from
			//the stack.  However, some operations "new"
			//for example could raise an OutOfMemoryError
			//which would if caught add an element to the 
			//stack without popping the stack.
			return this.stack.maxStack() + 1;
		}
	}
	
	private final int maxLocals() {
		return this.locals.maxLocals();
	}
	
	final int codeLength() {
		return this.codeOut.length();
	}
	
	@Override
	public final int pos() {
		return this.codeLength();
	}
	
	@Override
	public final JvmLocals localsMonitor() {
		return this.locals;
	}
	
	@Override
	public final JvmStack stackMonitor() {
		return this.stack;
	}
}
