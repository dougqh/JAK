package net.dougqh.jak;

import static net.dougqh.jak.Operation.*;

import java.lang.ref.Reference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.Category1;
import net.dougqh.jak.types.Category2;
import net.dougqh.java.meta.types.JavaTypes;

final class JavaCoreCodeWriterImpl implements JavaCoreCodeWriter {	
	private static final byte BOOLEAN_ARRAY = 4;
	private static final byte CHAR_ARRAY = 5;
	private static final byte FLOAT_ARRAY = 6;
	private static final byte DOUBLE_ARRAY = 7;
	private static final byte BYTE_ARRAY = 8;
	private static final byte SHORT_ARRAY = 9;
	private static final byte INT_ARRAY = 10;
	private static final byte LONG_ARRAY = 11;
	
	private final ConstantPool constantPool;
	private final ByteStream codeOut;
	
	private final Locals locals;
	private final Stack stack;
	
	private final ArrayList< ExceptionHandler > handlers = new ArrayList< ExceptionHandler >( 8 );	
	
	private final ArrayList< Byte2Slot > unresolvedSlots = new ArrayList < Byte2Slot >( 4 );
	private final ArrayList< Jump > unresolvedJumps = new ArrayList< Jump >( 4 );
	
	JavaCoreCodeWriterImpl(
		final ConstantPool constantPool,
		final Locals locals,
		final Stack stack )
	{
		this.codeOut = new ByteStream( 128 );
		this.constantPool = constantPool;
		
		this.locals = locals;
		this.stack = stack;
	}

