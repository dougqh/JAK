package net.dougqh.java.meta.types;

import java.lang.reflect.Type;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.AbstractTypeVisitor6;

final class AptTypeVisitor
	extends AbstractTypeVisitor6< Type, Void >
{
	@Override
	public final Type visitArray(
		final ArrayType arrayType,
		final Void param )
	{
		return JavaTypes.array(
			JavaTypes.type( arrayType.getComponentType() ).make() );
	}
	
	@Override
	public final Type visitDeclared(
		final DeclaredType type,
		final Void param )
	{
		//TODO: Improve handling of type.getTypeArguments()
		return JavaTypes.type( (TypeElement)type.asElement() );
	}
	
	@Override
	public final Type visitError( 
		final ErrorType errorType,
		final Void param ) 
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final Type visitExecutable(
		final ExecutableType execType,
		final Void param )
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final Type visitNoType(
		final NoType noType,
		final Void param )
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final  Type visitNull(
		final NullType nullType,
		final Void param )
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final Type visitPrimitive(
		final PrimitiveType primitiveType,
		final Void param )
	{
		switch ( primitiveType.getKind() ) {
			case BOOLEAN: {
				return boolean.class;
			}
			
			case BYTE: {
				return byte.class;
			}
			
			case CHAR: {
				return char.class;
			}
			
			case DOUBLE: {
				return double.class;
			}
			
			case FLOAT: {
				return float.class;
			}
			
			case INT: {
				return int.class;
			}
			
			case LONG: {
				return long.class;
			}
			
			case SHORT: {
				return short.class;
			}
			
			default: {
				throw new IllegalStateException( "Unknown primitive type" );
			}
		}
	}
	
	@Override
	public final Type visitTypeVariable(
		final TypeVariable typeVar,
		final Void param )
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public  final Type visitWildcard(
		final javax.lang.model.type.WildcardType type,
		final Void param )
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final Type visitUnknown(
		final TypeMirror typeMirror,
		final Void param )
	{
		throw new UnsupportedOperationException();
	}
}