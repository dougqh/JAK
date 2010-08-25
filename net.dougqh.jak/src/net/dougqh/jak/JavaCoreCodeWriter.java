package net.dougqh.jak;

import java.lang.reflect.Type;

import net.dougqh.jak.annotations.Op;
import net.dougqh.jak.operations.*;

public interface JavaCoreCodeWriter {
	@Op( Nop.class )
	public abstract JavaCoreCodeWriter nop();

	@Op( Aconst_null.class )
	public abstract JavaCoreCodeWriter aconst_null();

	@Op( Iconst_m1.class )
	public abstract JavaCoreCodeWriter iconst_m1();

	@Op( Iconst_0.class )
	public abstract JavaCoreCodeWriter iconst_0();

	@Op( Iconst_1.class )
	public abstract JavaCoreCodeWriter iconst_1();

	@Op( Iconst_2.class )
	public abstract JavaCoreCodeWriter iconst_2();

	@Op( Iconst_3.class )
	public abstract JavaCoreCodeWriter iconst_3();

	@Op( Iconst_4.class )
	public abstract JavaCoreCodeWriter iconst_4();

	@Op( Iconst_5.class )
	public abstract JavaCoreCodeWriter iconst_5();

	@Op( Lconst_0.class )
	public abstract JavaCoreCodeWriter lconst_0();

	@Op( Lconst_1.class )
	public abstract JavaCoreCodeWriter lconst_1();

	@Op( Fconst_0.class )
	public abstract JavaCoreCodeWriter fconst_0();

	@Op( Fconst_1.class )
	public abstract JavaCoreCodeWriter fconst_1();

	@Op( Fconst_2.class )
	public abstract JavaCoreCodeWriter fconst_2();

	@Op( Dconst_0.class )
	public abstract JavaCoreCodeWriter dconst_0();

	@Op( Dconst_1.class )
	public abstract JavaCoreCodeWriter dconst_1();

	@Op( Bipush.class )
	public abstract JavaCoreCodeWriter bipush( final byte value );

	@Op( Sipush.class )
	public abstract JavaCoreCodeWriter sipush( final short value );

	@Op( Ldc.class )
	public abstract JavaCoreCodeWriter ldc( final int index );
	
	@Op( Ldc_w.class )
	public abstract JavaCoreCodeWriter ldc_w( final int index );
	
	@Op( Ldc2_w.class )
	public abstract JavaCoreCodeWriter ldc2_w( final int index );

	@Op( Iload.class )
	public abstract JavaCoreCodeWriter iload( final int slot );

	@Op( Iload_0.class )
	public abstract JavaCoreCodeWriter iload_0();

	@Op( Iload_1.class )
	public abstract JavaCoreCodeWriter iload_1();

	@Op( Iload_2.class )
	public abstract JavaCoreCodeWriter iload_2();

	@Op( Iload_3.class )
	public abstract JavaCoreCodeWriter iload_3();

	@Op( Lload.class )
	public abstract JavaCoreCodeWriter lload( final int slot );

	@Op( Lload_0.class )
	public abstract JavaCoreCodeWriter lload_0();

	@Op( Lload_1.class )
	public abstract JavaCoreCodeWriter lload_1();

	@Op( Lload_2.class )
	public abstract JavaCoreCodeWriter lload_2();

	@Op( Lload_3.class )
	public abstract JavaCoreCodeWriter lload_3();

	@Op( Fload.class )
	public abstract JavaCoreCodeWriter fload( final int slot );

	@Op( Fload_0.class )
	public abstract JavaCoreCodeWriter fload_0();

	@Op( Fload_1.class )
	public abstract JavaCoreCodeWriter fload_1();

	@Op( Fload_2.class )
	public abstract JavaCoreCodeWriter fload_2();

	@Op( Fload_3.class )
	public abstract JavaCoreCodeWriter fload_3();

	@Op( Dload.class )
	public abstract JavaCoreCodeWriter dload( final int slot );

