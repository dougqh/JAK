package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public final class if_icmpne extends IfComparisonOperation {
	public static final String ID = "if_icmpne";
	public static final byte CODE = IF_ICMPNE;
	
	public static final if_icmpne prototype() {
		return new if_icmpne(new JumpPrototype());
	}
	
	public if_icmpne(final Jump jump) {
		super(jump);
	}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	
	@Override
	public final Type lhsType() {
		return int.class;
	}

	@Override
	public final Type rhsType() {
		return int.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.if_icmpne(this.jump());
	}
}