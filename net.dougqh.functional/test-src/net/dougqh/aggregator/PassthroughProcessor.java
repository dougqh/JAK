package net.dougqh.aggregator;

public final class PassthroughProcessor<T> extends Processor<T, T> {
	@Override
	public final void process(
		final InputChannel<? extends T> in,
		final OutputChannel<? super T> out)
		throws Exception
	{
		for ( T input = in.poll(); input != null; input = in.poll() ) {
			out.offer(input);
		}
	}
}
