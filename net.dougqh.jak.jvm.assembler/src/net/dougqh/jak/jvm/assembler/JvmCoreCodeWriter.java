package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.annotations.JvmOp;
import net.dougqh.jak.jvm.operations.*;

public interface JvmCoreCodeWriter {
	@JvmOp( nop.class )
	public abstract JvmCoreCodeWriter nop();

	@JvmOp( aconst_null.class )
	public abstract JvmCoreCodeWriter aconst_null();

	@JvmOp( iconst_m1.class )
	public abstract JvmCoreCodeWriter iconst_m1();

	@JvmOp( iconst_0.class )
	public abstract JvmCoreCodeWriter iconst_0();

	@JvmOp( iconst_1.class )
	public abstract JvmCoreCodeWriter iconst_1();

	@JvmOp( iconst_2.class )
	public abstract JvmCoreCodeWriter iconst_2();

	@JvmOp( iconst_3.class )
	public abstract JvmCoreCodeWriter iconst_3();

	@JvmOp( iconst_4.class )
	public abstract JvmCoreCodeWriter iconst_4();

	@JvmOp( iconst_5.class )
	public abstract JvmCoreCodeWriter iconst_5();

	@JvmOp( lconst_0.class )
	public abstract JvmCoreCodeWriter lconst_0();

	@JvmOp( lconst_1.class )
	public abstract JvmCoreCodeWriter lconst_1();

	@JvmOp( fconst_0.class )
	public abstract JvmCoreCodeWriter fconst_0();

	@JvmOp( fconst_1.class )
	public abstract JvmCoreCodeWriter fconst_1();

	@JvmOp( fconst_2.class )
	public abstract JvmCoreCodeWriter fconst_2();

	@JvmOp( dconst_0.class )
	public abstract JvmCoreCodeWriter dconst_0();

	@JvmOp( dconst_1.class )
	public abstract JvmCoreCodeWriter dconst_1();

	@JvmOp( bipush.class )
	public abstract JvmCoreCodeWriter bipush( final byte value );

	@JvmOp( sipush.class )
	public abstract JvmCoreCodeWriter sipush( final short value );

	@JvmOp( ldc.class )
	public abstract JvmCoreCodeWriter ldc( final ConstantEntry index );
	
	@JvmOp( ldc_w.class )
	public abstract JvmCoreCodeWriter ldc_w( final ConstantEntry index );
	
	@JvmOp( ldc2_w.class )
	public abstract JvmCoreCodeWriter ldc2_w( final ConstantEntry index );

	@JvmOp( iload.class )
	public abstract JvmCoreCodeWriter iload( final int slot );

	@JvmOp( iload_0.class )
	public abstract JvmCoreCodeWriter iload_0();

	@JvmOp( iload_1.class )
	public abstract JvmCoreCodeWriter iload_1();

	@JvmOp( iload_2.class )
	public abstract JvmCoreCodeWriter iload_2();

	@JvmOp( iload_3.class )
	public abstract JvmCoreCodeWriter iload_3();

	@JvmOp( lload.class )
	public abstract JvmCoreCodeWriter lload( final int slot );

	@JvmOp( lload_0.class )
	public abstract JvmCoreCodeWriter lload_0();

	@JvmOp( lload_1.class )
	public abstract JvmCoreCodeWriter lload_1();

	@JvmOp( lload_2.class )
	public abstract JvmCoreCodeWriter lload_2();

	@JvmOp( lload_3.class )
	public abstract JvmCoreCodeWriter lload_3();

	@JvmOp( fload.class )
	public abstract JvmCoreCodeWriter fload( final int slot );

	@JvmOp( fload_0.class )
	public abstract JvmCoreCodeWriter fload_0();

	@JvmOp( fload_1.class )
	public abstract JvmCoreCodeWriter fload_1();

	@JvmOp( fload_2.class )
	public abstract JvmCoreCodeWriter fload_2();

	@JvmOp( fload_3.class )
	public abstract JvmCoreCodeWriter fload_3();

	@JvmOp( dload.class )
	public abstract JvmCoreCodeWriter dload( final int slot );

	@JvmOp( dload_0.class )
	public abstract JvmCoreCodeWriter dload_0();

