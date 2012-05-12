package net.dougqh.java.meta.types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class typeRef< T > implements JavaTypeProvider {
	@Override
	public final Type get() {
		ParameterizedType superType = (ParameterizedType)this.getClass().getGenericSuperclass();
		return JavaTypes.resolve( superType.getActualTypeArguments()[0] );
	}
}
