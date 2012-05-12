package net.dougqh.jak.jvm.assembler.macros;

import static net.dougqh.jak.Jak.*;
import static net.dougqh.jak.assembler.JakAsm.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

import org.junit.Test;

public final class IfTest {
	@Test
	public final void if_elseif_else_terminal() {
		JvmWriter writer = new JvmWriter();
		
		JvmClassWriter classWriter = writer.define(
			public_().final_().class_( "Signum" ).extends_( Conditional.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( int_, "exec" ).arg( int_, "num" ) ).
			if_( gt( "num", 0 ), new stmt() {
				@Override
				protected final void body() {
					ireturn( 1 );
				}				
			} ).
			elseif( eq( "num", 0 ), new stmt() {
				@Override
				protected final void body() {
					ireturn( 0 );
				}
			} ).
			else_( new stmt() {
				@Override
				protected final void body() {
					ireturn( -1 );
				}
			} );
		
		Conditional signum = classWriter.newInstance();
		assertThat( signum.exec( 10 ), is( 1 ) );
		assertThat( signum.exec( 0 ), is( 0 ) );
		assertThat( signum.exec( -10 ), is( -1 ) );		
	}
	
	@Test
	public final void if_() {
		JvmWriter writer = new JvmWriter();
		
		JvmClassWriter classWriter = writer.define(
			public_().final_().class_( "Abs" ).extends_( Conditional.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( int_, "exec" ).arg( int_, "num" ) ).
			if_( ge( "num", 0 ), new stmt() {
				@Override
				protected final void body() {
					ireturn( "num" );
				}
			} ).
			ineg( "num" ).
			ireturn();
		
		Conditional abs = classWriter.newInstance();
		assertThat( abs.exec( 10 ), is( 10 ) );
		assertThat( abs.exec( 0 ), is( 0 ) );
		assertThat( abs.exec( -10 ), is( 10 ) );
	}
	
	@Test
	public final void if_non_terminal() {
		JvmWriter writer = new JvmWriter();
		
		JvmClassWriter classWriter = writer.define(
			public_().final_().class_( "Abs" ).extends_( Conditional.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( int_, "exec" ).arg( int_, "num" ) ).
			if_( ge( "num", 0 ), new stmt() {
				@Override
				protected final void body() {
					ireturn( "num" );
				}
			} ).else_( new stmt() {
				@Override
				protected final void body() {
					ineg( "num" ).
					ireturn();
				}				
			} );
		
		Conditional abs = classWriter.newInstance();
		assertThat( abs.exec( 10 ), is( 10 ) );
		assertThat( abs.exec( 0 ), is( 0 ) );
		assertThat( abs.exec( -10 ), is( 10 ) );
	}
	
	public static abstract class Conditional {
		public int exec( final int num ) {
			throw new UnsupportedOperationException();
		}
	}
}