	@Op( Dload_0.class )
	public abstract JavaCoreCodeWriter dload_0();

	@Op( Dload_1.class )
	public abstract JavaCoreCodeWriter dload_1();

	@Op( Dload_2.class )
	public abstract JavaCoreCodeWriter dload_2();

	@Op( Dload_3.class )
	public abstract JavaCoreCodeWriter dload_3();

	@Op( Aload.class )
	public abstract JavaCoreCodeWriter aload( final int slot );

	@Op( Aload_0.class )
	public abstract JavaCoreCodeWriter aload_0();

	@Op( Aload_1.class )
	public abstract JavaCoreCodeWriter aload_1();

	@Op( Aload_2.class )
	public abstract JavaCoreCodeWriter aload_2();

	@Op( Aload_3.class )
	public abstract JavaCoreCodeWriter aload_3();

	@Op( Iaload.class )
	public abstract JavaCoreCodeWriter iaload();

	@Op( Laload.class )
	public abstract JavaCoreCodeWriter laload();

	@Op( Faload.class )
	public abstract JavaCoreCodeWriter faload();

	@Op( Daload.class )
	public abstract JavaCoreCodeWriter daload();

	@Op( Aaload.class )
	public abstract JavaCoreCodeWriter aaload();

	@Op( Baload.class )
	public abstract JavaCoreCodeWriter baload();

	@Op( Caload.class )
	public abstract JavaCoreCodeWriter caload();

	@Op( Saload.class )
	public abstract JavaCoreCodeWriter saload();

	@Op( Istore.class )
	public abstract JavaCoreCodeWriter istore( final int index );

	@Op( Istore_0.class )
	public abstract JavaCoreCodeWriter istore_0();

	@Op( Istore_1.class )
	public abstract JavaCoreCodeWriter istore_1();

	@Op( Istore_2.class )
	public abstract JavaCoreCodeWriter istore_2();

	@Op( Istore_3.class )
	public abstract JavaCoreCodeWriter istore_3();

	@Op( Lstore.class )
	public abstract JavaCoreCodeWriter lstore( final int slot );

	@Op( Lstore_0.class )
	public abstract JavaCoreCodeWriter lstore_0();

	@Op( Lstore_1.class )
	public abstract JavaCoreCodeWriter lstore_1();

	@Op( Lstore_2.class )
	public abstract JavaCoreCodeWriter lstore_2();

	@Op( Lstore_3.class )
	public abstract JavaCoreCodeWriter lstore_3();

	@Op( Fstore.class )
	public abstract JavaCoreCodeWriter fstore( final int slot );

	@Op( Fstore_0.class )
	public abstract JavaCoreCodeWriter fstore_0();

	@Op( Fstore_1.class )
	public abstract JavaCoreCodeWriter fstore_1();

	@Op( Fstore_2.class )
	public abstract JavaCoreCodeWriter fstore_2();

	@Op( Fstore_3.class )
	public abstract JavaCoreCodeWriter fstore_3();

	@Op( Dstore.class )
	public abstract JavaCoreCodeWriter dstore( final int slot );

	@Op( Dstore_0.class )
	public abstract JavaCoreCodeWriter dstore_0();

	@Op( Dstore_1.class )
	public abstract JavaCoreCodeWriter dstore_1();

	@Op( Dstore_2.class )
	public abstract JavaCoreCodeWriter dstore_2();

	@Op( Dstore_3.class )
	public abstract JavaCoreCodeWriter dstore_3();

	@Op( Astore.class )
	public abstract JavaCoreCodeWriter astore( final int slot );

	@Op( Astore_0.class )
	public abstract JavaCoreCodeWriter astore_0();

	@Op( Astore_1.class )
	public abstract JavaCoreCodeWriter astore_1();

	@Op( Astore_2.class )
	public abstract JavaCoreCodeWriter astore_2();

