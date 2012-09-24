package net.dougqh.jak.jvm.optimizers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
	UnaryConstantFoldingTest.class,
	BinaryConstantFoldingTest.class
})
public final class OptimizationSuite {}
