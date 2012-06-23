package net.dougqh.jak.jvm;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.jvm.annotations.JvmOp;
import net.dougqh.jak.jvm.annotations.WrapOp;
import net.dougqh.jak.jvm.operations.*;

/**
 * Callback interface to use when processing operations as events;
 * JvmOperationHydrator implements this interface and can be used 
 * when the operation stream needs to be turned into JvmOperation
 * objects.
 */
public interface JvmOperationProcessor {
	@JvmOp( nop.class )
	public abstract void nop();

	@JvmOp( aconst_null.class )
	public abstract void aconst_null();

	@JvmOp( iconst_m1.class )
	public abstract void iconst_m1();

	@JvmOp( iconst_0.class )
	public abstract void iconst_0();

	@JvmOp( iconst_1.class )
	public abstract void iconst_1();

	@JvmOp( iconst_2.class )
	public abstract void iconst_2();

	@JvmOp( iconst_3.class )
	public abstract void iconst_3();

	@JvmOp( iconst_4.class )
	public abstract void iconst_4();

	@JvmOp( iconst_5.class )
	public abstract void iconst_5();

	@JvmOp( lconst_0.class )
	public abstract void lconst_0();

	@JvmOp( lconst_1.class )
	public abstract void lconst_1();

	@JvmOp( fconst_0.class )
	public abstract void fconst_0();

	@JvmOp( fconst_1.class )
	public abstract void fconst_1();

	@JvmOp( fconst_2.class )
	public abstract void fconst_2();

	@JvmOp( dconst_0.class )
	public abstract void dconst_0();

	@JvmOp( dconst_1.class )
	public abstract void dconst_1();

	@JvmOp( bipush.class )
	public abstract void bipush( final byte value );

	@JvmOp( sipush.class )
	public abstract void sipush( final short value );

	@WrapOp( value=ldc.class, stackResultTypes=Class.class )
	public abstract void ldc( final Type aClass );

	@WrapOp( value=ldc.class, stackResultTypes=int.class )
	public abstract void ldc( final int value );

	@WrapOp( value=ldc.class, stackResultTypes=float.class )
	public abstract void ldc( final float value );

	@WrapOp( value=ldc.class, stackResultTypes=String.class )
	public abstract void ldc( final String value );
	
	@WrapOp( value=ldc2_w.class, stackResultTypes=Class.class )
	public abstract void ldc_w( final Type aClass );

	@WrapOp( value=ldc2_w.class, stackResultTypes=int.class )
	public abstract void ldc_w( final int value );

	@WrapOp( value=ldc2_w.class, stackResultTypes=float.class )
	public abstract void ldc_w( final float value );

	@WrapOp( value=ldc2_w.class, stackResultTypes=String.class )
	public abstract void ldc_w( final String value );

	@WrapOp( value=ldc2_w.class, stackResultTypes=long.class )
	public abstract void ldc2_w( final long value );

	@WrapOp( value=ldc2_w.class, stackResultTypes=double.class )
	public abstract void ldc2_w( final double value );

	@JvmOp( iload.class )
	public abstract void iload( final int slot );
	
	@JvmOp( iload.class )
	public abstract void iload( final Slot slot );

	@JvmOp( iload_0.class )
	public abstract void iload_0();

	@JvmOp( iload_1.class )
	public abstract void iload_1();

	@JvmOp( iload_2.class )
	public abstract void iload_2();

	@JvmOp( iload_3.class )
	public abstract void iload_3();

	@JvmOp( lload.class )
	public abstract void lload( final int slot );

	@JvmOp( lload.class )
	public abstract void lload( final Slot slot );

	@JvmOp( lload_0.class )
	public abstract void lload_0();

	@JvmOp( lload_1.class )
	public abstract void lload_1();

	@JvmOp( lload_2.class )
	public abstract void lload_2();

	@JvmOp( lload_3.class )
	public abstract void lload_3();

	@JvmOp( fload.class )
	public abstract void fload( final int slot );

	@JvmOp( fload.class )
	public abstract void fload( final Slot slot );

	@JvmOp( fload_0.class )
	public abstract void fload_0();

	@JvmOp( fload_1.class )
	public abstract void fload_1();

	@JvmOp( fload_2.class )
	public abstract void fload_2();

	@JvmOp( fload_3.class )
	public abstract void fload_3();

