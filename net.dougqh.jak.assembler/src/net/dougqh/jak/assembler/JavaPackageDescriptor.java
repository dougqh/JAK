package net.dougqh.jak.assembler;

public final class JavaPackageDescriptor {
	private final String name;
	
	JavaPackageDescriptor( final String name ) {
		this.name = name;
	}
	
	final String name() {
		return this.name;
	}
}
