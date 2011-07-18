package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.io.InputStream;

public interface ClassLocator {
	public abstract Iterable< InputStream > list() throws IOException;
	
	public abstract InputStream load( final String className ) throws IOException;
}
