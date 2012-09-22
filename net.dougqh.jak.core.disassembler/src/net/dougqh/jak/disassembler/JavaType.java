package net.dougqh.jak.disassembler;

import java.lang.reflect.Type;
import java.util.List;

public interface JavaType {
	public abstract String getName();
	
	public abstract Type getParentType();
	
	public abstract List< Type > getInterfaces();
	
	public abstract boolean isPublic();
	
	public abstract boolean isDefault();
	
	public abstract boolean isProtected();
	
	public abstract boolean isPrivate();
}
