package net.dougqh.jak.disassembler;

import java.io.IOException;

public final class ClassFileFormatException extends IOException {
	private static final long serialVersionUID = 1L;

	public ClassFileFormatException( final String msg ) {
		super( msg );
	}
}
