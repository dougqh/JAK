package net.dougqh.jak.jvm;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.jvm.operations.*;

/**
 * A JvmOperationProcessor that hydrates the events into JvmOperation objects.
 */
public abstract class JvmOperationHydrator implements JvmOperationProcessor {
    @Override
    public final void pop() {
        this.add( pop.instance() );
    }

    @Override
    public final void swap() {
        this.add( swap.instance() );
    }

    @Override
    public final void handleException( final ExceptionHandler exceptionhandler ) {
    	//TODO: Support this operation
    	throw new UnsupportedOperationException();
    }

    @Override
    public final void dup() {
        this.add( dup.instance() );
    }

    @Override
    public final void nop() {
        this.add( nop.instance() );
    }

    @Override
    public final void aconst_null() {
        this.add( aconst_null.instance() );
    }

    @Override
    public final void iconst_m1() {
        this.add( iconst_m1.instance() );
    }

    @Override
    public final void iconst_0() {
        this.add( iconst_0.instance() );
    }

    @Override
    public final void iconst_1() {
        this.add( iconst_1.instance() );
    }

    @Override
    public final void iconst_2() {
        this.add( iconst_2.instance() );
    }

    @Override
    public final void iconst_3() {
        this.add( iconst_3.instance() );
    }

    @Override
    public final void iconst_4() {
        this.add( iconst_4.instance() );
    }

    @Override
    public final void iconst_5() {
        this.add( iconst_5.instance() );
    }

    @Override
    public final void lconst_0() {
        this.add( lconst_0.instance() );
    }

    @Override
    public final void lconst_1() {
        this.add( lconst_1.instance() );
    }

    @Override
    public final void fconst_0() {
        this.add( fconst_0.instance() );
    }

    @Override
    public final void fconst_1() {
        this.add( fconst_1.instance() );
    }

    @Override
    public final void fconst_2() {
        this.add( fconst_2.instance() );
    }

    @Override
    public final void dconst_0() {
        this.add( dconst_0.instance() );
    }

    @Override
    public final void dconst_1() {
        this.add( dconst_1.instance() );
    }

    @Override
    public final void bipush( final byte value ) {
        this.add( new bipush( value ) );
    }

    @Override
    public final void sipush( final short value ) {
        this.add( new sipush( value ) );
    }

    @Override
    public final void ldc( final Type type ) {
        this.add( new ldc( type ) );
    }

    @Override
    public final void ldc( final String value ) {
        this.add( new ldc( value ) );
    }

    @Override
    public final void ldc( final float value ) {
        this.add( new ldc( value ) );
    }

    @Override
    public final void ldc( final int value ) {
        this.add( new ldc( value ) );
    }

    @Override
    public final void ldc_w( final int value ) {
        this.add( new ldc_w( value ) );
    }

    @Override
    public final void ldc_w( final Type value ) {
        this.add( new ldc_w( value ) );
    }

    @Override
    public final void ldc_w( final float value ) {
        this.add( new ldc_w( value ) );
    }

    @Override
    public final void ldc_w( final String value ) {
        this.add( new ldc_w( value ) );
    }

    @Override
    public final void ldc2_w( final long value ) {
        this.add( new ldc2_w( value ) );
    }

    @Override
    public final void ldc2_w( final double value ) {
        this.add( new ldc2_w( value ) );
    }

    @Override
    public final void iload( final Slot slot ) {
        this.add( new iload( slot ) );
    }

    @Override
    public final void iload( final int slot ) {
        this.add( new iload( slot ) );
    }

    @Override
    public final void iload_0() {
        this.add( iload_0.instance() );
    }

    @Override
    public final void iload_1() {
        this.add( iload_1.instance() );
    }

    @Override
    public final void iload_2() {
        this.add( iload_2.instance() );
    }

    @Override
    public final void iload_3() {
        this.add( iload_3.instance() );
    }

    @Override
    public final void lload( final Slot slot ) {
        this.add( new lload( slot ) );
    }

    @Override
    public final void lload( final int slot ) {
        this.add( new lload( slot ) );
    }

