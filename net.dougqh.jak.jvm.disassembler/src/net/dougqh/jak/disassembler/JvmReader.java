package net.dougqh.jak.disassembler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import net.dougqh.iterable.Aggregator;
import net.dougqh.iterable.Aggregator.Scheduler;
import net.dougqh.iterable.Aggregator.Task;
import net.dougqh.jak.disassembler.ClassLocator.ClassBlock;
import net.dougqh.jak.disassembler.ClassLocator.ClassProcessor;

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
		Aggregator<ClassBlock> reactor = new Aggregator<ClassBlock>();
		reactor.initialize(new Task<ClassBlock>() {
			@Override
			public final void run(final Scheduler<ClassBlock> scheduler) throws Exception {
				JvmReader.this.locators.enumerate(scheduler);
			}
		});
		
		final ArrayList<JvmType> types = new ArrayList<JvmType>();
		for ( Iterator<ClassBlock> iter = reactor.iterator(); iter.hasNext(); ) {
			ClassBlock block = iter.next();
			
			try {
				block.process(new ClassProcessor() {
					@Override
					public void process(final InputStream in) throws IOException {
						types.add(JvmType.create(in));
					}
				});
			} catch (IOException e) {
				//TODO: Temporary until the transform can be moved into the reactor
				throw new IllegalStateException(e);
			}
		}
		return Collections.unmodifiableList(types);
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
