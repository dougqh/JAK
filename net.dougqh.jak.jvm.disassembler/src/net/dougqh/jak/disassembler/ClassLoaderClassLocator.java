package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

final class ClassLoaderClassLocator implements ClassLocator {
	private final ClassLoader classLoader;
	
	ClassLoaderClassLocator( final ClassLoader classLoader ) {
		this.classLoader = classLoader;
	}
	
	@Override
	public final Iterable< InputStream > list() {
		return Arrays.< InputStream >asList();
	}
	
	@Override
	public final InputStream load( final String className )
		throws IOException
	{
		return this.classLoader.getResourceAsStream( className.replace( '.', '/' ) + ".class" );
	}
}
