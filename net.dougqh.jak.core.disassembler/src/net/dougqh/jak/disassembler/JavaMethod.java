package net.dougqh.jak.disassembler;

import java.lang.reflect.Type;
import java.util.List;

import net.dougqh.jak.JavaElement;


public interface JavaMethod extends JavaElement {
	public abstract String getName();
	
	public abstract boolean isConstructor();
	
	public abstract boolean isClassInitializer();
	
	public abstract Type getReturnType();
	
	public abstract List<Type> getParameterTypes();
	
	//public abstract JavaCodeReader getCodeReader();
	
	public abstract int getFlags();
	
	public abstract boolean isPublic();
	
	public abstract boolean isDefault();
	
	public abstract boolean isProtected();
	
	public abstract boolean isPrivate();
}
