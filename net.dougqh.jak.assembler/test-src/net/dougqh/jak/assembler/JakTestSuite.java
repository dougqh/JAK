package net.dougqh.jak.assembler;

import net.dougqh.jak.assembler.api.PublicApiTestSuite;

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
