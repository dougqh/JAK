package net.dougqh.aggregator;

import java.util.Arrays;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public final class ChainedTest {
	@Test
	public final void bar() throws Exception {
		TestChannel<String> in = new TestChannel<String>("foo", "bar", "quux");
		TestChannel<Long> out = new TestChannel<Long>();

		Processor<String, Integer> firstProcessor = new SimpleInputProcessor<String, Integer>() {
			public final void process(
				final String input, 
				final OutputChannel<? super Integer> out) throws Exception
			{
				out.offer(input.length());
			}
		};
		
		Processor<Integer, Long> secondProcessor = new SimpleInputProcessor<Integer, Long>() {
			@Override
			public final void process(
				final Integer input,
				final OutputChannel<? super Long> out)
				throws Exception
			{
				out.offer(Long.valueOf(input * input));
			}
		};
		
		ChainedProcessor<String, Integer, Long> chainedProcessor = new ChainedProcessor<String, Integer, Long>(
			firstProcessor,
			secondProcessor
		);
		
		chainedProcessor.process(in, out);
		
		assertThat(out.list(), is(Arrays.asList(9L, 9L, 16L)));
	}
}
