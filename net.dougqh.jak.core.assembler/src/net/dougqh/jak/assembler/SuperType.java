package net.dougqh.jak.assembler;

import java.lang.reflect.Type;

final class SuperType implements Type {
	protected static SuperType INSTANCE = new SuperType();
	
	private SuperType() {}
}