    @Override
    public final void lload_0() {
        this.add( lload_0.instance() );
    }

    @Override
    public final void lload_1() {
        this.add( lload_1.instance() );
    }

    @Override
    public final void lload_2() {
        this.add( lload_2.instance() );
    }

    @Override
    public final void lload_3() {
        this.add( lload_3.instance() );
    }

    @Override
    public final void fload( final int slot ) {
        this.add( new fload( slot ) );
    }

    @Override
    public final void fload( final Slot slot ) {
        this.add( new fload( slot ) );
    }

    @Override
    public final void fload_0() {
        this.add( fload_0.instance() );
    }

    @Override
    public final void fload_1() {
        this.add( fload_1.instance() );
    }

    @Override
    public final void fload_2() {
        this.add( fload_2.instance() );
    }

    @Override
    public final void fload_3() {
        this.add( fload_3.instance() );
    }

    @Override
    public final void dload( final Slot slot ) {
        this.add( new dload( slot ) );
    }

    @Override
    public final void dload( final int slot ) {
        this.add( new dload( slot ) );
    }

    @Override
    public final void dload_0() {
        this.add( dload_0.instance() );
    }

    @Override
    public final void dload_1() {
        this.add( dload_1.instance() );
    }

    @Override
    public final void dload_2() {
        this.add( dload_2.instance() );
    }

    @Override
    public final void dload_3() {
        this.add( dload_3.instance() );
    }

    @Override
    public final void aload( final Slot slot ) {
        this.add( new aload( slot ) );
    }
    
    @Override
    public final void aload( final int slot ) {
        this.add( new aload( slot ) );
    }

    @Override
    public final void aload_0() {
        this.add( aload_0.instance() );
    }

    @Override
    public final void aload_1() {
        this.add( aload_1.instance() );
    }

    @Override
    public final void aload_2() {
        this.add( aload_2.instance() );
    }

    @Override
    public final void aload_3() {
        this.add( aload_3.instance() );
    }

    @Override
    public final void iaload() {
        this.add( iaload.instance() );
    }

    @Override
    public final void laload() {
        this.add( laload.instance() );
    }

    @Override
    public final void faload() {
        this.add( faload.instance() );
    }

    @Override
    public final void daload() {
        this.add( daload.instance() );
    }

    @Override
    public final void aaload() {
        this.add( aaload.instance() );
    }

    @Override
    public final void baload() {
        this.add( baload.instance() );
    }

    @Override
    public final void caload() {
        this.add( caload.instance() );
    }

    @Override
    public final void saload() {
        this.add( saload.instance() );
    }

    @Override
    public final void istore( final Slot slot ) {
        this.add( new istore( slot ) );
    }

    @Override
    public final void istore( final int slot ) {
        this.add( new istore( slot ) );
    }

    @Override
    public final void istore_0() {
        this.add( istore_0.instance() );
    }

    @Override
    public final void istore_1() {
        this.add( istore_1.instance() );
    }

    @Override
    public final void istore_2() {
        this.add( istore_2.instance() );
    }

    @Override
    public final void istore_3() {
        this.add( istore_3.instance() );
    }

    @Override
    public final void lstore( final Slot slot ) {
        this.add( new lstore( slot ) );
    }

    @Override
    public final void lstore( final int slot ) {
        this.add( new lstore( slot ) );
    }

    @Override
    public final void lstore_0() {
        this.add( lstore_0.instance() );
    }

    @Override
    public final void lstore_1() {
        this.add( lstore_1.instance() );
    }

    @Override
    public final void lstore_2() {
        this.add( lstore_2.instance() );
    }

    @Override
    public final void lstore_3() {
        this.add( lstore_3.instance() );
    }

    @Override
    public final void fstore( final int slot ) {
        this.add( new fstore( slot ) );
    }

    @Override
    public final void fstore( final Slot slot ) {
        this.add( new fstore( slot ) );
    }

    @Override
    public final void fstore_0() {
        this.add( fstore_0.instance() );
    }

