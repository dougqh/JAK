package net.dougqh.aggregator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	FilteringTest.class,
	TransformTest.class,
	ChainedTest.class
})
public final class AggregatorSuite {}
