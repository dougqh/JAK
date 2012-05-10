package net.dougqh.jak.jvm.assembler;

import static net.dougqh.jak.Jak.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import net.dougqh.jak.jvm.assembler.JvmCodeWriter.Scope;

import org.junit.Test;

public final class ScopeTest {
	@Test
	public final void trivial() {
		Scope scope = new Scope();
		scope.set( "foo", 10 );
		
		assertThat( scope.isDefined( "foo" ), is( true ) );
		assertThat( scope.get( "foo" ), is( 10 ) );
	}
	
	@Test
	public final void alias() {
		Scope scope = new Scope();
		scope.set( "foo", 10 );
		scope.alias( "bar", "foo" );
		
		assertThat( scope.isDefined( "bar" ), is( true ) );
		assertThat( scope.get( "bar" ), is( 10 ) );
	}
	
	@Test
	public final void subScope() {
		Scope outerScope = new Scope();
		outerScope.set( "foo", 10 );
		
		Scope innerScope = outerScope.startScope();
		innerScope.set( "foo", 20 );
		
		assertThat( innerScope.get( "foo" ), is( 20 ) );
		assertThat( outerScope.get( "foo" ), is( 10 ) );
		
		assertThat( innerScope.endScope(), is( outerScope ) );
	}
	
	@Test
	public final void subScopeAlias() {
		Scope outerScope = new Scope();
		outerScope.set( "foo", 10 );
		outerScope.alias( "bar", "foo" );
		
		Scope innerScope = outerScope.startScope();
		innerScope.set( "foo", 20 );
		
		assertThat( innerScope.get( "foo" ), is( 20 ) );
		assertThat( innerScope.get( "bar" ), is( 10 ) );
	}
	
	@Test
	public final void slotAssignment() {
		JvmClassWriter writer = new JvmWriter().define( public_().class_( "Main" ) );
		
		JvmCodeWriter codeWriter = writer.define( public_().static_().method( int_, "main" ).arg( String[].class, "args" ) );
		
		codeWriter.ideclare( "x" );
		assertThat( codeWriter.getVarSlot( "x" ), is( 1 ) );
		
		codeWriter.startScope();
		try {
			codeWriter.ideclare( "x" );
			assertThat( codeWriter.getVarSlot( "x" ), is( 2 ) );
		} finally {
			codeWriter.endScope();
		}
		
		codeWriter.startScope();
		try {
			codeWriter.ideclare( "y" );
			assertThat( codeWriter.getVarSlot( "y" ), is( 2 ) );
			
			codeWriter.ideclare( "x" );
			assertThat( codeWriter.getVarSlot( "x" ), is( 3 ) );
		} finally {
			codeWriter.endScope();
		}
		
		assertThat( codeWriter.getVarSlot( "x" ), is( 1 ) );
	}
}
