package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import net.dougqh.jak.assembler.TypeResolver;

//DQH - Visibility increased for WritingContext to allow REPL to work
public final class WritingContext {
	final TypeResolver resolver;
	final ConstantPool constantPool;
	final Type thisType;
	final Type superType;
	
	WritingContext(
		final TypeVariable<?>[] typeVars,
		final ConstantPool constantPool,
		final Type thisType,
		final Type superType )
	{
		this.resolver = new TypeResolver() {
			@Override
			protected final TypeVariable<?> resolveDeclaration( final TypeVariable<?> typeVar ) {
				TypeVariable<?> declaredVar = WritingContext.resolve( typeVar, typeVars );
				return declaredVar != null ? declaredVar : super.resolveDeclaration( typeVar );
			}
			
			@Override
			protected final Type thisType() {
				return thisType;
			}
			
			@Override
			protected final Type superType() {
				return superType;
			}
		};
		this.constantPool = constantPool;
		this.thisType = thisType;
		this.superType = superType;
	}
	
	WritingContext(
		final WritingContext parentContext,
		final TypeVariable<?>[] typeVars )
	{
		this.resolver = new TypeResolver( parentContext.resolver ) {
			@Override
			protected final TypeVariable<?> resolveDeclaration( final TypeVariable<?> typeVar ) {
				TypeVariable<?> declaredVar = WritingContext.resolve( typeVar, typeVars );
				return declaredVar != null ? declaredVar : super.resolveDeclaration( typeVar );
			}			
		};
		this.constantPool = parentContext.constantPool;
		this.thisType = parentContext.thisType;
		this.superType = parentContext.superType;
	}
	
	private static final TypeVariable<?> resolve(
		final TypeVariable<?> typeVar,
		final TypeVariable<?>[] declaredVars )
	{
		for ( TypeVariable<?> declaredVar: declaredVars ) {
			if ( typeVar.getName().equals( declaredVar.getName() ) ) {
				return declaredVar;
			}
		}
		return null;		
	}
}
