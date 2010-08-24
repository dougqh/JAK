package net.dougqh.jak.api;

import static net.dougqh.jak.JavaAssembler.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

import net.dougqh.jak.JavaAnnotationWriter;

import org.junit.Test;

public final class CodeGenAnnotationsTest {
	public final @Test void trivialAnnotation() {
		JavaAnnotationWriter annotationWriter = define( public_().$interface( "Annotation" ) );
		
		annotationWriter.define( field( String.class, "value" ) );
		
		Class< ? > annotationClass = annotationWriter.load();
		assertTrue( annotationClass.isAnnotation() );
		
		Method valueMethod = getMethod( annotationClass, "value" );
		assertEquals( String.class, valueMethod.getReturnType() );
	}
	
	private static final Method getMethod(
		final Class< ? > annotationClass,
		final String methodName )
	{
		try {
			return annotationClass.getMethod( methodName );
		} catch ( NoSuchMethodException e ) {
			throw new IllegalStateException( e );
		}
	}
}
