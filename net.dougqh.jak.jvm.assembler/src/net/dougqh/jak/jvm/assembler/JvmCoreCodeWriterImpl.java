package net.dougqh.jak.jvm.assembler;

import static net.dougqh.jak.jvm.operations.JvmOperation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import net.dougqh.jak.FormalArguments;
import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.JavaVariable;
import net.dougqh.jak.assembler.TypeResolver;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.Reference;
import net.dougqh.java.meta.types.JavaTypes;

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
		final ConstantPool constantPool,
		final JvmLocals locals,
		final JvmStack stack )
	{
		this.context = context;
		
		this.codeOut = new JvmOutputStream( 128 );
		this.constantPool = constantPool;
		
		this.locals = locals;
		this.stack = stack;
	}
	
	@Override
	public final JvmCoreCodeWriter prepare() {
		if ( this.deferredWrite != null ) {
			DeferredWrite deferredWrite = this.deferredWrite;
			this.deferredWrite = null;
			deferredWrite.write( this, false );
		}
		return this;
	}
	
	private final void prepareLast() {
		if ( this.deferredWrite != null ) {
			DeferredWrite deferredWrite = this.deferredWrite;
			this.deferredWrite = null;
			deferredWrite.write( this, true );
		}		
	}
	
	public final JvmCoreCodeWriter defer( final DeferredWrite deferredWrite ) {
		this.prepare();
		
		this.deferredWrite = deferredWrite;
		return this;
	}
	
	@Override
	public final WritingContext context() {
		return this.context;
	}

	@Override
	public final JvmCoreCodeWriterImpl nop() {
		return this.op( NOP );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl aconst_null() {
		try {
			return this.op( ACONST_NULL );
		} finally {
			this.stack( Reference.class );
		}
	}	
	
	@Override
	public final JvmCoreCodeWriterImpl iconst_m1() {
		try {
			return this.op( ICONST_M1 );			
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iconst_0() {
		try {
			return this.op( ICONST_0 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iconst_1() {
		try {
			return this.op( ICONST_1 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iconst_2() {
		try {
			return this.op( ICONST_2 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iconst_3() {
		try {
			return this.op( ICONST_3 );			
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iconst_4() {
		try {
			return this.op( ICONST_4 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iconst_5() {
		try {
			return this.op( ICONST_5 );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lconst_0() {
		try {
			return this.op( LCONST_0 );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lconst_1() {
		try {
			return this.op( LCONST_1 );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fconst_0() {
		try {
			return this.op( FCONST_0 );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fconst_1() {
		try {
			return this.op( FCONST_1 );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fconst_2() {
		try {
			return this.op( FCONST_2 );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dconst_0() {
		try {
			return this.op( DCONST_0 );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dconst_1() {
		try {
			return this.op( DCONST_1 );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl bipush( final byte value ) {
		try {
			return this.op( BIPUSH ).u1( value );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl sipush( final short value ) {
		try {
			return this.op( SIPUSH ).u2( value );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl ldc( final ConstantEntry entry ) {
		try {
			return this.op( LDC ).u1( entry );
		} finally {
			this.stack( entry.type() );			
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl ldc_w( final ConstantEntry entry ) {
		try {
			return this.op( LDC_W ).u2( entry );
		} finally {
			this.stack( entry.type() );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl ldc2_w( final ConstantEntry entry ) {
		try {
			return this.op( LDC2_W ).u2( entry );
		} finally {
			this.stack( entry.type() );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iload( final int slot ) {
		try {
			return this.op( ILOAD ).u1( slot );
		} finally {
			this.load( slot, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iload_0() {
		try {
			return this.op( ILOAD_0 );
		} finally {
			this.load( 0, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iload_1() {
		try {
			return this.op( ILOAD_1 );
		} finally {
			this.load( 1, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iload_2() {
		try {
			return this.op( ILOAD_2 );
		} finally {
			this.load( 2, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iload_3() {
		try {
			return this.op( ILOAD_3 );
		} finally {
			this.load( 3, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lload( final int slot ) {
		try {
			return this.op( LLOAD ).u1( slot );
		} finally {
			this.load( slot, long.class );
			this.stack( long.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lload_0() {
		try {
			return this.op( LLOAD_0 );
		} finally {
			this.load( 0, long.class );
			this.stack( long.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lload_1() {
		try {
			return this.op( LLOAD_1 );
		} finally {
			this.load( 1, long.class );
			this.stack( long.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lload_2() {
		try {
			return this.op( LLOAD_2 );
		} finally {
			this.load( 2, long.class );
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lload_3() {
		try {
			return this.op( LLOAD_3 );
		} finally {
			this.load( 3, long.class );
			this.stack( long.class );
		}
	}	
	
	@Override
	public final JvmCoreCodeWriterImpl fload( final int slot ) {
		try {
			return this.op( FLOAD ).u1( slot );
		} finally {
			this.load( slot, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fload_0() {
		try {
			return this.op( FLOAD_0 );
		} finally {
			this.load( 0, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fload_1() {
		try {
			return this.op( FLOAD_1 );
		} finally {
			this.load( 1, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fload_2() {
		try {
			return this.op( FLOAD_2 );	
		} finally {
			this.load( 2, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fload_3() {
		try {
			return this.op( FLOAD_3 );
		} finally {
			this.load( 3, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dload( final int slot ) {
		try {
			return this.op( DLOAD ).u1( slot );
		} finally {
			this.load( slot, double.class );
			this.stack( double.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dload_0() {
		try {
			return this.op( DLOAD_0 );
		} finally {
			this.load( 0, double.class );
			this.stack( double.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dload_1() {
		try {
			return this.op( DLOAD_1 );
		} finally {
			this.load( 1, double.class );
			this.stack( double.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dload_2() {
		try {
			return this.op( DLOAD_2 );
		} finally {
			this.load( 2, double.class );
			this.stack( double.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dload_3() {
		try {
			return this.op( DLOAD_3 );
		} finally {
			this.load( 3, double.class );
			this.stack( double.class );
		}
	}	
	
	@Override
	public final JvmCoreCodeWriterImpl aload( final int slot ) {
		try {
			return this.op( ALOAD ).u1( slot );
		} finally {
			Type actualType = this.load( slot, Reference.class );
			this.stack( actualType );
		}
	}
	
	public final JvmCoreCodeWriterImpl this_() {
		return this.aload_0();
	}
	
	@Override
	public final JvmCoreCodeWriterImpl aload_0() {
		try {
			return this.op( ALOAD_0 );
		} finally {
			Type actualType = this.load( 0, Reference.class );
			this.stack( actualType );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl aload_1() {
		try {
			return this.op( ALOAD_1 );
		} finally {
			Type actualType = this.load( 1, Reference.class );
			this.stack( actualType );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl aload_2() {
		try {
			return this.op( ALOAD_2 );
		} finally {
			Type actualType = this.load( 2, Reference.class );
			this.stack( actualType );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl aload_3() {
		try {
			return this.op( ALOAD_3 );
		} finally {
			Type actualType = this.load( 3, Reference.class );
			this.stack( actualType );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iaload() {
		this.unstack( int.class );
		this.unstack( int[].class );
		try {
			return this.op( IALOAD );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl laload() {
		this.unstack( int.class );
		this.unstack( long[].class );
		try {
			return this.op( LALOAD );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl faload() {
		this.unstack( int.class );
		this.unstack( float[].class );
		try {
			return this.op( FALOAD );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl daload() {
		this.unstack( int.class );
		this.unstack( double[].class );
		try {
			return this.op( DALOAD );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl aaload() {
		this.unstack( int.class );
		this.unstack( Reference[].class );
		try {
			return this.op( AALOAD );
		} finally {
			this.stack( Reference.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl baload() {
		this.unstack( int.class );
		this.unstack( byte[].class );
		try {
			return this.op( BALOAD );
		} finally {
			this.stack( byte.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl caload() {
		this.unstack( int.class );
		this.unstack( char[].class );
		try {
			return this.op( CALOAD );
		} finally {
			this.stack( char.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl saload() {
		this.unstack( int.class );
		this.unstack( short[].class );
		try {
			return this.op( SALOAD );
		} finally {
			this.stack( short.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl istore( final int index ) {
		this.store( index, int.class );
		try {
			return this.op( ISTORE ).u1( index );
		} finally {
			this.unstack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl istore_0() {
		this.store( 0, int.class );
		this.unstack( int.class );
		return this.op( ISTORE_0 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl istore_1() {
		this.store( 1, int.class );
		this.unstack( int.class );
		return this.op( ISTORE_1 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl istore_2() {
		this.store( 2, int.class );
		this.unstack( int.class );
		return this.op( ISTORE_2 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl istore_3() {
		this.store( 3, int.class );
		this.unstack( int.class );
		return this.op( ISTORE_3 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lstore( final int slot ) {
		this.store( slot, long.class );
		this.unstack( long.class );
		return this.op( LSTORE ).u1( slot );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lstore_0() {
		this.store( 0, long.class );
		this.unstack( long.class );
		return this.op( LSTORE_0 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lstore_1() {
		this.store( 1, long.class );
		this.unstack( long.class );
		return this.op( LSTORE_1 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lstore_2() {
		this.store( 2, long.class );
		this.unstack( long.class );
		return this.op( LSTORE_2 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lstore_3() {
		this.store( 3, long.class );
		this.unstack( long.class );
		return this.op( LSTORE_3 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fstore( final int slot ) {
		this.store( slot, float.class );
		this.unstack( float.class );
		return this.op( FSTORE ).u1( slot );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fstore_0() {
		this.store( 0, float.class );
		this.unstack( float.class );
		return this.op( FSTORE_0 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fstore_1() {
		this.store( 1, float.class );
		this.unstack( float.class );
		return this.op( FSTORE_1 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fstore_2() {
		this.store( 2, float.class );
		this.unstack( float.class );
		return this.op( FSTORE_2 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fstore_3() {
		this.store( 3, float.class );
		this.unstack( float.class );
		return this.op( FSTORE_3 );
	}

	@Override
	public final JvmCoreCodeWriterImpl dstore( final int slot ) {
		this.store( slot, double.class );
		this.unstack( double.class );
		return this.op( DSTORE ).u1( slot );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dstore_0() {
		this.store( 0, double.class );
		this.unstack( double.class );
		return this.op( DSTORE_0 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dstore_1() {
		this.store( 1, double.class );
		this.unstack( double.class );
		return this.op( DSTORE_1 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dstore_2() {
		this.store( 2, double.class );
		this.unstack( double.class );
		return this.op( DSTORE_2 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dstore_3() {
		this.store( 3, double.class );
		this.unstack( double.class );
		return this.op( DSTORE_3 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl astore( final int slot ) {
		Type actualType = this.topType( Reference.class );
		this.store( slot, actualType );
		this.unstack( actualType );
		return this.op( ASTORE ).u1( slot );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl astore_0() {
		Type actualType = this.topType( Reference.class );
		this.store( 0, actualType );
		this.unstack( actualType );
		return this.op( ASTORE_0 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl astore_1() {
		Type actualType = this.topType( Reference.class );
		this.store( 1, actualType );
		this.unstack( actualType );
		return this.op( ASTORE_1 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl astore_2() {
		Type actualType = this.topType( Reference.class );
		this.store( 2, actualType );
		this.unstack( actualType );
		return this.op( ASTORE_2 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl astore_3() {
		Type actualType = this.topType( Reference.class );
		this.store( 3, actualType );
		this.unstack( actualType );
		return this.op( ASTORE_3 );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iastore() {
		this.unstack( int.class );
		this.unstack( int.class );
		this.unstack( int[].class );
		return this.op( IASTORE );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lastore() {
		this.unstack( long.class );
		this.unstack( int.class );
		this.unstack( long[].class );
		return this.op( LASTORE );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fastore() {
		this.unstack( float.class );
		this.unstack( int.class );
		this.unstack( float[].class );
		return this.op( FASTORE );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dastore() {
		this.unstack( double.class );
		this.unstack( int.class );
		this.unstack( double[].class );
		return this.op( DASTORE );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl aastore() {
		this.unstack( Reference.class );
		this.unstack( int.class );
		this.unstack( Reference[].class );
		return this.op( AASTORE );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl bastore() {
		this.unstack( byte.class );
		this.unstack( int.class );
		this.unstack( byte[].class );
		return this.op( BASTORE );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl castore() {
		this.unstack( char.class );
		this.unstack( int.class );
		this.unstack( char[].class );
		return this.op( CASTORE );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl sastore() {
		this.unstack( short.class );
		this.unstack( int.class );
		this.unstack( short[].class );
		return this.op( SASTORE );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl pop() {
		try {
			return this.op( POP );
		} finally {
			this.stack.pop();
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl pop2() {
		try {
			return this.op( POP2 );
		} finally {
			this.stack.pop2();
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dup() {
		try {
			return this.op( DUP );
		} finally {
			this.stack.dup();
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dup_x1() {
		try {
			return this.op( DUP_X1 );
		} finally {
			this.stack.dup_x1();
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dup_x2() {
		try {
			return this.op( DUP_X2 );
		} finally {
			this.stack.dup_x2();
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dup2() {
		try {
			return this.op( DUP2 );
		} finally {
			this.stack.dup2();
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dup2_x1() {
		try {
			return this.op( DUP2_X1 );
		} finally {
			this.stack.dup2_x1();
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dup2_x2() {
		try {
			return this.op( DUP2_X2 );
		} finally {
			this.stack.dup2_x2();
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl swap() {
		try {
			return this.op( SWAP );
		} finally {
			this.stack.swap();
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iadd() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IADD );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl ladd() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LADD );
		} finally {
			this.stack( long.class );			
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fadd() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			return this.op( FADD );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dadd() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			return this.op( DADD );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl isub() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( ISUB );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lsub() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LSUB );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fsub() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			return this.op( FSUB );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dsub() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			return this.op( DSUB );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl imul() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IMUL );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lmul() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LMUL );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fmul() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			return this.op( FMUL );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dmul() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			return this.op( DMUL );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl idiv() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IDIV );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl ldiv() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LDIV );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fdiv() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			return this.op( FDIV );
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl ddiv() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			return this.op( DDIV );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl irem() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IREM );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lrem() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LREM );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl frem() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			return this.op( FREM );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl drem() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			return this.op( DREM );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl ineg() {
		this.unstack( int.class );
		try {
			return this.op( INEG );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lneg() {
		this.unstack( long.class );
		try {
			return this.op( LNEG );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fneg() {
		this.unstack( float.class );
		try {
			return this.op( FNEG );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dneg() {
		this.unstack( double.class );
		try {
			return this.op( DNEG );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl ishl() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( ISHL );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lshl() {
		this.unstack( int.class );
		this.unstack( long.class );
		try {
			return this.op( LSHL );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl ishr() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( ISHR );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lshr() {
		this.unstack( int.class );
		this.unstack( long.class );
		try {
			return this.op( LSHR );
		} finally {
			this.stack( long.class );						
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iushr() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IUSHR );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lushr() {
		this.unstack( int.class );
		this.unstack( long.class );
		try {
			return this.op( LUSHR );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iand() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IAND );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl land() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LAND );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl ior() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IOR );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lor() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LOR );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl ixor() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			return this.op( IXOR );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lxor() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LXOR );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl iinc(
		final int slot,
		final int amount )
	{
		//TODO: Bound checks on amount
		
		try {
			return this.op( IINC ).u1( slot ).u1( amount );
		} finally {
			this.inc( slot );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl i2l() {
		this.unstack( int.class );
		try {
			return this.op( I2L );
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl i2f() {
		this.unstack( int.class );
		try {
			return this.op( I2F );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl i2d() {
		this.unstack( int.class );
		try {
			return this.op( I2D );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl l2i() {
		this.unstack( long.class );
		try {
			return this.op( L2I );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl l2f() {
		this.unstack( long.class );
		try {
			return this.op( L2F );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl l2d() {
		this.unstack( long.class );
		try {
			return this.op( L2D );
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl f2i() {
		this.unstack( float.class );
		try {
			return this.op( F2I );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl f2l() {
		this.unstack( float.class );
		try {
			return this.op( F2L );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl f2d() {
		this.unstack( float.class );
		try {
			return this.op( F2D );
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl d2i() {
		this.unstack( double.class );
		try {
			return this.op( D2I );
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl d2l() {
		this.unstack( double.class );
		try {
			return this.op( D2L );
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl d2f() {
		this.unstack( double.class );
		try {
			return this.op( D2F );
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl i2b() {
		this.unstack( int.class );
		try {
			return this.op( I2B );
		} finally {
			this.stack( byte.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl i2c() {
		this.unstack( int.class );
		try {
			return this.op( I2C );
		} finally {
			this.stack( char.class );			
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl i2s() {
		this.unstack( int.class );
		try {
			return this.op( I2S );
		} finally {
			this.stack( short.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lcmp() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			return this.op( LCMP );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fcmpl() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			return this.op( FCMPL );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl fcmpg() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			return this.op( FCMPG );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dcmpl() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			return this.op( DCMPL );
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dcmpg() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			return this.op( DCMPG );
		} finally {
			this.stack( int.class );			
		}
	}
	
	final JvmCoreCodeWriterImpl jsr() {
		return this.op( JSR );
	}
	
	final JvmCoreCodeWriterImpl ret() {
		return this.op( RET );
	}
	
	final JvmCoreCodeWriterImpl tableswitch() {
		return this.op( TABLESWITCH );
	}
	
	final JvmCoreCodeWriterImpl lookupswitch() {
		return this.op( LOOKUPSWITCH );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl ireturn() {
		this.unstack( int.class );
		return this.op( IRETURN );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl lreturn() {
		this.unstack( long.class );
		return this.op( LRETURN );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl freturn() {
		this.unstack( float.class );
		return this.op( FRETURN );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl dreturn() {
		this.unstack( double.class );
		return this.op( DRETURN );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl areturn() {
		this.unstack( Reference.class );
		return this.op( ARETURN );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl return_() {
		return this.op( RETURN );
	}

	@Override
	public final JvmCoreCodeWriterImpl getstatic(
		final Type targetType,
		final JavaField field )
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
	public final JvmCoreCodeWriterImpl putstatic(
		final Type targetType,
		final JavaField field )
	{
		this.unstack( field.getType() );
		return this.op( PUTSTATIC ).u2(
			this.constantPool.addFieldReference(
				targetType,
				field.getType(),
				field.getName() ) );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl getfield(
		final Type targetType,
		final JavaField field )
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
	public final JvmCoreCodeWriterImpl putfield(
		final Type targetType,
		final JavaField field )
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
	public final JvmCoreCodeWriterImpl invokevirtual(
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
	public final JvmCoreCodeWriterImpl invokeinterface(
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
	public final JvmCoreCodeWriterImpl invokespecial(
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
	public final JvmCoreCodeWriterImpl invokestatic(
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
	public final JvmCoreCodeWriterImpl new_( final Type type ) {
		try {
			return this.op( NEW ).u2(
				this.constantPool.addClassInfo(
					JavaTypes.getRawClass( type ) ) );
		} finally {
			this.stack( type );			
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl newarray( final Type componentType ) {
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
	public final JvmCoreCodeWriterImpl anewarray( final Type componentType ) {
		this.unstack( int.class );
		try {
			return this.op( ANEWARRAY ).u2( this.constantPool.addClassInfo( componentType ) );
		} finally {
			this.stack( JavaTypes.array( componentType ) );
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl arraylength() {
		this.unstack( Any[].class );
		try {
			return this.op( ARRAYLENGTH );
		} finally {
			this.stack( int.class );			
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl athrow() {
		this.unstack( Throwable.class );
		return this.op( ATHROW );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl checkcast( final Type type ) {
		this.unstack( Reference.class );
		try {
			return this.op( CHECKCAST ).u2( this.constantPool.addClassInfo( type ) );
		} finally {
			this.stack( type );			
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl instanceof_( final Type type ) {
		this.unstack( Reference.class );
		try {
			return this.op( INSTANCEOF ).u2( this.constantPool.addClassInfo( type ) );
		} finally {
			this.stack( boolean.class );	
		}
	}
	
	@Override
	public final JvmCoreCodeWriterImpl monitorenter() {
		this.unstack( Reference.class );
		return this.op( MONITORENTER );
	}
	
	@Override
	public final JvmCoreCodeWriterImpl monitorexit() {
		this.unstack( Reference.class );
		return this.op( MONITOREXIT );
	}
	
	final JvmCoreCodeWriterImpl wide() {
		return this.op( WIDE );
	}

	@Override
	public final JvmCoreCodeWriterImpl multianewarray(
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
	public final JvmCoreCodeWriter ifnull( final Jump jump ) {
		this.unstack( Reference.class );
		return this.op( IFNULL ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter ifnonnull( final Jump jump ) {
		this.unstack( Reference.class );
		return this.op( IFNONNULL ).jumpTo( jump );
	}
	
	final JvmCoreCodeWriterImpl goto_w() {
		return this.op( GOTO_W );
	}
	
	final JvmCoreCodeWriterImpl jsr_w() {
		return this.op( JSR_W );
	}

	@Override
	public final JvmCoreCodeWriter ifeq( final Jump jump ) {
		this.unstack( int.class );
		return this.op( IFEQ ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter ifne( final Jump jump ) {
		this.unstack( int.class );
		return this.op( IFNE ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter iflt( final Jump jump ) {
		this.unstack( int.class );
		return this.op( IFLT ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter ifgt( final Jump jump ) {
		this.unstack( int.class );
		return this.op( IFGT ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter ifge( final Jump jump ) {
		this.unstack( int.class );
		return this.op( IFGE ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter ifle( final Jump jump ) {
		this.unstack( int.class );
		return this.op( IFLE ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter if_icmpeq( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		return this.op( IF_ICMPEQ ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter if_icmpne( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		return this.op( IF_ICMPNE ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter if_icmplt( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		return this.op( IF_ICMPLT ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter if_icmpgt( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		return this.op( IF_ICMPGT ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter if_icmpge( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		return this.op( IF_ICMPGE ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter if_icmple( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		return this.op( IF_ICMPLE ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter if_acmpeq( final Jump jump ) {
		this.unstack( Reference.class );
		this.unstack( Reference.class );
		return this.op( IF_ACMPEQ ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter if_acmpne( final Jump jump ) {
		this.unstack( Reference.class );
		this.unstack( Reference.class );
		return this.op( IF_ACMPNE ).jumpTo( jump );
	}
	
	@Override
	public final JvmCoreCodeWriter goto_( final Jump jump ) {
		return this.op( GOTO ).jumpTo( jump );
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
	public final JvmCoreCodeWriter handleException( final ExceptionHandler exceptionHandler ) {
		this.handlers.add( exceptionHandler );
		return this;
	}
	
	@Override
	public final void prepareForWrite() {
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
		this.wrapper.prepareForWrite();
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
