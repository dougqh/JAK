package net.dougqh.jak.repl;

import java.lang.reflect.Type;

enum ReplArgument {
	BOOLEAN( boolean.class ) {
		@Override
		public final Object parse( final String argString ) {
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
		public final Object parse( final String argString ) {
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
		public final Object parse( final String argString ) {
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
		public final Object parse( final String argString ) {
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
		public final Object parse( final String argString ) {
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
		public final Object parse( final String argString ) {
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
		public final Object parse( final String argString ) {
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
		public final Object parse( final String argString ) {
			checkType( argString, double.class );
			
			try {
				return Double.parseDouble( removeTypeQualifier( argString ) );
			} catch ( NumberFormatException e ) {
				throw new IllegalArgumentException( e );
			}
		}		
	},
	STRING_LITERAL( CharSequence.class ) {
		@Override
		public final Object parse( final String argString ) {
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
	TYPE_LITERAL( Type.class ) {
		@Override
		public final Object parse( final String argString ) {
			try {
				return Class.forName( argString );
			} catch ( ClassNotFoundException e ) {
				throw new IllegalArgumentException();
			}
		}
	},
	CLASS_LITERAL( Class.class ) {
		@Override
		public final Object parse( final String argString ) {
			try {
				return Class.forName( argString );
			} catch ( ClassNotFoundException e ) {
				throw new IllegalArgumentException();
			}
		}
	};
	
	public static final ReplArgument instance( final Type type ) {
		for ( ReplArgument arg : values() ) {
			if ( arg.matches( type ) ) {
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
	
	ReplArgument( final Type type ) {
		this.type = type;
	}
	
	public final boolean matches( final Type type ) {
		return this.type.equals( type );
	}
	
	public final String getTypeName() {
		return ReplFormatter.getDisplayName( this.type );
	}
	
	public abstract Object parse( final String argString );
	
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
}
