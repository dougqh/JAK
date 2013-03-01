package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CopyOnWriteArrayList;

import net.dougqh.iterable.Accumulator;
import net.dougqh.iterable.InputStreamProvider;

final class CompositeClassLocator implements ClassLocator {
	private final CopyOnWriteArrayList< ClassLocator > locators = new CopyOnWriteArrayList<ClassLocator>();
	
	final void add(final ClassLocator locator) {
		this.locators.add(locator);
	}
	
	final boolean isEmpty() {
		return this.locators.isEmpty();
	}
	
	@Override
	public final void enumerate(final Accumulator.Scheduler<InputStreamProvider> scheduler)
		throws InterruptedException
	{
		for ( ClassLocator locator: this.locators ) {
			locator.enumerate(scheduler);
		}
	}
	
	@Override
	public final InputStream load( final String className ) throws IOException {
		for ( ClassLocator locator : this.locators ) {
			InputStream in = locator.load(className);
			if ( in != null ) {
				return in;
			}
		}
		return null;
	}
}
