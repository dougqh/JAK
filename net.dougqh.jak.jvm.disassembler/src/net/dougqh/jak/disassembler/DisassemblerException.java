package net.dougqh.jak.disassembler;

import java.io.IOException;

public class DisassemblerException extends RuntimeException {
	private static final long serialVersionUID = -3029705351195189837L;
	
	public DisassemblerException( final IOException e ) {
		super( e );
	}
}
