package net.dougqh.jak.disassembler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.dougqh.aggregator.InputScheduler;
import net.dougqh.io.WrappedInputStream;

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
	public final void enumerate(final InputScheduler<ClassBlock> scheduler)
		throws InterruptedException
	{
		//TODO: Switch to use tasks that register multiple blocks up to a given size.
		scheduler.offer(new JarClassBlock());
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
	
	private final class JarClassBlock implements ClassBlock {
		public final void process(final ClassProcessor processor) throws IOException {
			// Unfortunately, the thread-safety of JarFile seems murky, so this ends up 
			// open and closing the file N + 1 times for each type.  The only alternative 
			// would seem to be published batch into the accumulator rather than just a 
			// single element.
			JarFile jarFile = JarClassLocator.this.openJarFile();
			try {
				for ( Enumeration<JarEntry> entryEnum = jarFile.entries(); entryEnum.hasMoreElements(); ) {
					JarEntry entry = entryEnum.nextElement();
					
					processor.process(jarFile.getInputStream(entry));
				}
			} finally {
				jarFile.close();
			}
		}
	}
}