	@Op( Astore_3.class )
	public abstract JavaCoreCodeWriter astore_3();

	@Op( Iastore.class )
	public abstract JavaCoreCodeWriter iastore();

	@Op( Lastore.class )
	public abstract JavaCoreCodeWriter lastore();

	@Op( Fastore.class )
	public abstract JavaCoreCodeWriter fastore();

	@Op( Dastore.class )
	public abstract JavaCoreCodeWriter dastore();

	@Op( Aastore.class )
	public abstract JavaCoreCodeWriter aastore();

	@Op( Bastore.class )
	public abstract JavaCoreCodeWriter bastore();

	@Op( Castore.class )
	public abstract JavaCoreCodeWriter castore();

	@Op( Sastore.class )
	public abstract JavaCoreCodeWriter sastore();

	@Op( Pop.class )
	public abstract JavaCoreCodeWriter pop();
	
	@Op( Pop2.class )
	public abstract JavaCoreCodeWriter pop2();

	@Op( Dup.class )
	public abstract JavaCoreCodeWriter dup();

	@Op( Dup_x1.class )
	public abstract JavaCoreCodeWriter dup_x1();

	@Op( Dup_x2.class )
	public abstract JavaCoreCodeWriter dup_x2();

	@Op( Dup2.class )
	public abstract JavaCoreCodeWriter dup2();

	@Op( Dup2_x1.class )
	public abstract JavaCoreCodeWriter dup2_x1();

	@Op( Dup2_x2.class )
	public abstract JavaCoreCodeWriter dup2_x2();

	@Op( Swap.class )
	public abstract JavaCoreCodeWriter swap();

	@Op( Iadd.class )
	public abstract JavaCoreCodeWriter iadd();

	@Op( Ladd.class )
	public abstract JavaCoreCodeWriter ladd();

	@Op( Fadd.class )
	public abstract JavaCoreCodeWriter fadd();

	@Op( Dadd.class )
	public abstract JavaCoreCodeWriter dadd();

	@Op( Isub.class )
	public abstract JavaCoreCodeWriter isub();

	@Op( Lsub.class )
	public abstract JavaCoreCodeWriter lsub();

	@Op( Fsub.class )
	public abstract JavaCoreCodeWriter fsub();

	@Op( Dsub.class )
	public abstract JavaCoreCodeWriter dsub();

	@Op( Imul.class )
	public abstract JavaCoreCodeWriter imul();

	@Op( Lmul.class )
	public abstract JavaCoreCodeWriter lmul();

	@Op( Fmul.class )
	public abstract JavaCoreCodeWriter fmul();

	@Op( Dmul.class )
	public abstract JavaCoreCodeWriter dmul();

	@Op( Idiv.class )
	public abstract JavaCoreCodeWriter idiv();

	@Op( Ldiv.class )
	public abstract JavaCoreCodeWriter ldiv();

	@Op( Fdiv.class )
	public abstract JavaCoreCodeWriter fdiv();

	@Op( Ddiv.class )
	public abstract JavaCoreCodeWriter ddiv();

	@Op( Irem.class )
	public abstract JavaCoreCodeWriter irem();

	@Op( Lrem.class )
	public abstract JavaCoreCodeWriter lrem();

	@Op( Frem.class )
	public abstract JavaCoreCodeWriter frem();

	@Op( Drem.class )
	public abstract JavaCoreCodeWriter drem();

	@Op( Ineg.class )
	public abstract JavaCoreCodeWriter ineg();

	@Op( Lneg.class )
	public abstract JavaCoreCodeWriter lneg();

	@Op( Fneg.class )
	public abstract JavaCoreCodeWriter fneg();

	@Op( Dneg.class )
	public abstract JavaCoreCodeWriter dneg();

	@Op( Ishl.class )
	public abstract JavaCoreCodeWriter ishl();

	@Op( Lshl.class )
	public abstract JavaCoreCodeWriter lshl();

