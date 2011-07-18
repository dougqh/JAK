package net.dougqh.jak.disassembler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.dougqh.io.WrappedInputStream;
import net.dougqh.iterable.TransformIterable;

final class JarClassLocator implements ClassLocator {
	private final File file;
	
	JarClassLocator( final File file ) {
		this.file = file;
	}
	
	private final List< JarEntry > entries() throws IOException {
		JarFile jarFile = new JarFile( this.file );
		try {
			ArrayList< JarEntry > entries = new ArrayList< JarEntry >( jarFile.size() );
			
			for ( Enumeration< JarEntry > entryEnum = jarFile.entries();
				entryEnum.hasMoreElements(); )
			{
				JarEntry entry = entryEnum.nextElement();
				if ( entry.getName().endsWith( ".class" ) ) {
					entries.add( entry );
				}
			}
			
			return Collections.unmodifiableList( entries );
		} finally {
			jarFile.close();
		}
	}
	
	@Override
	public final Iterable< InputStream > list() throws IOException {
		return new TransformIterable< JarEntry, InputStream >( this.entries() ) {
			@Override
			protected final InputStream transform( final JarEntry input ) {
				try {
					return JarClassLocator.this.loadEntry( input.getName() );
				} catch ( IOException e ) {
					throw new IllegalStateException( e );
				}
			}
		};
	}
	
	@Override
	public final InputStream load( final String className ) throws IOException {
		return this.loadEntry( className.replace( '.', '/' ) + ".class" );
	}
	
	private final InputStream loadEntry( final String entryName ) throws IOException {
		final JarFile jarFile = new JarFile( this.file );
		JarEntry entry = jarFile.getJarEntry( entryName );
		InputStream in = jarFile.getInputStream( entry );
		
		return new WrappedInputStream( in ) {
			@Override
			public final void close() throws IOException {
				jarFile.close();
				super.close();
			}
		};
	}
}
