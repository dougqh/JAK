package net.dougqh.jak;

import java.lang.reflect.Type;

import net.dougqh.jak.types.Category2;

final class TypeUtils {
	static final int size( final Type type ) {
		if ( isCategory2( type ) ) {
			return 2;
		} else {
			return 1;
		}
	}
	
	static final boolean isCategory1( final Type type ) {
		return ( ! type.equals( void.class ) ) &&
			! isCategory2( type );
	}
	
	static final boolean isCategory2( final Type type ) {
		return type.equals( long.class ) ||
			type.equals( double.class ) ||
			type.equals( Category2.class );
	}
}
