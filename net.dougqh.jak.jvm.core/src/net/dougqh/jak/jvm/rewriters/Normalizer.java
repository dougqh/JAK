package net.dougqh.jak.jvm.rewriters;

import java.util.List;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationRewriter;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.NormalizeableOperation;

public final class Normalizer extends JvmOperationRewriter {
	@Override
	public final void reset() {}
	
	@Override
	public final State match( final Class<? extends JvmOperation> opClass ) {
		return NormalizeableOperation.class.isAssignableFrom( opClass ) ?
			State.MATCH:
			State.MISMATCH;
	}
	
	@Override
	public final State match( final JvmOperation op ) {
		NormalizeableOperation normalizeableOp = (NormalizeableOperation)op;
		return normalizeableOp.canNormalize() ? State.FINISH : State.MISMATCH;
	}
	
	@Override
	public void next() {
		throw new IllegalStateException();
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
