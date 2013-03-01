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
import net.dougqh.iterable.Accumulator;
import net.dougqh.iterable.InputStreamProvider;

final class JarClassLocator implements ClassLocator {
	private final File file;
	
	JarClassLocator( final File file ) {
		this.file = file;
	}
	
	@Override
	public final InputStream load(final String className) throws IOException {
		return this.loadEntry(className.replace( '.', '/' ) + ".class");
	}
	
	@Override
	public final void enumerate(final Accumulator<InputStreamProvider> accumulator) {
		accumulator.schedule(new JarTask());
	}

	private final JarFile openJarFile() throws IOException {
		return new JarFile(this.file);
	}
	
	private final InputStream loadEntry(final String entryName) throws IOException {
		final JarFile jarFile = this.openJarFile();
		JarEntry entry = jarFile.getJarEntry(entryName);
		InputStream in = jarFile.getInputStream(entry);
		
		return new WrappedInputStream(in) {
			@Override
			public final void close() throws IOException {
				jarFile.close();
				super.close();
			}
		};
	}
	
	private final InputStream loadEntry(final JarEntry entry) throws IOException {
		final JarFile jarFile = this.openJarFile();
		InputStream in = jarFile.getInputStream(entry);
		
		return new WrappedInputStream(in) {
			@Override
			public final void close() throws IOException {
				jarFile.close();
				super.close();
			}
		};
	}
	
	private final class JarTask implements Accumulator.Task<InputStreamProvider> {
		@Override
		public final void run(final Accumulator<InputStreamProvider> accumulator) throws Exception {
			// Unfortunately, the thread-safety of JarFile seems murky, so this ends up 
			// open and closing the file N + 1 times for each type.  The only alternative 
			// would seem to be published batch into the accumulator rather than just a 
			// single element.
			JarFile jarFile = JarClassLocator.this.openJarFile();
			try {
				for ( Enumeration<JarEntry> entryEnum = jarFile.entries(); entryEnum.hasMoreElements(); ) {
					JarEntry entry = entryEnum.nextElement();
					
					accumulator.result(new JarEntryInputStreamProvider(entry));
				}
			} finally {
				jarFile.close();
			}
		}
	}
	
	private final class JarEntryInputStreamProvider implements InputStreamProvider {
		private final JarEntry entry;
		
		public JarEntryInputStreamProvider(final JarEntry entry) {
			this.entry = entry;
		}
		
		@Override
		public final InputStream open() throws IOException {
			return JarClassLocator.this.loadEntry(entry);
		}
	}
}
