package net.dougqh.jak.jvm.assembler.api;

import java.io.IOException;

public abstract class TestClassLoader extends ClassLoader {
	protected final String className;
	
	public TestClassLoader( 
		final ClassLoader parentLoader,
		final String className )
	{
		super( parentLoader );
		
		this.className = className;
	}
	
	public final Class< ? > loadClass() {
		try {
			return this.loadClass( this.className );
		} catch ( ClassNotFoundException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	@SuppressWarnings( "unchecked" )
	public final < T > T newInstance() {
		try {
			return (T)this.loadClass().newInstance();
		} catch ( InstantiationException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	@SuppressWarnings( "unchecked" )
	public final < T > T newInstance( final String name )
		throws ClassNotFoundException, IllegalAccessException, InstantiationException
	{
		return (T)this.loadClass( name ).newInstance();
	}
	
	@Override
	protected final synchronized Class<?> loadClass(
		final String name,
		final boolean resolve )
		throws ClassNotFoundException
	{
		if ( name.equals( this.className ) ) {
			try {
				byte[] classBytes = this.generateClass();
				return this.defineClass(
					name,
					classBytes,
					0,
					classBytes.length );
			} catch ( IOException e ) {
				throw new ClassNotFoundException( name, e );
			}
		} else {
			return super.loadClass( name, resolve );
		}
	}
	
	protected abstract byte[] generateClass() throws IOException;
}
