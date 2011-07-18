package net.dougqh.jak.disassembler;

import java.io.File;

public final class Tests {
	private static final File TESTDATA_DIR = new File( "testdata" );
	
	public static final File jar( final String jarName ) {
		return new File( TESTDATA_DIR, jarName );
	}
	
	public static final File dir( final String dirName ) {
		return new File( TESTDATA_DIR, dirName );
	}
	
	private Tests() {}
}
