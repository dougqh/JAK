package net.dougqh.jak.jvm.assembler.api;

import static net.dougqh.jak.Jak.*;
import static org.junit.Assert.*;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

import org.junit.Test;

public final class ClassLoaderTest {
	public final @Test void classLoader() {
		JvmClassWriter classWriter = new JvmWriter().define(
			class_( "NewClass" ) );
		classWriter.load();
	}
	
	public final @Test void sameClassLoader() {
		JvmClassWriter outerClassWriter = new JvmWriter().define(
			public_().final_().class_( "Outer" ) );
		JvmClassWriter innerClassWriter = outerClassWriter.define( class_( "Inner" ) );

		Class< ? > outerClass = outerClassWriter.load();
		Class< ? > innerClass = innerClassWriter.load();
		
		assertSame( outerClass.getClassLoader(), innerClass.getClassLoader() );
	}
	
	public final @Test void newInstance() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		classWriter.newInstance();
	}
	
	public final @Test void newInstanceVisibilityException() {
		JvmClassWriter classWriter = new JvmWriter().define(
			class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		try {
			classWriter.newInstance();
		} catch ( IllegalStateException e ) {
			//Should raise an exception because class lacks public visibility
		}
	}
	
	public final @Test void newInstanceAbstractException() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().abstract_().class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		try {
			classWriter.newInstance();
		} catch ( IllegalStateException e ) {
			//Should raise an exception because class is abstract
		}
	}
	
	public final @Test void newInstanceVisibilityExceptionWithClassLoader() {
		JvmClassWriter classWriter = new JvmWriter().define(
			class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		try {
			classWriter.newInstance();
		} catch ( IllegalStateException e ) {
			//Should raise an exception because class lacks public visibility
		}
	}
	
	public final @Test void newInstanceAbstractExceptionWithClassLoader() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().abstract_().class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		try {
			classWriter.newInstance();
		} catch ( IllegalStateException e ) {
			//Should raise an exception because class is abstract
		}
	}	
}
