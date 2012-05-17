package net.dougqh.java.meta.types;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public final class JavaTypes {
	public static final boolean isArrayType( final Type type ) {
		if ( type instanceof Class< ? > ) {
			Class< ? > aClass = (Class< ? >)type;
			return aClass.isArray();
		} else if ( type instanceof GenericArrayType ) {
			return true;
		} else {
			return false;
		}
	}
	
	public static final boolean isObjectType( final Type type ) {
		Type resolvedType = resolve( type );
		
		if ( resolvedType instanceof Class ) {
			Class< ? > aClass = (Class< ? >)resolvedType;
			return Object.class.isAssignableFrom( aClass );
		} else {
			return ! isPrimitiveType( type );
		}
	}
	
	public static final boolean isPrimitiveType( final Type type ) {
		Type resolvedType = resolve( type );
		
		return resolvedType.equals( boolean.class ) ||
			resolvedType.equals( char.class ) ||
			resolvedType.equals( byte.class ) ||
			resolvedType.equals( short.class ) ||
			resolvedType.equals( int.class ) ||
			resolvedType.equals( long.class ) ||
			resolvedType.equals( float.class ) ||
			resolvedType.equals( double.class ) ||
			resolvedType.equals( void.class );
	}
	
	public static final Type fromPersistedName( final CharSequence name ) {
		switch ( name.charAt( 0 ) ) {
			case 'V': {
				return void.class;
			}
			
			case 'Z': {
				return boolean.class;
			}
			
			case 'B': {
				return byte.class;
			}
			
			case 'C': {
				return char.class;
			}
			
			case 'S': {
				return short.class;
			}
			
			case 'I': {
				return int.class;
			}
			
			case 'J': {
				return long.class;
			}
			
			case 'F': {
				return float.class;
			}
			
			case 'D': {
				return double.class;
			}
			
			case 'L' : {
				String className = name.subSequence( 1, name.length() - 1 ).toString();
				return objectTypeName( className.replace( '/', '.' ) );
			}
			
			case '[' : {
				String className = name.subSequence( 1, name.length() - 1 ).toString();
				return array( fromPersistedName( className ) );
			}
			
			default : {
				throw new IllegalStateException( "Unsupported type: " + name.toString() );
			}
		}
	}
	
	public static final String getRawClassName( final Type type ) {
		Type rawType = getRawType( type );
		if ( rawType instanceof Class ) {
			Class< ? > aClass = (Class< ? >)rawType;
			return aClass.getName();
		} else {
			ClassNameRefType nameRef = (ClassNameRefType)rawType;
			return nameRef.getName();
		}
	}
	
	public static final Class< ? > getRawClass( final Type type ) {
		return (Class< ? >)getRawType( type );
	}
	
	public static final Type getRawType( final Type type ) {
		Type resolvedType = resolve( type );
		if ( resolvedType instanceof Class ) {
			return resolvedType;
		} else if ( resolvedType instanceof ParameterizedType ) {
			ParameterizedType parameterizedType = (ParameterizedType)resolvedType;
			return getRawType( parameterizedType.getRawType() );
		} else if ( resolvedType instanceof WildcardType ) {
			WildcardType wildcardType = (WildcardType)resolvedType;
			Type[] extendsTypes = wildcardType.getUpperBounds();
			if ( extendsTypes.length > 1 ) {
				throw new IllegalStateException( "Not supported" );
			} else {
				return getRawType( extendsTypes[ 0 ] );
			}
		} else if ( resolvedType instanceof TypeVariable ) {
			TypeVariable<?> typeVar = (TypeVariable<?>)resolvedType;
			Type[] bounds = typeVar.getBounds();
			if ( bounds.length != 1 ) {
				throw new IllegalStateException( "Incomplete code" );
			} else {
				return bounds[ 0 ];
			}
		} else if ( resolvedType instanceof ClassNameRefType ) {
			return resolvedType;
		} else {
			throw new IllegalStateException( "Unsupported type " + type.getClass().getSimpleName() );
		}
	}
	
	public static final Class< ? > getPrimitiveType( final Type aClass ) {
		if ( aClass.equals( boolean.class ) || aClass.equals( Boolean.class ) ) {
			return boolean.class;
		} else if ( aClass.equals( byte.class ) || aClass.equals( Byte.class ) ) {
			return byte.class;
		} else if ( aClass.equals( char.class ) || aClass.equals( Character.class ) ) {
			return char.class;
		} else if ( aClass.equals( short.class ) || aClass.equals( Short.class ) ) {
			return short.class;
		} else if ( aClass.equals( int.class ) || aClass.equals( Integer.class ) ) {
			return int.class;
		} else if ( aClass.equals( long.class ) || aClass.equals( Long.class ) ) {
			return long.class;
		} else if ( aClass.equals( float.class ) || aClass.equals( Float.class ) ) {
			return float.class;
		} else if ( aClass.equals( double.class ) || aClass.equals( Double.class ) ) {
			return double.class;
		} else if ( aClass.equals( void.class ) || aClass.equals( Void.class ) ) {
			return void.class;
		} else {
			return null;
		}
	}
	
	public static final Class< ? > getObjectType( final Class< ? > aClass ) {
		if ( aClass.equals( boolean.class ) ) {
			return Boolean.class;
		} else if ( aClass.equals( byte.class ) ) {
			return Byte.class;
		} else if ( aClass.equals( char.class ) ) {
			return Character.class;
		} else if ( aClass.equals( short.class ) ) {
			return Short.class;
		} else if ( aClass.equals( int.class ) ) {
			return Integer.class;
		} else if ( aClass.equals( long.class ) ) {
			return Long.class;
		} else if ( aClass.equals( float.class ) ) {
			return Float.class;
		} else if ( aClass.equals( double.class ) ) {
			return Double.class;
		} else if ( aClass.equals( void.class ) ) {
			return Void.class; 
		} else {
			return aClass;
		}
	}
	
	public static final int getArrayDepth( final Type arrayType ) {
		Type resolvedType = resolve( arrayType );
		return getArrayDepthInternal( resolvedType );
	}
	
	private static final int getArrayDepthInternal( final Type arrayType ) {
		if ( arrayType instanceof GenericArrayType ) {
			GenericArrayType genericArrayType = (GenericArrayType)arrayType;
			return getArrayDepthInternal( genericArrayType.getGenericComponentType() ) + 1;
		} else if ( arrayType instanceof Class ) {
			Class< ? > aClass = (Class< ? >)arrayType;
			if ( aClass.isArray() ) {
				return getArrayDepthInternal( aClass.getComponentType() ) + 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}
	
	public static final JavaWildcardType wildcard() {
		return new JavaWildcardType();
	}

	public static final JavaTypeBuilder type() {
		return new JavaTypeBuilder();
	}
	
	public static final Type objectTypeName( final CharSequence name ) {
 		return new JavaTypeBuilder( name, false ).make();
	}
	
	public static final Class< ? > loadClass( final CharSequence name )
		throws ClassNotFoundException
	{
		String nameStr = name.toString();
		if ( nameStr.equals( "void" ) ) {
			return void.class;
		} else if ( nameStr.equals( "boolean" ) ) {
			return boolean.class;
		} else if ( nameStr.equals( "byte" ) ) {
			return byte.class;
		} else if ( nameStr.equals( "char" ) ) {
			return char.class;
		} else if ( nameStr.equals( "short" ) ) {
			return short.class;
		} else if ( nameStr.equals( "int" ) ) {
			return int.class;
		} else if ( nameStr.equals( "float" ) ) {
			return float.class;
		} else if ( nameStr.equals( "long" ) ) {
			return long.class;
		} else if ( nameStr.equals( "double" ) ) {
			return double.class;
		} else if ( nameStr.endsWith( "[]" ) ) {
			return array( loadClass( nameStr.substring( 0, nameStr.length() - 2 ) ) );
		} else {
			return Class.forName( nameStr );
		}
	}
	
	static final Class< ? > loadClassImpl( final CharSequence name ) {
		String nameStr = name.toString();
		try {
			return Class.forName( nameStr );
		} catch ( ClassNotFoundException e ) {
			return null;
		}
	}
	
//	public static final JavaTypeBuilder type( final TypeMirror typeMirror ) {
//		return typeMirror.accept( new AptTypeVisitor(), null ) );
//	}
	
	public static final Type type( final TypeElement typeElement ) {
		return objectTypeName( typeElement.getQualifiedName() );
	}	
	
	public static final JavaTypeVar typeVar( final CharSequence name ) {
		return new JavaTypeVar( name.toString() );
	}
	
	public static final JavaSpecificType parameterize( final Type type ) {
		return new JavaSpecificType( type );
	}
	
	public static final Class< ? > array( final Class< ? > aClass ) {
		//TODO: Implement more efficiently if possible
		return Array.newInstance( aClass, 0 ).getClass();
	}
	
	public static final Type array( final Type type ) {
		Type resolvedType = JavaTypes.resolve( type );
		
		if ( resolvedType instanceof Class ) {
			return array( (Class< ? >)resolvedType );
		} else {
			return new JavaGenericArrayType( resolvedType );
		}
	}
	
	public static final Type array(
		final Type type,
		final int numDimensions )
	{
		Type curType = JavaTypes.resolve( type );
		for ( int i = 0; i < numDimensions; ++i ) {
			curType = array( curType );
		}
		return curType;
	}
	
	public static final Type resolve( final Type type ) {
		if ( type instanceof ClassNameRefType ) {
			ClassNameRefType nameRef = (ClassNameRefType)type;
			Class< ? > aClass = loadClassImpl( nameRef.getName() );
			if ( aClass == null ) {
				return nameRef;
			} else {
				return aClass;
			}
		} else if ( type instanceof JavaTypeProvider ) {
			JavaTypeProvider provider = (JavaTypeProvider)type;
			return JavaTypes.resolve( provider.get() );
		} else if ( type instanceof Class<?> ) {
			Class<?> aClass = (Class<?>)type;
			TypeAlias alias = aClass.getAnnotation( TypeAlias.class );
			return alias != null ? alias.value() : aClass;
		} else {
			return type;
		}
	}
	
	public static final Type[] resolve( final Type... types ) {
		Type[] resolvedTypes = new Type[ types.length ];
		for ( int i = 0; i < types.length; ++i ) {
			Type type = types[ i ];
			resolvedTypes[ i ] = resolve( type );
		}
		return resolvedTypes;
	}
	
	private JavaTypes() {}
}
