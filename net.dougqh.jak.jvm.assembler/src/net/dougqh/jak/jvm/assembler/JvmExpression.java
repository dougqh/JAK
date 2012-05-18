package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import net.dougqh.jak.JakContext;
import net.dougqh.jak.assembler.JakExpression;
import net.dougqh.jak.assembler.TypeResolver;
import net.dougqh.java.meta.types.JavaTypes;

public abstract class JvmExpression< T >
	extends JvmCodeWriter
	implements JakExpression
{
	private JvmCodeWriter codeWriter;
	
	@Override
	public Type type( final JakContext context ) {
		ParameterizedType superType = (ParameterizedType)this.getClass().getGenericSuperclass();
		return JavaTypes.resolve( superType.getActualTypeArguments()[0] );
	}
	
	@Override
	public final boolean hasConstantValue( final JakContext context ) {
		return false;
	}
	
	@Override
	public final Object constantValue( final JakContext context ) {
		throw new UnsupportedOperationException();
	}
	
	protected final void eval( final JvmCodeWriter codeWriter ) {
		this.codeWriter = codeWriter;
		try {
			this.eval();
		} finally {
			this.codeWriter = null;
		}
	}
	
	protected abstract void eval();
	
	protected final JvmCodeWriter codeWriter() {
		return this.codeWriter;
	}
	
	@Override
	protected final MethodWritingState sharedState() {
		return this.codeWriter.sharedState();
	}
	
	@Override
	public final JvmCoreCodeWriter coreWriter() {
		return this.codeWriter.coreWriter();
	}
	
	@Override
	public final void accept( final JakExpression.Visitor visitor ) {
		if ( visitor instanceof JvmExpression.Visitor ) {
			Visitor jvmVisitor = (JvmExpression.Visitor)visitor;
			jvmVisitor.arbitrary( this );
		}
	}
	
	public static abstract class Visitor extends JakExpression.Visitor {
		Visitor( final JakContext context ) {
			super( context );
		}

		protected abstract void arbitrary( final JvmExpression<?> expression );
	}
}
