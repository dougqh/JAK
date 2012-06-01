package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lrem extends BinaryOperation {
	public static final String ID = "lrem";
	public static final byte CODE = LREM;
	
	public static final lrem instance() {
		return new lrem();
	}
	
	private lrem() {}
	
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
		return REM;
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
		return BinaryOperation.<T>as( (Long)lhs % (Long)rhs );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.lrem();
	}
}