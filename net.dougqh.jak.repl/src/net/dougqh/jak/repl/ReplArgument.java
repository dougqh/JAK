package net.dougqh.jak.repl;

import java.lang.reflect.Type;

import net.dougqh.java.meta.types.JavaTypes;

enum ReplArgument {
	BOOLEAN( boolean.class ) {
		@Override
		public final Object parse( final String argString ) {
			if ( argString.equals( "true" ) ) {
				return true;
			} else if ( argString.equals( "false" ) ) {
				return false;
			} else {
				throw new IllegalArgumentException();
			}
		}
	},
	BYTE( byte.class ) {
		@Override
		public final Object parse( final String argString ) {
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
			if ( argString.charAt( 0 ) != '\'' ) {
				throw new IllegalArgumentException();
			}
			if ( argString.charAt( argString.length() - 1 ) != '\'' ) {
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
			try {
				return Float.parseFloat( argString );
			} catch ( NumberFormatException e ) {
				throw new IllegalArgumentException( e );
			}
		}	
	},
	LONG( long.class ) {
		@Override
		public final Object parse( final String argString ) {
			try {
				return Long.parseLong( argString );
			} catch ( NumberFormatException e ) {
				throw new IllegalArgumentException( e );
			}
		}		
	},
	DOUBLE( double.class ) {
		@Override
		public final Object parse( final String argString ) {
			try {
				return Double.parseDouble( argString );
			} catch ( NumberFormatException e ) {
				throw new IllegalArgumentException( e );
			}
		}		
	},
	STRING_LITERAL( CharSequence.class ) {
		@Override
		public final Object parse( final String argString ) {
			//TODO: Implement this proper escaping support
			if ( argString.charAt( 0 ) != '"' ) {
				throw new IllegalArgumentException();
			}
			if ( argString.charAt( argString.length() - 1 ) != '"' ) {
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
		throw new IllegalStateException( "Unsupported type: " + ReplUtils.getDisplayName( type ) );
	}
	
	private final Type type;
	
	ReplArgument( final Type type ) {
		this.type = type;
	}
	
	public final boolean matches( final Type type ) {
		return this.type.equals( type );
	}
	
	public final String getTypeName() {
		return ReplUtils.getDisplayName( this.type );
	}
	
	public abstract Object parse( final String argString );
}
