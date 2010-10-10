package net.dougqh.jak.repl;

import net.dougqh.jak.JakMonitor;
import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Locals;
import net.dougqh.jak.Stack;

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
	public final Locals monitor( final Locals locals ) {
		return locals;
	}
	
	@Override
	public final Stack monitor( final Stack stack ) {
		return new ReplStack( this.repl, stack );
	}
}