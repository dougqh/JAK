package net.dougqh.jak.disassembler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import net.dougqh.aggregator.Aggregator;
import net.dougqh.aggregator.InputProvider;
import net.dougqh.aggregator.InputScheduler;
import net.dougqh.aggregator.OutputChannel;
import net.dougqh.aggregator.Processor;
import net.dougqh.aggregator.SimpleInputProcessor;
import net.dougqh.functional.Filter;
import net.dougqh.jak.disassembler.ClassLocator.ClassBlock;
import net.dougqh.jak.disassembler.ClassLocator.ClassProcessor;

public final class JvmReader {
	private final CompositeClassLocator locators = new CompositeClassLocator();
	
	public JvmReader() {}
	
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
	
	public final JvmTypeSet classes() {
		return new JvmTypeSetImpl(new ClassBlockProcessor());
	}
	
	public final < T extends JvmType > T read( final Class<?> type ) throws IOException {
		return this.<T>read( type.getName() );
	}
	
	private final class JvmTypeSetImpl extends JvmTypeSet {
		private final Processor<ClassBlock, JvmType> processor;
		
		public JvmTypeSetImpl(final Processor<ClassBlock, JvmType> processor) {
			this.processor = processor;
		}
		
		@Override
		public final JvmType get(final String name) {
			try {
				return JvmReader.this.read(name);
			} catch ( IOException e ) {
				throw new IllegalStateException(e);
			}
		}
		
		@Override
		public final JvmTypeSet filter(final Filter<? super JvmType> filter) {
			return new JvmTypeSetImpl(this.processor.filter(filter));
		}
		
		@Override
		public final Iterator<JvmType> iterator() {
			Aggregator<ClassBlock, JvmType> aggregator = new Aggregator<ClassBlock, JvmType>(this.processor);
			aggregator.initialize(new RootInputProvider());
			return aggregator.iterator();
		}
	}
	
	private final class RootInputProvider implements InputProvider<ClassBlock> {
		@Override
		public final void run(final InputScheduler<ClassBlock> scheduler) throws Exception {
			JvmReader.this.locators.enumerate(scheduler);
		}
	}
	
	private static final class ClassBlockProcessor extends SimpleInputProcessor<ClassBlock, JvmType> {
		@Override
		public final void process(final ClassBlock classBlock, final OutputChannel<? super JvmType> out)
			throws Exception
		{
			classBlock.process(new ClassProcessor() {
				@Override
				public final void process(final InputStream in) throws IOException {
					out.offer(JvmType.create(in));
				}
			});
		}
	}
}
