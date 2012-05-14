package net.dougqh.jak.assembler;

import java.lang.reflect.Type;

import net.dougqh.jak.JakContext;

public interface JakExpression {
	public abstract Type type( final JakContext context );
	
	public abstract boolean hasConstantValue( final JakContext context );
	
	public abstract Object constantValue( final JakContext context );
	
	public abstract void accept( final Visitor visitor );
	
	@Override
	public abstract boolean equals( final Object obj );
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract String toString();
	
	public static abstract class Visitor {
		final JakContext context;
		
		public Visitor( final JakContext  context ) {
			this.context = context;
		}
		
		protected abstract void this_();
		
		protected abstract void var( final String name, final Type type );
		
		protected abstract void const_( final boolean value );
		
		protected abstract void const_( final byte value );
		
		protected abstract void const_( final char value );
		
		protected abstract void const_( final short value );
		
		protected abstract void const_( final int value );
		
		protected abstract void const_( final long value );
		
		protected abstract void const_( final float value );
		
		protected abstract void const_( final double value );
		
		protected abstract void const_( final Type aClass );
		
		protected abstract void const_( final String value );
		
		protected abstract void null_( final Type type );
	}
}
