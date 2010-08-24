package net.dougqh.jak;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import net.dougqh.java.meta.types.JavaTypes;
import static net.dougqh.jak.Operation.*;

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
	
	private int maxLocals;
	private int maxStack;
	private int curStack;
	
	private final ArrayList< ExceptionHandler > handlers = new ArrayList< ExceptionHandler >( 8 );	
	
	private final ArrayList< Byte2Slot > unresolvedSlots = new ArrayList < Byte2Slot >( 4 );
	private final ArrayList< Jump > unresolvedJumps = new ArrayList< Jump >( 4 );
	
	JavaCoreCodeWriterImpl(
		final ConstantPool constantPool,
		final int argSize )
	{
		this.codeOut = new ByteStream( 128 );
		this.constantPool = constantPool;
		
		this.maxStack = 0;
		this.maxLocals = argSize;
		this.curStack = 0;
	}

	@Override
	public final JavaCoreCodeWriterImpl nop() {
		return this.op( NOP );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aconst_null() {
		this.stack();
		return this.op( ACONST_NULL );
	}	
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_m1() {
		this.stack();
		return this.op( ICONST_M1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_0() {
		this.stack();
		return this.op( ICONST_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_1() {
		this.stack();
		return this.op( ICONST_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_2() {
		this.stack();
		return this.op( ICONST_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_3() {
		this.stack();
		return this.op( ICONST_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_4() {
		this.stack();
		return this.op( ICONST_4 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_5() {
		this.stack();
		return this.op( ICONST_5 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lconst_0() {
		this.stack2();
		return this.op( LCONST_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lconst_1() {
		this.stack2();
		return this.op( LCONST_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fconst_0() {
		this.stack();
		return this.op( FCONST_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fconst_1() {
		this.stack();
		return this.op( FCONST_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fconst_2() {
		this.stack();
		return this.op( FCONST_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dconst_0() {
		this.stack2();
		return this.op( DCONST_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dconst_1() {
		this.stack2();
		return this.op( DCONST_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl bipush( final byte value ) {
		this.stack();
		return this.op( BIPUSH ).u1( value );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl sipush( final short value ) {
		this.stack();
		return this.op( SIPUSH ).u2( value );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ldc( final int index ) {
		this.stack();		
		return this.op( LDC ).u1( index );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ldc_w( final int index ) {
		this.stack();
		return this.op( LDC_W ).u2( index );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ldc2_w( final int index ) {
		this.stack2();
		return this.op( LDC2_W ).u2( index );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload( final int slot ) {
		this.local( slot );
		this.stack();
		return this.op( ILOAD ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload_0() {
		this.local( 0 );
		this.stack();
		return this.op( ILOAD_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload_1() {
		this.local( 1 );
		this.stack();
		return this.op( ILOAD_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload_2() {
		this.local( 2 );
		this.stack();
		return this.op( ILOAD_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload_3() {
		this.local( 3 );
		this.stack();
		return this.op( ILOAD_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload( final int slot ) {
		this.local2( slot );
		this.stack2();
		return this.op( LLOAD ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload_0() {
		this.local2( 0 );
		this.stack2();
		return this.op( LLOAD_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload_1() {
		this.local2( 1 );
		this.stack2();
		return this.op( LLOAD_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload_2() {
		this.local2( 2 );
		this.stack2();
		return this.op( LLOAD_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload_3() {
		this.local2( 3 );
		this.stack2();
		return this.op( LLOAD_3 );
	}	
	
	@Override
	public final JavaCoreCodeWriterImpl fload( final int slot ) {
		this.local( slot );
		this.stack();
		return this.op( FLOAD ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fload_0() {
		this.stack();
		this.local( 0 );
		return this.op( FLOAD_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fload_1() {
		this.stack();
		this.local( 1 );
		return this.op( FLOAD_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fload_2() {
		this.stack();
		this.local( 2 );
		return this.op( FLOAD_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fload_3() {
		this.stack();
		this.local( 3 );
		return this.op( FLOAD_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload( final int slot ) {
		this.local2( slot );
		this.stack2();
		return this.op( DLOAD ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload_0() {
		this.local2( 0 );
		this.stack2();
		return this.op( DLOAD_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload_1() {
		this.local2( 1 );
		this.stack2();
		return this.op( DLOAD_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload_2() {
		this.local2( 2 );
		this.stack2();
		return this.op( DLOAD_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload_3() {
		this.local2( 3 );
		this.stack2();
		return this.op( DLOAD_3 );
	}	
	
	@Override
	public final JavaCoreCodeWriterImpl aload( final int slot ) {
		this.local( slot );
		this.unstack();
		return this.op( ALOAD ).u1( slot );
	}
	
	public final JavaCoreCodeWriterImpl this_() {
		return this.aload_0();
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aload_0() {
		this.local( 0 );
		this.stack();
		return this.op( ALOAD_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aload_1() {
		this.local( 1 );
		this.stack();
		return this.op( ALOAD_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aload_2() {
		this.local( 2 );
		this.stack();
		return this.op( ALOAD_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aload_3() {
		this.local( 3 );
		this.stack();
		return this.op( ALOAD_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iaload() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( IALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl laload() {
		this.unstack();
		this.unstack();
		this.stack2();
		return this.op( LALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl faload() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( FALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl daload() {
		this.unstack();
		this.unstack();
		this.stack2();
		return this.op( DALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aaload() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( AALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl baload() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( BALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl caload() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( CALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl saload() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( SALOAD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl istore( final int index ) {
		this.local( index );
		this.unstack();
		return this.op( ISTORE ).u1( index );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl istore_0() {
		this.local( 0 );
		this.unstack();
		return this.op( ISTORE_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl istore_1() {
		this.local( 1 );
		this.unstack();
		return this.op( ISTORE_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl istore_2() {
		this.local( 2 );
		this.unstack();
		return this.op( ISTORE_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl istore_3() {
		this.local( 3 );
		this.unstack();
		return this.op( ISTORE_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lstore( final int slot ) {
		this.local2( slot );
		this.unstack2();
		return this.op( LSTORE ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lstore_0() {
		this.local2( 0 );
		this.unstack();
		return this.op( LSTORE_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lstore_1() {
		this.local2( 1 );
		this.unstack();
		return this.op( LSTORE_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lstore_2() {
		this.local2( 2 );
		this.unstack();
		return this.op( LSTORE_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lstore_3() {
		this.local2( 3 );
		this.unstack();
		return this.op( LSTORE_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fstore( final int slot ) {
		this.local( slot );
		this.unstack();
		return this.op( FSTORE ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fstore_0() {
		this.local( 0 );
		this.unstack();
		return this.op( FSTORE_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fstore_1() {
		this.local( 1 );
		this.unstack();
		return this.op( FSTORE_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fstore_2() {
		this.local( 2 );
		this.unstack();
		return this.op( FSTORE_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fstore_3() {
		this.local( 3 );
		this.unstack();
		return this.op( FSTORE_3 );
	}

	@Override
	public final JavaCoreCodeWriterImpl dstore( final int slot ) {
		this.local2( slot );
		this.unstack2();
		return this.op( DSTORE ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dstore_0() {
		this.local2( 0 );
		this.unstack2();
		return this.op( DSTORE_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dstore_1() {
		this.local2( 1 );
		this.unstack2();
		return this.op( DSTORE_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dstore_2() {
		this.local2( 2 );
		this.unstack();
		return this.op( DSTORE_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dstore_3() {
		this.local2( 3 );
		this.unstack();
		return this.op( DSTORE_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl astore( final int slot ) {
		this.local( slot );
		this.unstack();
		return this.op( ASTORE ).u1( slot );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl astore_0() {
		this.local( 0 );
		this.unstack();		
		return this.op( ASTORE_0 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl astore_1() {
		this.local( 1 );
		this.unstack();
		return this.op( ASTORE_1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl astore_2() {
		this.local( 2 );
		this.unstack();
		return this.op( ASTORE_2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl astore_3() {
		this.local( 3 );
		this.unstack();
		return this.op( ASTORE_3 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iastore() {
		this.unstack();
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( IASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lastore() {
		this.unstack();
		this.unstack();
		this.unstack2();
		return this.op( LASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fastore() {
		this.unstack();
		this.unstack();
		this.unstack();
		return this.op( FASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dastore() {
		this.unstack();
		this.unstack();
		this.unstack2();
		return this.op( DASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aastore() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( AASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl bastore() {
		this.unstack();
		this.unstack();
		this.unstack();
		return this.op( BASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl castore() {
		this.unstack();
		this.unstack();
		this.unstack();
		return this.op( CASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl sastore() {
		this.unstack();
		this.unstack();
		this.unstack();
		return this.op( SASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl pop() {
		this.unstack();
		return this.op( POP );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl pop2() {
		return this.op( POP2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup() {
		this.unstack();
		this.stack();
		this.stack();
		return this.op( DUP );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup_x1() {
		//TODO: Verify this is correct
		this.unstack();
		this.stack();
		this.stack();
		return this.op( DUP_X1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup_x2() {
		//TODO: Verify this is correct
		this.unstack();
		this.stack();
		this.stack();
		return this.op( DUP_X2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup2() {
		//TODO: Verify this is correct
		this.unstack2();
		this.stack2();
		this.stack2();
		return this.op( DUP2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup2_x1() {
		//TODO: Verify this is correct
		this.unstack2();
		this.stack2();
		this.stack2();		
		return this.op( DUP2_X1 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup2_x2() {
		//TODO: Verify this is correct
		this.unstack2();
		this.stack2();
		this.stack2();
		return this.op( DUP2_X2 );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl swap() {
		this.unstack();
		this.unstack();
		this.stack();
		this.stack();
		return this.op( SWAP );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iadd() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( IADD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ladd() {
		this.unstack2();
		this.unstack2();
		this.stack2();
		return this.op( LADD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fadd() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( FADD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dadd() {
		this.unstack2();
		this.unstack2();
		this.stack();
		return this.op( DADD );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl isub() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( ISUB );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lsub() {
		this.unstack2();
		this.unstack2();
		this.stack2();
		return this.op( LSUB );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fsub() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( FSUB );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dsub() {
		this.unstack2();
		this.unstack2();
		this.stack2();
		return this.op( DSUB );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl imul() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( IMUL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lmul() {
		this.unstack2();
		this.unstack2();
		this.stack2();
		return this.op( LMUL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fmul() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( FMUL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dmul() {
		this.unstack2();
		this.unstack2();
		this.stack2();
		return this.op( DMUL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl idiv() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( IDIV );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ldiv() {
		this.unstack2();
		this.unstack2();
		this.stack2();
		return this.op( LDIV );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fdiv() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( FDIV );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ddiv() {
		this.unstack2();
		this.unstack2();
		this.stack2();
		return this.op( DDIV );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl irem() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( IREM );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lrem() {
		this.unstack2();
		this.unstack2();
		this.stack2();
		return this.op( LREM );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl frem() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( FREM );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl drem() {
		this.unstack2();
		this.unstack2();
		this.stack2();
		return this.op( DREM );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ineg() {
		this.unstack();
		this.stack();
		return this.op( INEG );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lneg() {
		this.unstack2();
		this.stack2();
		return this.op( LNEG );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fneg() {
		this.unstack();
		this.stack();
		return this.op( FNEG );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dneg() {
		this.unstack2();
		this.stack2();
		return this.op( DNEG );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ishl() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( ISHL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lshl() {
		this.unstack2();
		this.unstack();
		this.stack2();
		return this.op( LSHL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ishr() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( ISHR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lshr() {
		this.unstack2();
		this.unstack();
		this.stack2();		
		return this.op( LSHR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iushr() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( IUSHR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lushr() {
		this.unstack2();
		this.unstack();
		this.stack2();		
		return this.op( LUSHR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iand() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( IAND );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl land() {
		this.unstack2();
		this.unstack2();
		this.stack();
		return this.op( LAND );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ior() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( IOR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lor() {
		this.unstack2();
		this.unstack2();
		this.stack2();
		return this.op( LOR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ixor() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( IXOR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lxor() {
		this.unstack2();
		this.unstack2();
		this.stack2();
		return this.op( LXOR );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iinc(
		final int slot,
		final int amount )
	{
		//TODO: Bound checks on amount
		
		this.local( slot );
		return this.op( IINC ).u1( slot ).u1( amount );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2l() {
		this.unstack();
		this.stack2();
		return this.op( I2L );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2f() {
		this.unstack();
		this.stack();
		return this.op( I2F );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2d() {
		this.unstack();
		this.stack2();
		return this.op( I2D );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl l2i() {
		this.unstack2();
		this.stack();
		return this.op( L2I );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl l2f() {
		this.unstack2();
		this.stack();
		return this.op( L2F );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl l2d() {
		this.unstack2();
		this.stack2();
		return this.op( L2D );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl f2i() {
		this.unstack();
		this.stack();
		return this.op( F2I );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl f2l() {
		this.unstack();
		this.stack2();
		return this.op( F2L );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl f2d() {
		this.unstack();
		this.stack2();
		return this.op( F2D );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl d2i() {
		this.unstack2();
		this.stack();
		return this.op( D2I );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl d2l() {
		this.unstack2();
		this.stack2();
		return this.op( D2L );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl d2f() {
		this.unstack2();
		this.stack();
		return this.op( D2F );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2b() {
		this.unstack();
		this.stack();
		return this.op( I2B );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2c() {
		this.unstack();
		this.stack();
		return this.op( I2C );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2s() {
		this.unstack();
		this.stack();
		return this.op( I2S );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lcmp() {
		this.unstack2();
		this.unstack2();
		this.stack();
		return this.op( LCMP );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fcmpl() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( FCMPL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fcmpg() {
		this.unstack();
		this.unstack();
		this.stack();
		return this.op( FCMPG );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dcmpl() {
		this.unstack2();
		this.unstack2();
		this.stack();
		return this.op( DCMPL );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dcmpg() {
		this.unstack2();
		this.unstack2();
		this.stack();
		return this.op( DCMPG );
	}
//	
//	public final JavaCoreCodeWriterImpl ifeq( final String label ) {
//		this.unstack();
//		return this.op( IFEQ ).jumpTo( label );
//	}
//	
//	protected final JavaCoreCodeWriterImpl ifeq( final int relativeOffset ) {
//		this.unstack();
//		return this.op( IFEQ ).u2( relativeOffset );
//	}
//	
//	public final JavaCoreCodeWriterImpl ifne( final String label ) {
//		this.unstack();
//		return this.op( IFNE ).jumpTo( label );
//	}
//	
//	public final JavaCoreCodeWriterImpl iflt( final String label ) {
//		this.unstack();
//		return this.op( IFLT ).jumpTo( label );
//	}
//	
//	public final JavaCoreCodeWriterImpl ifgt( final String label ) {
//		return this.op( IFGT ).jumpTo( label );
//	}
//	
//	public final JavaCoreCodeWriterImpl ifge( final String label ) {
//		return this.op( IFGE ).jumpTo( label );
//	}
//	
//	public final JavaCoreCodeWriterImpl ifle( final String label ) {
//		return this.op( IFLE ).jumpTo( label );
//	}
//	
//	public final JavaCoreCodeWriterImpl if_icmpeq( final String label ) {
//		this.unstack();
//		this.unstack();
//		return this.op( IF_ICMPEQ ).jumpTo( label );
//	}
//	
//	public final JavaCoreCodeWriterImpl if_icmpne( final String label ) {
//		this.unstack();
//		this.unstack();
//		return this.op( IF_ICMPNE ).jumpTo( label );
//	}
//	
//	public final JavaCoreCodeWriterImpl if_icmplt( final String label ) {
//		this.unstack();
//		this.unstack();
//		return this.op( IF_ICMPLT ).jumpTo( label );
//	}
//	
//	public final JavaCoreCodeWriterImpl if_icmpgt( final String label ) {
//		this.unstack();
//		this.unstack();
//		return this.op( IF_ICMPGT ).jumpTo( label );
//	}
//	
//	public final JavaCoreCodeWriterImpl if_icmpge( final String label ) {
//		this.unstack();
//		this.unstack();
//		return this.op( IF_ICMPGE ).jumpTo( label );
//	}
//	
//	public final JavaCoreCodeWriterImpl if_icmple( final String label ) {
//		this.unstack();
//		this.unstack();
//		return this.op( IF_ICMPLE ).jumpTo( label );
//	}
//	
//	public final JavaCoreCodeWriterImpl if_acmpeq( final String label ) {
//		this.unstack();
//		this.unstack();
//		return this.op( IF_ACMPEQ ).jumpTo( label );
//	}
//	
//	public final JavaCoreCodeWriterImpl if_acmpne( final String label ) {
//		this.unstack();
//		this.unstack();
//		return this.op( IF_ACMPNE ).jumpTo( label );
//	}
//	
//	protected final JavaCoreCodeWriterImpl goto_( final int relativeOffset ) {
//		return this.op( GOTO ).u2( relativeOffset );
//	}
//	
//	public final JavaCoreCodeWriterImpl goto_( final String label ) {
//		return this.op( GOTO ).jumpTo( label );
//	}
	
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
		this.unstack();
		return this.op( IRETURN );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lreturn() {
		this.unstack2();
		return this.op( LRETURN );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl freturn() {
		this.unstack();
		return this.op( FRETURN );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dreturn() {
		this.unstack2();
		return this.op( DRETURN );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl areturn() {
		this.unstack();
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
		this.unstack(); //this
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
		this.unstack(); //this
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
		this.unstack(); //this
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
		this.stack();
		return this.op( NEW ).u2(
			this.constantPool.addClassInfo(
				JavaTypes.getRawClass( type ) ) );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl newarray( final Type componentType ) {
		this.unstack();
		this.stack();
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
		this.unstack();
		this.stack();
		return this.op( ANEWARRAY ).u2(
			this.constantPool.addClassInfo( componentType ) );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl arraylength() {
		this.unstack();
		this.stack();
		return this.op( ARRAYLENGTH );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl athrow() {
		this.unstack();
		return this.op( ATHROW );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl checkcast( final Type type ) {
		this.unstack();
		this.stack();
		return this.op( CHECKCAST ).u2( this.constantPool.addClassInfo( type ) );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl instanceof_( final Type type ) {
		this.unstack();
		this.stack();
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
			this.unstack();
		}
		this.stack();
		return this.op( MULTIANEWARRAY ).
			u2( this.constantPool.addClassInfo( arrayType ) ).
			u1( numDimensions );
	}
	
	@Override
	public final JavaCoreCodeWriter ifnull( final Jump jump ) {
		this.unstack();
		return this.op( IFNULL ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter ifnonnull( final Jump jump ) {
		this.unstack();
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
		this.unstack();
		return this.op( IFEQ ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter ifne( final Jump jump ) {
		this.unstack();
		return this.op( IFNE ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter iflt( final Jump jump ) {
		this.unstack();
		return this.op( IFLT ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter ifgt( final Jump jump ) {
		return this.op( IFGT ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter ifge( final Jump jump ) {
		return this.op( IFGE ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter ifle( final Jump jump ) {
		return this.op( IFLE ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_icmpeq( final Jump jump ) {
		this.unstack();
		this.unstack();
		return this.op( IF_ICMPEQ ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_icmpne( final Jump jump ) {
		this.unstack();
		this.unstack();
		return this.op( IF_ICMPNE ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_icmplt( final Jump jump ) {
		this.unstack();
		this.unstack();
		return this.op( IF_ICMPLT ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_icmpgt( final Jump jump ) {
		this.unstack();
		this.unstack();
		return this.op( IF_ICMPGT ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_icmpge( final Jump jump ) {
		this.unstack();
		this.unstack();
		return this.op( IF_ICMPGE ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_icmple( final Jump jump ) {
		this.unstack();
		this.unstack();
		return this.op( IF_ICMPLE ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_acmpeq( final Jump jump ) {
		this.unstack();
		this.unstack();
		return this.op( IF_ACMPEQ ).jumpTo( jump );
	}
	
	@Override
	public final JavaCoreCodeWriter if_acmpne( final Jump jump ) {
		this.unstack();
		this.unstack();
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
	
	private final void local( final int slot ) {
		if ( slot >= this.maxLocals ) {
			this.maxLocals = slot + 1;
		}
	}
	
	private final void local2( final int slot ) {
		if ( slot + 1 >= this.maxLocals ) {
			this.maxLocals = slot + 2;
		}
	}
	
	private final void stack() {
		++this.curStack;
		if ( this.curStack > this.maxStack ) {
			this.maxStack = this.curStack;
		}
	}
	
	private final void stack2() {
		this.curStack += 2;
		if ( this.curStack > this.maxStack ) {
			this.maxStack = this.curStack;
		}
	}
	
	private final void unstack( final FormalArguments arguments ) {
		this.curStack -= size( arguments );
	}
	
	private final void stack( final Type returnType ) {
		if ( returnType.equals( void.class ) ) {
			//nothing
		} else if ( returnType.equals( long.class ) ) {
			this.stack2();
		} else if ( returnType.equals( double.class ) ) {
			this.stack2();
		} else {
			this.stack();
		}
	}
	
	private final void unstack() {
		--this.curStack;
	}	
	
	private final void unstack2() {
		this.curStack -= 2;
	}
	
	private final void unstack( final Type returnType ) {
		if ( returnType.equals( void.class ) ) {
			//nothing
		} else if ( returnType.equals( long.class ) ) {
			this.unstack2();
		} else if ( returnType.equals( double.class ) ) {
			this.unstack2();
		} else {
			this.unstack();
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
			size += size( variable.type() );
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
		
		out.u2( this.maxStack );
		out.u2( this.maxLocals );
		
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
	
	@Override
	public final int maxStack() {
		if ( this.handlers.isEmpty() ) {
			return this.maxStack;
		} else {
			//DQH - Not sure if this is necessary.
			//In most cases, operations that could raise 
			//an Exception would also pop an item from
			//the stack.  However, some operations "new"
			//for example could raise an OutOfMemoryError
			//which would if caught add an element to the 
			//stack without popping the stack.
			return this.maxStack + 1;
		}
	}
	
	final int curStack() {
		return this.curStack;
	}
	
	@Override
	public final int maxLocals() {
		return this.maxLocals;
	}
	
	final int codeLength() {
		return this.codeOut.length();
	}
	
	@Override
	public final int pos() {
		return this.codeLength();
	}
}
