package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public final class if_icmpgt extends IfComparisonOperation {
	public static final String ID = "if_icmpgt";
	public static final byte CODE = IF_ICMPGT;
	
	public static final if_icmpgt prototype() {
		return new if_icmpgt(new JumpPrototype());
	}
	
	public if_icmpgt(final Jump jump) {
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
		processor.if_icmpgt(this.jump());
	}
}