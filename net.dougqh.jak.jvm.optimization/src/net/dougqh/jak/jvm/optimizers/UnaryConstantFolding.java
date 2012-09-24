package net.dougqh.jak.jvm.optimizers;

import net.dougqh.jak.jvm.JvmOperationRewriter;
import net.dougqh.jak.jvm.JvmOperationRewritingFilter.RewriterState;
import net.dougqh.jak.jvm.operations.ConstantOperation;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.UnaryOperation;

public final class UnaryConstantFolding extends JvmOperationRewriter {
	@Override
	public final boolean backTrackOnMismatch() {
		return true;
	}
	
	@Override
	public final boolean match(
		final RewriterState rewriterState,
		final Class<? extends JvmOperation> opClass)
	{
		switch ( rewriterState.state() ) {
			case 0:
			return isConst(opClass);
			
			case 1:
			return isUnaryOp(opClass);
			
			default:
			throw new IllegalStateException();
		}
	}	

	@Override
	public final void process(
		final RewriterState rewriterState,
		final JvmOperation operation)
	{
		switch ( rewriterState.state() ) {
			case 0:
			rewriterState.buffer(operation);
			rewriterState.incState();
			break;
			
			case 1:
			ConstantOperation constOp = (ConstantOperation)rewriterState.debuffer();
			UnaryOperation unaryOp = (UnaryOperation)operation;
			constant( rewriterState.processor(), unaryOp.fold(constOp.value()) );
			
			rewriterState.resetState();
			break;
			
			default:
			throw new IllegalStateException();
		}
	}
}
