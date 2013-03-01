package net.dougqh.jak.disassembler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import net.dougqh.iterable.Accumulator;
import net.dougqh.iterable.Accumulator.Scheduler;
import net.dougqh.iterable.Accumulator.Task;
import net.dougqh.iterable.InputStreamProvider;
import net.dougqh.iterable.TransformIterator;

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
	
	public final Iterable<JvmType> list() {
		return new Iterable<JvmType>() {
			@Override
			public final Iterator<JvmType> iterator() {
				Accumulator<InputStreamProvider> accumulator = new Accumulator<InputStreamProvider>();
				accumulator.initialize(new Task<InputStreamProvider>() {
					@Override
					public final void run(final Scheduler<InputStreamProvider> scheduler) throws Exception {
						JvmReader.this.locators.enumerate(scheduler);
					}
				});
			
				return new TransformIterator<InputStreamProvider, JvmType>(accumulator.iterator()) {
					protected final JvmType transform(final InputStreamProvider inputStreamProvider) {
						try {
							InputStream in = inputStreamProvider.open();
							try {
								return JvmType.create(in);
							} finally {
								in.close();
							}
						} catch ( IOException e ) {
							throw new IllegalStateException(e);
						}
					};
				};
			}
		};
	}
	
	public final <T extends JvmType> T read( final String name ) throws IOException {
		InputStream in = this.locator().load( name );
		try {
			@SuppressWarnings("unchecked")
			T result = (T)JvmType.create( in );
			return result;
		} finally {
			in.close();
		}
	}
	
	public final < T extends JvmType > T read( final Class<?> type ) throws IOException {
		return this.<T>read( type.getName() );
	}
}
