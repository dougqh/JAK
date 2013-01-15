package net.dougqh.jak.jvm;

import java.lang.reflect.Type;

import net.dougqh.jak.FormalArguments;
import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.Reference;
import net.dougqh.java.meta.types.JavaTypes;

//TODO: Fix some of the order of operations between stacks and locals -- particularly in store operations
public abstract class TrackingJvmOperationProcessor implements JvmOperationProcessor {	
	protected abstract JvmLocals locals();
	
	protected abstract JvmStack stack();
	
	protected abstract JvmOperationProcessor wrapped();

	@Override
	public final void nop() {
		this.wrapped().nop();
	}
	
	@Override
	public final void aconst_null() {
		try {
			this.wrapped().aconst_null();
		} finally {
			this.stack( Reference.class );
		}
	}	
	
	@Override
	public final void iconst_m1() {
		try {
			this.wrapped().iconst_m1();		
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iconst_0() {
		try {
			this.wrapped().iconst_0();
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iconst_1() {
		try {
			this.wrapped().iconst_1();
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iconst_2() {
		try {
			this.wrapped().iconst_2();
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iconst_3() {
		try {
			this.wrapped().iconst_3();	
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iconst_4() {
		try {
			this.wrapped().iconst_4();
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iconst_5() {
		try {
			this.wrapped().iconst_5();
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void lconst_0() {
		try {
			this.wrapped().lconst_0();
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final void lconst_1() {
		try {
			this.wrapped().lconst_1();
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final void fconst_0() {
		try {
			this.wrapped().fconst_0();
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void fconst_1() {
		try {
			this.wrapped().fconst_1();
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void fconst_2() {
		try {
			this.wrapped().fconst_2();
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void dconst_0() {
		try {
			this.wrapped().dconst_0();
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final void dconst_1() {
		try {
			this.wrapped().dconst_1();
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final void bipush( final byte value ) {
		try {
			this.wrapped().bipush(value);
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void sipush( final short value ) {
		try {
			this.wrapped().sipush(value);
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void ldc( final float value ) {
		try {
			this.wrapped().ldc(value);
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void ldc( final int value ) {
		try {
			this.wrapped().ldc(value);
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void ldc( final String value ) {
		try {
			this.wrapped().ldc(value);
		} finally {
			this.stack( String.class );
		}
	}
	
	@Override
	public final void ldc( final Type value ) {
		try {
			this.wrapped().ldc(value);
		} finally {
			this.stack( Class.class );
		}		
	}
	
	@Override
	public final void ldc_w( final float value ) {
		try {
			this.wrapped().ldc_w(value);
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void ldc_w( final int value ) {
		try {
			this.wrapped().ldc_w(value);
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void ldc_w( final String value ) {
		try {
			this.wrapped().ldc_w(value);
		} finally {
			this.stack( String.class );
		}
	}
	
	@Override
	public final void ldc_w( final Type value ) {
		try {
			this.wrapped().ldc_w(value);
		} finally {
			this.stack( Class.class );
		}
	}
	
	@Override
	public final void ldc2_w( final double value ) {
		try {
			this.wrapped().ldc2_w(value);
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final void ldc2_w( final long value ) {
		try {
			this.wrapped().ldc2_w(value);
		} finally {
			this.stack( double.class );
		}		
	}
	
	@Override
	public final void iload( final Slot slot ) {
		try {
			this.wrapped().iload(slot);
		} finally {
			this.load( slot.pos(), int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iload( final int slot ) {
		try {
			this.wrapped().iload(slot);
		} finally {
			this.load( slot, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iload_0() {
		try {
			this.wrapped().iload_0();
		} finally {
			this.load( 0, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iload_1() {
		try {
			this.wrapped().iload_1();
		} finally {
			this.load( 1, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iload_2() {
		try {
			this.wrapped().iload_2();
		} finally {
			this.load( 2, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final void iload_3() {
		try {
			this.wrapped().iload_3();
		} finally {
			this.load( 3, int.class );
			this.stack( int.class );
		}
	}
	
	@Override
	public final void lload( final Slot slot ) {
		try {
			this.wrapped().lload(slot);
		} finally {
			this.load( slot.pos(), long.class );
			this.stack( long.class );
		}
	}

	@Override
	public final void lload( final int slot ) {
		try {
			this.wrapped().lload(slot);
		} finally {
			this.load( slot, long.class );
			this.stack( long.class );
		}
	}
	
	@Override
	public final void lload_0() {
		try {
			this.wrapped().lload_0();
		} finally {
			this.load( 0, long.class );
			this.stack( long.class );
		}
	}
	
	@Override
	public final void lload_1() {
		try {
			this.wrapped().lload_1();
		} finally {
			this.load( 1, long.class );
			this.stack( long.class );
		}
	}
	
	@Override
	public final void lload_2() {
		try {
			this.wrapped().lload_2();
		} finally {
			this.load( 2, long.class );
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void lload_3() {
		try {
			this.wrapped().lload_3();
		} finally {
			this.load( 3, long.class );
			this.stack( long.class );
		}
	}	
	
	@Override
	public final void fload( final Slot slot ) {
		try {
			this.wrapped().fload(slot);
		} finally {
			this.load( slot.pos(), long.class );
			this.stack( long.class );
		}
	}

	@Override
	public final void fload( final int slot ) {
		try {
			this.wrapped().fload(slot);
		} finally {
			this.load( slot, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final void fload_0() {
		try {
			this.wrapped().fload_0();
		} finally {
			this.load( 0, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final void fload_1() {
		try {
			this.wrapped().fload_1();
		} finally {
			this.load( 1, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final void fload_2() {
		try {
			this.wrapped().fload_2();	
		} finally {
			this.load( 2, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final void fload_3() {
		try {
			this.wrapped().fload_3();
		} finally {
			this.load( 3, float.class );
			this.stack( float.class );
		}
	}
	
	@Override
	public final void dload( final Slot slot ) {
		try {
			this.wrapped().dload(slot);
		} finally {
			this.load(slot.pos(), double.class);
			this.stack(double.class);
		}
	}

	@Override
	public final void dload( final int slot ) {
		try {
			this.wrapped().dload(slot);
		} finally {
			this.load( slot, double.class );
			this.stack( double.class );
		}
	}
	
	@Override
	public final void dload_0() {
		try {
			this.wrapped().dload_0();
		} finally {
			this.load( 0, double.class );
			this.stack( double.class );
		}
	}
	
	@Override
	public final void dload_1() {
		try {
			this.wrapped().dload_1();
		} finally {
			this.load( 1, double.class );
			this.stack( double.class );
		}
	}
	
	@Override
	public final void dload_2() {
		try {
			this.wrapped().dload_2();
		} finally {
			this.load( 2, double.class );
			this.stack( double.class );
		}
	}
	
	@Override
	public final void dload_3() {
		try {
			this.wrapped().dload_3();
		} finally {
			this.load( 3, double.class );
			this.stack( double.class );
		}
	}
	
	@Override
	public final void aload( final Slot slot ) {
		try {
			this.wrapped().aload(slot);
		} finally {
			Type actualType = this.load(slot.pos(), Reference.class);
			this.stack(actualType);
		}
	}
	
	@Override
	public final void aload( final int slot ) {
		try {
			this.wrapped().aload(slot);
		} finally {
			Type actualType = this.load( slot, Reference.class );
			this.stack( actualType );
		}
	}
	
	@Override
	public final void aload_0() {
		try {
			this.wrapped().aload_0();
		} finally {
			Type actualType = this.load( 0, Reference.class );
			this.stack( actualType );
		}
	}
	
	@Override
	public final void aload_1() {
		try {
			this.wrapped().aload_1();
		} finally {
			Type actualType = this.load( 1, Reference.class );
			this.stack( actualType );
		}
	}
	
	@Override
	public final void aload_2() {
		try {
			this.wrapped().aload_2();
		} finally {
			Type actualType = this.load( 2, Reference.class );
			this.stack( actualType );
		}
	}
	
	@Override
	public final void aload_3() {
		try {
			this.wrapped().aload_3();
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
			this.wrapped().iaload();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void laload() {
		this.unstack( int.class );
		this.unstack( long[].class );
		try {
			this.wrapped().laload();
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final void faload() {
		this.unstack( int.class );
		this.unstack( float[].class );
		try {
			this.wrapped().faload();
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void daload() {
		this.unstack( int.class );
		this.unstack( double[].class );
		try {
			this.wrapped().daload();
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final void aaload() {
		this.unstack( int.class );
		this.unstack( Reference[].class );
		try {
			this.wrapped().aaload();
		} finally {
			this.stack( Reference.class );	
		}
	}
	
	@Override
	public final void baload() {
		this.unstack( int.class );
		this.unstack( byte[].class );
		try {
			this.wrapped().baload();
		} finally {
			this.stack( byte.class );	
		}
	}
	
	@Override
	public final void caload() {
		this.unstack( int.class );
		this.unstack( char[].class );
		try {
			this.wrapped().caload();
		} finally {
			this.stack( char.class );	
		}
	}
	
	@Override
	public final void saload() {
		this.unstack( int.class );
		this.unstack( short[].class );
		try {
			this.wrapped().saload();
		} finally {
			this.stack( short.class );	
		}
	}
	
	@Override
	public final void istore( final Slot slot ) {
		this.unstack(int.class);
		try {
			this.wrapped().istore(slot);
		} finally {
			this.istore( slot.pos() );			
		}
	}
	
	@Override
	public final void istore( final int index ) {
		this.unstack( int.class );
		this.store( index, int.class );
		
		this.wrapped().istore(index);
	}
	
	@Override
	public final void istore_0() {
		this.store( 0, int.class );
		this.unstack( int.class );
		
		this.wrapped().istore_0();
	}
	
	@Override
	public final void istore_1() {
		this.store( 1, int.class );
		this.unstack( int.class );
		
		this.wrapped().istore_1();
	}
	
	@Override
	public final void istore_2() {
		this.store( 2, int.class );
		this.unstack( int.class );
		
		this.wrapped().istore_2();
	}
	
	@Override
	public final void istore_3() {
		this.store( 3, int.class );
		this.unstack( int.class );
		
		this.wrapped().istore_3();
	}
	
	@Override
	public final void lstore( final Slot slot ) {
		this.store( slot.pos(), long.class );
		this.unstack( long.class );
		
		this.wrapped().lstore(slot);
	}
	
	@Override
	public final void lstore( final int slot ) {
		this.store( slot, long.class );
		this.unstack( long.class );
		
		this.wrapped().lstore(slot);
	}
	
	@Override
	public final void lstore_0() {
		this.store( 0, long.class );
		this.unstack( long.class );
		
		this.wrapped().lstore_0();
	}
	
	@Override
	public final void lstore_1() {
		this.store( 1, long.class );
		this.unstack( long.class );
		
		this.wrapped().lstore_1();
	}
	
	@Override
	public final void lstore_2() {
		this.store( 2, long.class );
		this.unstack( long.class );
		
		this.wrapped().lstore_1();
	}
	
	@Override
	public final void lstore_3() {
		this.store( 3, long.class );
		this.unstack( long.class );
		
		this.wrapped().lstore_3();
	}
	
	@Override
	public final void fstore( final Slot slot ) {
		this.store( slot.pos(), float.class );
		this.unstack( float.class );
		
		this.wrapped().fstore(slot);
	}
	
	@Override
	public final void fstore( final int slot ) {
		this.store( slot, float.class );
		this.unstack( float.class );
		
		this.wrapped().fstore(slot);
	}
	
	@Override
	public final void fstore_0() {
		this.store( 0, float.class );
		this.unstack( float.class );
		
		this.wrapped().fstore_0();
	}
	
	@Override
	public final void fstore_1() {
		this.store( 1, float.class );
		this.unstack( float.class );
		
		this.wrapped().fstore_1();
	}
	
	@Override
	public final void fstore_2() {
		this.store( 2, float.class );
		this.unstack( float.class );
		
		this.wrapped().fstore_2();
	}
	
	@Override
	public final void fstore_3() {
		this.store( 3, float.class );
		this.unstack( float.class );
		
		this.wrapped().fstore_3();
	}

	@Override
	public final void dstore( final Slot slot ) {
		this.store( slot.pos(), double.class );
		this.unstack( double.class );
		
		this.wrapped().dstore(slot);
	}
	
	@Override
	public final void dstore( final int slot ) {
		this.store( slot, double.class );
		this.unstack( double.class );
		
		this.wrapped().dstore(slot);
	}
	
	@Override
	public final void dstore_0() {
		this.store( 0, double.class );
		this.unstack( double.class );
		
		this.wrapped().dstore_0();
	}
	
	@Override
	public final void dstore_1() {
		this.store( 1, double.class );
		this.unstack( double.class );
		
		this.wrapped().dstore_1();
	}
	
	@Override
	public final void dstore_2() {
		this.store( 2, double.class );
		this.unstack( double.class );
		
		this.wrapped().dstore_1();
	}
	
	@Override
	public final void dstore_3() {
		this.store( 3, double.class );
		this.unstack( double.class );
		
		this.wrapped().dstore_3();
	}
	
	@Override
	public final void astore( final Slot slot ) {
		Type actualType = this.topType( Reference.class );
		this.store( slot.pos(), actualType );
		this.unstack( actualType );
		
		this.wrapped().astore(slot.pos());
	}
	
	@Override
	public final void astore( final int slot ) {
		Type actualType = this.topType( Reference.class );
		this.store( slot, actualType );
		this.unstack( actualType );
		
		this.wrapped().astore(slot);
	}
	
	@Override
	public final void astore_0() {
		Type actualType = this.topType( Reference.class );
		this.store( 0, actualType );
		this.unstack( actualType );
		
		this.wrapped().astore_0();
	}
	
	@Override
	public final void astore_1() {
		Type actualType = this.topType( Reference.class );
		this.store( 1, actualType );
		this.unstack( actualType );
		
		this.wrapped().astore_1();
	}
	
	@Override
	public final void astore_2() {
		Type actualType = this.topType( Reference.class );
		this.store( 2, actualType );
		this.unstack( actualType );
		
		this.wrapped().astore_2();
	}
	
	@Override
	public final void astore_3() {
		Type actualType = this.topType( Reference.class );
		this.store( 3, actualType );
		this.unstack( actualType );
		
		this.wrapped().astore_3();
	}
	
	@Override
	public final void iastore() {
		this.unstack( int.class );
		this.unstack( int.class );
		this.unstack( int[].class );
		
		this.wrapped().iastore();
	}
	
	@Override
	public final void lastore() {
		this.unstack( long.class );
		this.unstack( int.class );
		this.unstack( long[].class );
		
		this.wrapped().lastore();
	}
	
	@Override
	public final void fastore() {
		this.unstack( float.class );
		this.unstack( int.class );
		this.unstack( float[].class );
		
		this.wrapped().fastore();
	}
	
	@Override
	public final void dastore() {
		this.unstack( double.class );
		this.unstack( int.class );
		this.unstack( double[].class );
		
		this.wrapped().dastore();
	}
	
	@Override
	public final void aastore() {
		this.unstack( Reference.class );
		this.unstack( int.class );
		this.unstack( Reference[].class );
		
		this.wrapped().aastore();
	}
	
	@Override
	public final void bastore() {
		this.unstack( byte.class );
		this.unstack( int.class );
		this.unstack( byte[].class );
		
		this.wrapped().bastore();
	}
	
	@Override
	public final void castore() {
		this.unstack( char.class );
		this.unstack( int.class );
		this.unstack( char[].class );
		
		this.wrapped().castore();
	}
	
	@Override
	public final void sastore() {
		this.unstack( short.class );
		this.unstack( int.class );
		this.unstack( short[].class );
		
		this.wrapped().sastore();
	}
	
	@Override
	public final void pop() {
		try {
			this.wrapped().pop();
		} finally {
			this.stack().pop();
		}
	}
	
	@Override
	public final void pop2() {
		try {
			this.wrapped().pop2();
		} finally {
			this.stack().pop2();
		}
	}
	
	@Override
	public final void dup() {
		try {
			this.wrapped().dup();
		} finally {
			this.stack().dup();
		}
	}
	
	@Override
	public final void dup_x1() {
		try {
			this.wrapped().dup_x1();
		} finally {
			this.stack().dup_x1();
		}
	}
	
	@Override
	public final void dup_x2() {
		try {
			this.wrapped().dup_x2();
		} finally {
			this.stack().dup_x2();
		}
	}
	
	@Override
	public final void dup2() {
		try {
			this.wrapped().dup2();
		} finally {
			this.stack().dup2();
		}
	}
	
	@Override
	public final void dup2_x1() {
		try {
			this.wrapped().dup2_x1();
		} finally {
			this.stack().dup2_x1();
		}
	}
	
	@Override
	public final void dup2_x2() {
		try {
			this.wrapped().dup2_x2();
		} finally {
			this.stack().dup2_x2();
		}
	}
	
	@Override
	public final void swap() {
		try {
			this.wrapped().swap();
		} finally {
			this.stack().swap();
		}
	}
	
	@Override
	public final void iadd() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.wrapped().iadd();
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void ladd() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.wrapped().ladd();
		} finally {
			this.stack( long.class );			
		}
	}
	
	@Override
	public final void fadd() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			this.wrapped().fadd();
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void dadd() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			this.wrapped().dadd();
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final void isub() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.wrapped().isub();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lsub() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.wrapped().lsub();
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void fsub() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			this.wrapped().fsub();
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final void dsub() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			this.wrapped().dsub();
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final void imul() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.wrapped().imul();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lmul() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.wrapped().lmul();
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final void fmul() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			this.wrapped().fmul();
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final void dmul() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			this.wrapped().dmul();
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final void idiv() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.wrapped().idiv();
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void ldiv() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.wrapped().ldiv();
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final void fdiv() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			this.wrapped().fdiv();
		} finally {
			this.stack( float.class );
		}
	}
	
	@Override
	public final void ddiv() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			this.wrapped().ddiv();
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final void irem() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.wrapped().irem();
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void lrem() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.wrapped().lrem();
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void frem() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			this.wrapped().frem();
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final void drem() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			this.wrapped().drem();
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final void ineg() {
		this.unstack( int.class );
		try {
			this.wrapped().ineg();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lneg() {
		this.unstack( long.class );
		try {
			this.wrapped().lneg();
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void fneg() {
		this.unstack( float.class );
		try {
			this.wrapped().fneg();
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final void dneg() {
		this.unstack( double.class );
		try {
			this.wrapped().dneg();
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final void ishl() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.wrapped().ishl();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lshl() {
		this.unstack( int.class );
		this.unstack( long.class );
		try {
			this.wrapped().lshl();
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void ishr() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.wrapped().ishr();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lshr() {
		this.unstack( int.class );
		this.unstack( long.class );
		try {
			this.wrapped().lshr();
		} finally {
			this.stack( long.class );						
		}
	}
	
	@Override
	public final void iushr() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.wrapped().iushr();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lushr() {
		this.unstack( int.class );
		this.unstack( long.class );
		try {
			this.wrapped().lushr();
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final void iand() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.wrapped().iand();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void land() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.wrapped().land();
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void ior() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.wrapped().ior();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lor() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.wrapped().lor();
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void ixor() {
		this.unstack( int.class );
		this.unstack( int.class );
		try {
			this.wrapped().ixor();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void lxor() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.wrapped().lxor();
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void iinc(
		final int slot,
		final int amount )
	{	
		try {
			this.wrapped().iinc(slot, amount);
		} finally {
			this.inc( slot );	
		}
	}
	
	@Override
	public final void i2l() {
		this.unstack( int.class );
		try {
			this.wrapped().i2l();
		} finally {
			this.stack( long.class );
		}
	}
	
	@Override
	public final void i2f() {
		this.unstack( int.class );
		try {
			this.wrapped().i2f();
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final void i2d() {
		this.unstack( int.class );
		try {
			this.wrapped().i2d();
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final void l2i() {
		this.unstack( long.class );
		try {
			this.wrapped().l2i();
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void l2f() {
		this.unstack( long.class );
		try {
			this.wrapped().l2f();
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final void l2d() {
		this.unstack( long.class );
		try {
			this.wrapped().l2d();
		} finally {
			this.stack( double.class );	
		}
	}
	
	@Override
	public final void f2i() {
		this.unstack( float.class );
		try {
			this.wrapped().f2i();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void f2l() {
		this.unstack( float.class );
		try {
			this.wrapped().f2l();
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void f2d() {
		this.unstack( float.class );
		try {
			this.wrapped().f2d();
		} finally {
			this.stack( double.class );
		}
	}
	
	@Override
	public final void d2i() {
		this.unstack( double.class );
		try {
			this.wrapped().d2i();
		} finally {
			this.stack( int.class );
		}
	}
	
	@Override
	public final void d2l() {
		this.unstack( double.class );
		try {
			this.wrapped().d2l();
		} finally {
			this.stack( long.class );	
		}
	}
	
	@Override
	public final void d2f() {
		this.unstack( double.class );
		try {
			this.wrapped().d2f();
		} finally {
			this.stack( float.class );	
		}
	}
	
	@Override
	public final void i2b() {
		this.unstack( int.class );
		try {
			this.wrapped().i2b();
		} finally {
			this.stack( byte.class );	
		}
	}
	
	@Override
	public final void i2c() {
		this.unstack( int.class );
		try {
			this.wrapped().i2c();
		} finally {
			this.stack( char.class );			
		}
	}
	
	@Override
	public final void i2s() {
		this.unstack( int.class );
		try {
			this.wrapped().i2s();
		} finally {
			this.stack( short.class );	
		}
	}
	
	@Override
	public final void lcmp() {
		this.unstack( long.class );
		this.unstack( long.class );
		try {
			this.wrapped().lcmp();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void fcmpl() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			this.wrapped().fcmpl();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void fcmpg() {
		this.unstack( float.class );
		this.unstack( float.class );
		try {
			this.wrapped().fcmpg();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void dcmpl() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			this.wrapped().dcmpl();
		} finally {
			this.stack( int.class );	
		}
	}
	
	@Override
	public final void dcmpg() {
		this.unstack( double.class );
		this.unstack( double.class );
		try {
			this.wrapped().dcmpg();
		} finally {
			this.stack( int.class );			
		}
	}
	
	@Override
	public final void ireturn() {
		this.unstack( int.class );
		this.wrapped().ireturn();
	}
	
	@Override
	public final void lreturn() {
		this.unstack( long.class );
		this.wrapped().lreturn();
	}
	
	@Override
	public final void freturn() {
		this.unstack( float.class );
		this.wrapped().freturn();
	}
	
	@Override
	public final void dreturn() {
		this.unstack( double.class );
		this.wrapped().dreturn();
	}
	
	@Override
	public final void areturn() {
		this.unstack( Reference.class );
		this.wrapped().areturn();
	}
	
	@Override
	public final void return_() {
		this.wrapped().return_();
	}

	@Override
	public final void getstatic(
		final Type targetType,
		final JavaField field )
	{
		try {
			this.wrapped().getstatic(targetType, field);
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
		
		this.wrapped().putstatic(targetType, field);
	}
	
	@Override
	public final void getfield(
		final Type targetType,
		final JavaField field )
	{
		this.unstack( targetType );
		try {
			this.wrapped().getfield(targetType, field);
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
		
		this.wrapped().putfield(targetType, field);
	}
	
	@Override
	public final void invokevirtual(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.unstack( method.arguments() );
		this.unstack( targetType ); //this
		try {
			this.wrapped().invokevirtual(targetType, method);
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
			this.wrapped().invokeinterface(targetType, method);
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
			this.wrapped().invokespecial(targetType, method);
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
			this.wrapped().invokestatic(targetType, method);
		} finally {
			this.stack( method.getReturnType() );	
		}
	}
	
	@Override
	public final void new_( final Type type ) {
		try {
			this.wrapped().new_(type);
		} finally {
			this.stack(type);
		}
	}
	
	@Override
	public final void newarray( final Type componentType ) {
		this.unstack( int.class );
		try {
			this.wrapped().newarray(componentType);
		} finally {
			this.stack( JavaTypes.array( componentType ) );	
		}
	}
	
	@Override
	public final void anewarray( final Type componentType ) {
		this.unstack( int.class );
		try {
			this.wrapped().anewarray(componentType);
		} finally {
			this.stack( JavaTypes.array( componentType ) );
		}
	}
	
	@Override
	public final void arraylength() {
		this.unstack( Any[].class );
		try {
			this.wrapped().arraylength();
		} finally {
			this.stack( int.class );			
		}
	}
	
	@Override
	public final void athrow() {
		this.unstack( Throwable.class );
		
		this.wrapped().athrow();
	}
	
	@Override
	public final void checkcast( final Type type ) {
		this.unstack( Reference.class );
		try {
			this.wrapped().checkcast(type);
		} finally {
			this.stack( type );			
		}
	}
	
	@Override
	public final void instanceof_( final Type type ) {
		this.unstack( Reference.class );
		try {
			this.wrapped().instanceof_(type);
		} finally {
			this.stack( boolean.class );	
		}
	}
	
	@Override
	public final void monitorenter() {
		this.unstack( Reference.class );
		
		this.wrapped().monitorenter();
	}
	
	@Override
	public final void monitorexit() {
		this.unstack( Reference.class );
		
		this.wrapped().monitorexit();
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
			this.wrapped().multianewarray(arrayType, numDimensions);
		} finally {
			this.stack( arrayType );			
		}
	}
	
	@Override
	public final void ifnull( final Jump jump ) {
		this.unstack( Reference.class );
		
		this.wrapped().ifnull(jump);
	}
	
	@Override
	public final void ifnonnull( final Jump jump ) {
		this.unstack( Reference.class );
		
		this.wrapped().ifnonnull(jump);
	}

	@Override
	public final void ifeq( final Jump jump ) {
		this.unstack( int.class );
		
		this.wrapped().ifeq(jump);
	}
	
	@Override
	public final void ifne( final Jump jump ) {
		this.unstack( int.class );
		
		this.wrapped().ifne(jump);
	}
	
	@Override
	public final void iflt( final Jump jump ) {
		this.unstack( int.class );
		
		this.wrapped().iflt(jump);
	}
	
	@Override
	public final void ifgt( final Jump jump ) {
		this.unstack( int.class );
		
		this.wrapped().ifgt(jump);
	}
	
	@Override
	public final void ifge( final Jump jump ) {
		this.unstack( int.class );
		
		this.wrapped().ifge(jump);
	}
	
	@Override
	public final void ifle( final Jump jump ) {
		this.unstack( int.class );
		
		this.wrapped().ifle(jump);
	}
	
	@Override
	public final void if_icmpeq( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		
		this.wrapped().if_icmpeq(jump);
	}
	
	@Override
	public final void if_icmpne( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		
		this.wrapped().if_icmpne(jump);
	}
	
	@Override
	public final void if_icmplt( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		
		this.wrapped().if_icmplt(jump);
	}
	
	@Override
	public final void if_icmpgt( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		
		this.wrapped().if_icmpgt(jump);
	}
	
	@Override
	public final void if_icmpge( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		
		this.wrapped().if_icmpge(jump);
	}
	
	@Override
	public final void if_icmple( final Jump jump ) {
		this.unstack( int.class );
		this.unstack( int.class );
		
		this.wrapped().if_icmple(jump);
	}
	
	@Override
	public final void if_acmpeq( final Jump jump ) {
		this.unstack( Reference.class );
		this.unstack( Reference.class );
		
		this.wrapped().if_acmpeq(jump);
	}
	
	@Override
	public final void if_acmpne( final Jump jump ) {
		this.unstack( Reference.class );
		this.unstack( Reference.class );
		
		this.wrapped().if_acmpne(jump);
	}
	
	@Override
	public final void goto_( final Jump jump ) {
		this.wrapped().goto_(jump);
	}
	
	@Override
	public final void handleException( final ExceptionHandler exceptionHandler ) {
		this.wrapped().handleException(exceptionHandler);
	}
	
	private final void inc( final int slot ) {
		this.locals().inc( slot );
	}
	
	private final Type load( final int slot, final Type expectedType ) {
		Type actualType = this.locals().typeOf( slot );
		Type estimatedType = actualType != null ? actualType : expectedType;
		this.locals().load( slot, estimatedType );
		return estimatedType;
	}
	
	private final void store( final int slot, final Type type ) {
		this.locals().store( slot, type );
	}
	
	private final void stack( final Type type ) {
		if ( ! type.equals( void.class) ) {
			this.stack().stack( type );
		}
	}
	
	private final void unstack( final FormalArguments args ) {
		Type[] types = args.getTypes();
		for ( int i = types.length - 1; i >= 0; --i ) {
			this.unstack( types[ i ] );
		}
	}
	
	private final Type topType( final Type expectedType ) {
		return this.stack().topType( expectedType );
	}
	
	private final void unstack( final Type type ) {
		this.prepare();
		
		if ( ! type.equals( void.class ) ) {
			this.stack().unstack( type );
		}
	}
}
