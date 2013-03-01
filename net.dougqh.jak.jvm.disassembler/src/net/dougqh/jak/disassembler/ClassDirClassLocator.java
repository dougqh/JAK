package net.dougqh.jak.disassembler;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import net.dougqh.iterable.Accumulator;
import net.dougqh.iterable.InputStreamProvider;

final class ClassDirClassLocator implements ClassLocator {
	private static final FileFilter DIR_FILTER = new FileFilter() {
		@Override
		public final boolean accept( final File file ) {
			return file.isDirectory();
		}
	};
	
	private static final FilenameFilter CLASS_FILTER = new FilenameFilter() {
		@Override
		public final boolean accept( final File dir, final String name ) {
			return name.endsWith( ".class" );
		}
	};
	
	private final File dir;
	
	ClassDirClassLocator( final File dir ) {
		this.dir = dir;
	}
	
	@Override
	public final InputStream load( final String className ) throws IOException {
		File classFile = new File( this.dir, className.replace( '.', '/' ) + ".class" );
		return new FileInputStream( classFile );
	}
	
	@Override
	public final void enumerate(final Accumulator.Scheduler<InputStreamProvider> scheduler)
		throws InterruptedException
	{
		scheduler.schedule(new DirTask(this.dir));
	}
	
	private static final class DirTask implements Accumulator.Task<InputStreamProvider> {
		private final File dir;
		
		public DirTask(final File dir) {
			this.dir = dir;
		}
		
		@Override
		public final void run(final Accumulator.Scheduler<InputStreamProvider> scheduler) throws Exception {
			File[] subDirs = this.dir.listFiles( DIR_FILTER );
			if ( subDirs != null ) {
				for ( File subDir: subDirs ) {
					scheduler.schedule(new DirTask(subDir));
				}
			}
			
			File[] classFiles = dir.listFiles( CLASS_FILTER );
			if ( classFiles != null ) {
				for ( File classFile: classFiles ) {
					scheduler.result(new FileInputStreamProvider(classFile));
				}
			}
		}
	}
	
	private static final class FileInputStreamProvider implements InputStreamProvider {
		private final File file;
		
		public FileInputStreamProvider(final File file) {
			this.file = file;
		}
		
		@Override
		public final InputStream open() throws IOException {
			return new FileInputStream(this.file);
		}
	}
}
