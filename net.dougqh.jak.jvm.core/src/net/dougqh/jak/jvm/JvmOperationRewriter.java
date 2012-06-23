package net.dougqh.jak.jvm;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationRewritingFilter.RewriterState;
import net.dougqh.jak.jvm.operations.BinaryOperation;
import net.dougqh.jak.jvm.operations.ConstantOperation;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.UnaryOperation;
import net.dougqh.jak.jvm.operations.ldc;
import net.dougqh.jak.jvm.operations.ldc2_w;

public abstract class JvmOperationRewriter {
	public abstract boolean backTrackOnMismatch();
	
	public abstract boolean match(
		final RewriterState rewriterState,
		final Class<? extends JvmOperation> opClass );
	
	public boolean match(
		final RewriterState rewriterState,
		final JvmOperation operation )
	{
		return true;
	}
	
	public abstract void process(
		final RewriterState rewriterState,
		final JvmOperation operation );
	
	protected static final boolean isConst( final Class<? extends JvmOperation> opClass ) {
		return ConstantOperation.class.isAssignableFrom(opClass);
	}
	
	protected static final boolean isUnaryOp( final Class<? extends JvmOperation> opClass ) {
		return UnaryOperation.class.isAssignableFrom(opClass);
	}
	
	protected static final boolean isBinaryOp( final Class<? extends JvmOperation> opClass ) {
		return BinaryOperation.class.isAssignableFrom(opClass);
	}
	
	protected static final void constant(
		final JvmOperationProcessor processor,
		final Object value)
	{
		if ( value instanceof Integer ) {
			ldc.normalize( processor, (Integer)value );
		} else if ( value instanceof Float ) {
			ldc.normalize( processor, (Float)value );
		} else if ( value instanceof Long ) {
			ldc2_w.normalize( processor, (Long)value );
		} else if ( value instanceof Double ) {
			ldc2_w.normalize( processor, (Double)value );
		} else if ( value instanceof String ) {
			processor.ldc( (String)value );
		} else if ( value instanceof Type ) {
			processor.ldc( (Type)value );
		} else {
			throw new IllegalStateException();
		}
	}
}
