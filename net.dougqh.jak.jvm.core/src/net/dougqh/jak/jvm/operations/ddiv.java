package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class ddiv extends BinaryOperation {
	public static final String ID = "ddiv";
	public static final byte CODE = DDIV;
	
	public static final ddiv instance() {
		return new ddiv();
	}
	
	private ddiv() {}
	
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
		return DIV;
	}
	
	@Override
	public final Type lhsType() {
		return double.class;
	}
	
	@Override
	public final Type rhsType() {
		return double.class;
	}
	
	@Override
	public final Type resultType() {
		return double.class;
	}
	
	@Override
	public <T> T fold( final Object lhs, final Object rhs ) {
		return BinaryOperation.<T>as( (Double)lhs / (Double)rhs );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.ddiv();
	}
}