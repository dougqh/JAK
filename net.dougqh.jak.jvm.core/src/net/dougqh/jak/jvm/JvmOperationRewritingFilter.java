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
	public enum Approach {
		STREAMING() {
			@Override
			protected final ApproachImpl createImpl(
				final JvmOperationRewritingFilter rewritingFilter )
			{
				return new StreamingImpl(rewritingFilter);
			}
		},
		MULTI_PASS() {
			@Override
			protected final ApproachImpl createImpl(
				final JvmOperationRewritingFilter rewritingFilter )
			{
				return new MultiPassImpl(rewritingFilter);
			}
		};
		
		protected abstract ApproachImpl createImpl( final JvmOperationRewritingFilter rewritingFilter );
	}
	
	private final JvmOperationProcessor wrappedProcessor;
	private ApproachImpl approachImpl;
	private JvmOperationRewriter rewriter = null;
	
	private final RewriterState state = new RewriterState() {
		@Override
		public final JvmOperationProcessor processor() {
			return JvmOperationRewritingFilter.this.wrapped();
		}
	};
	
	public JvmOperationRewritingFilter( final JvmOperationProcessor wrappedProcessor ) {
		this.wrappedProcessor = wrappedProcessor;
		this.setApproach( Approach.STREAMING );
	}
	
	public JvmOperationRewritingFilter setApproach( final Approach approach ) {
		this.approachImpl = approach.createImpl(this);
		return this;
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
		return this.approachImpl.shouldFilter(operationClass);
	}
	
	@Override
	protected final void filter( final JvmOperation operation ) {
		this.approachImpl.filter(operation);
	}
	
	@Override
	public final void prepare() {
		this.approachImpl.finish();
	}
	
	public static abstract class RewriterState {
		//TODO: Replace with a ring buffer for efficiency
		//Deliberately, not final so that buffer ownership can be transferred when backtracking
		private LinkedList<JvmOperation> buffer = new LinkedList<JvmOperation>();
		private int state = 0;
		private boolean rewrote;
		
		public abstract JvmOperationProcessor processor();
		
		public final void resetState() {
			this.resetState(true);
		}
		
		public final void resetState(final boolean rewrote) {
			this.state(0);
			this.rewrote |= rewrote;
		}
		
		public final void state(final int state) {
			this.state = state;
		}
		
		public final void incState() {
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
		
		public final JvmOperation debuffer() {
			return this.buffer.removeFirst();
		}

		public final void flushBuffer() {
			for ( JvmOperation op: this.buffer ) {
				op.process(this.processor());
			}
		}
		
		protected final List<JvmOperation> takeBuffer() {
			List<JvmOperation> buffer = this.buffer;
			this.buffer = new LinkedList<JvmOperation>();
			return Collections.unmodifiableList(buffer);
		}
		
		public final List<JvmOperation> buffered() {
			return Collections.unmodifiableList(this.buffer);
		}
	}
	
	protected static abstract class ApproachImpl {
		protected final JvmOperationRewritingFilter rewritingFilter;
		
		ApproachImpl( JvmOperationRewritingFilter rewritingFilter ) {
			this.rewritingFilter = rewritingFilter;
		}
		
		protected final JvmOperationRewritingFilter rewritingFilter() {
			return this.rewritingFilter;
		}
		
		protected final JvmOperationRewriter rewriter() {
			return this.rewritingFilter.rewriter;
		}
		
		protected final RewriterState state() {
			return this.rewritingFilter.state;
		}
		
		protected abstract boolean shouldFilter( final Class<? extends JvmOperation> operationClass );
		
		protected abstract void filter( final JvmOperation operation );
		
		protected abstract void finish();
	}
	
	private static final class StreamingImpl extends ApproachImpl {
		StreamingImpl( final JvmOperationRewritingFilter rewritingFilter ) {
			super( rewritingFilter );
		}
		
		@Override
		protected final boolean shouldFilter( final Class<? extends JvmOperation> operationClass ) {
			boolean match = this.rewriter().match(this.state(), operationClass);
			if ( ! match ) {
				boolean backTracked = this.handleMismatch();
				//DQH - If we backtracked, then the state was reset so try again
				if ( backTracked ) {
					match = this.rewriter().match(this.state(), operationClass);
				}
			}
			return match;
		}
			
		@Override
		protected final void filter( final JvmOperation operation ) {
			boolean match = this.rewriter().match(this.state(), operation);
			if ( match ) {
				this.rewriter().process(this.state(), operation);
			} else {
				boolean backTracked = this.handleMismatch();
				//DQH - If we backtracked, then the state was reset so try again
				if ( backTracked && this.rewriter().match(this.state(), operation) ) {
					this.rewriter().process(this.state(), operation);
				} else {
					//Complete mismatch - buffer has been flushed, but this operation needs to be written, too.
					this.state().write(operation);
				}
			}
		}
		
		private final boolean handleMismatch() {
			boolean backTrack = ( this.rewriter().backTrackOnMismatch() && this.state().hasBufferContents() );
			if ( backTrack ) {
				//swallow the first operation which represented a false start
				this.state().write( this.state().debuffer() );
				
				//Now, try again...
				//Take the buffered operations & reset the state to process them again
				List<JvmOperation> prevBuffer = this.state().takeBuffer();
				this.state().resetState(false);
				
				//..., by walking through the buffered operations, calling back on this filter
				for ( JvmOperation bufferedOp: prevBuffer ) {
					bufferedOp.process(this.rewritingFilter);
				}
			} else {
				//no back tracking - just flush the buffer & reset
				this.state().flushBuffer();
				this.state().resetState(false);
			}
			return backTrack;
		}
		
		@Override
		protected final void finish() {
			this.rewriter().finish(this.state());
		}
	}
	
	private static final class MultiPassImpl extends ApproachImpl {
		MultiPassImpl( final JvmOperationRewritingFilter rewritingFilter ) {
			super( rewritingFilter );
		}
		
		@Override
		protected final boolean shouldFilter(
			Class<? extends JvmOperation> operationClass )
		{
			return true;
		}
		
		@Override
		protected final void filter( final JvmOperation operation ) {
			//this.operations.add
		}
		
		
		@Override
		protected final void finish() {
			this.rewriter().finish(this.state());
		}
	}
}
