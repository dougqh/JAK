package net.dougqh.java.meta.types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public final class JavaParameterizedType
	implements ParameterizedType
{
	private final Type rawType;
	private final Type[] actualTypeArguments;
	
	public JavaParameterizedType(
		final Type rawType,
		final Type... actualTypeArguments )
	{
		this.rawType = rawType;
		this.actualTypeArguments = actualTypeArguments;
	}
	 
	@Override
	public final Type getOwnerType() {
		return null;
	}
	
	@Override
	public final Type getRawType() {
		return this.rawType;
	}
	
	@Override
	public final Type[] getActualTypeArguments() {
		return this.actualTypeArguments;
	}
	
	//TODO: Implement hashCode
	
	@Override
	public final boolean equals( final Object obj ) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof ParameterizedType ) ) {
			return false;
		} else {
			ParameterizedType that = (ParameterizedType)obj;
			return this.getRawType().equals( that.getRawType() ) &&
				Arrays.equals( this.getActualTypeArguments(), that.getActualTypeArguments() );
		}
	}
	
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( this.rawType );
		builder.append( '<' );
		
		boolean isFirst = true;
		for ( Type type : this.actualTypeArguments ) {
			if ( isFirst ) {
				isFirst = false;
			} else {
				builder.append( ',' );
			}
			builder.append( type.toString() );
		}
		
		builder.append( '>' );
		return builder.toString();
	}
}