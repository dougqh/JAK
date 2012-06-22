package net.dougqh.jak.jvm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.dougqh.jak.jvm.operations.JvmOperation;

/**
 * JvmOperationRewritingFilter implements a relatively simple rewriting engine.
 * Different JvmOperationRewriter-s can be registered to the rewriting filter.
 * Filters can be added and removed during the course of a method.
 */
public final class JvmOperationRewritingFilter extends JvmOperationFilter {
	private final JvmOperationProcessor wrappedProcessor;
	private JvmOperationRewriter rewriter = null;
	
	private final RewriterState state = new RewriterState() {
		@Override
		public final JvmOperationProcessor processor() {
			return JvmOperationRewritingFilter.this.wrapped();
		}
	};
	
	public JvmOperationRewritingFilter( final JvmOperationProcessor wrappedProcessor ) {
		this.wrappedProcessor = wrappedProcessor;
	}
	
	@Override
	protected final JvmOperationProcessor wrapped() {
		return this.wrappedProcessor;
	}
	
	public final void set( final JvmOperationRewriter rewriter ) {
		this.rewriter = rewriter;
	}
	
	@Override
	protected final boolean shouldFilter( final Class<? extends JvmOperation> operationClass ) {
		boolean match = this.rewriter.match(this.state, operationClass);
		if ( ! match ) {
			boolean backTracked = this.handleMismatch();
			//DQH - If we backtracked, then the state was reset so try again
			if ( backTracked ) {
				match = this.rewriter.match(this.state, operationClass);
			}
		}
		return match;
	}
	
	@Override
	protected final void filter( final JvmOperation operation ) {
		boolean match = this.rewriter.match(this.state, operation);
		if ( match ) {
			this.rewriter.process(this.state, operation);
		} else {
			boolean backTracked = this.handleMismatch();
			//DQH - If we backtracked, then the state was reset so try again
			if ( backTracked && this.rewriter.match(this.state, operation) ) {
				this.rewriter.process(this.state, operation);
			} else {
				//Complete mismatch - buffer has been flushed, but this operation needs to written, too.
				this.state.write(operation);
			}
		}
	}
	
	private final boolean handleMismatch() {
		boolean backTrack = ( this.rewriter.backTrackOnMismatch() && this.state.hasBufferContents() );
		if ( backTrack ) {
			//swallow the first operation which represented a false start
			this.state.write( this.state.debuffer() );
			
			//take the buffered operations & reset the state to process them again
			List<JvmOperation> prevBuffer = this.state.takeBuffer();
			this.state.resetState();
			
			//walk through the buffered operations, calling back on this filter
			for ( JvmOperation bufferedOp: prevBuffer ) {
				bufferedOp.process(this);
			}
		} else {
			//no back tracking - just flush the buffer & reset
			this.state.flushBuffer();
			this.state.resetState();
		}
		return backTrack;
	}
	
	public static abstract class RewriterState {
		//TODO: Replace with a ring buffer for efficiency
		//Deliberately, not final so that buffer ownership can be transferred when backtracking
		private LinkedList<JvmOperation> buffer = new LinkedList<JvmOperation>();
		private int state = 0;
		
		public abstract JvmOperationProcessor processor();
		
		public final void resetState() {
			this.state(0);
		}
		
		public final void state(final int state) {
			this.state = state;
		}
		
		public final void nextState() {
			++this.state;
		}
				
		public final int state() {
			return this.state;
		}
		
		public final void write( final JvmOperation operation ) {
			operation.process(this.processor());
		}
		
		protected final boolean hasBufferContents() {
			return ! this.buffer.isEmpty();
		}
		
		public final void buffer( final JvmOperation operation ) {
			this.buffer.addLast(operation);
		}
		
		protected final JvmOperation debuffer() {
			return this.buffer.removeFirst();
		}
		
		protected final List<JvmOperation> takeBuffer() {
			List<JvmOperation> buffer = this.buffer;
			this.buffer = new LinkedList<JvmOperation>();
			return Collections.unmodifiableList(buffer);
		}

		public final void flushBuffer() {
			for ( JvmOperation op: this.buffer ) {
				op.process(this.processor());
			}
		}
		
		public final JvmOperation buffered(final int index) {
			return this.buffer.get(index);
		}
		
		public final List<JvmOperation> buffered() {
			return Collections.unmodifiableList(this.buffer);
		}
	}
}
