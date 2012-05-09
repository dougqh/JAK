package net.dougqh.jak.types;

import java.lang.reflect.Type;

public final class Union implements Type {
	private final Type[] types;
	
	public Union( final Type... types ) {
		this.types = types.clone();
	}
}
