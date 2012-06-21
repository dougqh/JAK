package net.dougqh.jak.jvm.rewriters;

import java.util.List;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationRewriter;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.NormalizeableOperation;

public final class Normalizer extends JvmOperationRewriter {
	@Override
	public final boolean match(
		final int state,
		final Class<? extends JvmOperation> opClass )
	{
		return NormalizeableOperation.class.isAssignableFrom( opClass );
	}
	
	@Override
	public final int match( final int state, final JvmOperation jvmOperation ) {
		NormalizeableOperation normalizeableOp = (NormalizeableOperation)jvmOperation;
		return normalizeableOp.canNormalize() ? JvmOperationRewriter.FINAL : JvmOperationRewriter.INITIAL;
	}
	
	@Override
	public final void finish(
		final JvmOperationProcessor processor,
		final List<? extends JvmOperation> operations )
	{
		NormalizeableOperation normalizeableOp = (NormalizeableOperation)operations.get(0);
		normalizeableOp.normalize( processor );
	}
}
