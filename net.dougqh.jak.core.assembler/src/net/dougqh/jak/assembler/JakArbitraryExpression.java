package net.dougqh.jak.assembler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import net.dougqh.jak.JakContext;
import net.dougqh.java.meta.types.JavaTypes;

public abstract class JakArbitraryExpression< T > implements JakExpression {
	@Override
	public final Type type( final JakContext context ) {
		ParameterizedType myType = (ParameterizedType)this.getClass().getGenericSuperclass();
		return JavaTypes.resolve(myType.getActualTypeArguments()[0]);
	}
	
	@Override
	public final boolean hasConstantValue( final JakContext context ) {
		return false;
	}
	
	@Override
	public final Object constantValue( final JakContext context ) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final void accept( final Visitor visitor ) {
		//TODO
		throw new UnsupportedOperationException( "TODO" );
	}
}
