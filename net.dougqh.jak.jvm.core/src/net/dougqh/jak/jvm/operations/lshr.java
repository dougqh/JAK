package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lshr extends BinaryOperation {
	public static final String ID = "lshr";
	public static final byte CODE = LSHR;
	
	public static final lshr instance() {
		return new lshr();
	}
	
	private lshr() {}
	
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
		return RIGHT_SHIFT;
	}
	
	@Override
	public final Type lhsType() {
		return long.class;
	}
	
	@Override
	public final Type rhsType() {
		return int.class;
	}
	
	@Override
	public final Type resultType() {
		return long.class;
	}
	
	@Override
	public final <T> T fold( final Object lhs, final Object rhs ) {
		return BinaryOperation.<T>as( (Long)lhs >> (Integer)rhs );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.lshr();
	}
}