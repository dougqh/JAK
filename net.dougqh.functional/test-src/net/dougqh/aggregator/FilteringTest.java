package net.dougqh.aggregator;

import java.util.Arrays;

import net.dougqh.aggregator.FilteringProcessor.FilteringOutputChannel;
import net.dougqh.functional.Filter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public final class FilteringTest {
	@Test
	public final void channel() {
		Filter<String> filter = new Filter<String>() {
			@Override
			public final boolean matches(final String value) {
				return value.startsWith("ba");
			}			
		};

		TestChannel<String> out = new TestChannel<String>();
		FilteringOutputChannel<String> filtered = new FilteringOutputChannel<String>(filter, out);
		
		filtered.offer("foo");
		filtered.offer("bar");
		filtered.offer("baz");
		filtered.offer("quux");
		
		assertThat(out.list(), is(Arrays.asList("bar", "baz")));
	}
	
	@Test
	public final void processor() throws Exception {
		TestChannel<String> in = new TestChannel<String>("foo", "bar", "baz");
		TestChannel<String> out = new TestChannel<String>();
		
		Filter<String> filter = new Filter<String>() {
			@Override
			public final boolean matches(final String value) {
				return value.startsWith("ba");
			}			
		};
		
		Processor<String, String> filteringProcessor = new FilteringProcessor<String, String>(
			new PassthroughProcessor<String>(),
			filter
		);
		
		filteringProcessor.process(in, out);
		
		assertThat(out.list(), is(Arrays.asList("bar", "baz")));
	}
}
