package net.dougqh.jak;

import static net.dougqh.jak.operations.Operation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.Category1;
import net.dougqh.jak.types.Category2;
import net.dougqh.jak.types.Reference;
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
		try {
			return this.op( ACONST_NULL );
		} finally {
			this.stack( Reference.class );
		}
	}	
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_m1() {
		try {
			return this.op( ICONST_M1 );			
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_0() {
		try {
			return this.op( ICONST_0 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_1() {
		try {
			return this.op( ICONST_1 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_2() {
		try {
			return this.op( ICONST_2 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_3() {
		try {
			return this.op( ICONST_3 );			
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_4() {
		try {
			return this.op( ICONST_4 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iconst_5() {
		try {
			return this.op( ICONST_5 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lconst_0() {
		try {
			return this.op( LCONST_0 );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lconst_1() {
		try {
			return this.op( LCONST_1 );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fconst_0() {
		try {
			return this.op( FCONST_0 );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fconst_1() {
		try {
			return this.op( FCONST_1 );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fconst_2() {
		try {
			return this.op( FCONST_2 );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dconst_0() {
		try {
			return this.op( DCONST_0 );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dconst_1() {
		try {
			return this.op( DCONST_1 );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl bipush( final byte value ) {
		try {
			return this.op( BIPUSH ).u1( value );
		} finally {
			this.stack( byte.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl sipush( final short value ) {
		try {
			return this.op( SIPUSH ).u2( value );
		} finally {
			this.stack( short.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ldc( final int index ) {
		try {
			return this.op( LDC ).u1( index );
		} finally {
			this.stack( Category1.class );			
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ldc_w( final int index ) {
		try {
			return this.op( LDC_W ).u2( index );
		} finally {
			this.stack( Category1.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ldc2_w( final int index ) {
		try {
			return this.op( LDC2_W ).u2( index );
		} finally {
			this.stack( Category2.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload( final int slot ) {
		this.local( slot, int.class );
		try {
			return this.op( ILOAD ).u1( slot );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload_0() {
		this.local( 0, int.class );
		try {
			return this.op( ILOAD_0 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload_1() {
		this.local( 1, int.class );
		try {
			return this.op( ILOAD_1 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload_2() {
		this.local( 2, int.class );
		try {
			return this.op( ILOAD_2 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iload_3() {
		this.local( 3, int.class );
		try {
			return this.op( ILOAD_3 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload( final int slot ) {
		this.local( slot, long.class );
		try {
			return this.op( LLOAD ).u1( slot );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload_0() {
		this.local( 0, long.class );
		try {
			return this.op( LLOAD_0 );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload_1() {
		this.local( 1, long.class );
		try {
			return this.op( LLOAD_1 );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload_2() {
		this.local( 2, long.class );
		try {
			return this.op( LLOAD_2 );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lload_3() {
		this.local( 3, long.class );
		try {
			return this.op( LLOAD_3 );
		} finally {
			this.stack( long.class );
		}
	}	
	
	@Override
	public final JavaCoreCodeWriterImpl fload( final int slot ) {
		this.local( slot, float.class );
		try {
			return this.op( FLOAD ).u1( slot );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fload_0() {
		this.local( 0, float.class );
		try {
			return this.op( FLOAD_0 );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fload_1() {
		this.local( 1, float.class );
		try {
			return this.op( FLOAD_1 );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fload_2() {
		this.local( 2, float.class );
		try {
			return this.op( FLOAD_2 );	
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fload_3() {
		this.local( 3, float.class );
		try {
			return this.op( FLOAD_3 );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload( final int slot ) {
		this.local( slot, double.class );
		try {
			return this.op( DLOAD ).u1( slot );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload_0() {
		this.local( 0, double.class );
		try {
			return this.op( DLOAD_0 );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload_1() {
		this.local( 1, double.class );
		try {
			return this.op( DLOAD_1 );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload_2() {
		this.local( 2, double.class );
		try {
			return this.op( DLOAD_2 );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dload_3() {
		this.local( 3, double.class );
		try {
			return this.op( DLOAD_3 );
		} finally {
			this.stack( double.class );
		}
	}	
	
	@Override
	public final JavaCoreCodeWriterImpl aload( final int slot ) {
		this.local( slot, Reference.class );
		try {
			return this.op( ALOAD ).u1( slot );
		} finally {
			this.stack( Reference.class );
		}
	}
	
	public final JavaCoreCodeWriterImpl this_() {
		return this.aload_0();
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aload_0() {
		this.local( 0, Reference.class );
		try {
			return this.op( ALOAD_0 );
		} finally {
			this.stack( Reference.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aload_1() {
		this.local( 1, Reference.class );
		try {
			return this.op( ALOAD_1 );
		} finally {
			this.stack( Reference.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aload_2() {
		this.local( 2, Reference.class );
		try {
			return this.op( ALOAD_2 );
		} finally {
			this.stack( Reference.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aload_3() {
		this.local( 3, Reference.class );
		try {
			return this.op( ALOAD_3 );
		} finally {
			this.stack( Reference.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iaload() {
		this.unstack( int.class );
		this.unstack( int[].class );
		try {
			return this.op( IALOAD );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl laload() {
		this.unstack( int.class );
		this.unstack( long[].class );
		try {
			return this.op( LALOAD );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl faload() {
		this.unstack( int.class );
		this.unstack( float[].class );
		try {
			return this.op( FALOAD );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl daload() {
		this.unstack( int.class );
		this.unstack( double[].class );
		try {
			return this.op( DALOAD );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aaload() {
		this.unstack( int.class );
		this.unstack( Reference[].class );
		try {
			return this.op( AALOAD );
		} finally {
			this.stack( Reference.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl baload() {
		this.unstack( int.class );
		this.unstack( byte[].class );
		try {
			return this.op( BALOAD );
		} finally {
			this.stack( byte.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl caload() {
		this.unstack( int.class );
		this.unstack( char[].class );
		try {
			return this.op( CALOAD );
		} finally {
			this.stack( char.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl saload() {
		this.unstack( int.class );
		this.unstack( short[].class );
		try {
			return this.op( SALOAD );
		} finally {
			this.stack( short.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl istore( final int index ) {
		this.local( index, int.class );
		try {
			return this.op( ISTORE ).u1( index );
		} finally {
			this.unstack( int.class );	
		}
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
		this.unstack( int.class );
		this.unstack( int.class );
		this.unstack( int[].class );
		return this.op( IASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lastore() {
		this.unstack( long.class );
		this.unstack( int.class );
		this.unstack( long[].class );
		return this.op( LASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fastore() {
		this.unstack( float.class );
		this.unstack( int.class );
		this.unstack( float[].class );
		return this.op( FASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dastore() {
		this.unstack( double.class );
		this.unstack( int.class );
		this.unstack( double[].class );
		return this.op( DASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl aastore() {
		this.unstack( Reference.class );
		this.unstack( int.class );
		this.unstack( Reference[].class );
		return this.op( AASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl bastore() {
		this.unstack( byte.class );
		this.unstack( int.class );
		this.unstack( byte[].class );
		return this.op( BASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl castore() {
		this.unstack( char.class );
		this.unstack( int.class );
		this.unstack( char[].class );
		return this.op( CASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl sastore() {
		this.unstack( short.class );
		this.unstack( int.class );
		this.unstack( short[].class );
		return this.op( SASTORE );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl pop() {
		try {
			return this.op( POP );
		} finally {
			this.stack.pop();
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl pop2() {
		try {
			return this.op( POP2 );
		} finally {
			this.stack.pop2();
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup() {
		try {
			return this.op( DUP );
		} finally {
			this.stack.dup();
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup_x1() {
		try {
			return this.op( DUP_X1 );
		} finally {
			this.stack.dup_x1();
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup_x2() {
		try {
			return this.op( DUP_X2 );
		} finally {
			this.stack.dup_x2();
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup2() {
		try {
			return this.op( DUP2 );
		} finally {
			this.stack.dup2();
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup2_x1() {
		try {
			return this.op( DUP2_X1 );
		} finally {
			this.stack.dup2_x1();
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dup2_x2() {
		try {
			return this.op( DUP2_X2 );
		} finally {
			this.stack.dup2_x2();
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl swap() {
		try {
			return this.op( SWAP );
		} finally {
			this.stack.swap();
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iadd() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IADD );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ladd() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LADD );
		} finally {
			this.stack( long.class );			
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fadd() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			return this.op( FADD );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dadd() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			return this.op( DADD );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl isub() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( ISUB );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lsub() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LSUB );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fsub() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			return this.op( FSUB );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dsub() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			return this.op( DSUB );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl imul() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IMUL );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lmul() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LMUL );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fmul() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			return this.op( FMUL );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dmul() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			return this.op( DMUL );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl idiv() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IDIV );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ldiv() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LDIV );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fdiv() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			return this.op( FDIV );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ddiv() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			return this.op( DDIV );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl irem() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IREM );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lrem() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LREM );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl frem() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			return this.op( FREM );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl drem() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			return this.op( DREM );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ineg() {
		this.unstack( int.class );
		try {
			return this.op( INEG );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lneg() {
		this.unstack( long.class );
		try {
			return this.op( LNEG );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fneg() {
		this.unstack( float.class );
		try {
			return this.op( FNEG );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dneg() {
		this.unstack( double.class );
		try {
			return this.op( DNEG );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ishl() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( ISHL );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lshl() {
		this.unstack( int.class );
		this.unstack( long.class );
		try {
			return this.op( LSHL );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ishr() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( ISHR );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lshr() {
		this.unstack( int.class );
		this.unstack( long.class );
		try {
			return this.op( LSHR );
		} finally {
			this.stack( long.class );						
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iushr() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IUSHR );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lushr() {
		this.unstack( int.class );
		this.unstack( long.class );
		try {
			return this.op( LUSHR );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl iand() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IAND );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl land() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LAND );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ior() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IOR );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lor() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LOR );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl ixor() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IXOR );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lxor() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LXOR );
		} finally {
			this.stack( long.class );	
		}
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
		try {
			return this.op( I2L );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2f() {
		this.unstack( int.class );
		try {
			return this.op( I2F );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2d() {
		this.unstack( int.class );
		try {
			return this.op( I2D );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl l2i() {
		this.unstack( long.class );
		try {
			return this.op( L2I );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl l2f() {
		this.unstack( long.class );
		try {
			return this.op( L2F );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl l2d() {
		this.unstack( long.class );
		try {
			return this.op( L2D );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl f2i() {
		this.unstack( float.class );
		try {
			return this.op( F2I );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl f2l() {
		this.unstack( float.class );
		try {
			return this.op( F2L );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl f2d() {
		this.unstack( float.class );
		try {
			return this.op( F2D );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl d2i() {
		this.unstack( double.class );
		try {
			return this.op( D2I );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl d2l() {
		this.unstack( double.class );
		try {
			return this.op( D2L );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl d2f() {
		this.unstack( double.class );
		try {
			return this.op( D2F );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2b() {
		this.unstack( int.class );
		try {
			return this.op( I2B );
		} finally {
			this.stack( byte.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2c() {
		this.unstack( int.class );
		try {
			return this.op( I2C );
		} finally {
			this.stack( char.class );			
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl i2s() {
		this.unstack( int.class );
		try {
			return this.op( I2S );
		} finally {
			this.stack( short.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl lcmp() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LCMP );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fcmpl() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			return this.op( FCMPL );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl fcmpg() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			return this.op( FCMPG );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dcmpl() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			return this.op( DCMPL );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl dcmpg() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			return this.op( DCMPG );
		} finally {
			this.stack( int.class );			
		}
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
		try {
			return this.op( GETSTATIC ).u2(
				this.constantPool.addFieldReference(
					targetType,
					field.getType(),
					field.getName() ) );
		} finally {
			this.stack( field.getType() );		
		}
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
		try {
			return this.op( GETFIELD ).u2(
				this.constantPool.addFieldReference(
					targetType,
					field.getType(),
					field.getName() ) );
		} finally {
			this.stack( field.getType() );	
		}
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
		this.unstack( method.arguments() );
		this.unstack( targetType ); //this
		try {
			return this.op( INVOKEVIRTUAL ).u2(
				this.constantPool.addMethodReference(
					targetType,
					method.getReturnType(),
					method.getName(),
					method.arguments() ) );
		} finally {
			this.stack( method.getReturnType() );			
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl invokeinterface(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.unstack( method.arguments() );
		this.unstack( targetType ); //this
		try {
			return this.op( INVOKEINTERFACE ).u2(
				this.constantPool.addInterfaceMethodReference(
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
	public final JavaCoreCodeWriterImpl invokespecial(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.unstack( method.arguments() );
		this.unstack( targetType ); //this
		try {
			return this.op( INVOKESPECIAL ).u2(
				this.constantPool.addMethodReference(
					targetType,
					method.getReturnType(),
					method.getName(),
					method.arguments() ) );
		} finally {
			this.stack( method.getReturnType() );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl invokestatic(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.unstack( method.arguments() );
		try {
			return this.op( INVOKESTATIC ).u2(
				this.constantPool.addMethodReference(
					targetType,
					method.getReturnType(),
					method.getName(),
					method.arguments() ) );
		} finally {
			this.stack( method.getReturnType() );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl new_( final Type type ) {
		try {
			return this.op( NEW ).u2(
				this.constantPool.addClassInfo(
					JavaTypes.getRawClass( type ) ) );
		} finally {
			this.stack( type );			
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl newarray( final Type componentType ) {
		this.unstack( int.class );
		try {
			return this.op( NEWARRAY ).u1( getArrayTypeCode( componentType ) );
		} finally {
			this.stack( JavaTypes.array( componentType ) );	
		}
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
		try {
			return this.op( ANEWARRAY ).u2( this.constantPool.addClassInfo( componentType ) );
		} finally {
			this.stack( JavaTypes.array( componentType ) );
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl arraylength() {
		this.unstack( Any[].class );
		try {
			return this.op( ARRAYLENGTH );
		} finally {
			this.stack( int.class );			
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl athrow() {
		this.unstack( Throwable.class );
		return this.op( ATHROW );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl checkcast( final Type type ) {
		this.unstack( Reference.class );
		try {
			return this.op( CHECKCAST ).u2( this.constantPool.addClassInfo( type ) );
		} finally {
			this.stack( type );			
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl instanceof_( final Type type ) {
		this.unstack( Reference.class );
		try {
			return this.op( INSTANCEOF ).u2( this.constantPool.addClassInfo( type ) );
		} finally {
			this.stack( boolean.class );	
		}
	}
	
	@Override
	public final JavaCoreCodeWriterImpl monitorenter() {
		this.unstack( Reference.class );
		return this.op( MONITORENTER );
	}
	
	@Override
	public final JavaCoreCodeWriterImpl monitorexit() {
		this.unstack( Reference.class );
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
		try {
			return this.op( MULTIANEWARRAY ).
				u2( this.constantPool.addClassInfo( arrayType ) ).
				u1( numDimensions );
		} finally {
			this.stack( arrayType );			
		}
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
		this.stack.stack( type );
	}
	
	private final void unstack( final FormalArguments args ) {
		Type[] types = args.getTypes();
		for ( int i = types.length - 1; i >= 0; --i ) {
			this.unstack( types[ i ] );
		}
	}
	
	private final void unstack( final Type type ) {
		if ( ! type.equals( void.class ) ) {
			this.stack.unstack( type );
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
