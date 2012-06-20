package net.dougqh.jak.jvm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.dougqh.jak.jvm.JvmOperationRewriter.State;
import net.dougqh.jak.jvm.operations.JvmOperation;

/**
 * JvmOperationRewritingFilter implements a relatively simple rewriting engine.
 * Different JvmOperationRewriter-s can be registered to the rewriting filter.
 * Filters can be added and removed during the course of a method.
 */
public final class JvmOperationRewritingFilter extends JvmOperationFilter {
	private final JvmOperationProcessor wrappedProcessor;
	private JvmOperationRewriter rewriter = null;
	
	private final List<JvmOperation> operations = new ArrayList<JvmOperation>(4);
	
	public JvmOperationRewritingFilter( final JvmOperationProcessor wrappedProcessor ) {
		this.wrappedProcessor = wrappedProcessor;
	}
	
	@Override
	protected final JvmOperationProcessor wrapped() {
		return this.wrappedProcessor;
	}
	
	public final void set( final JvmOperationRewriter rewriter ) {
		this.rewriter = rewriter;
		this.rewriter.reset();
	}
	
	@Override
	protected final boolean shouldFilter( final Class<? extends JvmOperation> operationClass ) {
		State state = this.rewriter.match(operationClass);
		switch ( state ) {
			case MATCH:
			return true;
			
			case MISMATCH:
			this.flush();
			this.reset();
			return false;
				
			case FINISH:
			default:
			throw new IllegalStateException();
		}
	}
	
	@Override
	protected final void filter( final JvmOperation operation ) {
		State state = this.rewriter.match(operation);
		switch ( state ) {
			case MATCH:
			this.operations.add(operation);
			break;
			
			case MISMATCH:
			this.operations.add(operation);
			this.flush();
			this.reset();
			break;
				
			case FINISH:
			this.operations.add(operation);
			this.rewriter.finish( this.wrapped(), Collections.unmodifiableList(this.operations) );
			this.reset();
			break;
		}
	}
	
	private final void flush() {
		for ( JvmOperation op: this.operations ) {
			op.process( this.wrapped() );
		}
	}
	
	private final void reset() {
		this.rewriter.reset();
		this.operations.clear();
	}
}
