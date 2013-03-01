package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.io.InputStream;

import net.dougqh.iterable.Accumulator;
import net.dougqh.iterable.InputStreamProvider;

public interface ClassLocator {
	public abstract void enumerate(final Accumulator.Scheduler<InputStreamProvider> scheduler)
		throws InterruptedException;
	
	public abstract InputStream load(final String className) throws IOException;
}
