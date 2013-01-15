package net.dougqh.jak.repl;

import net.dougqh.jak.jvm.JvmLocals;
import net.dougqh.jak.jvm.JvmStack;
import net.dougqh.jak.jvm.assembler.JakMonitor;
import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

final class ReplMonitor extends JakMonitor {
	private final JakRepl repl;
	
	ReplMonitor( final JakRepl repl ) {
		this.repl = repl;
	}
	
	@Override
	public final JvmCoreCodeWriter monitor( final JvmCoreCodeWriter wrappedWriter ) {
		return new WriterDelegate( this.repl, wrappedWriter ).getProxy();
	}
	
	@Override
	public final JvmLocals monitor( final JvmLocals locals ) {
		return new ReplLocals( this.repl, locals );
	}
	
	@Override
	public final JvmStack monitor( final JvmStack stack ) {
		return new ReplJvmStack( this.repl, stack );
	}
}