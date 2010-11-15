package net.dougqh.jak.repl;

import net.dougqh.jak.assembler.JakMonitor;
import net.dougqh.jak.assembler.JavaCoreCodeWriter;
import net.dougqh.jak.assembler.LocalsMonitor;
import net.dougqh.jak.assembler.StackMonitor;

final class ReplMonitor extends JakMonitor {
	private final JakRepl repl;
	
	ReplMonitor( final JakRepl repl ) {
		this.repl = repl;
	}
	
	@Override
	public final JavaCoreCodeWriter monitor( final JavaCoreCodeWriter wrappedWriter ) {
		return new WriterDelegate( this.repl, wrappedWriter ).getProxy();
	}
	
	@Override
	public final LocalsMonitor monitor( final LocalsMonitor locals ) {
		return new ReplLocalsMonitor( this.repl, locals );
	}
	
	@Override
	public final StackMonitor monitor( final StackMonitor stack ) {
		return new ReplStackMonitor( this.repl, stack );
	}
}