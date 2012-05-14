package net.dougqh.jak.jvm.assembler;

import net.dougqh.jak.jvm.assembler.api.JakAssemblerApiTestSuite;
import net.dougqh.jak.jvm.assembler.macros.api.MacrosTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith( Suite.class )
@SuiteClasses({
	FlagsTest.class,
	JvmOutputStreamTest.class,
	ConstantPoolTest.class,
	OperationTest.class,
	LocalsTest.class,
	ScopeTest.class,
	JakAssemblerApiTestSuite.class,
	MacrosTestSuite.class })
public final class JakAssemblerTestSuite {}
