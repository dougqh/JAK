package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class iand extends BinaryOperation {
	public static final String ID = "iand";
	public static final byte CODE = IAND;
	
	public static final iand instance() {
		return new iand();
	}
	
	private iand() {}
	
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
		return AND;
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
		return BinaryOperation.<T>as( (Integer)lhs & (Integer)rhs );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.iand();
	}
}