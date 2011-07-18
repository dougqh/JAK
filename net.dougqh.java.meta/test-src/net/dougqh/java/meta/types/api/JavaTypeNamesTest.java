package net.dougqh.java.meta.types.api;

import net.dougqh.java.meta.types.JavaTypeNames;

import org.junit.Test;
import static junit.framework.Assert.*;

public final class JavaTypeNamesTest {
	public final @Test void fromPersistedName() {
		assertEquals(
			"java.lang.Object",
			JavaTypeNames.fromPeristedName( "Ljava/lang/Object;" ) );
		
		assertEquals(
			"java.util.List",
			JavaTypeNames.fromPeristedName( "Ljava/util/List;" ) );
	}
	
	public final @Test void toPersistedName() {
		assertEquals(
			"Ljava/lang/Object;",
			JavaTypeNames.toPersistedName( "java.lang.Object" ) );
		
		assertEquals(
			"Ljava/util/List;",
			JavaTypeNames.toPersistedName( "java.util.List" ) );
	}
}
