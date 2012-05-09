package net.dougqh.analytics;

import java.util.List;

import net.dougqh.jak.disassembler.JavaType;

public final class AnalyzedType {
	private final AnalysisSet set;
	private final JavaType javaType;
	
	public AnalyzedType(
		final AnalysisSet set,
		final JavaType javaType )
	{
		this.set = set;
		this.javaType = javaType;
	}
	
	public final AnalyzedPackage getPackage() {
		return null;
		//return this.set.getPackage();
	}
	
	public final String getName() {
		return this.javaType.getName();
	}
	
	public final AnalyzedType getParent() {
		return this.set.asAnalyzed( this.javaType.getParentType() );
	}
	
	public final List< AnalyzedType > getInterfaces() {
		return this.set.asAnalyzed( this.javaType.getInterfaces() );
	}
	
	public final List< AnalyzedType > getReferencedTypes() {
		return this.set.asAnalyzed( this.javaType.getReferencedTypes() );
	}
}
