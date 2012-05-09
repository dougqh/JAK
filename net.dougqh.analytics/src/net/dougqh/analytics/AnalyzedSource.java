package net.dougqh.analytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.dougqh.jak.disassembler.JavaSource;

public final class AnalyzedSource {
	private final JavaSource source;
	private final List< AnalyzedPackage > packages = new ArrayList< AnalyzedPackage >();
	
	AnalyzedSource( final JavaSource source ) {
		this.source = source;
	}
	
	public final void add( final AnalyzedPackage aPackage ) {
		this.packages.add( aPackage );
	}
	
	public final List< AnalyzedPackage > getPackages() {
		return Collections.unmodifiableList( this.packages );
	}
}
