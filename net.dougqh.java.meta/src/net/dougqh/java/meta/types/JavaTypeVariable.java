package net.dougqh.java.meta.types;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

public final class JavaTypeVariable implements TypeVariable< GenericDeclaration > {
	private final String name;
	private final Type[] bounds;
	
	protected JavaTypeVariable( final String name ) {
		this.name = name;
		this.bounds = null;
	}
	
	JavaTypeVariable( final String name, final Type[] bounds ) {
		this.name = name;
		this.bounds = bounds;
	}
	
	public final TypeVariable< GenericDeclaration > extends_( final Type... types ) {
		return new JavaTypeVariable( this.name, types );
	}
	
	@Override
	public final String getName() {
		return this.name;
	}
	
	@Override
	public final Type[] getBounds() {
		if ( this.bounds == null ) {
			return new Type[] { Object.class };
		} else {
			return this.bounds;
		}
	}
	
	//DQH: hashCode and equals are implemented such that equivalence only
	//includes other instances of JavaTypeVariable, since JavaTypeVariable
	//instances are never related back to a generic declaration of 
	//a source element.
	@Override
	public final int hashCode() {
		int result = 0;
		int prime = 31;
		result = result * prime + this.name.hashCode();
		for ( Type type : this.bounds ) {
			result = result * prime + type.hashCode();
		}
		return result;
	}
	
	@Override
	public final boolean equals( final Object obj ) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof TypeVariable ) ) {
			return false;
		} else {
			TypeVariable<?> that = (TypeVariable<?>)obj;
			
			return this.getName().equals( that.getName() ) &&
				Arrays.equals( this.getBounds(), that.getBounds() );			
		}
	}
	
	@Override
	public final GenericDeclaration getGenericDeclaration() {
		//DQH: Somewhat non-compliant to return null, 
		//but reasonable given that no element declared
		//this type.
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( this.name );
		builder.append( " extends " );
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
