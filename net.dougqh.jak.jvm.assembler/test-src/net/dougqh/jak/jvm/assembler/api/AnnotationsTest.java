package net.dougqh.jak.jvm.assembler.api;

import static net.dougqh.jak.Jak.*;
import static net.dougqh.jak.matchers.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

import net.dougqh.jak.jvm.assembler.JvmAnnotationWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

import org.junit.Test;

public final class AnnotationsTest {
	public final @Test void trivialAnnotation() {
		JvmAnnotationWriter annotationWriter = 
			new JvmWriter().define( public_().$interface( "Annotation" ) );
		
		annotationWriter.define( field( String.class, "value" ) );
		
		Class< ? > annotationClass = annotationWriter.load();
		assertThat( annotationClass.isAnnotation(), is( true ) );
		
		Method valueMethod = getMethod( annotationClass, "value" );
		assertThat( valueMethod.getReturnType(), is( String.class ) );
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
