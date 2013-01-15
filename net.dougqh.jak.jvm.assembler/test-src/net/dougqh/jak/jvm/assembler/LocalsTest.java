package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Type;

import net.dougqh.jak.types.Any;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Test;

import static org.junit.Assert.*;

public final class LocalsTest {
	@Test
	public final void noDeclarations() {
		DefaultLocals locals = new DefaultLocals();
		
		locals.load( 0, int.class );
		assertThat( locals.maxLocals(), is( 1 ) );
		assertThat( locals.typeOf( 0, Any.class ), is( int.class ) );
		
		locals.load( 1, long.class );
		assertThat( locals.maxLocals(), is( 3 ) );
		assertThat( locals.typeOf( 1, Any.class ), is( long.class ) );
		
		locals.load( 3, String.class );
		assertThat( locals.maxLocals(), is( 4 ) );
		assertThat( locals.typeOf( 3, Any.class ), is( String.class ) );
	}
	
	@Test
	public final void declareCategory1() {
		DefaultLocals locals = new DefaultLocals();
		
		int firstSlot = locals.declare( int.class );
		assertThat( firstSlot, is( 0 ) );
		assertThat( locals.typeOf( firstSlot, Any.class ), is( int.class ) );
		
		int secondSlot = locals.declare( float.class );
		assertThat( secondSlot, is( 1 ) );
		assertThat( locals.typeOf( secondSlot, Any.class ), is( float.class ) );

		assertThat( locals.maxLocals(), is( 2 ) );
		
		locals.undeclare( firstSlot );
		
		int thirdSlot = locals.declare( float.class );
		assertThat( thirdSlot, is( 0 ) );
		assertThat( locals.typeOf( thirdSlot, Any.class ), is( float.class ) );

		assertThat( locals.maxLocals(), is( 2 ) );
	}
	
	@Test
	public final void declareCategory2() {
		DefaultLocals locals = new DefaultLocals();
		
		int firstSlot = locals.declare( long.class );
		assertThat( firstSlot, is( 0 ) );
		assertThat( locals.typeOf( firstSlot, Any.class ), is( long.class ) );
		
		int secondSlot = locals.declare( double.class );
		assertThat( secondSlot, is( 2 ) );
		assertThat( locals.typeOf( secondSlot, Any.class ), is( double.class ) );

		assertThat( locals.maxLocals(), is( 4 ) );
		
		locals.undeclare( firstSlot );
		
		int thirdSlot = locals.declare( long.class );
		assertThat( thirdSlot, is( 0 ) );
		assertThat( locals.typeOf( thirdSlot, Any.class ), is( long.class ) );

		assertThat( locals.maxLocals(), is( 4 ) );
	}
	
	@Test
	public final void declareMix() {
		DefaultLocals locals = new DefaultLocals();
		
		int firstSlot = locals.declare( int.class );
		assertThat( firstSlot, is( 0 ) );
		assertThat( locals.typeOf( firstSlot, Any.class ), is( int.class ) );
		
		int secondSlot = locals.declare( float.class );
		assertThat( secondSlot, is( 1 ) );
		assertThat( locals.typeOf( secondSlot, Any.class ), is( float.class ) );
		
		assertThat( locals.maxLocals(), is( 2 ) );
		
		locals.undeclare( firstSlot );
		
		int thirdSlot = locals.declare( long.class );
		assertThat( thirdSlot, is( 2 ) );
		
		locals.undeclare( secondSlot );
		
		int fourthSlot = locals.declare( long.class );
		assertThat( fourthSlot, is( 0 ) );
		
		assertThat( locals.maxLocals(), is( 4 ) );
	}
	
	private static final Matcher< Integer > is( final int value ) {
		return CoreMatchers.is( value );
	}
	
	private static final Matcher< Type > is( final Type value ) {
		return CoreMatchers.is( value );
	}
}