	@Op( Ishr.class )
	public abstract JavaCoreCodeWriter ishr();

	@Op( Lshr.class )
	public abstract JavaCoreCodeWriter lshr();

	@Op( Iushr.class )
	public abstract JavaCoreCodeWriter iushr();

	@Op( Lushr.class )
	public abstract JavaCoreCodeWriter lushr();

	@Op( Iand.class )
	public abstract JavaCoreCodeWriter iand();

	@Op( Land.class )
	public abstract JavaCoreCodeWriter land();

	@Op( Ior.class )
	public abstract JavaCoreCodeWriter ior();

	@Op( Lor.class )
	public abstract JavaCoreCodeWriter lor();

	@Op( Ixor.class )
	public abstract JavaCoreCodeWriter ixor();

	@Op( Lxor.class )
	public abstract JavaCoreCodeWriter lxor();

	@Op( Iinc.class )
	public abstract JavaCoreCodeWriter iinc(
		final int slot,
		final int amount );

	@Op( I2l.class )
	public abstract JavaCoreCodeWriter i2l();

	@Op( I2f.class )
	public abstract JavaCoreCodeWriter i2f();

	@Op( I2d.class )
	public abstract JavaCoreCodeWriter i2d();

	@Op( L2i.class )
	public abstract JavaCoreCodeWriter l2i();

	@Op( L2f.class )
	public abstract JavaCoreCodeWriter l2f();

	@Op( L2d.class )
	public abstract JavaCoreCodeWriter l2d();

	@Op( F2i.class )
	public abstract JavaCoreCodeWriter f2i();

	@Op( F2l.class )
	public abstract JavaCoreCodeWriter f2l();

	@Op( F2d.class )
	public abstract JavaCoreCodeWriter f2d();

	@Op( D2i.class )
	public abstract JavaCoreCodeWriter d2i();

	@Op( D2l.class )
	public abstract JavaCoreCodeWriter d2l();

	@Op( D2f.class )
	public abstract JavaCoreCodeWriter d2f();

	@Op( I2b.class )
	public abstract JavaCoreCodeWriter i2b();

	@Op( I2c.class )
	public abstract JavaCoreCodeWriter i2c();

	@Op( I2s.class )
	public abstract JavaCoreCodeWriter i2s();

	@Op( Lcmp.class )
	public abstract JavaCoreCodeWriter lcmp();

	@Op( Fcmpl.class )
	public abstract JavaCoreCodeWriter fcmpl();

	@Op( Fcmpg.class )
	public abstract JavaCoreCodeWriter fcmpg();

	@Op( Dcmpl.class )
	public abstract JavaCoreCodeWriter dcmpl();

	@Op( Dcmpg.class )
	public abstract JavaCoreCodeWriter dcmpg();

	@Op( Ireturn.class )
	public abstract JavaCoreCodeWriter ireturn();

	@Op( Lreturn.class )
	public abstract JavaCoreCodeWriter lreturn();

	@Op( Freturn.class )
	public abstract JavaCoreCodeWriter freturn();

	@Op( Dreturn.class )
	public abstract JavaCoreCodeWriter dreturn();

	@Op( Areturn.class )
	public abstract JavaCoreCodeWriter areturn();

	@Op( Return.class )
	public abstract JavaCoreCodeWriter return_();

	@Op( Getstatic.class )
	public abstract JavaCoreCodeWriter getstatic(
		final Type targetType,
		final JavaFieldDescriptor field );

	@Op( Putstatic.class )
	public abstract JavaCoreCodeWriter putstatic(
		final Type targetType,
		final JavaFieldDescriptor field );

	@Op( Getfield.class )
	public abstract JavaCoreCodeWriter getfield(
		final Type targetType,
		final JavaFieldDescriptor field );

	@Op( Putfield.class )
	public abstract JavaCoreCodeWriter putfield(
		final Type targetType,
		final JavaFieldDescriptor field );

