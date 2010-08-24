package net.dougqh.jak;

final class NullJakMonitor implements JakMonitor {
	@Override
	public final JavaCoreCodeWriter monitor( final JavaCoreCodeWriter wrappedWriter ) {
		return wrappedWriter;
	}
}