    @Override
    public final void fstore_1() {
        this.add( fstore_1.instance() );
    }

    @Override
    public final void fstore_2() {
        this.add( fstore_2.instance() );
    }

    @Override
    public final void fstore_3() {
        this.add( fstore_3.instance() );
    }

    @Override
    public final void dstore( final Slot slot ) {
        this.add( new dstore( slot ) );
    }

    @Override
    public final void dstore( final int slot ) {
        this.add( new dstore( slot ) );
    }

    @Override
    public final void dstore_0() {
        this.add( dstore_0.instance() );
    }

    @Override
    public final void dstore_1() {
        this.add( dstore_1.instance() );
    }

    @Override
    public final void dstore_2() {
        this.add( dstore_2.instance() );
    }

    @Override
    public final void dstore_3() {
        this.add( dstore_3.instance() );
    }

    @Override
    public final void astore( final Slot slot ) {
        this.add( new astore( slot ) );
    }

    @Override
    public final void astore( final int slot ) {
        this.add( new astore( slot ) );
    }

    @Override
    public final void astore_0() {
        this.add( astore_0.instance() );
    }

    @Override
    public final void astore_1() {
        this.add( astore_1.instance() );
    }

    @Override
    public final void astore_2() {
        this.add( astore_2.instance() );
    }

    @Override
    public final void astore_3() {
        this.add( astore_3.instance() );
    }

    @Override
    public final void iastore() {
        this.add( iastore.instance() );
    }

    @Override
    public final void lastore() {
        this.add( lastore.instance() );
    }

    @Override
    public final void fastore() {
        this.add( fastore.instance() );
    }

    @Override
    public final void dastore() {
        this.add( dastore.instance() );
    }

    @Override
    public final void aastore() {
        this.add( aastore.instance() );
    }

    @Override
    public final void bastore() {
        this.add( bastore.instance() );
    }

    @Override
    public final void castore() {
        this.add( castore.instance() );
    }

    @Override
    public final void sastore() {
        this.add( sastore.instance() );
    }

    @Override
    public final void pop2() {
        this.add( pop2.instance() );
    }

    @Override
    public final void dup_x1() {
        this.add( dup_x1.instance() );
    }

    @Override
    public final void dup_x2() {
        this.add( dup_x2.instance() );
    }

    @Override
    public final void dup2() {
        this.add( dup2.instance() );
    }

    @Override
    public final void dup2_x1() {
        this.add( dup2_x1.instance() );
    }

    @Override
    public final void dup2_x2() {
        this.add( dup2_x2.instance() );
    }

    @Override
    public final void iadd() {
        this.add( iadd.instance() );
    }

    @Override
    public final void ladd() {
        this.add( ladd.instance() );
    }

    @Override
    public final void fadd() {
        this.add( fadd.instance() );
    }

    @Override
    public final void dadd() {
        this.add( dadd.instance() );
    }

    @Override
    public final void isub() {
        this.add( isub.instance() );
    }

    @Override
    public final void lsub() {
        this.add( lsub.instance() );
    }

    @Override
    public final void fsub() {
        this.add( fsub.instance() );
    }

    @Override
    public final void dsub() {
        this.add( dsub.instance() );
    }

    @Override
    public final void imul() {
        this.add( imul.instance() );
    }

    @Override
    public final void lmul() {
        this.add( lmul.instance() );
    }

    @Override
    public final void fmul() {
        this.add( fmul.instance() );
    }

    @Override
    public final void dmul() {
        this.add( dmul.instance() );
    }

    @Override
    public final void idiv() {
        this.add( idiv.instance() );
    }

    @Override
    public final void ldiv() {
        this.add( ldiv.instance() );
    }

    @Override
    public final void fdiv() {
        this.add( fdiv.instance() );
    }

    @Override
    public final void ddiv() {
        this.add( ddiv.instance() );
    }

    @Override
    public final void irem() {
        this.add( irem.instance() );
    }

    @Override
    public final void lrem() {
        this.add( lrem.instance() );
    }

