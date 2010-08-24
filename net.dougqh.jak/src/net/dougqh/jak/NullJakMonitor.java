package net.dougqh.jak;

final class NullJakMonitor implements JakMonitor {
	@Override
	public final JavaCoreCodeWriter monitor( final JavaCoreCodeWriter wrappedWriter ) {
		return wrappedWriter;
	}
	
	@Override
	public final Locals monitor( final Locals locals ) {
		return locals;
	}
	
	@Override
	public final Stack monitor( final Stack stack ) {
		return stack;
	}
}
