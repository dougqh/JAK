package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class ishl extends BinaryOperation {
	public static final String ID = "ishl";
	public static final byte CODE = ISHL;
	
	public static final ishl instance() {
		return new ishl();
	}
	
	private ishl() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final String getOperator() {
		return LEFT_SHIFT;
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
	public final Type resultType() {
		return int.class;
	}
	
	@Override
	public final <T> T fold( final Object lhs, final Object rhs ) {
		return BinaryOperation.<T>as( (Integer)lhs << (Integer)rhs );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.ishl();
	}
}