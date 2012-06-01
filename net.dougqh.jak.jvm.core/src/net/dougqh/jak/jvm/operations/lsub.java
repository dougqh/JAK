package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lsub extends BinaryOperation {
	public static final String ID = "lsub";
	public static final byte CODE = LSUB;
	
	public static final lsub instance() {
		return new lsub();
	}
	
	private lsub() {}
	
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
		return long.class;
	}
	
	@Override
	public final Type rhsType() {
		return long.class;
	}
	
	@Override
	public final Type resultType() {
		return long.class;
	}
	
	@Override
	public final <T> T fold( final Object lhs, final Object rhs ) {
		return BinaryOperation.<T>as( (Long)lhs - (Long)rhs );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.lsub();
	}
}