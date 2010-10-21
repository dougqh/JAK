package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import net.dougqh.java.meta.types.JavaTypes;

final class ReplImports {
	private final HashMap< String, Class< ? > > classes = new HashMap< String, Class< ? > >( 4 );
	private final ArrayList< String > packages = new ArrayList< String >( 4 );
	
	final void reset() {
		this.classes.clear();
		this.packages.clear();
	}
	
	final void addImport( final String pattern )
		throws ClassNotFoundException
	{	
		if ( pattern.endsWith( ".*" ) ) {
			String packageName = pattern.substring( 0, pattern.length() - 2 );
			this.packages.add( packageName );
		} else {
			int lastDotPos = pattern.lastIndexOf( '.' );
			if ( lastDotPos == -1 ) {
				throw new IllegalArgumentException();
			}
			String shortName = pattern.substring( lastDotPos + 1 );
			
			this.classes.put( shortName, JavaTypes.loadClass( pattern ) );
		}
	}
	
	final Class< ? > loadClass( final String name ) throws ClassNotFoundException {
		//DQH - TODO: Rather dubious implementation of name look-up, since
		//it does not account for inner-classes.
		
		int lastDotPos = name.lastIndexOf( '.' );
		if ( lastDotPos == -1 ) {
			Class< ? > type = this.classes.get( name );
			if ( type != null ) {
				return type;
			}
			
			for ( String packageName : this.packages ) {
				String className = packageName + "." + name;
				try {
					return JavaTypes.loadClass( className );
				} catch ( ClassNotFoundException e ) {
					//skip
				}
			}
			try {
				return JavaTypes.loadClass( "java.lang." + name );
			} catch ( ClassNotFoundException e ) {
				//skip
			}
			
			try {
				return JavaTypes.loadClass( name );
			} catch ( ClassNotFoundException e ) {
				//skip
			}
			
			throw new ClassNotFoundException( "Unknown type: " + name );
		} else {
			return JavaTypes.loadClass( name );
		}
	}
	
	final void print( final ReplConsole console ) throws IOException {
		for ( Class< ? > type : this.classes.values() ) {
			console.println( "import " + type.getCanonicalName() );
		}
		for ( String packageName : this.packages ) {
			console.println( "import " + packageName + ".*" );
		}
	}
}
