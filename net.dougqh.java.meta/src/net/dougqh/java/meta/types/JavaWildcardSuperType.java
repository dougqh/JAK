package net.dougqh.java.meta.types;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

@Deprecated
public final class JavaWildcardSuperType
	implements WildcardType
{
	private final Type[] bounds;
	
	public JavaWildcardSuperType( final Type... bounds ) {
		this.bounds = JavaTypes.resolve( bounds );
	}
	
	@Override
	public final Type[] getUpperBounds() {
		return new Type[] { Object.class };
	}
	
	@Override
	public final Type[] getLowerBounds() {
		return this.bounds;
	}
	
	//TODO: Implement hashCode
	
	@Override
	public final boolean equals( final Object obj ) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof WildcardType ) ) {
			return false;
		} else {
			WildcardType that = (WildcardType)obj;
			return Arrays.equals( this.getLowerBounds(), that.getLowerBounds() ) &&
				Arrays.equals( this.getUpperBounds(), that.getUpperBounds() );
		}
	}
	
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( "? super " );
		boolean first = true;
		for ( Type lower : this.bounds ) {
			if ( first ) {
				first = false;
			} else {
				builder.append( "&" );
			}
			builder.append( lower.toString() );
		}
		return builder.toString();
	}
}