	@JvmOp( dload_1.class )
	public abstract JvmCoreCodeWriter dload_1();

	@JvmOp( dload_2.class )
	public abstract JvmCoreCodeWriter dload_2();

	@JvmOp( dload_3.class )
	public abstract JvmCoreCodeWriter dload_3();

	@JvmOp( aload.class )
	public abstract JvmCoreCodeWriter aload( final int slot );

	@JvmOp( aload_0.class )
	public abstract JvmCoreCodeWriter aload_0();

	@JvmOp( aload_1.class )
	public abstract JvmCoreCodeWriter aload_1();

	@JvmOp( aload_2.class )
	public abstract JvmCoreCodeWriter aload_2();

	@JvmOp( aload_3.class )
	public abstract JvmCoreCodeWriter aload_3();

	@JvmOp( iaload.class )
	public abstract JvmCoreCodeWriter iaload();

	@JvmOp( laload.class )
	public abstract JvmCoreCodeWriter laload();

	@JvmOp( faload.class )
	public abstract JvmCoreCodeWriter faload();

	@JvmOp( daload.class )
	public abstract JvmCoreCodeWriter daload();

	@JvmOp( aaload.class )
	public abstract JvmCoreCodeWriter aaload();

	@JvmOp( baload.class )
	public abstract JvmCoreCodeWriter baload();

	@JvmOp( caload.class )
	public abstract JvmCoreCodeWriter caload();

	@JvmOp( saload.class )
	public abstract JvmCoreCodeWriter saload();

	@JvmOp( istore.class )
	public abstract JvmCoreCodeWriter istore( final int index );

	@JvmOp( istore_0.class )
	public abstract JvmCoreCodeWriter istore_0();

	@JvmOp( istore_1.class )
	public abstract JvmCoreCodeWriter istore_1();

	@JvmOp( istore_2.class )
	public abstract JvmCoreCodeWriter istore_2();

	@JvmOp( istore_3.class )
	public abstract JvmCoreCodeWriter istore_3();

	@JvmOp( lstore.class )
	public abstract JvmCoreCodeWriter lstore( final int slot );

	@JvmOp( lstore_0.class )
	public abstract JvmCoreCodeWriter lstore_0();

	@JvmOp( lstore_1.class )
	public abstract JvmCoreCodeWriter lstore_1();

	@JvmOp( lstore_2.class )
	public abstract JvmCoreCodeWriter lstore_2();

	@JvmOp( lstore_3.class )
	public abstract JvmCoreCodeWriter lstore_3();

	@JvmOp( fstore.class )
	public abstract JvmCoreCodeWriter fstore( final int slot );

	@JvmOp( fstore_0.class )
	public abstract JvmCoreCodeWriter fstore_0();

	@JvmOp( fstore_1.class )
	public abstract JvmCoreCodeWriter fstore_1();

	@JvmOp( fstore_2.class )
	public abstract JvmCoreCodeWriter fstore_2();

	@JvmOp( fstore_3.class )
	public abstract JvmCoreCodeWriter fstore_3();

	@JvmOp( dstore.class )
	public abstract JvmCoreCodeWriter dstore( final int slot );

	@JvmOp( dstore_0.class )
	public abstract JvmCoreCodeWriter dstore_0();

	@JvmOp( dstore_1.class )
	public abstract JvmCoreCodeWriter dstore_1();

	@JvmOp( dstore_2.class )
	public abstract JvmCoreCodeWriter dstore_2();

	@JvmOp( dstore_3.class )
	public abstract JvmCoreCodeWriter dstore_3();

	@JvmOp( astore.class )
	public abstract JvmCoreCodeWriter astore( final int slot );

	@JvmOp( astore_0.class )
	public abstract JvmCoreCodeWriter astore_0();

	@JvmOp( astore_1.class )
	public abstract JvmCoreCodeWriter astore_1();

	@JvmOp( astore_2.class )
	public abstract JvmCoreCodeWriter astore_2();

	@JvmOp( astore_3.class )
	public abstract JvmCoreCodeWriter astore_3();

	@JvmOp( iastore.class )
	public abstract JvmCoreCodeWriter iastore();

