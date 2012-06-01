package net.dougqh.jak.jvm.assembler.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import net.dougqh.jak.jvm.assembler.JvmAnnotationWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

import org.junit.Test;

import static net.dougqh.jak.Jak.*;
import static net.dougqh.jak.matchers.Matchers.*;
import static org.junit.Assert.*;

public final class AnnotationsTest {
	@Test
	public final void trivialAnnotation() throws NoSuchMethodException {
		JvmAnnotationWriter annotationWriter = 
			new JvmWriter().define( public_().$interface( "Annotation" ) );
		
		annotationWriter.define( field( String.class, "value" ) );
		
		Class< ? > annotationClass = annotationWriter.load();
		assertThat( annotationClass.isAnnotation(), is( true ) );
		assertThat( Annotation.class.isAssignableFrom( annotationClass ), is( true ) );
		
		Method valueMethod = annotationClass.getMethod( "value" );
		assertThat( valueMethod.getReturnType(), is( String.class ) );
	}
}
