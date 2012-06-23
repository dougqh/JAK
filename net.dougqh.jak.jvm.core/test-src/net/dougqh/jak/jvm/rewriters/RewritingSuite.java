package net.dougqh.jak.jvm.rewriters;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	NormalizationTest.class,
	UnaryConstantFoldingTest.class,
	BinaryConstantFoldingTest.class
})
public final class RewritingSuite {}
