package net.dougqh.jak.disassembler;

import java.lang.reflect.Type;
import java.util.List;

import net.dougqh.jak.JavaElement;

public interface JavaType extends JavaElement {
	public abstract String getName();
	
	public abstract Type getParentType();
	
	public abstract List< Type > getInterfaces();
	
	public abstract int getFlags();
	
	public abstract boolean isPublic();
	
	public abstract boolean isDefault();
	
	public abstract boolean isProtected();
	
	public abstract boolean isPrivate();
}
