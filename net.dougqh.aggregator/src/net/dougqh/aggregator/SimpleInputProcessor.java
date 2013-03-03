package net.dougqh.aggregator;

public abstract class SimpleInputProcessor<I, O> extends Processor<I, O> {
	@Override
	public final void process(final InputChannel<I> in, final OutputChannel<O> out) throws Exception {
		for ( I input = in.poll(); input != null; input = in.poll() ) {
			this.process(input, out);
		}
	}
	
	public abstract void process(final I input, final OutputChannel<O> out) throws Exception;
}