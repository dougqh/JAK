package net.dougqh.jak.disassembler;

import java.lang.reflect.Type;
import java.util.List;

public interface JavaType {
	public abstract String getName();
	
	public abstract Type getParentType();
	
	public abstract List< Type > getInterfaces();
}
