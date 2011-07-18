package net.dougqh.java.meta.types;

public final class JavaTypeNames {
	private JavaTypeNames() {}
	
	public static final String fromPeristedName( final String persistedName ) {
		return persistedName.substring( 1, persistedName.length() - 1 ).replace( '/', '.' );
	}
	
	public static final String toPersistedName( final String normalName ) {
		return "L" + normalName.replace( '.', '/' ) + ";";
	}
}
