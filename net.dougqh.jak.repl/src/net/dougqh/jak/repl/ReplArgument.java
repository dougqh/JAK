package net.dougqh.jak.repl;

import java.lang.reflect.Type;

import net.dougqh.java.meta.types.JavaTypes;

enum ReplArgument {
	BOOLEAN( boolean.class ) {
		@Override
		public final Object parse( final String argString ) {
			if ( argString.equals( "true" ) ) {
				return true;
			} else if ( argString.equals( "fasle" ) ) {
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
				throw new IllegalStateException( e );
			}
		}
	},
	SHORT( short.class ) {
		@Override
		public final Object parse( final String argString ) {
			try {
				return Short.parseShort( argString );
			} catch ( NumberFormatException e ) {
				throw new IllegalStateException( e );
			}
		}		
	},
	INT( int.class ) {
		@Override
		public final Object parse( final String argString ) {
			try {
				return Integer.parseInt( argString );
			} catch ( NumberFormatException e ) {
				throw new IllegalStateException( e );
			}
		}	
	},
	FLOAT( float.class ) {
		@Override
		public final Object parse( final String argString ) {
			try {
				return Float.parseFloat( argString );
			} catch ( NumberFormatException e ) {
				throw new IllegalStateException( e );
			}
		}	
	},
	LONG( long.class ) {
		@Override
		public final Object parse( final String argString ) {
			try {
				return Long.parseLong( argString );
			} catch ( NumberFormatException e ) {
				throw new IllegalStateException( e );
			}
		}		
	},
	DOUBLE( double.class ) {
		@Override
		public final Object parse( final String argString ) {
			try {
				return Double.parseDouble( argString );
			} catch ( NumberFormatException e ) {
				throw new IllegalStateException( e );
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
		return JavaTypes.getRawClassName( this.type );
	}
	
	public abstract Object parse( final String argString );
}
