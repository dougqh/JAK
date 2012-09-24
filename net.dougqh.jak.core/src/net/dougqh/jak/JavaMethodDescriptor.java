package net.dougqh.jak;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

import static net.dougqh.jak.Methods.*;

public final class JavaMethodDescriptor {
	private final int flags;
	private final TypeVariable<?>[] typeVars;
	private final Type returnType;
	private final String methodName;
	
	private FormalArguments args = FormalArguments.EMPTY;
	private Type[] exceptionTypes = new Type[]{};

	JavaMethodDescriptor(
		final JavaModifiers modifiers,
		final Type returnType,
		final String methodName )
	{
		this.flags = modifiers.flags();
		this.typeVars = modifiers.typeVars();
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
	
	public final int getFlags() {
		return this.flags;
	}
	
	public final TypeVariable<?>[] getTypeVars() {
		return this.typeVars;
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
		return ( ( this.flags & Flags.STATIC ) != 0 );
	}
	
	public final boolean isAbstract() {
		return ( ( this.flags & Flags.ABSTRACT ) != 0 );
	}
	
	public final boolean isNative() {
		return ( ( this.flags & Flags.NATIVE ) != 0 );
	}
	
	public final FormalArguments arguments() {
		return this.args;
	}
	
	@Override
	public final int hashCode() {
		//Quick hash code calculation - will result in abnormal number of collisions
		return this.methodName.hashCode();
	}
	
	@Override
	public final boolean equals(final Object obj) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof JavaMethodDescriptor ) ) {
			return false;
		} else {
			JavaMethodDescriptor that = (JavaMethodDescriptor)obj;
			return this.returnType.equals( that.returnType ) &&
				this.args.equals( that.args );
		}
	}
}
