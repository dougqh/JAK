package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public final class if_icmpge extends IfComparisonOperation {
	public static final String ID = "if_icmpge";
	public static final byte CODE = IF_ICMPGE;
	
	public static final if_icmpge prototype() {
		return new if_icmpge( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public if_icmpge( final Jump jump ) {
		this.jump = jump;
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
		processor.if_icmpge( this.jump );
	}
}