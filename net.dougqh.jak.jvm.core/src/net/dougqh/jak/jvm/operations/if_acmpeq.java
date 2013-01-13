package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;
import net.dougqh.jak.types.Reference;

public final class if_acmpeq extends IfComparisonOperation {
	public static final String ID = "if_acmpeq";
	public static final byte CODE = IF_ACMPEQ;
	
	public static final if_acmpeq prototype() {
		return new if_acmpeq( new JumpPrototype() );
	}
	
	public if_acmpeq( final Jump jump ) {
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
		return Reference.class;
	}

	@Override
	public final Type rhsType() {
		return Reference.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.if_acmpeq(this.jump());
	}
}