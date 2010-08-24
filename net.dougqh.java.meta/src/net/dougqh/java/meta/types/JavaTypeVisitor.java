package net.dougqh.java.meta.types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

public abstract class JavaTypeVisitor {
	public final void visit( final Type type ) {
		this.visitHelper( JavaTypes.resolve( type ) );
	}

	private final void visitHelper( final Type type ) {
		if ( type instanceof Class ) {
			Class< ? > aClass = (Class< ? >)type;
			this.visitClass( aClass );
		} else if ( type instanceof ParameterizedType ) {
			ParameterizedType parameterizedType = (ParameterizedType)type;
			//TODO: DQH - Determine if this cast is safe
			this.visitParameterizedType(
				(Class< ? >)parameterizedType.getRawType(),
				parameterizedType.getActualTypeArguments() );
		} else if ( type instanceof TypeVariable ) {
			TypeVariable< ? > typeVariable = (TypeVariable< ? >)type;
			this.visitTypeVariable( typeVariable.getName(), typeVariable.getBounds() );
		} else if ( type instanceof WildcardType ) {
			WildcardType wildcardType = (WildcardType)type;
			if ( wildcardType.getLowerBounds().length != 0 ) {
				this.visitWildcardSuper( wildcardType.getLowerBounds() );
			} else {
				this.visitWildcardExtends( wildcardType.getUpperBounds() );
			}
		} else if ( type instanceof ClassNameRefType ) {
			ClassNameRefType className = (ClassNameRefType)type;
			this.visitObjectClass( this.transformName( className.getName() ) );
		} else {
			throw new IllegalStateException( "Incomplete code" );
		}
	}
	
	protected String getName( final Class< ? > aClass ) {
		return aClass.getCanonicalName();
	}
	
	@Deprecated
	protected String transformName( final String className ) {
		return className;
	}
	
	protected final void visitClass( final Class< ? > aClass ) {
		if ( aClass.isArray() ) {
			this.visitArray( aClass.getComponentType() );
		} else if ( JavaTypes.isPrimitiveType( aClass ) ) {
			this.visitPrimitiveClass( this.getName( aClass ) );
		} else {
			this.visitObjectClass( this.getName( aClass ) );
		}
	}
	
	protected abstract void visitPrimitiveClass( final String name );
	
	protected abstract void visitObjectClass( final String name );
	
	protected final void visitArray( final Class< ? > componentType ) {
		this.startArray();
		this.visitClass( componentType );
		this.endArray();
	}
	
	protected abstract void startArray();
	
	protected abstract void endArray();
	
	protected final void visitGenericArray( final Type componentType ) {
		this.startGenericArray();
		this.visit( componentType );
		this.endGenericArray();
	}
	
	protected abstract void startGenericArray();
	
	protected abstract void endGenericArray();
	
	protected final void visitParameterizedType(
		final Class< ? > rawType,
		final Type[] parameterTypes )
	{
		String rawClassName = this.getName( rawType );
		
		this.startParameterizedType( rawClassName );
		for ( Type type : parameterTypes ) {
			this.visit( type );
		}
		this.endParameterizedType( rawClassName );
	}
	
	protected abstract void startParameterizedType( final String rawClassName );
	
	protected abstract void endParameterizedType( final String rawClassName );
	
	protected final void visitTypeVariable(
		final String variableName,
		final Type[] bounds )
	{
		this.startTypeVariable( variableName );
		for ( Type bound : bounds ) {
			this.visit( bound );
		}
		this.endTypeVariable( variableName );
	}
	
	protected abstract void startTypeVariable( final String variableName );
	
	protected abstract void endTypeVariable( final String variableName );
	
	protected final void visitWildcardExtends( final Type[] bounds ) {
		this.startWildcardExtends();
		for ( Type bound : bounds ) {
			this.visit( bound );
		}
		this.endWildcardExtends();
	}
	
	protected abstract void startWildcardExtends();
	
	protected abstract void endWildcardExtends();
	
	protected final void visitWildcardSuper( final Type[] bounds ) {
		this.startWildcardSuper();
		for ( Type bound : bounds ) {
			this.visit( bound );
		}
		this.endWildcardSuper();
	}
	
	protected abstract void startWildcardSuper();
	
	protected abstract void endWildcardSuper();
}
