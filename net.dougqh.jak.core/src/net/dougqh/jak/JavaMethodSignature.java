package net.dougqh.jak;

import java.lang.reflect.Type;

public final class JavaMethodSignature {
	private final String methodName;
	private FormalArguments args = FormalArguments.EMPTY;

	JavaMethodSignature( final String methodName ) {
		this.methodName = methodName;
	}
	
	public final JavaMethodSignature args( final Type... types ) {
		this.args = FormalArguments.instance( types );
		return this;
	}
	
	public final String name() {
		return this.methodName;
	}

	public final FormalArguments arguments() {
		return this.args;
	}
}
