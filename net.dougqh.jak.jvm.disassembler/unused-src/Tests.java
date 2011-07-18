package net.dougqh.jak.disassembler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.dougqh.jak.disassembler.api.JarTest;

public class Tests {
	public static final File jar( final String jarName ) {
		try {
			InputStream in = JarTest.class.getResourceAsStream( "/testdata/" + jarName );
			try {
				File tempFile = File.createTempFile( "test-", "-" + jarName );
				FileOutputStream out = new FileOutputStream( tempFile );
				try {
					byte[] bytes = new byte[ 1024 ];
					for ( int numRead = in.read( bytes );
						numRead != -1;
						numRead = in.read( bytes ) )
					{
						out.write( bytes, 0, numRead );
					}
				} finally {
					out.close();
				}
				tempFile.deleteOnExit();
				return tempFile;
			} finally {
				in.close();
			}
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
	}
}
