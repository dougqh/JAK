package net.dougqh.jak.assembler.api;

import static net.dougqh.jak.assembler.JavaAssembler.*;
import static org.junit.Assert.*;
import net.dougqh.jak.assembler.JavaClassWriter;
import net.dougqh.jak.assembler.JavaWriter;

import org.junit.Test;

public final class ClassLoaderTest {
	public final @Test void classLoader() {
		JavaClassWriter classWriter = new JavaWriter().define(
			class_( "NewClass" ) );
		classWriter.load();
	}
	
	public final @Test void sameClassLoader() {
		JavaClassWriter outerClassWriter = new JavaWriter().define(
			public_().final_().class_( "Outer" ) );
		JavaClassWriter innerClassWriter = outerClassWriter.define( class_( "Inner" ) );

		Class< ? > outerClass = outerClassWriter.load();
		Class< ? > innerClass = innerClassWriter.load();
		
		assertSame( outerClass.getClassLoader(), innerClass.getClassLoader() );
	}
	
	public final @Test void newInstance() {
		JavaClassWriter classWriter = new JavaWriter().define(
			public_().class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		classWriter.newInstance();
	}
	
	public final @Test void newInstanceVisibilityException() {
		JavaClassWriter classWriter = new JavaWriter().define(
			class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		try {
			classWriter.newInstance();
		} catch ( IllegalStateException e ) {
			//Should raise an exception because class lacks public visibility
		}
	}
	
	public final @Test void newInstanceAbstractException() {
		JavaClassWriter classWriter = new JavaWriter().define(
			public_().abstract_().class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		try {
			classWriter.newInstance();
		} catch ( IllegalStateException e ) {
			//Should raise an exception because class is abstract
		}
	}
	
	public final @Test void newInstanceVisibilityExceptionWithClassLoader() {
		JavaClassWriter classWriter = new JavaWriter().define(
			class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		try {
			classWriter.newInstance();
		} catch ( IllegalStateException e ) {
			//Should raise an exception because class lacks public visibility
		}
	}
	
	public final @Test void newInstanceAbstractExceptionWithClassLoader() {
		JavaClassWriter classWriter = new JavaWriter().define(
			public_().abstract_().class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		try {
			classWriter.newInstance();
		} catch ( IllegalStateException e ) {
			//Should raise an exception because class is abstract
		}
	}	
}
