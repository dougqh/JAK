package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fsub extends BinaryOperation {
	public static final String ID = "fsub";
	public static final byte CODE = FSUB;
	
	public static final fsub instance() {
		return new fsub();
	}
	
	private fsub() {}
	
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
		return SUB;
	}
	
	@Override
	public final Type lhsType() {
		return float.class;
	}
	
	@Override
	public final Type rhsType() {
		return float.class;
	}
	
	@Override
	public final Type resultType() {
		return float.class;
	}
	
	@Override
	public final <T> T fold( final Object lhs, final Object rhs ) {
		return BinaryOperation.<T>as( (Float)lhs - (Float)rhs );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.fsub();
	}
}