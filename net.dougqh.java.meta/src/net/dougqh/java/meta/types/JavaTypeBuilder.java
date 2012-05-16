package net.dougqh.java.meta.types;

import java.lang.reflect.Type;

@Deprecated
public final class JavaTypeBuilder {
	private final boolean isTypeVar;
	private final CharSequence name;
	private final Type baseType;
	
	private Type[] ofTypes = null;
	private Type[] extendsTypes = null;
	private Type superType = null;
	
	JavaTypeBuilder() {
		this.isTypeVar = false;
		this.name = null;
		this.baseType = null;
	}
	
	JavaTypeBuilder(
		final CharSequence name,
		final boolean isTypeVar )
	{
		this.isTypeVar = isTypeVar;
		this.name = name;
		this.baseType = null;
	}
	
	JavaTypeBuilder( final Type baseType ) {
		this.isTypeVar = false;
		this.name = null;
		this.baseType = JavaTypes.resolve( baseType );
	}
	
	public final JavaTypeBuilder of( final JavaTypeBuilder... typeBuilders ) {
		return this.of( make( typeBuilders ) );
	}
	
	public final JavaTypeBuilder of( final Type... types ) {
		this.ofTypes = types;
		return this;
	}
	
	public final JavaTypeBuilder extends_( final JavaTypeBuilder... typeBuilders ) {
		return this.extends_( make( typeBuilders ) );
	}
	
	public final JavaTypeBuilder extends_( final Type... types ) {
		this.extendsTypes = types;
		return this;
	}
	
	public final JavaTypeBuilder super_( final JavaTypeBuilder typeBuilder ) {
		return this.super_( typeBuilder.make() );
	}
	
	public final JavaTypeBuilder super_( final Type type ) {
		this.superType = type;
		return this;
	}
	
	public static final Type[] make( final JavaTypeBuilder[] typeBuilders ) {
		Type[] types = new Type[ typeBuilders.length ];
		for ( int i = 0; i < typeBuilders.length; ++i ) {
			types[ i ] = typeBuilders[ i ].make();
		}
		return types;
	}
	
	public final Type make() {
		if ( this.baseType != null ) {
			return this.getConcreteType();
		} else if ( this.name != null ) {
			if ( this.isTypeVar ) {
				return this.getTypeVariable();
			} else {
				Class< ? > aClass = JavaTypes.loadClassImpl( this.name );
				if ( aClass == null ) {
					return new ClassNameRefType( this.name );
				} else {
					return aClass;
				}
			}
		} else {
			return this.getWildcardType();
		}
	}
	
	private final Type getConcreteType() {
		if ( this.ofTypes == null ) {
			return this.baseType;
		} else {
			return new JavaParameterizedType(
				this.baseType,
				this.ofTypes );
		}
	}
	
	private final Type getTypeVariable() {
		if ( this.extendsTypes != null ) {
			return new JavaTypeVariable( this.name, this.extendsTypes );
		} else {
			return new JavaTypeVariable( this.name );
		}
	}
	
	private final Type getWildcardType() {
		if ( this.extendsTypes != null ) {
			return new JavaWildcardExtendsType( this.extendsTypes );
		} else if ( this.superType != null ) {
			return new JavaWildcardSuperType( this.superType );
		} else {
			throw new IllegalStateException( "Invalid type construction" );
		}
	}
}