    @Override
    public final void frem() {
        this.add( frem.instance() );
    }

    @Override
    public final void drem() {
        this.add( drem.instance() );
    }

    @Override
    public final void ineg() {
        this.add( ineg.instance() );
    }

    @Override
    public final void lneg() {
        this.add( lneg.instance() );
    }

    @Override
    public final void fneg() {
        this.add( fneg.instance() );
    }

    @Override
    public final void dneg() {
        this.add( dneg.instance() );
    }

    @Override
    public final void ishl() {
        this.add( ishl.instance() );
    }

    @Override
    public final void lshl() {
        this.add( lshl.instance() );
    }

    @Override
    public final void ishr() {
        this.add( ishr.instance() );
    }

    @Override
    public final void lshr() {
        this.add( lshr.instance() );
    }

    @Override
    public final void iushr() {
        this.add( iushr.instance() );
    }

    @Override
    public final void lushr() {
        this.add( lushr.instance() );
    }

    @Override
    public final void iand() {
        this.add( iand.instance() );
    }

    @Override
    public final void land() {
        this.add( land.instance() );
    }

    @Override
    public final void ior() {
        this.add( ior.instance() );
    }

    @Override
    public final void lor() {
        this.add( lor.instance() );
    }

    @Override
    public final void ixor() {
        this.add( ixor.instance() );
    }

    @Override
    public final void lxor() {
        this.add( lxor.instance() );
    }

    @Override
    public final void iinc( final int slot, final int delta ) {
        this.add( new iinc( slot, delta ) );
    }

    @Override
    public final void i2l() {
        this.add( i2l.instance() );
    }

    @Override
    public final void i2f() {
        this.add( i2f.instance() );
    }

    @Override
    public final void i2d() {
        this.add( i2d.instance() );
    }

    @Override
    public final void l2i() {
        this.add( l2i.instance() );
    }

    @Override
    public final void l2f() {
        this.add( l2f.instance() );
    }

    @Override
    public final void l2d() {
        this.add( l2d.instance() );
    }

    @Override
    public final void f2i() {
        this.add( f2i.instance() );
    }

    @Override
    public final void f2l() {
        this.add( f2l.instance() );
    }

    @Override
    public final void f2d() {
        this.add( f2d.instance() );
    }

    @Override
    public final void d2i() {
        this.add( d2i.instance() );
    }

    @Override
    public final void d2l() {
        this.add( d2l.instance() );
    }

    @Override
    public final void d2f() {
        this.add( d2f.instance() );
    }

    @Override
    public final void i2b() {
        this.add( i2b.instance() );
    }

    @Override
    public final void i2c() {
        this.add( i2c.instance() );
    }

    @Override
    public final void i2s() {
        this.add( i2s.instance() );
    }

    @Override
    public final void lcmp() {
        this.add( lcmp.instance() );
    }

    @Override
    public final void fcmpl() {
        this.add( fcmpl.instance() );
    }

    @Override
    public final void fcmpg() {
        this.add( fcmpg.instance() );
    }

    @Override
    public final void dcmpl() {
        this.add( dcmpl.instance() );
    }

    @Override
    public final void dcmpg() {
        this.add( dcmpg.instance() );
    }

    @Override
    public final void ireturn() {
        this.add( ireturn.instance() );
    }

    @Override
    public final void lreturn() {
        this.add( lreturn.instance() );
    }

    @Override
    public final void freturn() {
        this.add( freturn.instance() );
    }

    @Override
    public final void dreturn() {
        this.add( dreturn.instance() );
    }

    @Override
    public final void areturn() {
        this.add( areturn.instance() );
    }

    @Override
    public final void return_() {
        this.add( return_.instance() );
    }

    @Override
    public final void getstatic( final Type type, final JavaField javafield ) {
        this.add( new getstatic( type, javafield ) );
    }

    @Override
    public final void putstatic( final Type type, final JavaField javafield ) {
        this.add( new putstatic( type, javafield ) );
    }

    @Override
    public final void getfield( final Type type, final JavaField javafield ) {
        this.add( new getfield( type, javafield ) );
    }

