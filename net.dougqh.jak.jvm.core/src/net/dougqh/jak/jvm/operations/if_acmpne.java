package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;
import net.dougqh.jak.types.Reference;

public final class if_acmpne extends IfComparisonOperation {
	public static final String ID = "if_acmpne";
	public static final byte CODE = IF_ACMPNE;
	
	public static final if_acmpne prototype() {
		return new if_acmpne( new JumpPrototype() );
	}
	
	public if_acmpne( final Jump jump ) {
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
		processor.if_acmpne(this.jump());
	}
}