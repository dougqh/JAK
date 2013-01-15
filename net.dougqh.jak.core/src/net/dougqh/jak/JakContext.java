package net.dougqh.jak;

import java.lang.reflect.Type;

public interface JakContext {
	public abstract Type thisType();
	
	public abstract Type localType( final String name, final Type expectedType );
}
