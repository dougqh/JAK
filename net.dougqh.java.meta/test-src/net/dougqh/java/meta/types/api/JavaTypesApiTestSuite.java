package net.dougqh.java.meta.types.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith( Suite.class )
@SuiteClasses( {
	JavaTypeNamesTest.class,
	JavaTypesTest.class,
	JavaTypeBuildingTest.class,
	JavaTypesArrayTest.class,
	JavaJvmTypesTest.class,
	JavaTypePrimitivesTest.class } )
public final class JavaTypesApiTestSuite {}
