package net.dougqh.jak.jvm;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.jvm.operations.*;

/**
 * A JvmOperationProcessor that hydrates the events into JvmOperation objects.
 */
public abstract class JvmOperationHydrator
	implements JvmOperationProcessor, JvmOperationProcessor.PositionAware
{
	private Integer pos = null;
	
	@Override
	public final void pos(final int pos) {
		this.pos = pos;
	}
	
    @Override
    public final void pop() {
        this.addImpl( pop.instance() );
    }

    @Override
    public final void swap() {
        this.addImpl( swap.instance() );
    }

    @Override
    public final void handleException( final ExceptionHandler exceptionhandler ) {
    	//TODO: Support this operation
    	throw new UnsupportedOperationException();
    }

    @Override
    public final void dup() {
        this.addImpl( dup.instance() );
    }

    @Override
    public final void nop() {
        this.addImpl( nop.instance() );
    }

    @Override
    public final void aconst_null() {
        this.addImpl( aconst_null.instance() );
    }

    @Override
    public final void iconst_m1() {
        this.addImpl( iconst_m1.instance() );
    }

    @Override
    public final void iconst_0() {
        this.addImpl( iconst_0.instance() );
    }

    @Override
    public final void iconst_1() {
        this.addImpl( iconst_1.instance() );
    }

    @Override
    public final void iconst_2() {
        this.addImpl( iconst_2.instance() );
    }

    @Override
    public final void iconst_3() {
        this.addImpl( iconst_3.instance() );
    }

    @Override
    public final void iconst_4() {
        this.addImpl( iconst_4.instance() );
    }

    @Override
    public final void iconst_5() {
        this.addImpl( iconst_5.instance() );
    }

    @Override
    public final void lconst_0() {
        this.addImpl( lconst_0.instance() );
    }

    @Override
    public final void lconst_1() {
        this.addImpl( lconst_1.instance() );
    }

    @Override
    public final void fconst_0() {
        this.addImpl( fconst_0.instance() );
    }

    @Override
    public final void fconst_1() {
        this.addImpl( fconst_1.instance() );
    }

    @Override
    public final void fconst_2() {
        this.addImpl( fconst_2.instance() );
    }

    @Override
    public final void dconst_0() {
        this.addImpl( dconst_0.instance() );
    }

    @Override
    public final void dconst_1() {
        this.addImpl( dconst_1.instance() );
    }

    @Override
    public final void bipush( final byte value ) {
        this.addImpl( new bipush( value ) );
    }

    @Override
    public final void sipush( final short value ) {
        this.addImpl( new sipush( value ) );
    }

    @Override
    public final void ldc( final Type type ) {
        this.addImpl( new ldc( type ) );
    }

    @Override
    public final void ldc( final String value ) {
        this.addImpl( new ldc( value ) );
    }

    @Override
    public final void ldc( final float value ) {
        this.addImpl( new ldc( value ) );
    }

    @Override
    public final void ldc( final int value ) {
        this.addImpl( new ldc( value ) );
    }

    @Override
    public final void ldc_w( final int value ) {
        this.addImpl( new ldc_w( value ) );
    }

    @Override
    public final void ldc_w( final Type value ) {
        this.addImpl( new ldc_w( value ) );
    }

    @Override
    public final void ldc_w( final float value ) {
        this.addImpl( new ldc_w( value ) );
    }

    @Override
    public final void ldc_w( final String value ) {
        this.addImpl( new ldc_w( value ) );
    }

    @Override
    public final void ldc2_w( final long value ) {
        this.addImpl( new ldc2_w( value ) );
    }

    @Override
    public final void ldc2_w( final double value ) {
        this.addImpl( new ldc2_w( value ) );
    }

    @Override
    public final void iload( final Slot slot ) {
        this.addImpl( new iload( slot ) );
    }

    @Override
    public final void iload( final int slot ) {
        this.addImpl( new iload( slot ) );
    }

    @Override
    public final void iload_0() {
        this.addImpl( iload_0.instance() );
    }

    @Override
    public final void iload_1() {
        this.addImpl( iload_1.instance() );
    }

    @Override
    public final void iload_2() {
        this.addImpl( iload_2.instance() );
    }

    @Override
    public final void iload_3() {
        this.addImpl( iload_3.instance() );
    }

    @Override
    public final void lload( final Slot slot ) {
        this.addImpl( new lload( slot ) );
    }

    @Override
    public final void lload( final int slot ) {
        this.addImpl( new lload( slot ) );
    }

    @Override
    public final void lload_0() {
        this.addImpl( lload_0.instance() );
    }

    @Override
    public final void lload_1() {
        this.addImpl( lload_1.instance() );
    }

    @Override
    public final void lload_2() {
        this.addImpl( lload_2.instance() );
    }

    @Override
    public final void lload_3() {
        this.addImpl( lload_3.instance() );
    }

    @Override
    public final void fload( final int slot ) {
        this.addImpl( new fload( slot ) );
    }

    @Override
    public final void fload( final Slot slot ) {
        this.addImpl( new fload( slot ) );
    }

    @Override
    public final void fload_0() {
        this.addImpl( fload_0.instance() );
    }

    @Override
    public final void fload_1() {
        this.addImpl( fload_1.instance() );
    }

    @Override
    public final void fload_2() {
        this.addImpl( fload_2.instance() );
    }

    @Override
    public final void fload_3() {
        this.addImpl( fload_3.instance() );
    }

    @Override
    public final void dload( final Slot slot ) {
        this.addImpl( new dload( slot ) );
    }

    @Override
    public final void dload( final int slot ) {
        this.addImpl( new dload( slot ) );
    }

    @Override
    public final void dload_0() {
        this.addImpl( dload_0.instance() );
    }

    @Override
    public final void dload_1() {
        this.addImpl( dload_1.instance() );
    }

    @Override
    public final void dload_2() {
        this.addImpl( dload_2.instance() );
    }

    @Override
    public final void dload_3() {
        this.addImpl( dload_3.instance() );
    }

    @Override
    public final void aload( final Slot slot ) {
        this.addImpl( new aload( slot ) );
    }
    
    @Override
    public final void aload( final int slot ) {
        this.addImpl( new aload( slot ) );
    }

    @Override
    public final void aload_0() {
        this.addImpl( aload_0.instance() );
    }

    @Override
    public final void aload_1() {
        this.addImpl( aload_1.instance() );
    }

    @Override
    public final void aload_2() {
        this.addImpl( aload_2.instance() );
    }

    @Override
    public final void aload_3() {
        this.addImpl( aload_3.instance() );
    }

    @Override
    public final void iaload() {
        this.addImpl( iaload.instance() );
    }

    @Override
    public final void laload() {
        this.addImpl( laload.instance() );
    }

    @Override
    public final void faload() {
        this.addImpl( faload.instance() );
    }

    @Override
    public final void daload() {
        this.addImpl( daload.instance() );
    }

    @Override
    public final void aaload() {
        this.addImpl( aaload.instance() );
    }

    @Override
    public final void baload() {
        this.addImpl( baload.instance() );
    }

    @Override
    public final void caload() {
        this.addImpl( caload.instance() );
    }

    @Override
    public final void saload() {
        this.addImpl( saload.instance() );
    }

    @Override
    public final void istore( final Slot slot ) {
        this.addImpl( new istore( slot ) );
    }

    @Override
    public final void istore( final int slot ) {
        this.addImpl( new istore( slot ) );
    }

    @Override
    public final void istore_0() {
        this.addImpl( istore_0.instance() );
    }

    @Override
    public final void istore_1() {
        this.addImpl( istore_1.instance() );
    }

    @Override
    public final void istore_2() {
        this.addImpl( istore_2.instance() );
    }

    @Override
    public final void istore_3() {
        this.addImpl( istore_3.instance() );
    }

    @Override
    public final void lstore( final Slot slot ) {
        this.addImpl( new lstore( slot ) );
    }

    @Override
    public final void lstore( final int slot ) {
        this.addImpl( new lstore( slot ) );
    }

    @Override
    public final void lstore_0() {
        this.addImpl( lstore_0.instance() );
    }

    @Override
    public final void lstore_1() {
        this.addImpl( lstore_1.instance() );
    }

    @Override
    public final void lstore_2() {
        this.addImpl( lstore_2.instance() );
    }

    @Override
    public final void lstore_3() {
        this.addImpl( lstore_3.instance() );
    }

    @Override
    public final void fstore( final int slot ) {
        this.addImpl( new fstore( slot ) );
    }

    @Override
    public final void fstore( final Slot slot ) {
        this.addImpl( new fstore( slot ) );
    }

    @Override
    public final void fstore_0() {
        this.addImpl( fstore_0.instance() );
    }

    @Override
    public final void fstore_1() {
        this.addImpl( fstore_1.instance() );
    }

    @Override
    public final void fstore_2() {
        this.addImpl( fstore_2.instance() );
    }

    @Override
    public final void fstore_3() {
        this.addImpl( fstore_3.instance() );
    }

    @Override
    public final void dstore( final Slot slot ) {
        this.addImpl( new dstore( slot ) );
    }

    @Override
    public final void dstore( final int slot ) {
        this.addImpl( new dstore( slot ) );
    }

    @Override
    public final void dstore_0() {
        this.addImpl( dstore_0.instance() );
    }

    @Override
    public final void dstore_1() {
        this.addImpl( dstore_1.instance() );
    }

    @Override
    public final void dstore_2() {
        this.addImpl( dstore_2.instance() );
    }

    @Override
    public final void dstore_3() {
        this.addImpl( dstore_3.instance() );
    }

    @Override
    public final void astore( final Slot slot ) {
        this.addImpl( new astore( slot ) );
    }

    @Override
    public final void astore( final int slot ) {
        this.addImpl( new astore( slot ) );
    }

    @Override
    public final void astore_0() {
        this.addImpl( astore_0.instance() );
    }

    @Override
    public final void astore_1() {
        this.addImpl( astore_1.instance() );
    }

    @Override
    public final void astore_2() {
        this.addImpl( astore_2.instance() );
    }

    @Override
    public final void astore_3() {
        this.addImpl( astore_3.instance() );
    }

    @Override
    public final void iastore() {
        this.addImpl( iastore.instance() );
    }

    @Override
    public final void lastore() {
        this.addImpl( lastore.instance() );
    }

    @Override
    public final void fastore() {
        this.addImpl( fastore.instance() );
    }

    @Override
    public final void dastore() {
        this.addImpl( dastore.instance() );
    }

    @Override
    public final void aastore() {
        this.addImpl( aastore.instance() );
    }

    @Override
    public final void bastore() {
        this.addImpl( bastore.instance() );
    }

    @Override
    public final void castore() {
        this.addImpl( castore.instance() );
    }

    @Override
    public final void sastore() {
        this.addImpl( sastore.instance() );
    }

    @Override
    public final void pop2() {
        this.addImpl( pop2.instance() );
    }

    @Override
    public final void dup_x1() {
        this.addImpl( dup_x1.instance() );
    }

    @Override
    public final void dup_x2() {
        this.addImpl( dup_x2.instance() );
    }

    @Override
    public final void dup2() {
        this.addImpl( dup2.instance() );
    }

    @Override
    public final void dup2_x1() {
        this.addImpl( dup2_x1.instance() );
    }

    @Override
    public final void dup2_x2() {
        this.addImpl( dup2_x2.instance() );
    }

    @Override
    public final void iadd() {
        this.addImpl( iadd.instance() );
    }

    @Override
    public final void ladd() {
        this.addImpl( ladd.instance() );
    }

    @Override
    public final void fadd() {
        this.addImpl( fadd.instance() );
    }

    @Override
    public final void dadd() {
        this.addImpl( dadd.instance() );
    }

    @Override
    public final void isub() {
        this.addImpl( isub.instance() );
    }

    @Override
    public final void lsub() {
        this.addImpl( lsub.instance() );
    }

    @Override
    public final void fsub() {
        this.addImpl( fsub.instance() );
    }

    @Override
    public final void dsub() {
        this.addImpl( dsub.instance() );
    }

    @Override
    public final void imul() {
        this.addImpl( imul.instance() );
    }

    @Override
    public final void lmul() {
        this.addImpl( lmul.instance() );
    }

    @Override
    public final void fmul() {
        this.addImpl( fmul.instance() );
    }

    @Override
    public final void dmul() {
        this.addImpl( dmul.instance() );
    }

    @Override
    public final void idiv() {
        this.addImpl( idiv.instance() );
    }

    @Override
    public final void ldiv() {
        this.addImpl( ldiv.instance() );
    }

    @Override
    public final void fdiv() {
        this.addImpl( fdiv.instance() );
    }

    @Override
    public final void ddiv() {
        this.addImpl( ddiv.instance() );
    }

    @Override
    public final void irem() {
        this.addImpl( irem.instance() );
    }

    @Override
    public final void lrem() {
        this.addImpl( lrem.instance() );
    }

    @Override
    public final void frem() {
        this.addImpl( frem.instance() );
    }

    @Override
    public final void drem() {
        this.addImpl( drem.instance() );
    }

    @Override
    public final void ineg() {
        this.addImpl( ineg.instance() );
    }

    @Override
    public final void lneg() {
        this.addImpl( lneg.instance() );
    }

    @Override
    public final void fneg() {
        this.addImpl( fneg.instance() );
    }

    @Override
    public final void dneg() {
        this.addImpl( dneg.instance() );
    }

    @Override
    public final void ishl() {
        this.addImpl( ishl.instance() );
    }

    @Override
    public final void lshl() {
        this.addImpl( lshl.instance() );
    }

    @Override
    public final void ishr() {
        this.addImpl( ishr.instance() );
    }

    @Override
    public final void lshr() {
        this.addImpl( lshr.instance() );
    }

    @Override
    public final void iushr() {
        this.addImpl( iushr.instance() );
    }

    @Override
    public final void lushr() {
        this.addImpl( lushr.instance() );
    }

    @Override
    public final void iand() {
        this.addImpl( iand.instance() );
    }

    @Override
    public final void land() {
        this.addImpl( land.instance() );
    }

    @Override
    public final void ior() {
        this.addImpl( ior.instance() );
    }

    @Override
    public final void lor() {
        this.addImpl( lor.instance() );
    }

    @Override
    public final void ixor() {
        this.addImpl( ixor.instance() );
    }

    @Override
    public final void lxor() {
        this.addImpl( lxor.instance() );
    }

    @Override
    public final void iinc( final int slot, final int delta ) {
        this.addImpl( new iinc( slot, delta ) );
    }

    @Override
    public final void i2l() {
        this.addImpl( i2l.instance() );
    }

    @Override
    public final void i2f() {
        this.addImpl( i2f.instance() );
    }

    @Override
    public final void i2d() {
        this.addImpl( i2d.instance() );
    }

    @Override
    public final void l2i() {
        this.addImpl( l2i.instance() );
    }

    @Override
    public final void l2f() {
        this.addImpl( l2f.instance() );
    }

    @Override
    public final void l2d() {
        this.addImpl( l2d.instance() );
    }

    @Override
    public final void f2i() {
        this.addImpl( f2i.instance() );
    }

    @Override
    public final void f2l() {
        this.addImpl( f2l.instance() );
    }

    @Override
    public final void f2d() {
        this.addImpl( f2d.instance() );
    }

    @Override
    public final void d2i() {
        this.addImpl( d2i.instance() );
    }

    @Override
    public final void d2l() {
        this.addImpl( d2l.instance() );
    }

    @Override
    public final void d2f() {
        this.addImpl( d2f.instance() );
    }

    @Override
    public final void i2b() {
        this.addImpl( i2b.instance() );
    }

    @Override
    public final void i2c() {
        this.addImpl( i2c.instance() );
    }

    @Override
    public final void i2s() {
        this.addImpl( i2s.instance() );
    }

    @Override
    public final void lcmp() {
        this.addImpl( lcmp.instance() );
    }

    @Override
    public final void fcmpl() {
        this.addImpl( fcmpl.instance() );
    }

    @Override
    public final void fcmpg() {
        this.addImpl( fcmpg.instance() );
    }

    @Override
    public final void dcmpl() {
        this.addImpl( dcmpl.instance() );
    }

    @Override
    public final void dcmpg() {
        this.addImpl( dcmpg.instance() );
    }

    @Override
    public final void ireturn() {
        this.addImpl( ireturn.instance() );
    }

    @Override
    public final void lreturn() {
        this.addImpl( lreturn.instance() );
    }

    @Override
    public final void freturn() {
        this.addImpl( freturn.instance() );
    }

    @Override
    public final void dreturn() {
        this.addImpl( dreturn.instance() );
    }

    @Override
    public final void areturn() {
        this.addImpl( areturn.instance() );
    }

    @Override
    public final void return_() {
        this.addImpl( return_.instance() );
    }

    @Override
    public final void getstatic( final Type type, final JavaField javaField ) {
        this.addImpl( new getstatic( type, javaField ) );
    }

    @Override
    public final void putstatic( final Type type, final JavaField javaField ) {
        this.addImpl( new putstatic( type, javaField ) );
    }

    @Override
    public final void getfield( final Type type, final JavaField javaField ) {
        this.addImpl( new getfield( type, javaField ) );
    }

    @Override
    public final void putfield( final Type type, final JavaField javaField ) {
        this.addImpl( new putfield( type, javaField ) );
    }

    @Override
    public final void invokevirtual( final Type type, final JavaMethodDescriptor methodDescriptor ) {
        this.addImpl( new invokevirtual( type, methodDescriptor ) );
    }

    @Override
    public final void invokeinterface( final Type type, final JavaMethodDescriptor methodDescriptor ) {
        this.addImpl( new invokeinterface( type, methodDescriptor ) );
    }

    @Override
    public final void invokestatic( final Type type, final JavaMethodDescriptor methodDescriptor ) {
        this.addImpl( new invokestatic( type, methodDescriptor ) );
    }

    @Override
    public final void invokespecial( final Type type, final JavaMethodDescriptor methodDescriptor ) {
        this.addImpl( new invokespecial( type, methodDescriptor ) );
    }

    @Override
    public final void new_( final Type type ) {
        this.addImpl( new new_( type ) );
    }

    @Override
    public final void newarray( final Type type ) {
        this.addImpl( new newarray( type ) );
    }

    @Override
    public final void anewarray( final Type type ) {
        this.addImpl( new anewarray( type ) );
    }

    @Override
    public final void arraylength() {
        this.addImpl( arraylength.instance() );
    }

    @Override
    public final void athrow() {
        this.addImpl( athrow.instance() );
    }

    @Override
    public final void checkcast( final Type type ) {
        this.addImpl( new checkcast( type ) );
    }

    @Override
    public final void instanceof_( final Type type ) {
        this.addImpl( new instanceof_( type ) );
    }

    @Override
    public final void monitorenter() {
        this.addImpl( monitorenter.instance() );
    }

    @Override
    public final void monitorexit() {
        this.addImpl( monitorexit.instance() );
    }

    @Override
    public final void multianewarray( final Type type, final int numDimensions ) {
        this.addImpl( new multianewarray( type, numDimensions ) );
    }

    @Override
    public final void ifnull( final Jump jump ) {
        this.addImpl( new ifnull( jump ) );
    }

    @Override
    public final void ifnonnull( final Jump jump ) {
        this.addImpl( new ifnonnull( jump ) );
    }

    @Override
    public final void ifeq( final Jump jump ) {
        this.addImpl( new ifeq( jump ) );
    }

    @Override
    public final void ifne( final Jump jump ) {
        this.addImpl( new ifne( jump ) );
    }

    @Override
    public final void iflt( final Jump jump ) {
        this.addImpl( new iflt( jump ) );
    }

    @Override
    public final void ifgt( final Jump jump ) {
        this.addImpl( new ifgt( jump ) );
    }

    @Override
    public final void ifge( final Jump jump ) {
        this.addImpl( new ifge( jump ) );
    }

    @Override
    public final void ifle( final Jump jump ) {
        this.addImpl( new ifle( jump ) );
    }

    @Override
    public final void if_icmpeq( final Jump jump ) {
        this.addImpl( new if_icmpeq( jump ) );
    }

    @Override
    public final void if_icmpne( final Jump jump ) {
        this.addImpl( new if_icmpne( jump ) );
    }

    @Override
    public final void if_icmplt( final Jump jump ) {
        this.addImpl( new if_icmplt( jump ) );
    }

    @Override
    public final void if_icmpgt( final Jump jump ) {
        this.addImpl( new if_icmpgt( jump ) );
    }

    @Override
    public final void if_icmpge( final Jump jump ) {
        this.addImpl( new if_icmpge( jump ) );
    }

    @Override
    public final void if_icmple( final Jump jump ) {
        this.addImpl( new if_icmple( jump ) );
    }

    @Override
    public final void if_acmpeq( final Jump jump ) {
        this.addImpl( new if_acmpeq( jump ) );
    }

    @Override
    public final void if_acmpne( final Jump jump ) {
        this.addImpl( new if_acmpne( jump ) );
    }

    @Override
    public final void goto_( final Jump jump ) {
        this.addImpl( new goto_( jump ) );
    }
    
    @Override
    public final void prepare() {
    }
    
    protected final void addImpl( final JvmOperation operation ) {
    	if ( this.pos != null ) {
    		operation.internals().initPos(this.pos);
    		this.pos = null;
    	}
    	this.add(operation);
    }
    
    protected abstract void add( final JvmOperation operation );
}
