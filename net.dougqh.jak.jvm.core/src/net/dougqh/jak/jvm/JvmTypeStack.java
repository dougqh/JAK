package net.dougqh.jak.jvm;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import net.dougqh.java.meta.types.JavaTypes;

public final class JvmTypeStack extends BaseJvmStack< Type > {
	public JvmTypeStack( final int initialCapacity ) {
		super( initialCapacity );
	}
	
	public JvmTypeStack() {
		this( 16 );
	}
	
	@Override
	protected final void stack1(Type type) {
		this.push1(type);
	}
	
	@Override
	protected final void stack2(Type type) {
		this.push2(type);
	}
	
	@Override
	protected final void unstack1(Type type) {
		this.pop1(type);
	}
	
	@Override
	protected void unstack2(Type type) {
		this.pop2(type);
	}
	
	public final boolean matches( final Type... matchTypes ) {
		Iterator< Type > actualIterator = this.iterator();
		for ( Type matchType : matchTypes ) {
			Type actualType = actualIterator.hasNext() ? actualIterator.next() : null;
			
			if ( ! matchType.equals( actualType ) ) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public final String toString() {
		ArrayList< String > strings = new ArrayList< String >( this.size() );
		for ( Type type : this ) {
			strings.add( JavaTypes.getRawClassName( type ) );
		}
		return strings.toString();
	}
}
