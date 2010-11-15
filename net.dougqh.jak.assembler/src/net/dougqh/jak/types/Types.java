package net.dougqh.jak.types;

import java.lang.reflect.Type;

import net.dougqh.java.meta.types.JavaTypes;


public final class Types {
	public static final int size( final Type type ) {
		if ( isCategory2( type ) ) {
			return 2;
		} else {
			return 1;
		}
	}
	
	public static final boolean isIntegerType( final Type type ) {
		return type.equals( boolean.class ) ||
			type.equals( byte.class ) ||
			type.equals( char.class ) ||
			type.equals( short.class ) ||
			type.equals( int.class );
	}
	
	public static final boolean isReferenceType( final Type type ) {
		if ( JavaTypes.isObjectType( type ) ) {
			return ! type.equals( Category1.class ) &&
				! type.equals( Category2.class );
		} else {
			return false;
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
}
