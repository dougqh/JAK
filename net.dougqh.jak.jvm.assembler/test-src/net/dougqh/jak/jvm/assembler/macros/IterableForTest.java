package net.dougqh.jak.jvm.assembler.macros;

import static net.dougqh.jak.Jak.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

import org.junit.Test;

public final class IterableForTest {
	@Test
	public final void overrideBody() {
		JvmWriter jvmWriter = new JvmWriter();
		
		JvmClassWriter classWriter = jvmWriter.define(
			public_().final_().class_("foo").implements_(IterableOperation.class) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "exec" ).arg( Iterable.class, "iterable" ) ).
			ideclare( "sum" ).
			istore( 0, "sum" ).
			iterable_for( Integer.class, "x", "iterable", new stmt() {
				@Override
				protected final void body() {
					iload( "sum" ).
					iunbox( "x" ).
					iadd().
					istore( "sum" );
				}				
			} ).
			ibox( "sum" ).
			areturn();
		
		IterableOperation op = classWriter.<IterableOperation>newInstance();
		assertThat( (Integer)op.exec( Arrays.asList( 1,2,3,4 ) ), is( 10 ) );
	}
	
	@Test
	public final void provideBody() {
		JvmWriter jvmWriter = new JvmWriter();
		
		JvmClassWriter classWriter = jvmWriter.define(
			public_().final_().class_("foo").implements_(IterableOperation.class) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "exec" ).arg( Iterable.class, "iterable" ) ).
			ideclare( "sum" ).
			istore( 0, "sum" ).
			macro( new IterableFor( Integer.class, "x", "iterable", new stmt() {
				@Override
				protected final void body() {
					iload( "sum" ).
					iunbox( "x" ).
					iadd().
					istore( "sum" );
				}
			}) ).
			ibox( "sum" ).
			areturn();
		
		IterableOperation op = classWriter.<IterableOperation>newInstance();
		assertThat( (Integer)op.exec( Arrays.asList( 1,2,3,4 ) ), is( 10 ) );
	}

	public static interface IterableOperation {
		public abstract Object exec( final Iterable<?> iterable );
	}
}
