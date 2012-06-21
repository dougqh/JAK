package net.dougqh.jak.jvm;

import java.lang.reflect.Type;
import java.util.List;

import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.ldc;
import net.dougqh.jak.jvm.operations.ldc2_w;

public abstract class JvmOperationRewriter {
	public static final int INITIAL = 0;
	public static final int FINAL = Integer.MAX_VALUE;
	
	public abstract boolean match(
		final int state,
		final Class<? extends JvmOperation> opClass );
	
	public abstract int match(
		final int state,
		final JvmOperation jvmOperation );
	
	public abstract void finish(
		final JvmOperationProcessor processor,
		final List<? extends JvmOperation> operations );
	
	protected final void constant(
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
