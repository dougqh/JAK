package net.dougqh.aggregator;

import java.util.LinkedList;


final class ChainedProcessor<I, T, O> extends Processor<I, O> {
	private final int BATCH_SIZE = 16;
	
	private final Processor<I, T> processor1;
	private final Processor<T, O> processor2;
	
	public ChainedProcessor(
		final Processor<I, T> processor1,
		final Processor<T, O> processor2)
	{  
		this.processor1 = processor1;
		this.processor2 = processor2;
	}
	
	@Override
	public final void process(final InputChannel<I> in, final OutputChannel<O> out)
		throws Exception
	{
		// The chained input processor performs all its worked in the same 
		// thread ultimately aggregating in a queue with back pressure in 
		// the end.  The back pressure is needed to prevent the producers 
		// from outstripping the consumers which cause us to run of memory.
		// However, using a queue with back pressure in a single thread 
		// will result in blocking our progress.
		// Instead the input stream is handled in batched chunks which are 
		// passed through all the processing stages to the collecting 
		// bounded queue which creates the desired back pressure.
		// This also ensures that data is available in timely fashion to 
		// the reading thread while still processing in batches for 
		// efficiency.
		InputBatchChannel batchChannel = new InputBatchChannel(BATCH_SIZE);
		TransferChannelImpl transferChannel = new TransferChannelImpl();
		
		for ( I input = in.poll(); input != null; input = in.poll() ) {
			batchChannel.buffer(input);
			
			if ( batchChannel.isFull() ) {
				this.processBatch(batchChannel, transferChannel, out);
			}
		}
		
		// process any residual
		this.processBatch(batchChannel, transferChannel, out);
	}
	
	private final void processBatch(
		final InputBatchChannel batchChannel,
		final TransferChannelImpl transferChannel,
		final OutputChannel<O> out)
		throws Exception
	{
		batchChannel.flip();
		
		this.processor1.process(batchChannel, transferChannel);
		this.processor2.process(transferChannel, out);
		
		batchChannel.flip();
	}
	
	final class InputBatchChannel implements InputChannel<I> {
		private final I[] inputs;
		private int pos;
		private int limit;
		
		public InputBatchChannel(final int capacity) {
			@SuppressWarnings("unchecked")
			I[] inputs = (I[])new Object[capacity];
			this.inputs = inputs;
		}
		
		public final void buffer(final I input) {
			this.inputs[this.pos++] = input;
		}
		
		@Override
		public final I poll() {
			if ( this.pos == this.limit ) {
				return null;
			} else {
				return this.inputs[this.pos++];
			}
		}
		
		public boolean isFull() {
			return ( this.pos == this.inputs.length );
		}
		
		public final void flip() {
			this.limit = this.pos;
			this.pos = 0;
		}
	}
	
	final class TransferChannelImpl implements OutputChannel<T>, InputChannel<T> {
		// LinkedList is fine because were transferring within the same thread.
		// If the transfer was across threads a different approach would be needed.
		private final LinkedList<T> intermediates;
		private Throwable cause = null;
		
		public TransferChannelImpl() {
			this.intermediates = new LinkedList<T>();
		}
		
		public final void offer(final T offer) {
			this.intermediates.addLast(offer);
		}
		
		@Override
		public final T poll() {
			if ( this.intermediates.isEmpty() ) {
				return null;
			} else {
				return this.intermediates.removeFirst();
			}
		}
	}
}