	@JvmOp( lastore.class )
	public abstract JvmCoreCodeWriter lastore();

	@JvmOp( fastore.class )
	public abstract JvmCoreCodeWriter fastore();

	@JvmOp( dastore.class )
	public abstract JvmCoreCodeWriter dastore();

	@JvmOp( aastore.class )
	public abstract JvmCoreCodeWriter aastore();

	@JvmOp( bastore.class )
	public abstract JvmCoreCodeWriter bastore();

	@JvmOp( castore.class )
	public abstract JvmCoreCodeWriter castore();

	@JvmOp( sastore.class )
	public abstract JvmCoreCodeWriter sastore();

	@JvmOp( pop.class )
	public abstract JvmCoreCodeWriter pop();
	
	@JvmOp( pop2.class )
	public abstract JvmCoreCodeWriter pop2();

	@JvmOp( dup.class )
	public abstract JvmCoreCodeWriter dup();

	@JvmOp( dup_x1.class )
	public abstract JvmCoreCodeWriter dup_x1();

	@JvmOp( dup_x2.class )
	public abstract JvmCoreCodeWriter dup_x2();

	@JvmOp( dup2.class )
	public abstract JvmCoreCodeWriter dup2();

	@JvmOp( dup2_x1.class )
	public abstract JvmCoreCodeWriter dup2_x1();

	@JvmOp( dup2_x2.class )
	public abstract JvmCoreCodeWriter dup2_x2();

	@JvmOp( swap.class )
	public abstract JvmCoreCodeWriter swap();

	@JvmOp( iadd.class )
	public abstract JvmCoreCodeWriter iadd();

	@JvmOp( ladd.class )
	public abstract JvmCoreCodeWriter ladd();

	@JvmOp( fadd.class )
	public abstract JvmCoreCodeWriter fadd();

	@JvmOp( dadd.class )
	public abstract JvmCoreCodeWriter dadd();

	@JvmOp( isub.class )
	public abstract JvmCoreCodeWriter isub();

	@JvmOp( lsub.class )
	public abstract JvmCoreCodeWriter lsub();

	@JvmOp( fsub.class )
	public abstract JvmCoreCodeWriter fsub();

	@JvmOp( dsub.class )
	public abstract JvmCoreCodeWriter dsub();

	@JvmOp( imul.class )
	public abstract JvmCoreCodeWriter imul();

	@JvmOp( lmul.class )
	public abstract JvmCoreCodeWriter lmul();

	@JvmOp( fmul.class )
	public abstract JvmCoreCodeWriter fmul();

	@JvmOp( dmul.class )
	public abstract JvmCoreCodeWriter dmul();

	@JvmOp( idiv.class )
	public abstract JvmCoreCodeWriter idiv();

	@JvmOp( ldiv.class )
	public abstract JvmCoreCodeWriter ldiv();

	@JvmOp( fdiv.class )
	public abstract JvmCoreCodeWriter fdiv();

	@JvmOp( ddiv.class )
	public abstract JvmCoreCodeWriter ddiv();

	@JvmOp( irem.class )
	public abstract JvmCoreCodeWriter irem();

	@JvmOp( lrem.class )
	public abstract JvmCoreCodeWriter lrem();

	@JvmOp( frem.class )
	public abstract JvmCoreCodeWriter frem();

	@JvmOp( drem.class )
	public abstract JvmCoreCodeWriter drem();

	@JvmOp( ineg.class )
	public abstract JvmCoreCodeWriter ineg();

	@JvmOp( lneg.class )
	public abstract JvmCoreCodeWriter lneg();

	@JvmOp( fneg.class )
	public abstract JvmCoreCodeWriter fneg();

	@JvmOp( dneg.class )
	public abstract JvmCoreCodeWriter dneg();

	@JvmOp( ishl.class )
	public abstract JvmCoreCodeWriter ishl();

	@JvmOp( lshl.class )
	public abstract JvmCoreCodeWriter lshl();

	@JvmOp( ishr.class )
	public abstract JvmCoreCodeWriter ishr();

	@JvmOp( lshr.class )
	public abstract JvmCoreCodeWriter lshr();

	@JvmOp( iushr.class )
	public abstract JvmCoreCodeWriter iushr();

