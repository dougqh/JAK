package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.io.InputStream;

import net.dougqh.aggregator.InputScheduler;

public interface ClassLocator {
	public abstract void enumerate(final InputScheduler<? super ClassBlock> scheduler)
		throws InterruptedException;
	
	public abstract InputStream load(final String className) throws IOException;
	
	public interface ClassBlock {
		public abstract void process(final ClassProcessor processor) throws IOException;
	}
	
	public interface ClassProcessor {
		public abstract void process(final InputStream in) throws IOException;
	}
}
