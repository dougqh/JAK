package net.dougqh.jak.jvm.assembler.macros.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	IterableForTest.class,
	ArrayForTest.class,
	IfTest.class,
	ExpressionsTest.class,
	TryTest.class,
	SynchronizedTest.class,
	WhileTest.class,
	DoWhileTest.class
})
public final class MacrosTestSuite {}
