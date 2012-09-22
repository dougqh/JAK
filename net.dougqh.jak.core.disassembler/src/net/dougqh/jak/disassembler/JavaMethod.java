package net.dougqh.jak.disassembler;

import java.lang.reflect.Type;
import java.util.List;


public interface JavaMethod {
	public abstract String getName();
	
	public abstract boolean isConstructor();
	
	public abstract boolean isClassInitializer();
	
	public abstract Type getReturnType();
	
	public abstract List<Type> getParameterTypes();
	
	//public abstract JavaCodeReader getCodeReader();
}
