package net.dougqh.jak.jvm;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.jvm.operations.*;

public abstract class JvmOperationFilter implements JvmOperationProcessor {
	private final HydratorImpl hydrator = new HydratorImpl();
	
    @Override
    public final void pop() {
        if ( this.shouldFilter( pop.class ) ) {
            this.hydrator.pop();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().pop();
        }
    }

    @Override
    public final void swap() {
        if ( this.shouldFilter( swap.class ) ) {
            this.hydrator.swap();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().swap();
        }
    }

    @Override
    public final void handleException( final ExceptionHandler exceptionhandler ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void dup() {
        if ( this.shouldFilter( dup.class ) ) {
            this.hydrator.dup();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dup();
        }
    }

    @Override
    public final void nop() {
        if ( this.shouldFilter( nop.class ) ) {
            this.hydrator.nop();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().nop();
        }
    }

    @Override
    public final void aconst_null() {
        if ( this.shouldFilter( aconst_null.class ) ) {
            this.hydrator.aconst_null();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().aconst_null();
        }
    }

    @Override
    public final void iconst_m1() {
        if ( this.shouldFilter( iconst_m1.class ) ) {
            this.hydrator.iconst_m1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iconst_m1();
        }
    }

    @Override
    public final void iconst_0() {
        if ( this.shouldFilter( iconst_0.class ) ) {
            this.hydrator.iconst_0();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iconst_0();
        }
    }

    @Override
    public final void iconst_1() {
        if ( this.shouldFilter( iconst_1.class ) ) {
            this.hydrator.iconst_1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iconst_1();
        }
    }

    @Override
    public final void iconst_2() {
        if ( this.shouldFilter( iconst_2.class ) ) {
            this.hydrator.iconst_2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iconst_2();
        }
    }

    @Override
    public final void iconst_3() {
        if ( this.shouldFilter( iconst_3.class ) ) {
            this.hydrator.iconst_3();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iconst_3();
        }
    }

    @Override
    public final void iconst_4() {
        if ( this.shouldFilter( iconst_4.class ) ) {
            this.hydrator.iconst_4();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iconst_4();
        }
    }

    @Override
    public final void iconst_5() {
        if ( this.shouldFilter( iconst_5.class ) ) {
            this.hydrator.iconst_5();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iconst_5();
        }
    }

    @Override
    public final void lconst_0() {
        if ( this.shouldFilter( lconst_0.class ) ) {
            this.hydrator.lconst_0();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lconst_0();
        }
    }

    @Override
    public final void lconst_1() {
        if ( this.shouldFilter( lconst_1.class ) ) {
            this.hydrator.lconst_1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lconst_1();
        }
    }

    @Override
    public final void fconst_0() {
        if ( this.shouldFilter( fconst_0.class ) ) {
            this.hydrator.fconst_0();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fconst_0();
        }
    }

    @Override
    public final void fconst_1() {
        if ( this.shouldFilter( fconst_1.class ) ) {
            this.hydrator.fconst_1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fconst_1();
        }
    }

    @Override
    public final void fconst_2() {
        if ( this.shouldFilter( fconst_2.class ) ) {
            this.hydrator.fconst_2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fconst_2();
        }
    }

    @Override
    public final void dconst_0() {
        if ( this.shouldFilter( dconst_0.class ) ) {
            this.hydrator.dconst_0();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dconst_0();
        }
    }

    @Override
    public final void dconst_1() {
        if ( this.shouldFilter( dconst_1.class ) ) {
            this.hydrator.dconst_1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dconst_1();
        }
    }

    @Override
    public final void bipush( final byte value ) {
        if ( this.shouldFilter( bipush.class ) ) {
            this.hydrator.bipush( value );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().bipush( value );
        }
    }

    @Override
    public final void sipush( final short value ) {
        if ( this.shouldFilter( sipush.class ) ) {
            this.hydrator.sipush( value );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().sipush( value );
        }
    }

    @Override
    public final void ldc( final Type type ) {
        if ( this.shouldFilter( ldc.class ) ) {
            this.hydrator.ldc( type );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ldc( type );
        }
    }

    @Override
    public final void ldc( final String string ) {
        if ( this.shouldFilter( ldc.class ) ) {
            this.hydrator.ldc( string );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ldc( string );
        }
    }

    @Override
    public final void ldc( final float value ) {
        if ( this.shouldFilter( ldc.class ) ) {
            this.hydrator.ldc( value );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ldc( value );
        }
    }

    @Override
    public final void ldc( final int value ) {
        if ( this.shouldFilter( ldc.class ) ) {
            this.hydrator.ldc( value );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ldc( value );
        }
    }

    @Override
    public final void ldc_w( final int value ) {
        if ( this.shouldFilter( ldc_w.class ) ) {
            this.hydrator.ldc_w( value );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ldc_w( value );
        }
    }

    @Override
    public final void ldc_w( final Type type ) {
        if ( this.shouldFilter( ldc_w.class ) ) {
            this.hydrator.ldc_w( type );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ldc_w( type );
        }
    }

    @Override
    public final void ldc_w( final float value ) {
        if ( this.shouldFilter( ldc_w.class ) ) {
            this.hydrator.ldc_w( value );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ldc_w( value );
        }
    }

    @Override
    public final void ldc_w( final String string ) {
        if ( this.shouldFilter( ldc_w.class ) ) {
            this.hydrator.ldc_w( string );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ldc_w( string );
        }
    }

    @Override
    public final void ldc2_w( final long value ) {
        if ( this.shouldFilter( ldc2_w.class ) ) {
            this.hydrator.ldc2_w( value );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ldc2_w( value );
        }
    }

    @Override
    public final void ldc2_w( final double value ) {
        if ( this.shouldFilter( ldc2_w.class ) ) {
            this.hydrator.ldc2_w( value );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ldc2_w( value );
        }
    }

    @Override
    public final void iload( final Slot slot ) {
        if ( this.shouldFilter( iload.class ) ) {
            this.hydrator.iload( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iload( slot );
        }
    }

    @Override
    public final void iload( final int slot ) {
        if ( this.shouldFilter( iload.class ) ) {
            this.hydrator.iload( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iload( slot );
        }
    }

    @Override
    public final void iload_0() {
        if ( this.shouldFilter( iload_0.class ) ) {
            this.hydrator.iload_0();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iload_0();
        }
    }

    @Override
    public final void iload_1() {
        if ( this.shouldFilter( iload_1.class ) ) {
            this.hydrator.iload_1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iload_1();
        }
    }

    @Override
    public final void iload_2() {
        if ( this.shouldFilter( iload_2.class ) ) {
            this.hydrator.iload_2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iload_2();
        }
    }

    @Override
    public final void iload_3() {
        if ( this.shouldFilter( iload_3.class ) ) {
            this.hydrator.iload_3();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iload_3();
        }
    }

    @Override
    public final void lload( final Slot slot ) {
        if ( this.shouldFilter( lload.class ) ) {
            this.hydrator.lload( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lload( slot );
        }
    }

    @Override
    public final void lload( final int slot ) {
        if ( this.shouldFilter( lload.class ) ) {
            this.hydrator.lload( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lload( slot );
        }
    }

    @Override
    public final void lload_0() {
        if ( this.shouldFilter( lload_0.class ) ) {
            this.hydrator.lload_0();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lload_0();
        }
    }

    @Override
    public final void lload_1() {
        if ( this.shouldFilter( lload_1.class ) ) {
            this.hydrator.lload_1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lload_1();
        }
    }

    @Override
    public final void lload_2() {
        if ( this.shouldFilter( lload_2.class ) ) {
            this.hydrator.lload_2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lload_2();
        }
    }

    @Override
    public final void lload_3() {
        if ( this.shouldFilter( lload_3.class ) ) {
            this.hydrator.lload_3();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lload_3();
        }
    }

    @Override
    public final void fload( final int slot ) {
        if ( this.shouldFilter( fload.class ) ) {
            this.hydrator.fload( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fload( slot );
        }
    }

    @Override
    public final void fload( final Slot slot ) {
        if ( this.shouldFilter( fload.class ) ) {
            this.hydrator.fload( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fload( slot );
        }
    }

    @Override
    public final void fload_0() {
        if ( this.shouldFilter( fload_0.class ) ) {
            this.hydrator.fload_0();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fload_0();
        }
    }

    @Override
    public final void fload_1() {
        if ( this.shouldFilter( fload_1.class ) ) {
            this.hydrator.fload_1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fload_1();
        }
    }

    @Override
    public final void fload_2() {
        if ( this.shouldFilter( fload_2.class ) ) {
            this.hydrator.fload_2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fload_2();
        }
    }

    @Override
    public final void fload_3() {
        if ( this.shouldFilter( fload_3.class ) ) {
            this.hydrator.fload_3();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fload_3();
        }
    }

    @Override
    public final void dload( final Slot slot ) {
        if ( this.shouldFilter( dload.class ) ) {
            this.hydrator.dload( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dload( slot );
        }
    }

    @Override
    public final void dload( final int slot ) {
        if ( this.shouldFilter( dload.class ) ) {
            this.hydrator.dload( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dload( slot );
        }
    }

    @Override
    public final void dload_0() {
        if ( this.shouldFilter( dload_0.class ) ) {
            this.hydrator.dload_0();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dload_0();
        }
    }

    @Override
    public final void dload_1() {
        if ( this.shouldFilter( dload_1.class ) ) {
            this.hydrator.dload_1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dload_1();
        }
    }

    @Override
    public final void dload_2() {
        if ( this.shouldFilter( dload_2.class ) ) {
            this.hydrator.dload_2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dload_2();
        }
    }

    @Override
    public final void dload_3() {
        if ( this.shouldFilter( dload_3.class ) ) {
            this.hydrator.dload_3();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dload_3();
        }
    }

    @Override
    public final void aload( final int slot ) {
        if ( this.shouldFilter( aload.class ) ) {
            this.hydrator.aload( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().aload( slot );
        }
    }

    @Override
    public final void aload( final Slot slot ) {
        if ( this.shouldFilter( aload.class ) ) {
            this.hydrator.aload( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().aload( slot );
        }
    }

    @Override
    public final void aload_0() {
        if ( this.shouldFilter( aload_0.class ) ) {
            this.hydrator.aload_0();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().aload_0();
        }
    }

    @Override
    public final void aload_1() {
        if ( this.shouldFilter( aload_1.class ) ) {
            this.hydrator.aload_1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().aload_1();
        }
    }

    @Override
    public final void aload_2() {
        if ( this.shouldFilter( aload_2.class ) ) {
            this.hydrator.aload_2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().aload_2();
        }
    }

    @Override
    public final void aload_3() {
        if ( this.shouldFilter( aload_3.class ) ) {
            this.hydrator.aload_3();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().aload_3();
        }
    }

    @Override
    public final void iaload() {
        if ( this.shouldFilter( iaload.class ) ) {
            this.hydrator.iaload();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iaload();
        }
    }

    @Override
    public final void laload() {
        if ( this.shouldFilter( laload.class ) ) {
            this.hydrator.laload();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().laload();
        }
    }

    @Override
    public final void faload() {
        if ( this.shouldFilter( faload.class ) ) {
            this.hydrator.faload();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().faload();
        }
    }

    @Override
    public final void daload() {
        if ( this.shouldFilter( daload.class ) ) {
            this.hydrator.daload();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().daload();
        }
    }

    @Override
    public final void aaload() {
        if ( this.shouldFilter( aaload.class ) ) {
            this.hydrator.aaload();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().aaload();
        }
    }

    @Override
    public final void baload() {
        if ( this.shouldFilter( baload.class ) ) {
            this.hydrator.baload();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().baload();
        }
    }

    @Override
    public final void caload() {
        if ( this.shouldFilter( caload.class ) ) {
            this.hydrator.caload();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().caload();
        }
    }

    @Override
    public final void saload() {
        if ( this.shouldFilter( saload.class ) ) {
            this.hydrator.saload();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().saload();
        }
    }

    @Override
    public final void istore( final Slot slot ) {
        if ( this.shouldFilter( istore.class ) ) {
            this.hydrator.istore( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().istore( slot );
        }
    }

    @Override
    public final void istore( final int slot ) {
        if ( this.shouldFilter( istore.class ) ) {
            this.hydrator.istore( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().istore( slot );
        }
    }

    @Override
    public final void istore_0() {
        if ( this.shouldFilter( istore_0.class ) ) {
            this.hydrator.istore_0();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().istore_0();
        }
    }

    @Override
    public final void istore_1() {
        if ( this.shouldFilter( istore_1.class ) ) {
            this.hydrator.istore_1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().istore_1();
        }
    }

    @Override
    public final void istore_2() {
        if ( this.shouldFilter( istore_2.class ) ) {
            this.hydrator.istore_2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().istore_2();
        }
    }

    @Override
    public final void istore_3() {
        if ( this.shouldFilter( istore_3.class ) ) {
            this.hydrator.istore_3();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().istore_3();
        }
    }

    @Override
    public final void lstore( final Slot slot ) {
        if ( this.shouldFilter( lstore.class ) ) {
            this.hydrator.lstore( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lstore( slot );
        }
    }

    @Override
    public final void lstore( final int slot ) {
        if ( this.shouldFilter( lstore.class ) ) {
            this.hydrator.lstore( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lstore( slot );
        }
    }

    @Override
    public final void lstore_0() {
        if ( this.shouldFilter( lstore_0.class ) ) {
            this.hydrator.lstore_0();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lstore_0();
        }
    }

    @Override
    public final void lstore_1() {
        if ( this.shouldFilter( lstore_1.class ) ) {
            this.hydrator.lstore_1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lstore_1();
        }
    }

    @Override
    public final void lstore_2() {
        if ( this.shouldFilter( lstore_2.class ) ) {
            this.hydrator.lstore_2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lstore_2();
        }
    }

    @Override
    public final void lstore_3() {
        if ( this.shouldFilter( lstore_3.class ) ) {
            this.hydrator.lstore_3();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lstore_3();
        }
    }

    @Override
    public final void fstore( final int slot ) {
        if ( this.shouldFilter( fstore.class ) ) {
            this.hydrator.fstore( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fstore( slot );
        }
    }

    @Override
    public final void fstore( final Slot slot ) {
        if ( this.shouldFilter( fstore.class ) ) {
            this.hydrator.fstore( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fstore( slot );
        }
    }

    @Override
    public final void fstore_0() {
        if ( this.shouldFilter( fstore_0.class ) ) {
            this.hydrator.fstore_0();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fstore_0();
        }
    }

    @Override
    public final void fstore_1() {
        if ( this.shouldFilter( fstore_1.class ) ) {
            this.hydrator.fstore_1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fstore_1();
        }
    }

    @Override
    public final void fstore_2() {
        if ( this.shouldFilter( fstore_2.class ) ) {
            this.hydrator.fstore_2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fstore_2();
        }
    }

    @Override
    public final void fstore_3() {
        if ( this.shouldFilter( fstore_3.class ) ) {
            this.hydrator.fstore_3();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fstore_3();
        }
    }

    @Override
    public final void dstore( final Slot slot ) {
        if ( this.shouldFilter( dstore.class ) ) {
            this.hydrator.dstore( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dstore( slot );
        }
    }

    @Override
    public final void dstore( final int slot ) {
        if ( this.shouldFilter( dstore.class ) ) {
            this.hydrator.dstore( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dstore( slot );
        }
    }

    @Override
    public final void dstore_0() {
        if ( this.shouldFilter( dstore_0.class ) ) {
            this.hydrator.dstore_0();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dstore_0();
        }
    }

    @Override
    public final void dstore_1() {
        if ( this.shouldFilter( dstore_1.class ) ) {
            this.hydrator.dstore_1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dstore_1();
        }
    }

    @Override
    public final void dstore_2() {
        if ( this.shouldFilter( dstore_2.class ) ) {
            this.hydrator.dstore_2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dstore_2();
        }
    }

    @Override
    public final void dstore_3() {
        if ( this.shouldFilter( dstore_3.class ) ) {
            this.hydrator.dstore_3();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dstore_3();
        }
    }

    @Override
    public final void astore( final Slot slot ) {
        if ( this.shouldFilter( astore.class ) ) {
            this.hydrator.astore( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().astore( slot );
        }
    }

    @Override
    public final void astore( final int slot ) {
        if ( this.shouldFilter( astore.class ) ) {
            this.hydrator.astore( slot );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().astore( slot );
        }
    }

    @Override
    public final void astore_0() {
        if ( this.shouldFilter( astore_0.class ) ) {
            this.hydrator.astore_0();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().astore_0();
        }
    }

    @Override
    public final void astore_1() {
        if ( this.shouldFilter( astore_1.class ) ) {
            this.hydrator.astore_1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().astore_1();
        }
    }

    @Override
    public final void astore_2() {
        if ( this.shouldFilter( astore_2.class ) ) {
            this.hydrator.astore_2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().astore_2();
        }
    }

    @Override
    public final void astore_3() {
        if ( this.shouldFilter( astore_3.class ) ) {
            this.hydrator.astore_3();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().astore_3();
        }
    }

    @Override
    public final void iastore() {
        if ( this.shouldFilter( iastore.class ) ) {
            this.hydrator.iastore();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iastore();
        }
    }

    @Override
    public final void lastore() {
        if ( this.shouldFilter( lastore.class ) ) {
            this.hydrator.lastore();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lastore();
        }
    }

    @Override
    public final void fastore() {
        if ( this.shouldFilter( fastore.class ) ) {
            this.hydrator.fastore();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fastore();
        }
    }

    @Override
    public final void dastore() {
        if ( this.shouldFilter( dastore.class ) ) {
            this.hydrator.dastore();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dastore();
        }
    }

    @Override
    public final void aastore() {
        if ( this.shouldFilter( aastore.class ) ) {
            this.hydrator.aastore();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().aastore();
        }
    }

    @Override
    public final void bastore() {
        if ( this.shouldFilter( bastore.class ) ) {
            this.hydrator.bastore();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().bastore();
        }
    }

    @Override
    public final void castore() {
        if ( this.shouldFilter( castore.class ) ) {
            this.hydrator.castore();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().castore();
        }
    }

    @Override
    public final void sastore() {
        if ( this.shouldFilter( sastore.class ) ) {
            this.hydrator.sastore();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().sastore();
        }
    }

    @Override
    public final void pop2() {
        if ( this.shouldFilter( pop2.class ) ) {
            this.hydrator.pop2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().pop2();
        }
    }

    @Override
    public final void dup_x1() {
        if ( this.shouldFilter( dup_x1.class ) ) {
            this.hydrator.dup_x1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dup_x1();
        }
    }

    @Override
    public final void dup_x2() {
        if ( this.shouldFilter( dup_x2.class ) ) {
            this.hydrator.dup_x2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dup_x2();
        }
    }

    @Override
    public final void dup2() {
        if ( this.shouldFilter( dup2.class ) ) {
            this.hydrator.dup2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dup2();
        }
    }

    @Override
    public final void dup2_x1() {
        if ( this.shouldFilter( dup2_x1.class ) ) {
            this.hydrator.dup2_x1();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dup2_x1();
        }
    }

    @Override
    public final void dup2_x2() {
        if ( this.shouldFilter( dup2_x2.class ) ) {
            this.hydrator.dup2_x2();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dup2_x2();
        }
    }

    @Override
    public final void iadd() {
        if ( this.shouldFilter( iadd.class ) ) {
            this.hydrator.iadd();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iadd();
        }
    }

    @Override
    public final void ladd() {
        if ( this.shouldFilter( ladd.class ) ) {
            this.hydrator.ladd();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ladd();
        }
    }

    @Override
    public final void fadd() {
        if ( this.shouldFilter( fadd.class ) ) {
            this.hydrator.fadd();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fadd();
        }
    }

    @Override
    public final void dadd() {
        if ( this.shouldFilter( dadd.class ) ) {
            this.hydrator.dadd();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dadd();
        }
    }

    @Override
    public final void isub() {
        if ( this.shouldFilter( isub.class ) ) {
            this.hydrator.isub();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().isub();
        }
    }

    @Override
    public final void lsub() {
        if ( this.shouldFilter( lsub.class ) ) {
            this.hydrator.lsub();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lsub();
        }
    }

    @Override
    public final void fsub() {
        if ( this.shouldFilter( fsub.class ) ) {
            this.hydrator.fsub();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fsub();
        }
    }

    @Override
    public final void dsub() {
        if ( this.shouldFilter( dsub.class ) ) {
            this.hydrator.dsub();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dsub();
        }
    }

    @Override
    public final void imul() {
        if ( this.shouldFilter( imul.class ) ) {
            this.hydrator.imul();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().imul();
        }
    }

    @Override
    public final void lmul() {
        if ( this.shouldFilter( lmul.class ) ) {
            this.hydrator.lmul();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lmul();
        }
    }

    @Override
    public final void fmul() {
        if ( this.shouldFilter( fmul.class ) ) {
            this.hydrator.fmul();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fmul();
        }
    }

    @Override
    public final void dmul() {
        if ( this.shouldFilter( dmul.class ) ) {
            this.hydrator.dmul();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dmul();
        }
    }

    @Override
    public final void idiv() {
        if ( this.shouldFilter( idiv.class ) ) {
            this.hydrator.idiv();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().idiv();
        }
    }

    @Override
    public final void ldiv() {
        if ( this.shouldFilter( ldiv.class ) ) {
            this.hydrator.ldiv();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ldiv();
        }
    }

    @Override
    public final void fdiv() {
        if ( this.shouldFilter( fdiv.class ) ) {
            this.hydrator.fdiv();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fdiv();
        }
    }

    @Override
    public final void ddiv() {
        if ( this.shouldFilter( ddiv.class ) ) {
            this.hydrator.ddiv();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ddiv();
        }
    }

    @Override
    public final void irem() {
        if ( this.shouldFilter( irem.class ) ) {
            this.hydrator.irem();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().irem();
        }
    }

    @Override
    public final void lrem() {
        if ( this.shouldFilter( lrem.class ) ) {
            this.hydrator.lrem();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lrem();
        }
    }

    @Override
    public final void frem() {
        if ( this.shouldFilter( frem.class ) ) {
            this.hydrator.frem();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().frem();
        }
    }

    @Override
    public final void drem() {
        if ( this.shouldFilter( drem.class ) ) {
            this.hydrator.drem();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().drem();
        }
    }

    @Override
    public final void ineg() {
        if ( this.shouldFilter( ineg.class ) ) {
            this.hydrator.ineg();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ineg();
        }
    }

    @Override
    public final void lneg() {
        if ( this.shouldFilter( lneg.class ) ) {
            this.hydrator.lneg();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lneg();
        }
    }

    @Override
    public final void fneg() {
        if ( this.shouldFilter( fneg.class ) ) {
            this.hydrator.fneg();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fneg();
        }
    }

    @Override
    public final void dneg() {
        if ( this.shouldFilter( dneg.class ) ) {
            this.hydrator.dneg();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dneg();
        }
    }

    @Override
    public final void ishl() {
        if ( this.shouldFilter( ishl.class ) ) {
            this.hydrator.ishl();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ishl();
        }
    }

    @Override
    public final void lshl() {
        if ( this.shouldFilter( lshl.class ) ) {
            this.hydrator.lshl();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lshl();
        }
    }

    @Override
    public final void ishr() {
        if ( this.shouldFilter( ishr.class ) ) {
            this.hydrator.ishr();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ishr();
        }
    }

    @Override
    public final void lshr() {
        if ( this.shouldFilter( lshr.class ) ) {
            this.hydrator.lshr();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lshr();
        }
    }

    @Override
    public final void iushr() {
        if ( this.shouldFilter( iushr.class ) ) {
            this.hydrator.iushr();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iushr();
        }
    }

    @Override
    public final void lushr() {
        if ( this.shouldFilter( lushr.class ) ) {
            this.hydrator.lushr();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lushr();
        }
    }

    @Override
    public final void iand() {
        if ( this.shouldFilter( iand.class ) ) {
            this.hydrator.iand();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iand();
        }
    }

    @Override
    public final void land() {
        if ( this.shouldFilter( land.class ) ) {
            this.hydrator.land();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().land();
        }
    }

    @Override
    public final void ior() {
        if ( this.shouldFilter( ior.class ) ) {
            this.hydrator.ior();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ior();
        }
    }

    @Override
    public final void lor() {
        if ( this.shouldFilter( lor.class ) ) {
            this.hydrator.lor();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lor();
        }
    }

    @Override
    public final void ixor() {
        if ( this.shouldFilter( ixor.class ) ) {
            this.hydrator.ixor();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ixor();
        }
    }

    @Override
    public final void lxor() {
        if ( this.shouldFilter( lxor.class ) ) {
            this.hydrator.lxor();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lxor();
        }
    }

    @Override
    public final void iinc( final int slot, final int delta ) {
        if ( this.shouldFilter( iinc.class ) ) {
            this.hydrator.iinc( slot, delta );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iinc( slot, delta );
        }
    }

    @Override
    public final void i2l() {
        if ( this.shouldFilter( i2l.class ) ) {
            this.hydrator.i2l();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().i2l();
        }
    }

    @Override
    public final void i2f() {
        if ( this.shouldFilter( i2f.class ) ) {
            this.hydrator.i2f();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().i2f();
        }
    }

    @Override
    public final void i2d() {
        if ( this.shouldFilter( i2d.class ) ) {
            this.hydrator.i2d();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().i2d();
        }
    }

    @Override
    public final void l2i() {
        if ( this.shouldFilter( l2i.class ) ) {
            this.hydrator.l2i();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().l2i();
        }
    }

    @Override
    public final void l2f() {
        if ( this.shouldFilter( l2f.class ) ) {
            this.hydrator.l2f();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().l2f();
        }
    }

    @Override
    public final void l2d() {
        if ( this.shouldFilter( l2d.class ) ) {
            this.hydrator.l2d();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().l2d();
        }
    }

    @Override
    public final void f2i() {
        if ( this.shouldFilter( f2i.class ) ) {
            this.hydrator.f2i();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().f2i();
        }
    }

    @Override
    public final void f2l() {
        if ( this.shouldFilter( f2l.class ) ) {
            this.hydrator.f2l();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().f2l();
        }
    }

    @Override
    public final void f2d() {
        if ( this.shouldFilter( f2d.class ) ) {
            this.hydrator.f2d();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().f2d();
        }
    }

    @Override
    public final void d2i() {
        if ( this.shouldFilter( d2i.class ) ) {
            this.hydrator.d2i();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().d2i();
        }
    }

    @Override
    public final void d2l() {
        if ( this.shouldFilter( d2l.class ) ) {
            this.hydrator.d2l();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().d2l();
        }
    }

    @Override
    public final void d2f() {
        if ( this.shouldFilter( d2f.class ) ) {
            this.hydrator.d2f();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().d2f();
        }
    }

    @Override
    public final void i2b() {
        if ( this.shouldFilter( i2b.class ) ) {
            this.hydrator.i2b();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().i2b();
        }
    }

    @Override
    public final void i2c() {
        if ( this.shouldFilter( i2c.class ) ) {
            this.hydrator.i2c();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().i2c();
        }
    }

    @Override
    public final void i2s() {
        if ( this.shouldFilter( i2s.class ) ) {
            this.hydrator.i2s();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().i2s();
        }
    }

    @Override
    public final void lcmp() {
        if ( this.shouldFilter( lcmp.class ) ) {
            this.hydrator.lcmp();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lcmp();
        }
    }

    @Override
    public final void fcmpl() {
        if ( this.shouldFilter( fcmpl.class ) ) {
            this.hydrator.fcmpl();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fcmpl();
        }
    }

    @Override
    public final void fcmpg() {
        if ( this.shouldFilter( fcmpg.class ) ) {
            this.hydrator.fcmpg();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().fcmpg();
        }
    }

    @Override
    public final void dcmpl() {
        if ( this.shouldFilter( dcmpl.class ) ) {
            this.hydrator.dcmpl();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dcmpl();
        }
    }

    @Override
    public final void dcmpg() {
        if ( this.shouldFilter( dcmpg.class ) ) {
            this.hydrator.dcmpg();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dcmpg();
        }
    }

    @Override
    public final void ireturn() {
        if ( this.shouldFilter( ireturn.class ) ) {
            this.hydrator.ireturn();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ireturn();
        }
    }

    @Override
    public final void lreturn() {
        if ( this.shouldFilter( lreturn.class ) ) {
            this.hydrator.lreturn();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().lreturn();
        }
    }

    @Override
    public final void freturn() {
        if ( this.shouldFilter( freturn.class ) ) {
            this.hydrator.freturn();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().freturn();
        }
    }

    @Override
    public final void dreturn() {
        if ( this.shouldFilter( dreturn.class ) ) {
            this.hydrator.dreturn();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().dreturn();
        }
    }

    @Override
    public final void areturn() {
        if ( this.shouldFilter( areturn.class ) ) {
            this.hydrator.areturn();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().areturn();
        }
    }

    @Override
    public final void return_() {
        if ( this.shouldFilter( return_.class ) ) {
            this.hydrator.return_();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().return_();
        }
    }

    @Override
    public final void getstatic( final Type type, final JavaField javafield ) {
        if ( this.shouldFilter( getstatic.class ) ) {
            this.hydrator.getstatic( type, javafield );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().getstatic( type, javafield );
        }
    }

    @Override
    public final void putstatic( final Type type, final JavaField javafield ) {
        if ( this.shouldFilter( putstatic.class ) ) {
            this.hydrator.putstatic( type, javafield );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().putstatic( type, javafield );
        }
    }

    @Override
    public final void getfield( final Type type, final JavaField javafield ) {
        if ( this.shouldFilter( getfield.class ) ) {
            this.hydrator.getfield( type, javafield );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().getfield( type, javafield );
        }
    }

    @Override
    public final void putfield( final Type type, final JavaField javafield ) {
        if ( this.shouldFilter( putfield.class ) ) {
            this.hydrator.putfield( type, javafield );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().putfield( type, javafield );
        }
    }

    @Override
    public final void invokevirtual( final Type type, final JavaMethodDescriptor javamethoddescriptor ) {
        if ( this.shouldFilter( invokevirtual.class ) ) {
            this.hydrator.invokevirtual( type, javamethoddescriptor );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().invokevirtual( type, javamethoddescriptor );
        }
    }

    @Override
    public final void invokeinterface( final Type type, final JavaMethodDescriptor javamethoddescriptor ) {
        if ( this.shouldFilter( invokeinterface.class ) ) {
            this.hydrator.invokeinterface( type, javamethoddescriptor );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().invokeinterface( type, javamethoddescriptor );
        }
    }

    @Override
    public final void invokestatic( final Type type, final JavaMethodDescriptor javamethoddescriptor ) {
        if ( this.shouldFilter( invokestatic.class ) ) {
            this.hydrator.invokestatic( type, javamethoddescriptor );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().invokestatic( type, javamethoddescriptor );
        }
    }

    @Override
    public final void invokespecial( final Type type, final JavaMethodDescriptor javamethoddescriptor ) {
        if ( this.shouldFilter( invokespecial.class ) ) {
            this.hydrator.invokespecial( type, javamethoddescriptor );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().invokespecial( type, javamethoddescriptor );
        }
    }

    @Override
    public final void new_( final Type type ) {
        if ( this.shouldFilter( new_.class ) ) {
            this.hydrator.new_( type );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().new_( type );
        }
    }

    @Override
    public final void newarray( final Type type ) {
        if ( this.shouldFilter( newarray.class ) ) {
            this.hydrator.newarray( type );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().newarray( type );
        }
    }

    @Override
    public final void anewarray( final Type type ) {
        if ( this.shouldFilter( anewarray.class ) ) {
            this.hydrator.anewarray( type );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().anewarray( type );
        }
    }

    @Override
    public final void arraylength() {
        if ( this.shouldFilter( arraylength.class ) ) {
            this.hydrator.arraylength();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().arraylength();
        }
    }

    @Override
    public final void athrow() {
        if ( this.shouldFilter( athrow.class ) ) {
            this.hydrator.athrow();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().athrow();
        }
    }

    @Override
    public final void checkcast( final Type type ) {
        if ( this.shouldFilter( checkcast.class ) ) {
            this.hydrator.checkcast( type );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().checkcast( type );
        }
    }

    @Override
    public final void instanceof_( final Type type ) {
        if ( this.shouldFilter( instanceof_.class ) ) {
            this.hydrator.instanceof_( type );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().instanceof_( type );
        }
    }

    @Override
    public final void monitorenter() {
        if ( this.shouldFilter( monitorenter.class ) ) {
            this.hydrator.monitorenter();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().monitorenter();
        }
    }

    @Override
    public final void monitorexit() {
        if ( this.shouldFilter( monitorexit.class ) ) {
            this.hydrator.monitorexit();
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().monitorexit();
        }
    }

    @Override
    public final void multianewarray( final Type type, final int numDimensions ) {
        if ( this.shouldFilter( multianewarray.class ) ) {
            this.hydrator.multianewarray( type, numDimensions );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().multianewarray( type, numDimensions );
        }
    }

    @Override
    public final void ifnull( final Jump jump ) {
        if ( this.shouldFilter( ifnull.class ) ) {
            this.hydrator.ifnull( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ifnull( jump );
        }
    }

    @Override
    public final void ifnonnull( final Jump jump ) {
        if ( this.shouldFilter( ifnonnull.class ) ) {
            this.hydrator.ifnonnull( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ifnonnull( jump );
        }
    }

    @Override
    public final void ifeq( final Jump jump ) {
        if ( this.shouldFilter( ifeq.class ) ) {
            this.hydrator.ifeq( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ifeq( jump );
        }
    }

    @Override
    public final void ifne( final Jump jump ) {
        if ( this.shouldFilter( ifne.class ) ) {
            this.hydrator.ifne( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ifne( jump );
        }
    }

    @Override
    public final void iflt( final Jump jump ) {
        if ( this.shouldFilter( iflt.class ) ) {
            this.hydrator.iflt( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().iflt( jump );
        }
    }

    @Override
    public final void ifgt( final Jump jump ) {
        if ( this.shouldFilter( ifgt.class ) ) {
            this.hydrator.ifgt( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ifgt( jump );
        }
    }

    @Override
    public final void ifge( final Jump jump ) {
        if ( this.shouldFilter( ifge.class ) ) {
            this.hydrator.ifge( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ifge( jump );
        }
    }

    @Override
    public final void ifle( final Jump jump ) {
        if ( this.shouldFilter( ifle.class ) ) {
            this.hydrator.ifle( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().ifle( jump );
        }
    }

    @Override
    public final void if_icmpeq( final Jump jump ) {
        if ( this.shouldFilter( if_icmpeq.class ) ) {
            this.hydrator.if_icmpeq( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().if_icmpeq( jump );
        }
    }

    @Override
    public final void if_icmpne( final Jump jump ) {
        if ( this.shouldFilter( if_icmpne.class ) ) {
            this.hydrator.if_icmpne( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().if_icmpne( jump );
        }
    }

    @Override
    public final void if_icmplt( final Jump jump ) {
        if ( this.shouldFilter( if_icmplt.class ) ) {
            this.hydrator.if_icmplt( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().if_icmplt( jump );
        }
    }

    @Override
    public final void if_icmpgt( final Jump jump ) {
        if ( this.shouldFilter( if_icmpgt.class ) ) {
            this.hydrator.if_icmpgt( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().if_icmpgt( jump );
        }
    }

    @Override
    public final void if_icmpge( final Jump jump ) {
        if ( this.shouldFilter( if_icmpge.class ) ) {
            this.hydrator.if_icmpge( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().if_icmpge( jump );
        }
    }

    @Override
    public final void if_icmple( final Jump jump ) {
        if ( this.shouldFilter( if_icmple.class ) ) {
            this.hydrator.if_icmple( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().if_icmple( jump );
        }
    }

    @Override
    public final void if_acmpeq( final Jump jump ) {
        if ( this.shouldFilter( if_acmpeq.class ) ) {
            this.hydrator.if_acmpeq( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().if_acmpeq( jump );
        }
    }

    @Override
    public final void if_acmpne( final Jump jump ) {
        if ( this.shouldFilter( if_acmpne.class ) ) {
            this.hydrator.if_acmpne( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().if_acmpne( jump );
        }
    }

    @Override
    public final void goto_( final Jump jump ) {
        if ( this.shouldFilter( goto_.class ) ) {
            this.hydrator.goto_( jump );
            this.filter( this.hydrator.get() );
        } else {
            this.wrapped().goto_( jump );
        }
    }
    
    protected abstract JvmOperationProcessor wrapped();
    
    protected abstract boolean shouldFilter( final Class<? extends JvmOperation> operationClass );
    
    protected abstract void filter( final JvmOperation operation );
    
    private static final class HydratorImpl extends JvmOperationHydrator {
    	private JvmOperation operation = null;
    	
    	@Override
    	protected final void add( final JvmOperation operation ) {
    		if ( this.operation != null ) {
    			throw new IllegalStateException();
    		}
    		this.operation = operation;
    	}
    	
    	protected final JvmOperation get() {
    		if ( this.operation == null ) {
    			throw new IllegalStateException();
    		}
    		JvmOperation operation = this.operation;
    		this.operation = null;
    		return operation;
    	}
    }
}

