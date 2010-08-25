package net.dougqh.jak.types;

import java.lang.reflect.Type;


public final class Types {
	public static final int size( final Type type ) {
		if ( isCategory2( type ) ) {
			return 2;
		} else {
			return 1;
		}
	}
	
	public static final boolean isCategory1( final Type type ) {
		return ( ! type.equals( void.class ) ) &&
			! isCategory2( type );
	}
	
	public static final boolean isCategory2( final Type type ) {
		return type.equals( long.class ) ||
			type.equals( double.class ) ||
			type.equals( Category2.class );
	}
	
	public static final boolean isPlaceholderClass( final Class< ? > aClass ) {
		if ( aClass.isArray() ) {
			return isPlaceholderClass( aClass.getComponentType() );
		} else {
			return
				aClass.equals( Any.class ) ||
				aClass.equals( Reference.class ) ||
				aClass.equals( ArgList.class ) ||
				aClass.equals( Category1.class ) ||
				aClass.equals( Category2.class );
		}
	}	
}
