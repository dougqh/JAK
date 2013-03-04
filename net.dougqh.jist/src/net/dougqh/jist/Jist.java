package net.dougqh.jist;

import java.io.File;

import net.dougqh.jak.disassembler.JvmReader;

public final class Jist {
	private final JvmReader reader = new JvmReader();
	
	public Jist() {}
	
	public final Jist addJar(final String file) {
		return this.addJar(new File(file));
	}
	
	public final Jist addJar(final File jar) {
		return this.add(new JarClassLocator(jar));
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
}
