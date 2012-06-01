package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dcmpl extends ComparisonOperation {
	public static final String ID = "dcmpl";
	public static final byte CODE = DCMPL;
	
	public static final dcmpl instance() {
		return new dcmpl();
	}
	
	private dcmpl() {}
	
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
		return double.class;
	}
	
	@Override
	public final Type rhsType() {
		return double.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dcmpl();
	}
}