    @Override
    public final void putfield( final Type type, final JavaField javafield ) {
        this.add( new putfield( type, javafield ) );
    }

    @Override
    public final void invokevirtual( final Type type, final JavaMethodDescriptor javamethoddescriptor ) {
        this.add( new invokevirtual( type, javamethoddescriptor ) );
    }

    @Override
    public final void invokeinterface( final Type type, final JavaMethodDescriptor javamethoddescriptor ) {
        this.add( new invokeinterface( type, javamethoddescriptor ) );
    }

    @Override
    public final void invokestatic( final Type type, final JavaMethodDescriptor javamethoddescriptor ) {
        this.add( new invokestatic( type, javamethoddescriptor ) );
    }

    @Override
    public final void invokespecial( final Type type, final JavaMethodDescriptor javamethoddescriptor ) {
        this.add( new invokespecial( type, javamethoddescriptor ) );
    }

    @Override
    public final void new_( final Type type ) {
        this.add( new new_( type ) );
    }

    @Override
    public final void newarray( final Type type ) {
        this.add( new newarray( type ) );
    }

    @Override
    public final void anewarray( final Type type ) {
        this.add( new anewarray( type ) );
    }

    @Override
    public final void arraylength() {
        this.add( arraylength.instance() );
    }

    @Override
    public final void athrow() {
        this.add( athrow.instance() );
    }

    @Override
    public final void checkcast( final Type type ) {
        this.add( new checkcast( type ) );
    }

    @Override
    public final void instanceof_( final Type type ) {
        this.add( new instanceof_( type ) );
    }

    @Override
    public final void monitorenter() {
        this.add( monitorenter.instance() );
    }

    @Override
    public final void monitorexit() {
        this.add( monitorexit.instance() );
    }

    @Override
    public final void multianewarray( final Type type, final int numDimensions ) {
        this.add( new multianewarray( type, numDimensions ) );
    }

    @Override
    public final void ifnull( final Jump jump ) {
        this.add( new ifnull( jump ) );
    }

    @Override
    public final void ifnonnull( final Jump jump ) {
        this.add( new ifnonnull( jump ) );
    }

    @Override
    public final void ifeq( final Jump jump ) {
        this.add( new ifeq( jump ) );
    }

    @Override
    public final void ifne( final Jump jump ) {
        this.add( new ifne( jump ) );
    }

    @Override
    public final void iflt( final Jump jump ) {
        this.add( new iflt( jump ) );
    }

    @Override
    public final void ifgt( final Jump jump ) {
        this.add( new ifgt( jump ) );
    }

    @Override
    public final void ifge( final Jump jump ) {
        this.add( new ifge( jump ) );
    }

    @Override
    public final void ifle( final Jump jump ) {
        this.add( new ifle( jump ) );
    }

    @Override
    public final void if_icmpeq( final Jump jump ) {
        this.add( new if_icmpeq( jump ) );
    }

    @Override
    public final void if_icmpne( final Jump jump ) {
        this.add( new if_icmpne( jump ) );
    }

    @Override
    public final void if_icmplt( final Jump jump ) {
        this.add( new if_icmplt( jump ) );
    }

    @Override
    public final void if_icmpgt( final Jump jump ) {
        this.add( new if_icmpgt( jump ) );
    }

    @Override
    public final void if_icmpge( final Jump jump ) {
        this.add( new if_icmpge( jump ) );
    }

    @Override
    public final void if_icmple( final Jump jump ) {
        this.add( new if_icmple( jump ) );
    }

    @Override
    public final void if_acmpeq( final Jump jump ) {
        this.add( new if_acmpeq( jump ) );
    }

    @Override
    public final void if_acmpne( final Jump jump ) {
        this.add( new if_acmpne( jump ) );
    }

    @Override
    public final void goto_( final Jump jump ) {
        this.add( new goto_( jump ) );
    }
    
    @Override
    public final void prepare() {
    }
    
    protected abstract void add( final JvmOperation operation );
}
