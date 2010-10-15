package net.dougqh.jak;

import net.dougqh.jak.api.PublicApiTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith( Suite.class )
@SuiteClasses( {
	FlagsTest.class,
	ByteStreamTest.class,
	ConstantPoolTest.class,
	OperationTest.class,
	PublicApiTestSuite.class } )
public final class JakTestSuite {}
