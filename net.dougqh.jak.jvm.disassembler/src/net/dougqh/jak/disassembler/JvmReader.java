package net.dougqh.jak.disassembler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

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
	public interface Parallelizer {
		public abstract JvmTypeSet types();
		
		public abstract void shutdownAndWait();
	}
	
	private final CompositeClassLocator locators = new CompositeClassLocator();
	
	private volatile Executor executor;
	private volatile int numWorkers;
	
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
	
	public final <T extends JvmType> T read(final String name) throws IOException {
		InputStream in = this.locator().load( name );
		try {
			@SuppressWarnings("unchecked")
			T result = (T)JvmType.create( in );
			return result;
		} finally {
			in.close();
		}
	}
	
	public final Parallelizer parallelize() {
		// because the work is mostly I/O bound overuse threads
		return this.parallelize(Runtime.getRuntime().availableProcessors() * 4);
	}
	
	public final Parallelizer parallelize(final int numThreads) {
		return this.parallelize(createExecutor(numThreads), numThreads);
	}
	
	public final Parallelizer parallelize(final Executor executor, final int numWorkers) {
		return new ParallelizerImpl(executor, numWorkers);
	}
	
	private static final ExecutorService createExecutor(final int numThreads) {
		final ThreadGroup threadGroup = new ThreadGroup("jist");
		
		return Executors.newFixedThreadPool(numThreads, new ThreadFactory() {
			@Override
			public final Thread newThread(final Runnable runnable) {
				return new Thread(threadGroup, runnable);
			}
		});
	}
	
	public final JvmTypeSet types() {
		return new JvmTypeSetImpl(new ClassBlockProcessor(), new IteratorCreator() {
			@Override
			public final <O> Iterator<O> iterator(final Aggregator<ClassBlock, O> aggregator) {
				return aggregator.iterator();
			}
		});
	}
	
	abstract class IteratorCreator {
		public abstract <O> Iterator<O> iterator(final Aggregator<ClassBlock, O> aggregator);
	}
	
	public final < T extends JvmType > T read(final Class<?> type) throws IOException {
		return this.<T>read( type.getName() );
	}
	
	private final class JvmTypeSetImpl extends JvmTypeSet {
		private final Processor<ClassBlock, JvmType> processor;
		private final IteratorCreator iteratorCreator;
		
		public JvmTypeSetImpl(
			final Processor<ClassBlock, JvmType> processor,
			final IteratorCreator iteratorCreator)
		{
			this.processor = processor;
			this.iteratorCreator = iteratorCreator;
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
			return new JvmTypeSetImpl(this.processor.filter(filter), this.iteratorCreator);
		}
		
		@Override
		public final Iterator<JvmType> iterator() {
			Aggregator<ClassBlock, JvmType> aggregator = 
				new Aggregator<ClassBlock, JvmType>(new RootInputProvider(), this.processor);
			
			return this.iteratorCreator.iterator(aggregator);
		}
	}
	
	private final class RootInputProvider implements InputProvider<ClassBlock> {
		@Override
		public final void run(final InputScheduler<? super ClassBlock> scheduler) throws Exception {
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
	
	final class ParallelizerImpl implements Parallelizer {
		private final Executor executor;
		private final int numWorkers;
		
		ParallelizerImpl(final Executor executor, final int numWorkers) {
			this.executor = executor;
			this.numWorkers = numWorkers;
		}
		
		@Override
		public final JvmTypeSet types() {
			return new JvmTypeSetImpl(new ClassBlockProcessor(), new IteratorCreator() {
				@Override
				public final <O> Iterator<O> iterator(final Aggregator<ClassBlock, O> aggregator) {
					return aggregator.parallelIterator(ParallelizerImpl.this.executor, ParallelizerImpl.this.numWorkers);
				}
			});
		}
		
		@Override
		public final void shutdownAndWait() {
			if ( this.executor instanceof ExecutorService ) {
				ExecutorService executorService = (ExecutorService)this.executor;
				executorService.shutdown();
				try {
					executorService.awaitTermination(5, TimeUnit.MINUTES);
				} catch (InterruptedException e) {
					throw new IllegalStateException(e);
				}
			}
		}
	}
}
