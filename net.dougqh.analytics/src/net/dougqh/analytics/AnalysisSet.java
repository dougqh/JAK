package net.dougqh.analytics;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dougqh.jak.disassembler.JavaType;
import net.dougqh.jak.disassembler.JvmReader;

public final class AnalysisSet {
	private final JvmReader reader;
	
	private final Map< String, AnalyzedPackage > packages = 
		new HashMap< String, AnalyzedPackage >();
	private final Map< String, AnalyzedType > types =
		new HashMap< String, AnalyzedType >();
	
	public AnalysisSet() {
		this.reader = new JvmReader();
	}
	
	public final AnalysisSet addJar( final String file ) {
		this.reader.addJar( file );
		return this;
	}
	
	public final AnalysisSet addJar( final File jar ) {
		this.reader.addJar( jar );
		return this;
	}
	
	public final AnalysisSet addDir( final String file ) {
		this.reader.addDir( new File( file ) );
		return this;
	}
	
	public final AnalysisSet addDir( final File dir ) {
		this.reader.addDir( dir );
		return this;
	}
	
	private final void load() {
		for ( JavaType type : this.reader.list() ) {
			Name name = new Name( type.getName() );
			AnalyzedPackage aPackage = this.getOrCreatePackage( name.packageName );

			AnalyzedType analyzedType = new AnalyzedType( this, type );
			this.types.put( type.getName(), analyzedType );
			
			
		}
	}
	
	public final List< AnalyzedPackage > getPackages() {
		this.load();
		return list( this.packages );
	}
	
	public final List< AnalyzedType > getTypes() {
		this.load();
		return list( this.types );
	}
	
	private static final < T > List< T > list( final Map< ?, ? extends T > map ) {
		return Collections.unmodifiableList( new ArrayList< T >( map.values() ) );
	}
	
	static final class Name {
		public final String packageName;
		public final String shortName;
		
		public Name( final String fullName ) {
			int lastDotPos = fullName.lastIndexOf( '.' );
			if ( lastDotPos == -1 ) {
				this.packageName = null;
				this.shortName = fullName;
			} else {
				this.packageName = fullName.substring( 0, lastDotPos );
				this.shortName = fullName.substring( lastDotPos + 1 );
			}
		}
	}
}
