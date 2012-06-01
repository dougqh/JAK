package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public final class if_icmple extends IfComparisonOperation {
	public static final String ID = "if_icmple";
	public static final byte CODE = IF_ICMPLE;
	
	public static final if_icmple prototype() {
		return new if_icmple( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public if_icmple( final Jump jump ) {
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
		processor.if_icmple( this.jump );
	}
}