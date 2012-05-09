package net.dougqh.jak.jvm.assembler.macros;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	IterableForTest.class,
	ArrayForTest.class,
	IfTest.class
})
public final class MacrosTestSuite {}
