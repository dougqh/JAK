package net.dougqh.java.meta.types;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

public class JavaWildcardType implements WildcardType {
	private final Type lowerBound;
	private final Type[] upperBounds;
	
	public JavaWildcardType() {
		this.lowerBound = null;
		this.upperBounds = null;
	}
	
	public JavaWildcardType( final Type lowerBound, final Type[] upperBounds ) {
		this.lowerBound = lowerBound;
		this.upperBounds = upperBounds == null ? null : upperBounds.clone();
	}
	
	public WildcardType extends_( final Type... upperBounds ) {
		return new JavaWildcardType( null, upperBounds );
	}
	
	public WildcardType super_( final Type lowerBound ) {
		return new JavaWildcardType( lowerBound, null );
	}
	
	@Override
	public final Type[] getUpperBounds() {
		if ( this.upperBounds == null ) {
			return new Type[] { Object.class };			
		} else {
			return this.upperBounds;
		}
	}
	
	@Override
	public final Type[] getLowerBounds() {
		if ( this.upperBounds == null ) {
			return new Type[] { this.lowerBound };
		} else {
			return new Type[ 0 ];
		}
	}
	
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
	public final int hashCode() {
		//TODO: Provide a better implementation of hashCode
		return Arrays.hashCode( this.getLowerBounds() ) ^ Arrays.hashCode( this.getUpperBounds() );
	}
	
	
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		if ( this.upperBounds != null ) {
			builder.append( "? extends " );
			boolean first = true;
			for ( Type upper : this.upperBounds ) {
				if ( first ) {
					first = false;
				} else {
					builder.append( "&" );
				}
				builder.append( upper.toString() );
			}
		} else {
			builder.append( "? super " ).append( this.lowerBound.toString() );
		}
		return builder.toString();
	}
}
