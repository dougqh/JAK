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
	
	public Integer pos() {
		return this.pos;
	}
	
    @Override
    public final void pop() {
        this.processImpl( pop.instance() );
    }

    @Override
    public final void swap() {
        this.processImpl( swap.instance() );
    }

    @Override
    public final void handleException( final ExceptionHandler exceptionhandler ) {
    	//TODO: Support this operation
    	throw new UnsupportedOperationException();
    }

    @Override
    public final void dup() {
        this.processImpl( dup.instance() );
    }

    @Override
    public final void nop() {
        this.processImpl( nop.instance() );
    }

    @Override
    public final void aconst_null() {
        this.processImpl( aconst_null.instance() );
    }

    @Override
    public final void iconst_m1() {
        this.processImpl( iconst_m1.instance() );
    }

    @Override
    public final void iconst_0() {
        this.processImpl( iconst_0.instance() );
    }

    @Override
    public final void iconst_1() {
        this.processImpl( iconst_1.instance() );
    }

    @Override
    public final void iconst_2() {
        this.processImpl( iconst_2.instance() );
    }

    @Override
    public final void iconst_3() {
        this.processImpl( iconst_3.instance() );
    }

    @Override
    public final void iconst_4() {
        this.processImpl( iconst_4.instance() );
    }

    @Override
    public final void iconst_5() {
        this.processImpl( iconst_5.instance() );
    }

    @Override
    public final void lconst_0() {
        this.processImpl( lconst_0.instance() );
    }

    @Override
    public final void lconst_1() {
        this.processImpl( lconst_1.instance() );
    }

    @Override
    public final void fconst_0() {
        this.processImpl( fconst_0.instance() );
    }

    @Override
    public final void fconst_1() {
        this.processImpl( fconst_1.instance() );
    }

    @Override
    public final void fconst_2() {
        this.processImpl( fconst_2.instance() );
    }

    @Override
    public final void dconst_0() {
        this.processImpl( dconst_0.instance() );
    }

    @Override
    public final void dconst_1() {
        this.processImpl( dconst_1.instance() );
    }

    @Override
    public final void bipush( final byte value ) {
        this.processImpl( new bipush( value ) );
    }

    @Override
    public final void sipush( final short value ) {
        this.processImpl( new sipush( value ) );
    }

    @Override
    public final void ldc( final Type type ) {
        this.processImpl( new ldc( type ) );
    }

    @Override
    public final void ldc( final String value ) {
        this.processImpl( new ldc( value ) );
    }

    @Override
    public final void ldc( final float value ) {
        this.processImpl( new ldc( value ) );
    }

    @Override
    public final void ldc( final int value ) {
        this.processImpl( new ldc( value ) );
    }

    @Override
    public final void ldc_w( final int value ) {
        this.processImpl( new ldc_w( value ) );
    }

    @Override
    public final void ldc_w( final Type value ) {
        this.processImpl( new ldc_w( value ) );
    }

    @Override
    public final void ldc_w( final float value ) {
        this.processImpl( new ldc_w( value ) );
    }

    @Override
    public final void ldc_w( final String value ) {
        this.processImpl( new ldc_w( value ) );
    }

    @Override
    public final void ldc2_w( final long value ) {
        this.processImpl( new ldc2_w( value ) );
    }

    @Override
    public final void ldc2_w( final double value ) {
        this.processImpl( new ldc2_w( value ) );
    }

    @Override
    public final void iload( final Slot slot ) {
        this.processImpl( new iload( slot ) );
    }

    @Override
    public final void iload( final int slot ) {
        this.processImpl( new iload( slot ) );
    }

    @Override
    public final void iload_0() {
        this.processImpl( iload_0.instance() );
    }

    @Override
    public final void iload_1() {
        this.processImpl( iload_1.instance() );
    }

    @Override
    public final void iload_2() {
        this.processImpl( iload_2.instance() );
    }

    @Override
    public final void iload_3() {
        this.processImpl( iload_3.instance() );
    }

    @Override
    public final void lload( final Slot slot ) {
        this.processImpl( new lload( slot ) );
    }

    @Override
    public final void lload( final int slot ) {
        this.processImpl( new lload( slot ) );
    }

    @Override
    public final void lload_0() {
        this.processImpl( lload_0.instance() );
    }

    @Override
    public final void lload_1() {
        this.processImpl( lload_1.instance() );
    }

    @Override
    public final void lload_2() {
        this.processImpl( lload_2.instance() );
    }

    @Override
    public final void lload_3() {
        this.processImpl( lload_3.instance() );
    }

    @Override
    public final void fload( final int slot ) {
        this.processImpl( new fload( slot ) );
    }

    @Override
    public final void fload( final Slot slot ) {
        this.processImpl( new fload( slot ) );
    }

    @Override
    public final void fload_0() {
        this.processImpl( fload_0.instance() );
    }

    @Override
    public final void fload_1() {
        this.processImpl( fload_1.instance() );
    }

    @Override
    public final void fload_2() {
        this.processImpl( fload_2.instance() );
    }

    @Override
    public final void fload_3() {
        this.processImpl( fload_3.instance() );
    }

    @Override
    public final void dload( final Slot slot ) {
        this.processImpl( new dload( slot ) );
    }

    @Override
    public final void dload( final int slot ) {
        this.processImpl( new dload( slot ) );
    }

    @Override
    public final void dload_0() {
        this.processImpl( dload_0.instance() );
    }

    @Override
    public final void dload_1() {
        this.processImpl( dload_1.instance() );
    }

    @Override
    public final void dload_2() {
        this.processImpl( dload_2.instance() );
    }

    @Override
    public final void dload_3() {
        this.processImpl( dload_3.instance() );
    }

    @Override
    public final void aload( final Slot slot ) {
        this.processImpl( new aload( slot ) );
    }
    
    @Override
    public final void aload( final int slot ) {
        this.processImpl( new aload( slot ) );
    }

    @Override
    public final void aload_0() {
        this.processImpl( aload_0.instance() );
    }

    @Override
    public final void aload_1() {
        this.processImpl( aload_1.instance() );
    }

    @Override
    public final void aload_2() {
        this.processImpl( aload_2.instance() );
    }

    @Override
    public final void aload_3() {
        this.processImpl( aload_3.instance() );
    }

    @Override
    public final void iaload() {
        this.processImpl( iaload.instance() );
    }

    @Override
    public final void laload() {
        this.processImpl( laload.instance() );
    }

    @Override
    public final void faload() {
        this.processImpl( faload.instance() );
    }

    @Override
    public final void daload() {
        this.processImpl( daload.instance() );
    }

    @Override
    public final void aaload() {
        this.processImpl( aaload.instance() );
    }

    @Override
    public final void baload() {
        this.processImpl( baload.instance() );
    }

    @Override
    public final void caload() {
        this.processImpl( caload.instance() );
    }

    @Override
    public final void saload() {
        this.processImpl( saload.instance() );
    }

    @Override
    public final void istore( final Slot slot ) {
        this.processImpl( new istore( slot ) );
    }

    @Override
    public final void istore( final int slot ) {
        this.processImpl( new istore( slot ) );
    }

    @Override
    public final void istore_0() {
        this.processImpl( istore_0.instance() );
    }

    @Override
    public final void istore_1() {
        this.processImpl( istore_1.instance() );
    }

    @Override
    public final void istore_2() {
        this.processImpl( istore_2.instance() );
    }

    @Override
    public final void istore_3() {
        this.processImpl( istore_3.instance() );
    }

    @Override
    public final void lstore( final Slot slot ) {
        this.processImpl( new lstore( slot ) );
    }

    @Override
    public final void lstore( final int slot ) {
        this.processImpl( new lstore( slot ) );
    }

    @Override
    public final void lstore_0() {
        this.processImpl( lstore_0.instance() );
    }

    @Override
    public final void lstore_1() {
        this.processImpl( lstore_1.instance() );
    }

    @Override
    public final void lstore_2() {
        this.processImpl( lstore_2.instance() );
    }

    @Override
    public final void lstore_3() {
        this.processImpl( lstore_3.instance() );
    }

    @Override
    public final void fstore( final int slot ) {
        this.processImpl( new fstore( slot ) );
    }

    @Override
    public final void fstore( final Slot slot ) {
        this.processImpl( new fstore( slot ) );
    }

    @Override
    public final void fstore_0() {
        this.processImpl( fstore_0.instance() );
    }

    @Override
    public final void fstore_1() {
        this.processImpl( fstore_1.instance() );
    }

    @Override
    public final void fstore_2() {
        this.processImpl( fstore_2.instance() );
    }

    @Override
    public final void fstore_3() {
        this.processImpl( fstore_3.instance() );
    }

    @Override
    public final void dstore( final Slot slot ) {
        this.processImpl( new dstore( slot ) );
    }

    @Override
    public final void dstore( final int slot ) {
        this.processImpl( new dstore( slot ) );
    }

    @Override
    public final void dstore_0() {
        this.processImpl( dstore_0.instance() );
    }

    @Override
    public final void dstore_1() {
        this.processImpl( dstore_1.instance() );
    }

    @Override
    public final void dstore_2() {
        this.processImpl( dstore_2.instance() );
    }

    @Override
    public final void dstore_3() {
        this.processImpl( dstore_3.instance() );
    }

    @Override
    public final void astore( final Slot slot ) {
        this.processImpl( new astore( slot ) );
    }

    @Override
    public final void astore( final int slot ) {
        this.processImpl( new astore( slot ) );
    }

    @Override
    public final void astore_0() {
        this.processImpl( astore_0.instance() );
    }

    @Override
    public final void astore_1() {
        this.processImpl( astore_1.instance() );
    }

    @Override
    public final void astore_2() {
        this.processImpl( astore_2.instance() );
    }

    @Override
    public final void astore_3() {
        this.processImpl( astore_3.instance() );
    }

    @Override
    public final void iastore() {
        this.processImpl( iastore.instance() );
    }

    @Override
    public final void lastore() {
        this.processImpl( lastore.instance() );
    }

    @Override
    public final void fastore() {
        this.processImpl( fastore.instance() );
    }

    @Override
    public final void dastore() {
        this.processImpl( dastore.instance() );
    }

    @Override
    public final void aastore() {
        this.processImpl( aastore.instance() );
    }

    @Override
    public final void bastore() {
        this.processImpl( bastore.instance() );
    }

    @Override
    public final void castore() {
        this.processImpl( castore.instance() );
    }

    @Override
    public final void sastore() {
        this.processImpl( sastore.instance() );
    }

    @Override
    public final void pop2() {
        this.processImpl( pop2.instance() );
    }

    @Override
    public final void dup_x1() {
        this.processImpl( dup_x1.instance() );
    }

    @Override
    public final void dup_x2() {
        this.processImpl( dup_x2.instance() );
    }

    @Override
    public final void dup2() {
        this.processImpl( dup2.instance() );
    }

    @Override
    public final void dup2_x1() {
        this.processImpl( dup2_x1.instance() );
    }

    @Override
    public final void dup2_x2() {
        this.processImpl( dup2_x2.instance() );
    }

    @Override
    public final void iadd() {
        this.processImpl( iadd.instance() );
    }

    @Override
    public final void ladd() {
        this.processImpl( ladd.instance() );
    }

    @Override
    public final void fadd() {
        this.processImpl( fadd.instance() );
    }

    @Override
    public final void dadd() {
        this.processImpl( dadd.instance() );
    }

    @Override
    public final void isub() {
        this.processImpl( isub.instance() );
    }

    @Override
    public final void lsub() {
        this.processImpl( lsub.instance() );
    }

    @Override
    public final void fsub() {
        this.processImpl( fsub.instance() );
    }

    @Override
    public final void dsub() {
        this.processImpl( dsub.instance() );
    }

    @Override
    public final void imul() {
        this.processImpl( imul.instance() );
    }

    @Override
    public final void lmul() {
        this.processImpl( lmul.instance() );
    }

    @Override
    public final void fmul() {
        this.processImpl( fmul.instance() );
    }

    @Override
    public final void dmul() {
        this.processImpl( dmul.instance() );
    }

    @Override
    public final void idiv() {
        this.processImpl( idiv.instance() );
    }

    @Override
    public final void ldiv() {
        this.processImpl( ldiv.instance() );
    }

    @Override
    public final void fdiv() {
        this.processImpl( fdiv.instance() );
    }

    @Override
    public final void ddiv() {
        this.processImpl( ddiv.instance() );
    }

    @Override
    public final void irem() {
        this.processImpl( irem.instance() );
    }

    @Override
    public final void lrem() {
        this.processImpl( lrem.instance() );
    }

    @Override
    public final void frem() {
        this.processImpl( frem.instance() );
    }

    @Override
    public final void drem() {
        this.processImpl( drem.instance() );
    }

    @Override
    public final void ineg() {
        this.processImpl( ineg.instance() );
    }

    @Override
    public final void lneg() {
        this.processImpl( lneg.instance() );
    }

    @Override
    public final void fneg() {
        this.processImpl( fneg.instance() );
    }

    @Override
    public final void dneg() {
        this.processImpl( dneg.instance() );
    }

    @Override
    public final void ishl() {
        this.processImpl( ishl.instance() );
    }

    @Override
    public final void lshl() {
        this.processImpl( lshl.instance() );
    }

    @Override
    public final void ishr() {
        this.processImpl( ishr.instance() );
    }

    @Override
    public final void lshr() {
        this.processImpl( lshr.instance() );
    }

    @Override
    public final void iushr() {
        this.processImpl( iushr.instance() );
    }

    @Override
    public final void lushr() {
        this.processImpl( lushr.instance() );
    }

    @Override
    public final void iand() {
        this.processImpl( iand.instance() );
    }

    @Override
    public final void land() {
        this.processImpl( land.instance() );
    }

    @Override
    public final void ior() {
        this.processImpl( ior.instance() );
    }

    @Override
    public final void lor() {
        this.processImpl( lor.instance() );
    }

    @Override
    public final void ixor() {
        this.processImpl( ixor.instance() );
    }

    @Override
    public final void lxor() {
        this.processImpl( lxor.instance() );
    }

    @Override
    public final void iinc( final int slot, final int delta ) {
        this.processImpl( new iinc( slot, delta ) );
    }

    @Override
    public final void i2l() {
        this.processImpl( i2l.instance() );
    }

    @Override
    public final void i2f() {
        this.processImpl( i2f.instance() );
    }

    @Override
    public final void i2d() {
        this.processImpl( i2d.instance() );
    }

    @Override
    public final void l2i() {
        this.processImpl( l2i.instance() );
    }

    @Override
    public final void l2f() {
        this.processImpl( l2f.instance() );
    }

    @Override
    public final void l2d() {
        this.processImpl( l2d.instance() );
    }

    @Override
    public final void f2i() {
        this.processImpl( f2i.instance() );
    }

    @Override
    public final void f2l() {
        this.processImpl( f2l.instance() );
    }

    @Override
    public final void f2d() {
        this.processImpl( f2d.instance() );
    }

    @Override
    public final void d2i() {
        this.processImpl( d2i.instance() );
    }

    @Override
    public final void d2l() {
        this.processImpl( d2l.instance() );
    }

    @Override
    public final void d2f() {
        this.processImpl( d2f.instance() );
    }

    @Override
    public final void i2b() {
        this.processImpl( i2b.instance() );
    }

    @Override
    public final void i2c() {
        this.processImpl( i2c.instance() );
    }

    @Override
    public final void i2s() {
        this.processImpl( i2s.instance() );
    }

    @Override
    public final void lcmp() {
        this.processImpl( lcmp.instance() );
    }

    @Override
    public final void fcmpl() {
        this.processImpl( fcmpl.instance() );
    }

    @Override
    public final void fcmpg() {
        this.processImpl( fcmpg.instance() );
    }

    @Override
    public final void dcmpl() {
        this.processImpl( dcmpl.instance() );
    }

    @Override
    public final void dcmpg() {
        this.processImpl( dcmpg.instance() );
    }

    @Override
    public final void ireturn() {
        this.processImpl( ireturn.instance() );
    }

    @Override
    public final void lreturn() {
        this.processImpl( lreturn.instance() );
    }

    @Override
    public final void freturn() {
        this.processImpl( freturn.instance() );
    }

    @Override
    public final void dreturn() {
        this.processImpl( dreturn.instance() );
    }

    @Override
    public final void areturn() {
        this.processImpl( areturn.instance() );
    }

    @Override
    public final void return_() {
        this.processImpl( return_.instance() );
    }

    @Override
    public final void getstatic( final Type type, final JavaField javaField ) {
        this.processImpl( new getstatic( type, javaField ) );
    }

    @Override
    public final void putstatic( final Type type, final JavaField javaField ) {
        this.processImpl( new putstatic( type, javaField ) );
    }

    @Override
    public final void getfield( final Type type, final JavaField javaField ) {
        this.processImpl( new getfield( type, javaField ) );
    }

    @Override
    public final void putfield( final Type type, final JavaField javaField ) {
        this.processImpl( new putfield( type, javaField ) );
    }

    @Override
    public final void invokevirtual( final Type type, final JavaMethodDescriptor methodDescriptor ) {
        this.processImpl( new invokevirtual( type, methodDescriptor ) );
    }

    @Override
    public final void invokeinterface( final Type type, final JavaMethodDescriptor methodDescriptor ) {
        this.processImpl( new invokeinterface( type, methodDescriptor ) );
    }

    @Override
    public final void invokestatic( final Type type, final JavaMethodDescriptor methodDescriptor ) {
        this.processImpl( new invokestatic( type, methodDescriptor ) );
    }

    @Override
    public final void invokespecial( final Type type, final JavaMethodDescriptor methodDescriptor ) {
        this.processImpl( new invokespecial( type, methodDescriptor ) );
    }

    @Override
    public final void new_( final Type type ) {
        this.processImpl( new new_( type ) );
    }

    @Override
    public final void newarray( final Type type ) {
        this.processImpl( new newarray( type ) );
    }

    @Override
    public final void anewarray( final Type type ) {
        this.processImpl( new anewarray( type ) );
    }

    @Override
    public final void arraylength() {
        this.processImpl( arraylength.instance() );
    }

    @Override
    public final void athrow() {
        this.processImpl( athrow.instance() );
    }

    @Override
    public final void checkcast( final Type type ) {
        this.processImpl( new checkcast( type ) );
    }

    @Override
    public final void instanceof_( final Type type ) {
        this.processImpl( new instanceof_( type ) );
    }

    @Override
    public final void monitorenter() {
        this.processImpl( monitorenter.instance() );
    }

    @Override
    public final void monitorexit() {
        this.processImpl( monitorexit.instance() );
    }

    @Override
    public final void multianewarray( final Type type, final int numDimensions ) {
        this.processImpl( new multianewarray( type, numDimensions ) );
    }

    @Override
    public final void ifnull( final Jump jump ) {
        this.processImpl( new ifnull( jump ) );
    }

    @Override
    public final void ifnonnull( final Jump jump ) {
        this.processImpl( new ifnonnull( jump ) );
    }

    @Override
    public final void ifeq( final Jump jump ) {
        this.processImpl( new ifeq( jump ) );
    }

    @Override
    public final void ifne( final Jump jump ) {
        this.processImpl( new ifne( jump ) );
    }

    @Override
    public final void iflt( final Jump jump ) {
        this.processImpl( new iflt( jump ) );
    }

    @Override
    public final void ifgt( final Jump jump ) {
        this.processImpl( new ifgt( jump ) );
    }

    @Override
    public final void ifge( final Jump jump ) {
        this.processImpl( new ifge( jump ) );
    }

    @Override
    public final void ifle( final Jump jump ) {
        this.processImpl( new ifle( jump ) );
    }

    @Override
    public final void if_icmpeq( final Jump jump ) {
        this.processImpl( new if_icmpeq( jump ) );
    }

    @Override
    public final void if_icmpne( final Jump jump ) {
        this.processImpl( new if_icmpne( jump ) );
    }

    @Override
    public final void if_icmplt( final Jump jump ) {
        this.processImpl( new if_icmplt( jump ) );
    }

    @Override
    public final void if_icmpgt( final Jump jump ) {
        this.processImpl( new if_icmpgt( jump ) );
    }

    @Override
    public final void if_icmpge( final Jump jump ) {
        this.processImpl( new if_icmpge( jump ) );
    }

    @Override
    public final void if_icmple( final Jump jump ) {
        this.processImpl( new if_icmple( jump ) );
    }

    @Override
    public final void if_acmpeq( final Jump jump ) {
        this.processImpl( new if_acmpeq( jump ) );
    }

    @Override
    public final void if_acmpne( final Jump jump ) {
        this.processImpl( new if_acmpne( jump ) );
    }

    @Override
    public final void goto_( final Jump jump ) {
        this.processImpl( new goto_( jump ) );
    }
    
    @Override
    public final void prepare() {
    }
    
    protected final void processImpl( final JvmOperation operation ) {
    	if ( this.pos != null ) {
    		operation.internals().initPos(this.pos);
    		this.pos = null;
    	}
    	this.process(operation);
    }
    
    protected abstract void process( final JvmOperation operation );
}
