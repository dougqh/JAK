package net.dougqh.jak.disassembler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.dougqh.iterable.TransformIterable;

public final class JvmReader {
	private final CompositeClassLocator locators = new CompositeClassLocator();
	
	public JvmReader() {}
	
	public JvmReader( final ClassLoader classLoader ) {
		this.addClassLoader( classLoader );
	}
	
	public final JvmReader addJar( final String file ) {
		return this.addJar( new File( file ) );
	}
	
	public final JvmReader addJar( final File jar ) {
		return this.add( new JarClassLocator( jar ) );
	}
	
	public final JvmReader addDir( final String file ) {
		return this.addDir( new File( file ) );
	}
	
	public final JvmReader addDir( final File dir ) {
		return this.add( new ClassDirClassLocator( dir ) );
	}
	
	public final JvmReader addClassLoader( final ClassLoader classLoader ) {
		return this.add( new ClassLoaderClassLocator( classLoader ) );
	}
	
	private final JvmReader add( final ClassLocator locator ) {
		this.locators.add( locator );
		return this;
	}
	
	private final ClassLocator locator() {
		if ( this.locators.isEmpty() ) {
			this.addClassLoader( JvmReader.class.getClassLoader() );
		}
		return this.locators;
	}
	
	public final Iterable< JvmType > list() {
		try {
			return new TransformIterable< InputStream, JvmType >( this.locator().list() ) {
				@Override
				protected final JvmType transform( final InputStream in ) {
					try {
						try {
							return JvmType.create( in );
						} finally {
							in.close();
						}
					} catch ( IOException e ) {
						throw new IllegalStateException( e );
					}
				}
			};
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	public final JvmType read( final String name ) throws IOException {
		InputStream in = this.locator().load( name );
		try {
			return JvmType.create( in );
		} finally {
			in.close();
		}
	}
}
