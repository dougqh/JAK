package net.dougqh.aggregator;

import java.util.Arrays;

import net.dougqh.aggregator.TransformProcessor.TransformingOutputChannel;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public final class TransformTest {
	@Test
	public final void channel() {		
		Transform<String, Integer> transform = new Transform<String, Integer>() {
			@Override
			public final Integer transform(final String input) throws Exception {
				return input.length();
			}
		};
		
		TestChannel<Integer> out = new TestChannel<Integer>();

		TransformingOutputChannel<String, Integer> transformedOut = 
			new TransformingOutputChannel<String, Integer>(transform, out);
		
		transformedOut.offer("foo");
		transformedOut.offer("bar");
		transformedOut.offer("quux");
		
		assertThat(out.list(), is(Arrays.asList(3, 3, 4)));
	}
	
	@Test
	public final void processor() throws Exception {
		TestChannel<String> in = new TestChannel<String>("foo", "bar", "quux");
		TestChannel<Integer> out = new TestChannel<Integer>();
		
		Transform<String, Integer> transform = new Transform<String, Integer>() {
			@Override
			public final Integer transform(final String input) throws Exception {
				return input.length();
			}
		};
		
		TransformProcessor<String, String, Integer> transformProcessor = 
			new TransformProcessor<String, String, Integer>(
				new PassthroughProcessor<String>(),
				transform
			);
		
		transformProcessor.process(in, out);
		
		assertThat(out.list(), is(Arrays.asList(3, 3, 4)));
	}
}
