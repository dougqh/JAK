package net.dougqh.jak;

import java.lang.reflect.Type;

public final class JavaMethodDescriptor {
	static final String INIT = "<init>";
	static final String CLINIT = "<clinit>";
	
	private final int flags;
	private final Type returnType;
	private final String methodName;
	
	private FormalArguments args = FormalArguments.EMPTY;
	private Type[] exceptionTypes = new Type[]{};

	JavaMethodDescriptor(
		final JavaFlagsBuilder flagsBuilder,
		final Type returnType,
		final String methodName )
	{
		this.flags = flagsBuilder.flags();
		this.returnType = returnType;
		this.methodName = methodName;
	}
	
	public final JavaMethodDescriptor arg(
		final Type type,
		final String varName )
	{
		//TODO: Make this more efficient
		this.args = FormalArguments.instance(
			this.args,
			new JavaVariable( type, varName ) );
		return this;
	}

	public final JavaMethodDescriptor args( final Type... types ) {
		this.args = FormalArguments.instance( types );
		return this;
	}
	
	public final JavaMethodDescriptor throws_( final Type... exceptionTypes ) {
		this.exceptionTypes = exceptionTypes;
		return this;
	}
	
	protected final int flags() {
		return this.flags;
	}
	
	public final String getName() {
		return this.methodName;
	}

	public final Type getReturnType() {
		return this.returnType;
	}
	
	public final Type[] getArgumentTypes() {
		return this.args.getTypes();
	}
	
	public final Type[] exceptions() {
		return this.exceptionTypes;
	}
	
	public final boolean isInit() {
		return this.methodName.equals( INIT );
	}
	
	public final boolean isClinit() {
		return this.methodName.equals( CLINIT );
	}
	
	public final boolean isStatic() {
		return ( ( this.flags & JavaFlagsBuilder.STATIC ) != 0 );
	}
	
	protected final FormalArguments arguments() {
		return this.args;
	}


}