	@JvmOp( lushr.class )
	public abstract JvmCoreCodeWriter lushr();

	@JvmOp( iand.class )
	public abstract JvmCoreCodeWriter iand();

	@JvmOp( land.class )
	public abstract JvmCoreCodeWriter land();

	@JvmOp( ior.class )
	public abstract JvmCoreCodeWriter ior();

	@JvmOp( lor.class )
	public abstract JvmCoreCodeWriter lor();

	@JvmOp( ixor.class )
	public abstract JvmCoreCodeWriter ixor();

	@JvmOp( lxor.class )
	public abstract JvmCoreCodeWriter lxor();

	@JvmOp( iinc.class )
	public abstract JvmCoreCodeWriter iinc(
		final int slot,
		final int amount );

	@JvmOp( i2l.class )
	public abstract JvmCoreCodeWriter i2l();

	@JvmOp( i2f.class )
	public abstract JvmCoreCodeWriter i2f();

	@JvmOp( i2d.class )
	public abstract JvmCoreCodeWriter i2d();

	@JvmOp( l2i.class )
	public abstract JvmCoreCodeWriter l2i();

	@JvmOp( l2f.class )
	public abstract JvmCoreCodeWriter l2f();

	@JvmOp( l2d.class )
	public abstract JvmCoreCodeWriter l2d();

	@JvmOp( f2i.class )
	public abstract JvmCoreCodeWriter f2i();

	@JvmOp( f2l.class )
	public abstract JvmCoreCodeWriter f2l();

	@JvmOp( f2d.class )
	public abstract JvmCoreCodeWriter f2d();

	@JvmOp( d2i.class )
	public abstract JvmCoreCodeWriter d2i();

	@JvmOp( d2l.class )
	public abstract JvmCoreCodeWriter d2l();

	@JvmOp( d2f.class )
	public abstract JvmCoreCodeWriter d2f();

	@JvmOp( i2b.class )
	public abstract JvmCoreCodeWriter i2b();

	@JvmOp( i2c.class )
	public abstract JvmCoreCodeWriter i2c();

	@JvmOp( i2s.class )
	public abstract JvmCoreCodeWriter i2s();

	@JvmOp( lcmp.class )
	public abstract JvmCoreCodeWriter lcmp();

	@JvmOp( fcmpl.class )
	public abstract JvmCoreCodeWriter fcmpl();

	@JvmOp( fcmpg.class )
	public abstract JvmCoreCodeWriter fcmpg();

	@JvmOp( dcmpl.class )
	public abstract JvmCoreCodeWriter dcmpl();

	@JvmOp( dcmpg.class )
	public abstract JvmCoreCodeWriter dcmpg();

	@JvmOp( ireturn.class )
	public abstract JvmCoreCodeWriter ireturn();

	@JvmOp( lreturn.class )
	public abstract JvmCoreCodeWriter lreturn();

	@JvmOp( freturn.class )
	public abstract JvmCoreCodeWriter freturn();

	@JvmOp( dreturn.class )
	public abstract JvmCoreCodeWriter dreturn();

	@JvmOp( areturn.class )
	public abstract JvmCoreCodeWriter areturn();

	@JvmOp( return_.class )
	public abstract JvmCoreCodeWriter return_();

	@JvmOp( getstatic.class )
	public abstract JvmCoreCodeWriter getstatic(
		final Type targetType,
		final JavaField field );

	@JvmOp( putstatic.class )
	public abstract JvmCoreCodeWriter putstatic(
		final Type targetType,
		final JavaField field );

	@JvmOp( getfield.class )
	public abstract JvmCoreCodeWriter getfield(
		final Type targetType,
		final JavaField field );

	@JvmOp( putfield.class )
	public abstract JvmCoreCodeWriter putfield(
		final Type targetType,
		final JavaField field );

	@JvmOp( invokevirtual.class )
	public abstract JvmCoreCodeWriter invokevirtual(
		final Type targetType,
		final JavaMethodDescriptor method );
	
	@JvmOp( invokeinterface.class )
	public abstract JvmCoreCodeWriter invokeinterface(
		final Type targetType,
		final JavaMethodDescriptor method );

