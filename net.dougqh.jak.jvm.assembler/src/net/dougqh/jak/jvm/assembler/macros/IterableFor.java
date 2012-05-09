package net.dougqh.jak.jvm.assembler.macros;

import java.lang.reflect.Type;
import java.util.Iterator;

import net.dougqh.jak.Jak;
import net.dougqh.jak.JavaMethodDescriptor;

public final class IterableFor extends For {
	//DQH - The myriad optional overrides makes for complicated implementation,
	//but a pleasant API.  Still not sure how I feel about this overall.
	
	protected static final String var_iterable = "iterable";
	protected static final String var_iter = "iter";
	protected static final String var_element = "element";
	
	private static final JavaMethodDescriptor ITERATOR = Jak.method( Iterator.class, "iterator" );
	private static final JavaMethodDescriptor HAS_NEXT = Jak.method( boolean.class, "hasNext" );
	private static final JavaMethodDescriptor NEXT = Jak.method( Object.class, "next" );
	
	private final Type elementType;
	private final String elementVar;
	private final String iterableVar;
	private final stmt body;

	public IterableFor(
		final Type elementType, final String elementVar, final String iterableVar,
		final stmt body )
	{
		this.elementType = elementType;
		this.elementVar = elementVar;
		this.iterableVar = iterableVar;
		this.body = body;
	}
	
	@Override
	public final void preinit() {
		alias( var_iterable, this.iterableVar );
	}
	
	@Override
	protected final void init() {
		//invoke Iterable.iterator()
		adeclare( var_iter ).
		aload( var_iterable ).
		invokeinterface( Iterable.class, ITERATOR ).
		astore( var_iter );
	}
	
	@Override
	protected final void bodyStart() {
		//invoke Iterator.next() and cast the result
		declare( this.elementType, this.elementVar ).
		alias( var_element, this.elementVar ).
				
		aload( var_iter ).
		invokeinterface( Iterator.class, NEXT ).
		checkcast( this.elementType ).
		astore( var_element );
	}
	
	protected final void body() {
		macro( this.body );
	}
	
	@Override
	protected final void test( final String trueLabel ) {
		//Invoke Iterator.hasNext(); jump if true
		aload( var_iter ).
		invokeinterface( Iterator.class, HAS_NEXT ).
		if_( trueLabel );
	}
	
	@Override
	protected final void step() {
	}
}
