package net.dougqh.jak.api;

import static net.dougqh.jak.JavaAssembler.*;
import net.dougqh.jak.JavaClassWriter;

import org.junit.Test;

public final class ClassLoaderTest {
	public final @Test void classLoader() {
		JavaClassWriter classWriter = define( class_( "NewClass" ) );
		classWriter.load( new ClassLoader() {} );
	}
	
	public final @Test void sameClassLoader() {
		JavaClassWriter outerClassWriter = define( public_().final_().class_( "Outer" ) );
		JavaClassWriter innerClassWriter = outerClassWriter.define( class_( "Inner" ) );
		
		ClassLoader classLoader = new ClassLoader() {};
		outerClassWriter.load( classLoader );
		innerClassWriter.load( classLoader );
	}
	
	public final @Test void mixedClassLoader() {
		JavaClassWriter outerClassWriter = define( public_().final_().class_( "Outer" ) );
		JavaClassWriter innerClassWriter = outerClassWriter.define( class_( "Inner" ) );
		
		try {
			outerClassWriter.load( new ClassLoader() {} );
			innerClassWriter.load( new ClassLoader() {} );
		} catch ( IllegalStateException e ) {
			//Should raise an exception because two connected classes are 
			//being loaded by two different ClassLoaders.
		}
	}
	
	public final @Test void newInstance() {
		JavaClassWriter classWriter = define( public_().class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		classWriter.newInstance( new ClassLoader() {} );
	}
	
	public final @Test void newInstanceVisibilityException() {
		JavaClassWriter classWriter = define( class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		try {
			classWriter.newInstance();
		} catch ( IllegalStateException e ) {
			//Should raise an exception because class lacks public visibility
		}
	}
	
	public final @Test void newInstanceAbstractException() {
		JavaClassWriter classWriter = define( public_().abstract_().class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		try {
			classWriter.newInstance();
		} catch ( IllegalStateException e ) {
			//Should raise an exception because class is abstract
		}
	}
	
	public final @Test void newInstanceVisibilityExceptionWithClassLoader() {
		JavaClassWriter classWriter = define( class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		try {
			classWriter.newInstance( new ClassLoader() {} );
		} catch ( IllegalStateException e ) {
			//Should raise an exception because class lacks public visibility
		}
	}
	
	public final @Test void newInstanceAbstractExceptionWithClassLoader() {
		JavaClassWriter classWriter = define( public_().abstract_().class_( "NewClass" ) );
		classWriter.defineDefaultConstructor();
		
		try {
			classWriter.newInstance( new ClassLoader() {} );
		} catch ( IllegalStateException e ) {
			//Should raise an exception because class is abstract
		}
	}	
}