	@JvmOp( dload.class )
	public abstract void dload( final int slot );

	@JvmOp( dload.class )
	public abstract void dload( final Slot slot );
	
	@JvmOp( dload_0.class )
	public abstract void dload_0();

	@JvmOp( dload_1.class )
	public abstract void dload_1();

	@JvmOp( dload_2.class )
	public abstract void dload_2();

	@JvmOp( dload_3.class )
	public abstract void dload_3();

	@JvmOp( aload.class )
	public abstract void aload( final int slot );
	
	@JvmOp( aload.class )
	public abstract void aload( final Slot slot );

	@JvmOp( aload_0.class )
	public abstract void aload_0();

	@JvmOp( aload_1.class )
	public abstract void aload_1();

	@JvmOp( aload_2.class )
	public abstract void aload_2();

	@JvmOp( aload_3.class )
	public abstract void aload_3();

	@JvmOp( iaload.class )
	public abstract void iaload();

	@JvmOp( laload.class )
	public abstract void laload();

	@JvmOp( faload.class )
	public abstract void faload();

	@JvmOp( daload.class )
	public abstract void daload();

	@JvmOp( aaload.class )
	public abstract void aaload();

	@JvmOp( baload.class )
	public abstract void baload();

	@JvmOp( caload.class )
	public abstract void caload();

	@JvmOp( saload.class )
	public abstract void saload();

	@JvmOp( istore.class )
	public abstract void istore( final int index );
	
	@JvmOp( istore.class )
	public abstract void istore( final Slot slot );

	@JvmOp( istore_0.class )
	public abstract void istore_0();

	@JvmOp( istore_1.class )
	public abstract void istore_1();

	@JvmOp( istore_2.class )
	public abstract void istore_2();

	@JvmOp( istore_3.class )
	public abstract void istore_3();

	@JvmOp( lstore.class )
	public abstract void lstore( final int slot );

	@JvmOp( lstore.class )
	public abstract void lstore( final Slot slot );

	@JvmOp( lstore_0.class )
	public abstract void lstore_0();

	@JvmOp( lstore_1.class )
	public abstract void lstore_1();

	@JvmOp( lstore_2.class )
	public abstract void lstore_2();

	@JvmOp( lstore_3.class )
	public abstract void lstore_3();

	@JvmOp( fstore.class )
	public abstract void fstore( final int slot );

	@JvmOp( fstore.class )
	public abstract void fstore( final Slot slot );

	@JvmOp( fstore_0.class )
	public abstract void fstore_0();

	@JvmOp( fstore_1.class )
	public abstract void fstore_1();

	@JvmOp( fstore_2.class )
	public abstract void fstore_2();

	@JvmOp( fstore_3.class )
	public abstract void fstore_3();

	@JvmOp( dstore.class )
	public abstract void dstore( final int slot );

	@JvmOp( dstore.class )
	public abstract void dstore( final Slot slot );

	@JvmOp( dstore_0.class )
	public abstract void dstore_0();

	@JvmOp( dstore_1.class )
	public abstract void dstore_1();

	@JvmOp( dstore_2.class )
	public abstract void dstore_2();

	@JvmOp( dstore_3.class )
	public abstract void dstore_3();

	@JvmOp( astore.class )
	public abstract void astore( final int slot );

	@JvmOp( astore.class )
	public abstract void astore( final Slot slot );
	
	@JvmOp( astore_0.class )
	public abstract void astore_0();

	@JvmOp( astore_1.class )
	public abstract void astore_1();

	@JvmOp( astore_2.class )
	public abstract void astore_2();

	@JvmOp( astore_3.class )
	public abstract void astore_3();

	@JvmOp( iastore.class )
	public abstract void iastore();

	@JvmOp( lastore.class )
	public abstract void lastore();

	@JvmOp( fastore.class )
	public abstract void fastore();

	@JvmOp( dastore.class )
	public abstract void dastore();

	@JvmOp( aastore.class )
	public abstract void aastore();

	@JvmOp( bastore.class )
	public abstract void bastore();

	@JvmOp( castore.class )
	public abstract void castore();

	@JvmOp( sastore.class )
	public abstract void sastore();

	@JvmOp( pop.class )
	public abstract void pop();
	
	@JvmOp( pop2.class )
	public abstract void pop2();

	@JvmOp( dup.class )
	public abstract void dup();

