package net.dougqh.jak.assembler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public final class JavaAnnotationDescriptor {
	private final int flags;
	private final String name;
	
	JavaAnnotationDescriptor(
		final JavaFlagsBuilder flagsBuilder,
		final String name )
	{
		this.flags = flagsBuilder.flags();
		this.name = name;
	}
	
	final TypeDescriptor typeDescriptor() {
		return new TypeDescriptor(
			this.flags,
			this.name,
			Object.class,
			new Type[] { Annotation.class } );
	}
}
