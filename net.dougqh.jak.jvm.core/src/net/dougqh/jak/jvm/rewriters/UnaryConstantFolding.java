package net.dougqh.jak.jvm.rewriters;

import java.util.List;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationRewriter;
import net.dougqh.jak.jvm.operations.ConstantOperation;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.UnaryOperation;

public final class UnaryConstantFolding extends JvmOperationRewriter {
	@Override
	public final boolean match(
		final int state,
		final Class<? extends JvmOperation> opClass)
	{
		switch ( state ) {
			case 0:
			return opClass.equals(UnaryConstantFolding.class);
			
			case 1:
			return opClass.equals(ConstantOperation.class);
			
			default:
			throw new IllegalStateException();
		}
	}
	
	@Override
	public final int match(
		final int state,
		final JvmOperation jvmOperation)
	{
		switch ( state ) {
			case 0:
			return 1;
			
			case 1:
			return FINAL;
			
			default:
			throw new IllegalStateException();
		}
	}
	
	@Override
	public final void finish(
		final JvmOperationProcessor processor,
		final List<? extends JvmOperation> operations)
	{
		UnaryOperation unaryOp = (UnaryOperation)operations.get(0);
		ConstantOperation constOp = (ConstantOperation)operations.get(1);
		
		constant( processor, unaryOp.fold(constOp.value()) );
	}
}