	@JvmOp( invokestatic.class )
	public abstract JvmCoreCodeWriter invokestatic(
		final Type targetType,
		final JavaMethodDescriptor method );

	@JvmOp( invokespecial.class )
	public abstract JvmCoreCodeWriter invokespecial(
		final Type targetType,
		final JavaMethodDescriptor method );

	@JvmOp( new_.class )
	public abstract JvmCoreCodeWriter new_( final Type type );

	@JvmOp( newarray.class )
	public abstract JvmCoreCodeWriter newarray( final Type componentType );

	@JvmOp( anewarray.class )
	public abstract JvmCoreCodeWriter anewarray(final Type componentType);

	@JvmOp( arraylength.class )
	public abstract JvmCoreCodeWriter arraylength();

	@JvmOp( athrow.class )
	public abstract JvmCoreCodeWriter athrow();

	@JvmOp( checkcast.class )
	public abstract JvmCoreCodeWriter checkcast( final Type type );
	
	@JvmOp( instanceof_.class )
	public abstract JvmCoreCodeWriter instanceof_( final Type type );

	@JvmOp( monitorenter.class )
	public abstract JvmCoreCodeWriter monitorenter();

	@JvmOp( monitorexit.class )
	public abstract JvmCoreCodeWriter monitorexit();

	@JvmOp( multianewarray.class )
	public abstract JvmCoreCodeWriter multianewarray(
		final Type arrayType,
		final int numDimensions );
	
	@JvmOp( ifnull.class )
	public abstract JvmCoreCodeWriter ifnull( final Jump jump );
	
	@JvmOp( ifnonnull.class )
	public abstract JvmCoreCodeWriter ifnonnull( final Jump jump );
	
	@JvmOp( ifeq.class )
	public abstract JvmCoreCodeWriter ifeq( final Jump jump );
	
	@JvmOp( ifne.class )
	public abstract JvmCoreCodeWriter ifne( final Jump jump );
	
	@JvmOp( iflt.class )
	public abstract JvmCoreCodeWriter iflt( final Jump jump );
	
	@JvmOp( ifgt.class )
	public abstract JvmCoreCodeWriter ifgt( final Jump jump );
	
	@JvmOp( ifge.class )
	public abstract JvmCoreCodeWriter ifge( final Jump jump );
	
	@JvmOp( ifle.class )
	public abstract JvmCoreCodeWriter ifle( final Jump jump );
	
	@JvmOp( if_icmpeq.class )
	public abstract JvmCoreCodeWriter if_icmpeq( final Jump jump );
	
	@JvmOp( if_icmpne.class )
	public abstract JvmCoreCodeWriter if_icmpne( final Jump jump );
	
	@JvmOp( if_icmplt.class )
	public abstract JvmCoreCodeWriter if_icmplt( final Jump jump );
	
	@JvmOp( if_icmpgt.class )
	public abstract JvmCoreCodeWriter if_icmpgt( final Jump jump );
	
	@JvmOp( if_icmpge.class )
	public abstract JvmCoreCodeWriter if_icmpge( final Jump jump );
	
	@JvmOp( if_icmple.class )
	public abstract JvmCoreCodeWriter if_icmple( final Jump jump );
	
	@JvmOp( if_acmpeq.class )
	public abstract JvmCoreCodeWriter if_acmpeq( final Jump jump );
	
	@JvmOp( if_acmpne.class )
	public abstract JvmCoreCodeWriter if_acmpne( final Jump jump );
	
	@JvmOp( goto_.class )
	public abstract JvmCoreCodeWriter goto_( final Jump jump );
	
	public abstract JvmCoreCodeWriter handleException( final ExceptionHandler exceptionHandler );
	
	public abstract void prepareForWrite();
	
	public abstract JvmStack stackMonitor();
	
	public abstract JvmLocals localsMonitor();
	
	public abstract int pos();
	
	public abstract class Jump {
		public abstract Integer pos();
		
		@Override
		public abstract String toString();
	}
	
	public abstract class ExceptionHandler {
		public abstract int startPos();
		
		public abstract int endPos();
		
		public abstract Class< ? extends Throwable > throwableClass();
		
		public abstract int handlerPos();		
	}
}