	@Override
	public final JavaCoreCodeWriterImpl nop() {
		return this.op( NOP );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aconst_null() {
		this.stack( Reference.class );
		return this.op( ACONST_NULL );
	}	
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_m1() {
		this.stack( int.class );
		return this.op( ICONST_M1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_0() {
		this.stack( int.class );
		return this.op( ICONST_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_1() {
		this.stack( int.class );
		return this.op( ICONST_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_2() {
		this.stack( int.class );
		return this.op( ICONST_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_3() {
		this.stack( int.class );
		return this.op( ICONST_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_4() {
		this.stack( int.class );
		return this.op( ICONST_4 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_5() {
		this.stack( int.class );
		return this.op( ICONST_5 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lconst_0() {
		this.stack( long.class );
		return this.op( LCONST_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lconst_1() {
		this.stack( long.class );
		return this.op( LCONST_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fconst_0() {
		this.stack( float.class );
		return this.op( FCONST_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fconst_1() {
		this.stack( float.class );
		return this.op( FCONST_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fconst_2() {
		this.stack( float.class );
		return this.op( FCONST_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dconst_0() {
		this.stack( double.class );
		return this.op( DCONST_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dconst_1() {
		this.stack( double.class );
		return this.op( DCONST_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl bipush( final byte value ) {
		this.stack( byte.class );
		return this.op( BIPUSH ).u1( value );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl sipush( final short value ) {
		this.stack( short.class );
		return this.op( SIPUSH ).u2( value );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ldc( final int index ) {
		this.stack( Category1.class );		
		return this.op( LDC ).u1( index );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ldc_w( final int index ) {
		this.stack( Category1.class );
		return this.op( LDC_W ).u2( index );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ldc2_w( final int index ) {
		this.stack( Category2.class );
		return this.op( LDC2_W ).u2( index );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload( final int slot ) {
		this.local( slot, int.class );
		this.stack( int.class );
		return this.op( ILOAD ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload_0() {
		this.local( 0, int.class );
		this.stack( int.class );
		return this.op( ILOAD_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload_1() {
		this.local( 1, int.class );
		this.stack( int.class );
		return this.op( ILOAD_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload_2() {
		this.local( 2, int.class );
		this.stack( int.class );
		return this.op( ILOAD_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload_3() {
		this.local( 3, int.class );
		this.stack( int.class );
		return this.op( ILOAD_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload( final int slot ) {
		this.local( slot, long.class );
		this.stack( long.class );
		return this.op( LLOAD ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload_0() {
		this.local( 0, long.class );
		this.stack( long.class );
		return this.op( LLOAD_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload_1() {
		this.local( 1, long.class );
		this.stack( long.class );
		return this.op( LLOAD_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload_2() {
		this.local( 2, long.class );
		this.stack( long.class );
		return this.op( LLOAD_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload_3() {
		this.local( 3, long.class );
		this.stack( long.class );
		return this.op( LLOAD_3 );
	}	
	
	@Override
	public final JavaCoreCodeWriterImpl fload( final int slot ) {
		this.local( slot, float.class );
		this.stack( float.class );
		return this.op( FLOAD ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fload_0() {
		this.local( 0, float.class );
		this.stack( float.class );
		return this.op( FLOAD_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fload_1() {
		this.local( 1, float.class );
		this.stack( float.class );
		return this.op( FLOAD_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fload_2() {
		this.local( 2, float.class );
		this.stack( float.class );
		return this.op( FLOAD_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fload_3() {
		this.local( 3, float.class );
		this.stack( float.class );
		return this.op( FLOAD_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload( final int slot ) {
		this.local( slot, double.class );
		this.stack( double.class );
		return this.op( DLOAD ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload_0() {
		this.local( 0, double.class );
		this.stack( double.class );
		return this.op( DLOAD_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload_1() {
		this.local( 1, double.class );
		this.stack( double.class );
		return this.op( DLOAD_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload_2() {
		this.local( 2, double.class );
		this.stack( double.class );
		return this.op( DLOAD_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload_3() {
		this.local( 3, double.class );
		this.stack( double.class );
		return this.op( DLOAD_3 );
	}	
	
	@Override
	public final JavaCoreCodeWriterImpl aload( final int slot ) {
		this.local( slot, Reference.class );
		this.unstack( Reference.class );
		return this.op( ALOAD ).u1( slot );
	}
	
	public final JavaCoreCodeWriterImpl this_() {
		return this.aload_0();
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aload_0() {
		this.local( 0, Reference.class );
		this.stack( Reference.class );
		return this.op( ALOAD_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aload_1() {
		this.local( 1, Reference.class );
		this.stack( Reference.class );
		return this.op( ALOAD_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aload_2() {
		this.local( 2, Reference.class );
		this.stack( Reference.class );
		return this.op( ALOAD_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aload_3() {
		this.local( 3, Reference.class );
		this.stack( Reference.class );
		return this.op( ALOAD_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iaload() {
		this.unstack( int[].class );
		this.unstack( int.class );
		this.stack( int.class );
		return this.op( IALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl laload() {
		this.unstack( long[].class );
		this.unstack( int.class );
		this.stack( int.class );
		return this.op( LALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl faload() {
		this.unstack( float[].class );
		this.unstack( int.class );
		this.stack( float.class );
		return this.op( FALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl daload() {
		this.unstack( double[].class );
		this.unstack( int.class );
		this.stack( double.class );
		return this.op( DALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aaload() {
		this.unstack( Reference[].class );
		this.unstack( int.class );
		this.stack( Reference.class );
		return this.op( AALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl baload() {
		this.unstack( byte[].class );
		this.unstack( int.class );
		this.stack( byte.class );
		return this.op( BALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl caload() {
		this.unstack( char[].class );
		this.unstack( int.class );
		this.stack( char.class );
		return this.op( CALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl saload() {
		this.unstack( short[].class );
		this.unstack( int.class );
		this.stack( short.class );
		return this.op( SALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl istore( final int index ) {
		this.local( index, int.class );
		this.unstack( int.class );
		return this.op( ISTORE ).u1( index );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl istore_0() {
		this.local( 0, int.class );
		this.unstack( int.class );
		return this.op( ISTORE_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl istore_1() {
		this.local( 1, int.class );
		this.unstack( int.class );
		return this.op( ISTORE_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl istore_2() {
		this.local( 2, int.class );
		this.unstack( int.class );
		return this.op( ISTORE_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl istore_3() {
		this.local( 3, int.class );
		this.unstack( int.class );
		return this.op( ISTORE_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lstore( final int slot ) {
		this.local( slot, long.class );
		this.unstack( long.class );
		return this.op( LSTORE ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lstore_0() {
		this.local( 0, long.class );
		this.unstack( long.class );
		return this.op( LSTORE_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lstore_1() {
		this.local( 1, long.class );
		this.unstack( long.class );
		return this.op( LSTORE_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lstore_2() {
		this.local( 2, long.class );
		this.unstack( long.class );
		return this.op( LSTORE_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lstore_3() {
		this.local( 3, long.class );
		this.unstack( long.class );
		return this.op( LSTORE_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fstore( final int slot ) {
		this.local( slot, float.class );
		this.unstack( float.class );
		return this.op( FSTORE ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fstore_0() {
		this.local( 0, float.class );
		this.unstack( float.class );
		return this.op( FSTORE_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fstore_1() {
		this.local( 1, float.class );
		this.unstack( float.class );
		return this.op( FSTORE_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fstore_2() {
		this.local( 2, float.class );
		this.unstack( float.class );
		return this.op( FSTORE_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fstore_3() {
		this.local( 3, float.class );
		this.unstack( float.class );
		return this.op( FSTORE_3 );
	}

	@Override
	public final JavaCoreCodeWriterImpl dstore( final int slot ) {
		this.local( slot, double.class );
		this.unstack( double.class );
		return this.op( DSTORE ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dstore_0() {
		this.local( 0, double.class );
		this.unstack( double.class );
		return this.op( DSTORE_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dstore_1() {
		this.local( 1, double.class );
		this.unstack( double.class );
		return this.op( DSTORE_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dstore_2() {
		this.local( 2, double.class );
		this.unstack( double.class );
		return this.op( DSTORE_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dstore_3() {
		this.local( 3, double.class );
		this.unstack( double.class );
		return this.op( DSTORE_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl astore( final int slot ) {
		this.local( slot, Reference.class );
		this.unstack( Reference.class );
		return this.op( ASTORE ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl astore_0() {
		this.local( 0, Reference.class );
		this.unstack( Reference.class );
		return this.op( ASTORE_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl astore_1() {
		this.local( 1, Reference.class );
		this.unstack( Reference.class );
		return this.op( ASTORE_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl astore_2() {
		this.local( 2, Reference.class );
		this.unstack( Reference.class );
		return this.op( ASTORE_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl astore_3() {
		this.local( 3, Reference.class );
		this.unstack( Reference.class );
		return this.op( ASTORE_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iastore() {
		this.unstack( int[].class );
		this.unstack( int.class );
		this.unstack( int.class );
		return this.op( IASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lastore() {
		this.unstack( long[].class );
		this.unstack( int.class );
		this.unstack( long.class );
		return this.op( LASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fastore() {
		this.unstack( float[].class );
		this.unstack( int.class );
		this.unstack( float.class );
		return this.op( FASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dastore() {
		this.unstack( double[].class );
		this.unstack( int.class );
		this.unstack( double.class );
		return this.op( DASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aastore() {
		this.unstack( Reference[].class );
		this.unstack( int.class );
		this.stack( Reference.class );
		return this.op( AASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl bastore() {
		this.unstack( byte[].class );
		this.unstack( int.class );
		this.unstack( byte.class );
		return this.op( BASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl castore() {
		this.unstack( char[].class );
		this.unstack( int.class );
		this.unstack( char.class );
		return this.op( CASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl sastore() {
		this.unstack( short[].class );
		this.unstack( int.class );
		this.unstack( short.class );
		return this.op( SASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl pop() {
		this.unstack( Category1.class );
		return this.op( POP );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl pop2() {
		this.unstack( Category2.class );
		return this.op( POP2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup() {
		this.unstack( Category1.class );
		this.stack( Category2.class );
		this.stack( Category2.class );
		return this.op( DUP );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup_x1() {
		//TODO: Verify this is correct
//		this.unstack();
//		this.stack();
//		this.stack();
		return this.op( DUP_X1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup_x2() {
		//TODO: Verify this is correct
//		this.unstack();
//		this.stack();
//		this.stack();
		return this.op( DUP_X2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup2() {
		//TODO: Verify this is correct
//		this.unstack();
//		this.stack();
//		this.stack();
		return this.op( DUP2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup2_x1() {
		//TODO: Verify this is correct
//		this.unstack();
//		this.stack();
//		this.stack();		
		return this.op( DUP2_X1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup2_x2() {
		//TODO: Verify this is correct
//		this.unstack();
//		this.stack();
//		this.stack();
		return this.op( DUP2_X2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl swap() {
		this.unstack( Category1.class );
		this.unstack( Category1.class );
		this.stack( Category1.class );
		this.stack( Category1.class );
		return this.op( SWAP );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iadd() {
		this.unstack( int.class );
		this.unstack( int.class );
		this.stack( int.class );
		return this.op( IADD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ladd() {
		this.unstack( long.class );
		this.unstack( long.class );
		this.stack( long.class );
		return this.op( LADD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fadd() {
		this.unstack( float.class );
		this.unstack( float.class );
		this.stack( float.class );
		return this.op( FADD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dadd() {
		this.unstack( double.class );
		this.unstack( double.class );
		this.stack( double.class );
		return this.op( DADD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl isub() {
		this.unstack( int.class );
		this.unstack( int.class );
		this.stack( int.class );
		return this.op( ISUB );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lsub() {
		this.unstack( long.class );
		this.unstack( long.class );
		this.stack( long.class );
		return this.op( LSUB );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fsub() {
		this.unstack( float.class );
		this.unstack( float.class );
		this.stack( float.class );
		return this.op( FSUB );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dsub() {
		this.unstack( double.class );
		this.unstack( double.class );
		this.stack( double.class );
		return this.op( DSUB );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl imul() {
		this.unstack( int.class );
		this.unstack( int.class );
		this.stack( int.class );
		return this.op( IMUL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lmul() {
		this.unstack( long.class );
		this.unstack( long.class );
		this.stack( long.class );
		return this.op( LMUL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fmul() {
		this.unstack( float.class );
		this.unstack( float.class );
		this.stack( float.class );
		return this.op( FMUL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dmul() {
		this.unstack( double.class );
		this.unstack( double.class );
		this.stack( double.class );
		return this.op( DMUL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl idiv() {
		this.unstack( int.class );
		this.unstack( int.class );
		this.stack( int.class );
		return this.op( IDIV );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ldiv() {
		this.unstack( long.class );
		this.unstack( long.class );
		this.stack( long.class );
		return this.op( LDIV );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fdiv() {
		this.unstack( float.class );
		this.unstack( float.class );
		this.stack( float.class );
		return this.op( FDIV );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ddiv() {
		this.unstack( double.class );
		this.unstack( double.class );
		this.stack( double.class );
		return this.op( DDIV );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl irem() {
		this.unstack( int.class );
		this.unstack( int.class );
		this.stack( int.class );
		return this.op( IREM );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lrem() {
		this.unstack( long.class );
		this.unstack( long.class );
		this.stack( long.class );
		return this.op( LREM );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl frem() {
		this.unstack( float.class );
		this.unstack( float.class );
		this.stack( float.class );
		return this.op( FREM );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl drem() {
		this.unstack( double.class );
		this.unstack( double.class );
		this.stack( double.class );
		return this.op( DREM );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ineg() {
		this.unstack( int.class );
		this.stack( int.class );
		return this.op( INEG );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lneg() {
		this.unstack( long.class );
		this.stack( long.class );
		return this.op( LNEG );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fneg() {
		this.unstack( float.class );
		this.stack( float.class );
		return this.op( FNEG );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dneg() {
		this.unstack( double.class );
		this.stack( double.class );
		return this.op( DNEG );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ishl() {
		this.unstack( int.class );
		this.unstack( int.class );
		this.stack( int.class );
		return this.op( ISHL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lshl() {
		this.unstack( long.class );
		this.unstack( int.class );
		this.stack( long.class );
		return this.op( LSHL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ishr() {
		this.unstack( int.class );
		this.unstack( int.class );
		this.stack( int.class );
		return this.op( ISHR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lshr() {
		this.unstack( long.class );
		this.unstack( int.class );
		this.stack( long.class );		
		return this.op( LSHR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iushr() {
		this.unstack( int.class );
		this.unstack( int.class );
		this.stack( int.class );
		return this.op( IUSHR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lushr() {
		this.unstack( long.class );
		this.unstack( int.class );
		this.stack( long.class );
		return this.op( LUSHR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iand() {
		this.unstack( int.class );
		this.unstack( int.class );
		this.stack( int.class );
		return this.op( IAND );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl land() {
		this.unstack( long.class );
		this.unstack( long.class );
		this.stack( long.class );
		return this.op( LAND );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ior() {
		this.unstack( int.class );
		this.unstack( int.class );
		this.stack( int.class );
		return this.op( IOR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lor() {
		this.unstack( long.class );
		this.unstack( long.class );
		this.stack( long.class );
		return this.op( LOR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ixor() {
		this.unstack( int.class );
		this.unstack( int.class );
		this.stack( int.class );
		return this.op( IXOR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lxor() {
		this.unstack( long.class );
		this.unstack( long.class );
		this.stack( long.class );
		return this.op( LXOR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iinc(
		final int slot,
		final int amount )
	{
		//TODO: Bound checks on amount
		
		this.local( slot, int.class );
		return this.op( IINC ).u1( slot ).u1( amount );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2l() {
		this.unstack( int.class );
		this.stack( long.class );
		return this.op( I2L );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2f() {
		this.unstack( int.class );
		this.stack( float.class );
		return this.op( I2F );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2d() {
		this.unstack( int.class );
		this.stack( double.class );
		return this.op( I2D );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl l2i() {
		this.unstack( long.class );
		this.stack( int.class );
		return this.op( L2I );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl l2f() {
		this.unstack( long.class );
		this.stack( float.class );
		return this.op( L2F );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl l2d() {
		this.unstack( long.class );
		this.stack( double.class );
		return this.op( L2D );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl f2i() {
		this.unstack( float.class );
		this.stack( int.class );
		return this.op( F2I );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl f2l() {
		this.unstack( float.class );
		this.stack( long.class );
		return this.op( F2L );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl f2d() {
		this.unstack( float.class );
		this.stack( double.class );
		return this.op( F2D );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl d2i() {
		this.unstack( double.class );
		this.stack( int.class );
		return this.op( D2I );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl d2l() {
		this.unstack( double.class );
		this.stack( long.class );
		return this.op( D2L );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl d2f() {
		this.unstack( double.class );
		this.stack( float.class );
		return this.op( D2F );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2b() {
		this.unstack( int.class );
		this.stack( byte.class );
		return this.op( I2B );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2c() {
		this.unstack( int.class );
		this.stack( char.class );
		return this.op( I2C );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2s() {
		this.unstack( int.class );
		this.stack( short.class );
		return this.op( I2S );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lcmp() {
		this.unstack( long.class );
		this.unstack( long.class );
		this.stack( int.class );
		return this.op( LCMP );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fcmpl() {
		this.unstack( float.class );
		this.unstack( float.class );
		this.stack( int.class );
		return this.op( FCMPL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fcmpg() {
		this.unstack( float.class );
		this.unstack( float.class );
		this.stack( int.class );
		return this.op( FCMPG );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dcmpl() {
		this.unstack( double.class );
		this.unstack( double.class );
		this.stack( int.class );
		return this.op( DCMPL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dcmpg() {
		this.unstack( double.class );
		this.unstack( double.class );
		this.stack( int.class );
		return this.op( DCMPG );
	}
	
	final JavaCoreCodeWriterImpl jsr() {
		return this.op( JSR );
	}
	
	final JavaCoreCodeWriterImpl ret() {
		return this.op( RET );
	}
	
	final JavaCoreCodeWriterImpl tableswitch() {
		return this.op( TABLESWITCH );
	}
	
	final JavaCoreCodeWriterImpl lookupswitch() {
		return this.op( LOOKUPSWITCH );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ireturn() {
		this.unstack( int.class );
		return this.op( IRETURN );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lreturn() {
		this.unstack( long.class );
		return this.op( LRETURN );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl freturn() {
		this.unstack( float.class );
		return this.op( FRETURN );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dreturn() {
		this.unstack( double.class );
		return this.op( DRETURN );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl areturn() {
		this.unstack( Reference.class );
		return this.op( ARETURN );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl return_() {
		return this.op( RETURN );
	}

	@Override
	public final JavaCoreCodeWriterImpl getstatic(
		final Type targetType,
		final JavaFieldDescriptor field )
	{
		this.stack( field.getType() );	
		return this.op( GETSTATIC ).u2(
			this.constantPool.addFieldReference(
				targetType,
				field.getType(),
				field.getName() ) );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl putstatic(
		final Type targetType,
		final JavaFieldDescriptor field )
	{
		this.unstack( field.getType() );
		return this.op( PUTSTATIC ).u2(
			this.constantPool.addFieldReference(
				targetType,
				field.getType(),
				field.getName() ) );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl getfield(
		final Type targetType,
		final JavaFieldDescriptor field )
	{
		this.unstack( targetType );
		this.stack( field.getType() );	
		return this.op( GETFIELD ).u2(
			this.constantPool.addFieldReference(
				targetType,
				field.getType(),
				field.getName() ) );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl putfield(
		final Type targetType,
		final JavaFieldDescriptor field )
	{
		this.unstack( targetType );
		this.unstack( field.getType() );
		return this.op( PUTFIELD ).u2(
			this.constantPool.addFieldReference(
				targetType,
				field.getType(),
				field.getName() ) );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl invokevirtual(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.unstack( targetType ); //this
		this.unstack( method.arguments() );
		this.stack( method.getReturnType() );
		
		return this.op( INVOKEVIRTUAL ).u2(
			this.constantPool.addMethodReference(
				targetType,
				method.getReturnType(),
				method.getName(),
				method.arguments() ) );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl invokeinterface(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.unstack( targetType ); //this
		this.unstack( method.arguments() );
		this.stack( method.getReturnType() );		
		
		return this.op( INVOKEINTERFACE ).u2(
			this.constantPool.addInterfaceMethodReference(
				targetType,
				method.getReturnType(),
				method.getName(),
				method.arguments() ) ).
			u1( size( method.arguments() ) + 1 ).
			u1( 0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl invokespecial(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.unstack( targetType ); //this
		this.unstack( method.arguments() );
		this.stack( method.getReturnType() );
		
		return this.op( INVOKESPECIAL ).u2(
			this.constantPool.addMethodReference(
				targetType,
				method.getReturnType(),
				method.getName(),
				method.arguments() ) );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl invokestatic(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.unstack( method.arguments() );
		this.stack( method.getReturnType() );
		
		return this.op( INVOKESTATIC ).u2(
			this.constantPool.addMethodReference(
				targetType,
				method.getReturnType(),
				method.getName(),
				method.arguments() ) );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl new_( final Type type ) {
		this.stack( type );
		return this.op( NEW ).u2(
			this.constantPool.addClassInfo(
				JavaTypes.getRawClass( type ) ) );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl newarray( final Type componentType ) {
		this.unstack( int.class );
		this.stack( JavaTypes.array( componentType ) );
		return this.op( NEWARRAY ).u1( getArrayTypeCode( componentType ) );
	}
	
	private static final byte getArrayTypeCode( final Type componentType ) {
		Type resolvedType = JavaTypes.resolve( componentType );
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
	public final JavaCoreCodeWriterImpl anewarray( final Type componentType ) {
		this.unstack( int.class );
		this.stack( JavaTypes.array( componentType ) );
		return this.op( ANEWARRAY ).u2(
			this.constantPool.addClassInfo( componentType ) );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl arraylength() {
		this.unstack( Any[].class );
		this.stack( int.class );
		return this.op( ARRAYLENGTH );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl athrow() {
		this.unstack( Exception.class );
		return this.op( ATHROW );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl checkcast( final Type type ) {
		this.unstack( Reference.class );
		this.stack( type );
		return this.op( CHECKCAST ).u2( this.constantPool.addClassInfo( type ) );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl instanceof_( final Type type ) {
		this.unstack( Reference.class );
		this.stack( boolean.class );
		return this.op( INSTANCEOF ).u2( this.constantPool.addClassInfo( type ) );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl monitorenter() {
		return this.op( MONITORENTER );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl monitorexit() {
		return this.op( MONITOREXIT );
	}
	
	final JavaCoreCodeWriterImpl wide() {
		return this.op( WIDE );
	}

	@Override
	public final JavaCoreCodeWriterImpl multianewarray(
		final Type arrayType,
		final int numDimensions )
	{	
		for ( int i = 0; i < numDimensions; ++i ) {
			this.unstack( int.class );
		}
		this.stack( arrayType );
		return this.op( MULTIANEWARRAY ).
			u2( this.constantPool.addClassInfo( arrayType ) ).
			u1( numDimensions );
	}
	
	@Override
	public final JavaCoreCodeWriter ifnull( final Jump jump ) {
		this.unstack( Reference.class );
		return this.op( IFNULL ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter ifnonnull( final Jump jump ) {
		this.unstack( Reference.class );
		return this.op( IFNONNULL ).jumpTo( jump );
	}
	
	final JavaCoreCodeWriterImpl goto_w() {
		return this.op( GOTO_W );
	}
	
	final JavaCoreCodeWriterImpl jsr_w() {
		return this.op( JSR_W );
	}

	@Override
	public final JavaCoreCodeWriter ifeq( final Jump jump ) {
		this.unstack( int.class );
		return this.op( IFEQ ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter ifne( final Jump jump ) {
		this.unstack( int.class );
		return this.op( IFNE ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter iflt( final Jump jump ) {
		this.unstack( int.class );
		return this.op( IFLT ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter ifgt( final Jump jump ) {
		this.unstack( int.class );
		return this.op( IFGT ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter ifge( final Jump jump ) {
		this.unstack( int.class );
		return this.op( IFGE ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter ifle( final Jump jump ) {
		this.unstack( int.class );
		return this.op( IFLE ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_icmpeq( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		return this.op( IF_ICMPEQ ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_icmpne( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		return this.op( IF_ICMPNE ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_icmplt( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		return this.op( IF_ICMPLT ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_icmpgt( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		return this.op( IF_ICMPGT ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_icmpge( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		return this.op( IF_ICMPGE ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_icmple( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		return this.op( IF_ICMPLE ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_acmpeq( final Jump jump ) {
		this.unstack( Reference.class );
		this.unstack( Reference.class );
		return this.op( IF_ACMPEQ ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_acmpne( final Jump jump ) {
		this.unstack( Reference.class );
		this.unstack( Reference.class );
		return this.op( IF_ACMPNE ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter goto_( final Jump jump ) {
		return this.op( GOTO ).jumpTo( jump );
	}
	
	private JavaCoreCodeWriter jumpTo( final Jump jump ) {		
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
	public final JavaCoreCodeWriter handleException( final ExceptionHandler exceptionHandler ) {
		this.handlers.add( exceptionHandler );
		return this;
	}
	
	private final void local( final int slot, final Type type ) {
		this.locals.local( slot, type );
	}
	
	private final void stack( final Type type ) {
		this.stack.push( type );
	}
	
	private final void unstack( final FormalArguments args ) {
		for ( JavaVariable var : args ) {
			this.unstack( var.getType() );
		}
	}
	
	private final void unstack( final Type type ) {
		if ( ! type.equals( void.class ) ) {
			this.stack.pop( type );
		}
	}
	
	private final JavaCoreCodeWriterImpl op( final byte opCode ) {
		this.codeOut.u1( opCode );
		return this;
	}
	
	private final JavaCoreCodeWriterImpl u1( final int value ) {
		this.codeOut.u1( value );
		return this;
	}
	
	private final JavaCoreCodeWriterImpl u2( final int value ) {
		this.codeOut.u2( value );
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
	
	final void write( final ByteStream out ) {
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
			int exceptionIndex =  this.constantPool.addClassInfo( handler.throwableClass() );
			out.u2( handler.startPos() ).
				u2( handler.endPos() ).
				u2( handler.handlerPos() ).
				u2( exceptionIndex );
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
	public final Locals locals() {
		return this.locals;
	}
	
	@Override
	public final Stack stack() {
		return this.stack;
	}
}
