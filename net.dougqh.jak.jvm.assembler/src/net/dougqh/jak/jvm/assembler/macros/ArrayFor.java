package net.dougqh.jak.jvm.assembler.macros;

import java.lang.reflect.Type;


public final class ArrayFor extends For {
	protected static final String var_index = "i";
	protected static final String var_array = "array";
	protected static final String var_len = "len";
	protected static final String var_element = "element";
	
	private final Type elementType;
	private final String elementVar;
	private final String arrayVar;
	private final stmt body;
	
	public ArrayFor(
		final Type elementType,
		final String elementVar,
		final String arrayVar,
		final stmt body )
	{
		this.elementType = elementType;
		this.elementVar = elementVar;
		this.arrayVar = arrayVar;
		this.body = body;
	}
	
	protected final void preinit() {
		alias( var_array, this.arrayVar ).
		
		ideclare( var_len ).
		aload( var_array ).
		arraylength().
		istore( var_len );
	}
	
	protected final void init() {
		//i = 0
		ideclare( var_index ).
		istore( 0, var_index );
	}
	
	@Override
	protected final void bodyStart() {
		declare( this.elementType, this.elementVar ).
		alias( var_element, this.elementVar ).
		aload( var_array ).
		iload( var_index ).
		arrayload( this.elementType ).
		store( this.elementType, var_element );
	}
	
	protected final void body() {
		macro( this.body );
	}
	
	protected final void test( final String trueLabel ) {
		//if i < len; then jump
		iload( var_index ).
		iload( var_len ).
		if_icmplt( trueLabel );
	}
	
	@Override
	protected final void step() {
		iinc( var_index );
	}
}
