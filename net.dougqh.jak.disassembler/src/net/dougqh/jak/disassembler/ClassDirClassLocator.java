package net.dougqh.jak.disassembler;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

import net.dougqh.iterable.RecursiveIterable;
import net.dougqh.iterable.TransformIterable;

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
	public final Iterable< InputStream > list() {
		Iterable< File > classFiles = new RecursiveIterable< File, File >( this.dir ) {
			@Override
			protected final Iterable< File > branches( final File dir ) {
				File[] dirs = dir.listFiles( DIR_FILTER );
				if ( dirs == null || dirs.length == 0 ) {
					return Collections.< File >emptyList();
				} else {
					return Arrays.asList( dirs );
				}
			}
		
			@Override
			protected final Iterable< File > leaves( final File dir ) {
				File[] classFiles = dir.listFiles( CLASS_FILTER );
				if ( classFiles == null || classFiles.length == 0 ) {
					return Collections.< File >emptyList();
				} else {
					return Arrays.asList( classFiles );
				}
			}
		};

		return new TransformIterable< File, InputStream >( classFiles ) {
			@Override
			protected final InputStream transform( final File input ) {
				try {
					return new FileInputStream( input );
				} catch ( IOException e ) {
					throw new IllegalStateException( e );
				}
			}
		};	
	}
}
