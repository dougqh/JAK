package net.dougqh.jak.repl;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaAssembler;
import net.dougqh.jak.JavaFieldDescriptor;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.java.meta.types.JavaTypes;

enum ReplArgument {
	BOOLEAN( boolean.class ) {
		@Override
		public final Object parse( final JakRepl repl, final String argString ) {
			checkNoQualifier( argString );
			
			if ( argString.equals( TRUE ) ) {
				return true;
			} else if ( argString.equals( FALSE ) ) {
				return false;
			} else {
				throw new IllegalArgumentException();
			}
		}
	},
	BYTE( byte.class ) {
		@Override
		public final Object parse( final JakRepl repl, final String argString ) {
			checkNoQualifier( argString );
			
			try {
				return Byte.parseByte( argString );
			} catch ( NumberFormatException e ) {
				throw new IllegalArgumentException( e );
			}
		}
	},
	CHAR( char.class ) {
		@Override
		public final Object parse( final JakRepl repl, final String argString ) {
			//TODO: Implement this proper escaping support
			if ( argString.charAt( 0 ) != CHAR_QUOTE ) {
				throw new IllegalArgumentException();
			}
			if ( argString.charAt( argString.length() - 1 ) != CHAR_QUOTE ) {
				throw new IllegalArgumentException();
			}
			if ( argString.length() != 3 ) {
				throw new IllegalArgumentException();
			}
			return argString.charAt( 1 );
		}		
	},
	SHORT( short.class ) {
		@Override
		public final Object parse( final JakRepl repl, final String argString ) {
			checkNoQualifier( argString );
			
			try {
				return Short.parseShort( argString );
			} catch ( NumberFormatException e ) {
				throw new IllegalArgumentException( e );
			}
		}	
	},
	INT( int.class ) {
		@Override
		public final Object parse( final JakRepl repl, final String argString ) {
			checkNoQualifier( argString );
			
			try {
				return Integer.parseInt( argString );
			} catch ( NumberFormatException e ) {
				throw new IllegalArgumentException( e );
			}
		}
	},
	FLOAT( float.class ) {
		@Override
		public final Object parse( final JakRepl repl, final String argString ) {
			checkType( argString, float.class );
			
			try {
				return Float.parseFloat( removeTypeQualifier( argString ) );
			} catch ( NumberFormatException e ) {
				throw new IllegalArgumentException( e );
			}
		}	
	},
	LONG( long.class ) {
		@Override
		public final Object parse( final JakRepl repl, final String argString ) {
			checkType( argString, long.class );
			
			try {
				return Long.parseLong( removeTypeQualifier( argString ) );
			} catch ( NumberFormatException e ) {
				throw new IllegalArgumentException( e );
			}
		}		
	},
	DOUBLE( double.class ) {
		@Override
		public final Object parse( final JakRepl repl, final String argString ) {
			checkType( argString, double.class );
			
			try {
				return Double.parseDouble( removeTypeQualifier( argString ) );
			} catch ( NumberFormatException e ) {
				throw new IllegalArgumentException( e );
			}
		}		
	},
	SYMBOL( String.class, true ) {
		@Override
		public final String getTypeName() {
			return "Symbol";
		}
		
		@Override
		public final Object parse( final JakRepl repl, final String argString ) {
			char[] chars = argString.toCharArray();
			if ( ! Character.isJavaIdentifierStart( chars[ 0 ] ) ) {
				throw new IllegalArgumentException();
			}
			for ( int i = 1; i < chars.length; ++i ) {
				if ( ! Character.isJavaIdentifierPart( chars[ i ] ) ) {
					throw new IllegalArgumentException();
				}
			}
			return argString;
		}
	},
	STRING_LITERAL( CharSequence.class ) {
		@Override
		public final Object parse( final JakRepl repl, final String argString ) {
			//TODO: Implement this proper escaping support
			if ( argString.charAt( 0 ) != STRING_QUOTE ) {
				throw new IllegalArgumentException();
			}
			if ( argString.charAt( argString.length() - 1 ) != STRING_QUOTE ) {
				throw new IllegalArgumentException();
			}
			return argString.substring( 1, argString.length() - 1 );
		}
	},
	CLASS_LITERAL( Type.class ) {
		@Override
		public final Object parse( final JakRepl repl, final String argString ) {
			return loadClass( repl, argString );
		}
	},
	FIELD( JavaFieldDescriptor.class ) {
		@Override
		public final Object parse( final JakRepl repl, final String argString ) {
			String[] parts = splitOnLast( argString, ':' );
			if ( parts.length != 2 ) {
				throw new IllegalArgumentException();
			}
			
			String name = parts[ 0 ];
			Type fieldType = loadClass( repl, parts[ 1 ] );
			
			return JavaAssembler.field( fieldType, name );
		}
	},
	METHOD( JavaMethodDescriptor.class ) {
		@Override
		public final Object parse( final JakRepl repl, final String argString ) {
			//DQH: TODO - Make this parsing more robust
			
			String[] parts = splitOnLast( argString, ':' );
			if ( parts.length != 2 ) {
				throw new IllegalArgumentException();
			}
			Type returnType = loadClass( repl, parts[ 1 ] );
			
			int startParenPos = parts[ 0 ].indexOf( '(' );
			int endParenPos = parts[ 0 ].indexOf( ')' );
			
			if ( ( startParenPos == -1 ) || ( endParenPos == -1 ) ) {
				throw new IllegalArgumentException();
			}
			
			String name = parts[ 0 ].substring( 0, startParenPos );
			
			Type[] types;
			if ( startParenPos + 1 == endParenPos ) {
				types = new Type[] {};
			} else {
				String typeList = parts[ 0 ].substring( startParenPos + 1, endParenPos );
				String[] typeNames = typeList.split( "," );
				types = loadClasses( repl, typeNames );
			}
			
			return JavaAssembler.method( returnType, name, types );
		}
	};
	
