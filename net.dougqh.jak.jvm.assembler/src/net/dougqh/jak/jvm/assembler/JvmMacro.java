package net.dougqh.jak.jvm.assembler;

import net.dougqh.jak.assembler.JakMacro;

public abstract class JvmMacro
	extends JvmCodeWriter
	implements JakMacro
{	
	private JvmCodeWriter codeWriter;
	private boolean terminal = false;
	
	protected boolean defer() {
		return false;
	}
	
	protected final boolean terminal() {
		if ( ! this.defer() ) {
			throw new IllegalStateException( "JakMacro-s must be deferred to check terminal status" );
		}
		return this.terminal;
	}
	
	protected final void write(
		final JvmCodeWriter codeWriter,
		final boolean terminal )
	{
		this.codeWriter = codeWriter;
		this.terminal = terminal;
		try {
			this.write();
		} finally {
			this.codeWriter = null;
		}
	}
	
	protected abstract void write();
	
	protected final JvmCodeWriter codeWriter() {
		return this.codeWriter;
	}
	
	@Override
	protected final CodeWritingState sharedState() {
		return this.codeWriter.sharedState();
	}
	
	@Override
	public final JvmCoreCodeWriter coreWriter() {
		return this.codeWriter.coreWriter();
	}
}