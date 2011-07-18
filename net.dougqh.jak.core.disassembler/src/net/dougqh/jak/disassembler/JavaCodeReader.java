package net.dougqh.jak.disassembler;

import java.io.IOException;

import net.dougqh.jak.JavaOperation;

public interface JavaCodeReader {
	public abstract boolean hasNext() throws IOException;
	
	public abstract JavaOperation next() throws IOException;
}