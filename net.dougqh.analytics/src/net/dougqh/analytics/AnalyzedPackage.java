package net.dougqh.analytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AnalyzedPackage {
	private final AnalyzedSource source;
	private final String name;
	private final List< AnalyzedType > types = new ArrayList< AnalyzedType >();
	
	public AnalyzedPackage(
		final AnalyzedSource source,
		final String name )
	{
		this.source = source;
		this.name = name;
	}
	
	public final AnalyzedSource getSource() {
		return this.source;
	}
	
	final void add( final AnalyzedType type ) {
		this.types.add( type );
	}
	
	public final List< AnalyzedType > getTypes() {
		return Collections.unmodifiableList( this.types );
	}
}
