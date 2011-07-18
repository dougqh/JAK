package net.dougqh.jak.jvm.assembler;

import net.dougqh.jak.jvm.assembler.api.JakAssemblerApiTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith( Suite.class )
@SuiteClasses( {
	FlagsTest.class,
	JvmOutputStreamTest.class,
	ConstantPoolTest.class,
	OperationTest.class,
	JakAssemblerApiTestSuite.class } )
public final class JakAssemblerTestSuite {}
