package net.dougqh.jak.jvm;

import java.util.ArrayList;
import java.util.Collections;
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
	
	private int state = JvmOperationRewriter.INITIAL;
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
	}
	
	@Override
	protected final boolean shouldFilter( final Class<? extends JvmOperation> operationClass ) {
		boolean match = this.rewriter.match(this.state, operationClass);
		
		if ( ! match) {
			this.flush();
			this.reset();
		}
		return match;
	}
	
	@Override
	protected final void filter( final JvmOperation operation ) {
		this.operations.add(operation);
		
		this.state = this.rewriter.match(this.state, operation);
		if ( this.state == JvmOperationRewriter.INITIAL ) {
			this.flush();
			this.reset();
		} else if ( this.state == JvmOperationRewriter.FINAL ) {
			this.rewriter.finish( this.wrapped(), Collections.unmodifiableList(this.operations) );
			this.reset();
		}
	}
	
	private final void flush() {
		for ( JvmOperation op: this.operations ) {
			op.process( this.wrapped() );
		}
	}
	
	private final void reset() {
		this.operations.clear();
		this.state = JvmOperationRewriter.INITIAL;
	}
}
