package net.dougqh.jak.jvm.rewriters;

import net.dougqh.jak.jvm.JvmOperationRewriter;
import net.dougqh.jak.jvm.JvmOperationRewritingFilter.RewriterState;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.NormalizeableOperation;

public final class Normalizer extends JvmOperationRewriter {
	@Override
	public final boolean backTrackOnMismatch() {
		return false;
	}
	
	@Override
	public final boolean match(
		final RewriterState rewriterState,
		final Class<? extends JvmOperation> opClass)
	{
		return NormalizeableOperation.class.isAssignableFrom(opClass);
	}
	
	@Override
	public final void process(
		final RewriterState rewriterState,
		final JvmOperation operation )
	{
		NormalizeableOperation normalizeableOp = (NormalizeableOperation)operation;
		normalizeableOp.normalize(rewriterState.processor());
	}
}
