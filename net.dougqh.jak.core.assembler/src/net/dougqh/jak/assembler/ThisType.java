package net.dougqh.jak.assembler;

import java.lang.reflect.Type;

final class ThisType implements Type {
	protected static ThisType INSTANCE = new ThisType();
	
	private ThisType() {}
}