	@JvmOp( dup_x1.class )
	public abstract void dup_x1();

	@JvmOp( dup_x2.class )
	public abstract void dup_x2();

	@JvmOp( dup2.class )
	public abstract void dup2();

	@JvmOp( dup2_x1.class )
	public abstract void dup2_x1();

	@JvmOp( dup2_x2.class )
	public abstract void dup2_x2();

	@JvmOp( swap.class )
	public abstract void swap();

	@JvmOp( iadd.class )
	public abstract void iadd();

	@JvmOp( ladd.class )
	public abstract void ladd();

	@JvmOp( fadd.class )
	public abstract void fadd();

	@JvmOp( dadd.class )
	public abstract void dadd();

	@JvmOp( isub.class )
	public abstract void isub();

	@JvmOp( lsub.class )
	public abstract void lsub();

	@JvmOp( fsub.class )
	public abstract void fsub();

	@JvmOp( dsub.class )
	public abstract void dsub();

	@JvmOp( imul.class )
	public abstract void imul();

	@JvmOp( lmul.class )
	public abstract void lmul();

	@JvmOp( fmul.class )
	public abstract void fmul();

	@JvmOp( dmul.class )
	public abstract void dmul();

	@JvmOp( idiv.class )
	public abstract void idiv();

	@JvmOp( ldiv.class )
	public abstract void ldiv();

	@JvmOp( fdiv.class )
	public abstract void fdiv();

	@JvmOp( ddiv.class )
	public abstract void ddiv();

	@JvmOp( irem.class )
	public abstract void irem();

	@JvmOp( lrem.class )
	public abstract void lrem();

	@JvmOp( frem.class )
	public abstract void frem();

	@JvmOp( drem.class )
	public abstract void drem();

	@JvmOp( ineg.class )
	public abstract void ineg();

	@JvmOp( lneg.class )
	public abstract void lneg();

	@JvmOp( fneg.class )
	public abstract void fneg();

	@JvmOp( dneg.class )
	public abstract void dneg();

	@JvmOp( ishl.class )
	public abstract void ishl();

	@JvmOp( lshl.class )
	public abstract void lshl();

	@JvmOp( ishr.class )
	public abstract void ishr();

	@JvmOp( lshr.class )
	public abstract void lshr();

	@JvmOp( iushr.class )
	public abstract void iushr();

	@JvmOp( lushr.class )
	public abstract void lushr();

	@JvmOp( iand.class )
	public abstract void iand();

	@JvmOp( land.class )
	public abstract void land();

	@JvmOp( ior.class )
	public abstract void ior();

	@JvmOp( lor.class )
	public abstract void lor();

	@JvmOp( ixor.class )
	public abstract void ixor();

	@JvmOp( lxor.class )
	public abstract void lxor();

	@JvmOp( iinc.class )
	public abstract void iinc(
		final int slot,
		final int amount );

	@JvmOp( i2l.class )
	public abstract void i2l();

	@JvmOp( i2f.class )
	public abstract void i2f();

	@JvmOp( i2d.class )
	public abstract void i2d();

	@JvmOp( l2i.class )
	public abstract void l2i();

	@JvmOp( l2f.class )
	public abstract void l2f();

	@JvmOp( l2d.class )
	public abstract void l2d();

	@JvmOp( f2i.class )
	public abstract void f2i();

	@JvmOp( f2l.class )
	public abstract void f2l();

	@JvmOp( f2d.class )
	public abstract void f2d();

	@JvmOp( d2i.class )
	public abstract void d2i();

	@JvmOp( d2l.class )
	public abstract void d2l();

	@JvmOp( d2f.class )
	public abstract void d2f();

	@JvmOp( i2b.class )
	public abstract void i2b();

	@JvmOp( i2c.class )
	public abstract void i2c();

	@JvmOp( i2s.class )
	public abstract void i2s();

	@JvmOp( lcmp.class )
	public abstract void lcmp();

	@JvmOp( fcmpl.class )
	public abstract void fcmpl();

	@JvmOp( fcmpg.class )
	public abstract void fcmpg();

	@JvmOp( dcmpl.class )
	public abstract void dcmpl();

	@JvmOp( dcmpg.class )
	public abstract void dcmpg();

	@JvmOp( ireturn.class )
	public abstract void ireturn();

