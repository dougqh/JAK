package net.dougqh.jak.jvm.rewriters;

import net.dougqh.jak.jvm.JvmOperationRewriter;
import net.dougqh.jak.jvm.JvmOperationRewritingFilter.RewriterState;
import net.dougqh.jak.jvm.operations.BinaryOperation;
import net.dougqh.jak.jvm.operations.ConstantOperation;
import net.dougqh.jak.jvm.operations.JvmOperation;

public final class BinaryConstantFolding extends JvmOperationRewriter {
	@Override
	public final boolean backTrackOnMismatch() {
		return true;
	}
	
	@Override
	public final boolean match(
		final RewriterState rewriterState,
		final Class<? extends JvmOperation> opClass )
	{
		switch ( rewriterState.state() ) {
			case 0:
			case 1:
			return isConst(opClass);
				
			case 2:
			return isBinaryOp(opClass);
			
			default:
			throw new IllegalStateException();
		}
	}
	
	public final void process(
		final RewriterState rewriterState,
		final JvmOperation operation )
	{
		switch ( rewriterState.state() ) {
			case 0:
			case 1:
			rewriterState.buffer(operation);
			rewriterState.incState();
			break;
			
			case 2:
			ConstantOperation lhsOp = (ConstantOperation)rewriterState.debuffer();
			ConstantOperation rhsOp = (ConstantOperation)rewriterState.debuffer();
			BinaryOperation binOp = (BinaryOperation)operation;
			constant( rewriterState.processor(), binOp.fold(lhsOp.value(), rhsOp.value()) );
			
			rewriterState.resetState();
			break;
			
			default:
			throw new IllegalStateException();
		}
	}
}
