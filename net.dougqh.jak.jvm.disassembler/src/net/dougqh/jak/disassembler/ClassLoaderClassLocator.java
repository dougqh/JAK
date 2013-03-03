package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.io.InputStream;

import net.dougqh.iterable.AggregatingPipeline;

final class ClassLoaderClassLocator implements ClassLocator {
	private final ClassLoader classLoader;
	
	ClassLoaderClassLocator( final ClassLoader classLoader ) {
		this.classLoader = classLoader;
	}
	
	@Override
	public void enumerate(final AggregatingPipeline.Scheduler<ClassBlock> scheduler) {
		//TODO: Implement this if the classloader is URLClassLoader
	}
	
	@Override
	public final InputStream load( final String className )
		throws IOException
	{
		return this.classLoader.getResourceAsStream( className.replace( '.', '/' ) + ".class" );
	}
}