	public static final ReplArgument find( final Type type, final boolean isSymbol ) {
		for ( ReplArgument arg : values() ) {
			if ( arg.matches( type, isSymbol ) ) {
				return arg;
			}
		}
		throw new IllegalStateException( "Unsupported type: " + ReplFormatter.getDisplayName( type ) );
	}
	
	public static final char CHAR_QUOTE = '\'';
	public static final char STRING_QUOTE = '"';
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	private final Type type;
	private final boolean acceptSymbols;
	
	ReplArgument( final Type type ) {
		this( type, false );
	}
	
	ReplArgument(
		final Type type,
		final boolean acceptSymbols )
	{
		this.type = type;
		this.acceptSymbols = acceptSymbols;
	}
	
	public final boolean matches(
		final Type type,
		final boolean isSymbol )
	{
		if ( this.acceptSymbols != isSymbol ) {
			return false;
		} else {
			Class< ? > thisType = JavaTypes.getRawClass( this.type );
			Class< ? > thatType = JavaTypes.getRawClass( type );
			
			return thisType.isAssignableFrom( thatType );
		}
	}
	
	public String getTypeName() {
		return ReplFormatter.getShortDisplayName( this.type );
	}
	
	public abstract Object parse( final JakRepl repl, final String argString );
	
	static final Class< ? > loadClass( final JakRepl repl, final String name ) {
		try {
			return repl.imports().loadClass( name );
		} catch ( ClassNotFoundException e ) {
			throw new IllegalArgumentException( e );
		}
	}
	
	static final Class< ? >[] loadClasses( final JakRepl repl, final String[] names ) {	
		Class< ? >[] classes = new Class< ? >[ names.length ];
		for ( int i = 0; i < names.length; ++i ) {
			classes[ i ] = loadClass( repl, names[ i ] );
		}
		return classes;
	}
	
	static final Class< ? > typeQualifier( final String argString ) {
		char lastChar = Character.toUpperCase( argString.charAt( argString.length() - 1 ) );
		if ( Character.isDigit( lastChar ) ) {
			return null;
		} else if ( lastChar == 'F' ) {
			return float.class;
		} else if ( lastChar == 'L' ) {
			return long.class;
		} else if ( lastChar == 'D' ) {
			return double.class;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	private static final String removeTypeQualifier( final String argString ) {
		char lastChar = Character.toUpperCase( argString.charAt( argString.length() - 1 ) );
		if ( Character.isDigit( lastChar ) ) {
			return argString;
		} else {
			return argString.substring( 0, argString.length() - 1 );
		}
	}
	
	private static final void checkNoQualifier( final String argString ) {
		Class< ? > type = typeQualifier( argString );
		if ( type != null ) {
			throw new IllegalStateException();
		}
	}
	
	private static final void checkType(
		final String argString,
		final Class< ? > expectedType )
	{
		Class< ? > type = typeQualifier( argString );
		if ( type != null && ! type.equals( expectedType ) ) {
			throw new IllegalStateException();
		}
	}
	
	private static final String[] splitOnLast(
		final String string,
		final char splitChar )
	{
		int lastIndex = string.lastIndexOf( splitChar );
		if ( lastIndex == -1 ) {
			return new String[] { string };
		} else {
			return new String[] {
				string.substring( 0, lastIndex ),
				string.substring( lastIndex + 1 ) };
		}
	}
}