	@Op( Invokevirtual.class )
	public abstract JavaCoreCodeWriter invokevirtual(
		final Type targetType,
		final JavaMethodDescriptor method );
	
	@Op( Invokevirtual.class )
	public abstract JavaCoreCodeWriter invokeinterface(
		final Type targetType,
		final JavaMethodDescriptor method );

	@Op( Invokestatic.class )
	public abstract JavaCoreCodeWriter invokestatic(
		final Type targetType,
		final JavaMethodDescriptor method );

	@Op( Invokestatic.class )
	public abstract JavaCoreCodeWriter invokespecial(
		final Type targetType,
		final JavaMethodDescriptor method );

	@Op( New.class )
	public abstract JavaCoreCodeWriter new_( final Type type );

	@Op( Newarray.class )
	public abstract JavaCoreCodeWriter newarray( final Type componentType );

	@Op( Anewarray.class )
	public abstract JavaCoreCodeWriter anewarray(final Type componentType);

	@Op( Arraylength.class )
	public abstract JavaCoreCodeWriter arraylength();

	@Op( Athrow.class )
	public abstract JavaCoreCodeWriter athrow();

	@Op( Checkcast.class )
	public abstract JavaCoreCodeWriter checkcast( final Type type );
	
	@Op( Instanceof.class )
	public abstract JavaCoreCodeWriter instanceof_( final Type type );

	@Op( Monitorenter.class )
	public abstract JavaCoreCodeWriter monitorenter();

	@Op( Monitorexit.class )
	public abstract JavaCoreCodeWriter monitorexit();

	@Op( Multianewarray.class )
	public abstract JavaCoreCodeWriter multianewarray(
		final Type arrayType,
		final int numDimensions );
	
	@Op( Ifnull.class )
	public abstract JavaCoreCodeWriter ifnull( final Jump jump );
	
	@Op( Ifnonnull.class )
	public abstract JavaCoreCodeWriter ifnonnull( final Jump jump );
	
	@Op( Ifeq.class )
	public abstract JavaCoreCodeWriter ifeq( final Jump jump );
	
	@Op( Ifne.class )
	public abstract JavaCoreCodeWriter ifne( final Jump jump );
	
	@Op( Iflt.class )
	public abstract JavaCoreCodeWriter iflt( final Jump jump );
	
	@Op( Ifgt.class )
	public abstract JavaCoreCodeWriter ifgt( final Jump jump );
	
	@Op( Ifge.class )
	public abstract JavaCoreCodeWriter ifge( final Jump jump );
	
	@Op( Ifle.class )
	public abstract JavaCoreCodeWriter ifle( final Jump jump );
	
	@Op( If_icmpeq.class )
	public abstract JavaCoreCodeWriter if_icmpeq( final Jump jump );
	
	@Op( If_icmpne.class )
	public abstract JavaCoreCodeWriter if_icmpne( final Jump jump );
	
	@Op( If_icmplt.class )
	public abstract JavaCoreCodeWriter if_icmplt( final Jump jump );
	
	@Op( If_icmpgt.class )
	public abstract JavaCoreCodeWriter if_icmpgt( final Jump jump );
	
	@Op( If_icmpge.class )
	public abstract JavaCoreCodeWriter if_icmpge( final Jump jump );
	
	@Op( If_icmple.class )
	public abstract JavaCoreCodeWriter if_icmple( final Jump jump );
	
	@Op( If_acmpeq.class )
	public abstract JavaCoreCodeWriter if_acmpeq( final Jump jump );
	
	@Op( If_acmpne.class )
	public abstract JavaCoreCodeWriter if_acmpne( final Jump jump );
	
	@Op( Goto.class )
	public abstract JavaCoreCodeWriter goto_( final Jump jump );
	
	public abstract JavaCoreCodeWriter handleException( final ExceptionHandler exceptionHandler );
	
	public abstract Stack stack();
	
	public abstract Locals locals();
	
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