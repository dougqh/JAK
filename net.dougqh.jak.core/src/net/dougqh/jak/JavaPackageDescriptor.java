package net.dougqh.jak;

public final class JavaPackageDescriptor {
	private final String name;
	
	JavaPackageDescriptor( final String name ) {
		this.name = name;
	}
	
	public final String name() {
		return this.name;
	}
}