	@JvmOp( lreturn.class )
	public abstract void lreturn();

	@JvmOp( freturn.class )
	public abstract void freturn();

	@JvmOp( dreturn.class )
	public abstract void dreturn();

	@JvmOp( areturn.class )
	public abstract void areturn();

	@JvmOp( return_.class )
	public abstract void return_();

	@JvmOp( getstatic.class )
	public abstract void getstatic(
		final Type targetType,
		final JavaField field );

	@JvmOp( putstatic.class )
	public abstract void putstatic(
		final Type targetType,
		final JavaField field );

	@JvmOp( getfield.class )
	public abstract void getfield(
		final Type targetType,
		final JavaField field );

	@JvmOp( putfield.class )
	public abstract void putfield(
		final Type targetType,
		final JavaField field );

	@JvmOp( invokevirtual.class )
	public abstract void invokevirtual(
		final Type targetType,
		final JavaMethodDescriptor method );
	
	@JvmOp( invokeinterface.class )
	public abstract void invokeinterface(
		final Type targetType,
		final JavaMethodDescriptor method );

	@JvmOp( invokestatic.class )
	public abstract void invokestatic(
		final Type targetType,
		final JavaMethodDescriptor method );

	@JvmOp( invokespecial.class )
	public abstract void invokespecial(
		final Type targetType,
		final JavaMethodDescriptor method );

	@JvmOp( new_.class )
	public abstract void new_( final Type type );

	@JvmOp( newarray.class )
	public abstract void newarray( final Type componentType );

	@JvmOp( anewarray.class )
	public abstract void anewarray(final Type componentType);

	@JvmOp( arraylength.class )
	public abstract void arraylength();

	@JvmOp( athrow.class )
	public abstract void athrow();

	@JvmOp( checkcast.class )
	public abstract void checkcast( final Type type );
	
	@JvmOp( instanceof_.class )
	public abstract void instanceof_( final Type type );

	@JvmOp( monitorenter.class )
	public abstract void monitorenter();

	@JvmOp( monitorexit.class )
	public abstract void monitorexit();

	@JvmOp( multianewarray.class )
	public abstract void multianewarray(
		final Type arrayType,
		final int numDimensions );
	
	@JvmOp( ifnull.class )
	public abstract void ifnull( final Jump jump );
	
	@JvmOp( ifnonnull.class )
	public abstract void ifnonnull( final Jump jump );
	
	@JvmOp( ifeq.class )
	public abstract void ifeq( final Jump jump );
	
	@JvmOp( ifne.class )
	public abstract void ifne( final Jump jump );
	
	@JvmOp( iflt.class )
	public abstract void iflt( final Jump jump );
	
	@JvmOp( ifgt.class )
	public abstract void ifgt( final Jump jump );
	
	@JvmOp( ifge.class )
	public abstract void ifge( final Jump jump );
	
	@JvmOp( ifle.class )
	public abstract void ifle( final Jump jump );
	
	@JvmOp( if_icmpeq.class )
	public abstract void if_icmpeq( final Jump jump );
	
	@JvmOp( if_icmpne.class )
	public abstract void if_icmpne( final Jump jump );
	
	@JvmOp( if_icmplt.class )
	public abstract void if_icmplt( final Jump jump );
	
	@JvmOp( if_icmpgt.class )
	public abstract void if_icmpgt( final Jump jump );
	
	@JvmOp( if_icmpge.class )
	public abstract void if_icmpge( final Jump jump );
	
	@JvmOp( if_icmple.class )
	public abstract void if_icmple( final Jump jump );
	
	@JvmOp( if_acmpeq.class )
	public abstract void if_acmpeq( final Jump jump );
	
	@JvmOp( if_acmpne.class )
	public abstract void if_acmpne( final Jump jump );
	
	@JvmOp( goto_.class )
	public abstract void goto_( final Jump jump );
	
	public abstract void handleException( final ExceptionHandler exceptionHandler );
	
	public abstract class Slot {
		public abstract Integer pos();
		
		@Override
		public abstract String toString();
	}
	
	public abstract class Jump {
		public abstract Integer pos();
		
		@Override
		public abstract String toString();
	}
	
	public abstract class ExceptionHandler {
		public abstract int startPos();
		
		public abstract int endPos();
		
		public abstract Type exceptionType();
		
		public abstract int handlerPos();		
	}

}