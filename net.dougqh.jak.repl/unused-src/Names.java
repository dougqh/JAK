import net.dougqh.jak.repl.ReplArgument.QualifiedName;

	private static final class NameAndType {
		private final Class< ? > targetType;
		private final String name;
		private final Class< ? > returnType;
		
		NameAndType( final String arg ) {
			String[] parts = splitOnLast( arg, ':' );
			
			QualifiedName qualifiedName = new QualifiedName( parts[ 0 ] );
			this.targetType = qualifiedName.targetType;
			this.name = qualifiedName.remainder;
			
			if ( parts.length == 2 ) {
				this.returnType = loadClass( parts[ 1 ] );
			} else {
				this.returnType = null;
			}
		}
		
		final Class< ? > targetType() {
			return this.targetType;
		}
		
		final String name() {
			return this.name;
		}
		
		final Class< ? > returnType() {
			return this.returnType;
		}
	}
	
	private static final class QualifiedName {
		final Class< ? > targetType;
		final String remainder;
		
		QualifiedName( final String qualifiedName ) {
			String[] parts = splitOnLast( qualifiedName, '.' );
			
			if ( parts.length == 1 ) {
				String[] jvmNameParts = splitOnLast( parts[ 0 ], '#' );
				if ( jvmNameParts.length != 2 ) {
					throw new IllegalStateException();
				}
				this.targetType = forJvmName( jvmNameParts[ 0 ] );
				this.remainder = jvmNameParts[ 1 ];
			} else {
				this.targetType = forJavaName( parts[ 0 ] );
				this.remainder = parts[ 1 ];
			}
		}
	}