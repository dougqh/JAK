package net.dougqh.analytics;


public abstract class Analysis< T > {
	protected void visitSource( final AnalyzedSource source ) {
		this.visitPackagesOf( source );
	}
	
	protected final void visitPackagesOf( final AnalyzedSource source ) {
		for ( AnalyzedPackage aPackage : source.getPackages() ) {
			this.visitPackage( aPackage );
		}
	}
	
	protected void visitPackage( final AnalyzedPackage aPackage ) {
		this.visitTypesOf( aPackage );
	}
	
	protected final void visitTypesOf( final AnalyzedPackage aPackage ) {
		for ( AnalyzedType type : aPackage.getTypes() ) {
			this.visitType( type );
		}
	}
	
	protected void visitType( final AnalyzedType type ) {
	}
	
	protected T getResult( final AnalysisSet set ) {
		return null;
	}
}
