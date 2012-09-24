package net.dougqh.jak.disassembler;


public interface JavaClass {
	//public abstract List< JavaField > getFields();
	
	public abstract JavaMethod getClassInitializer();
	
	public abstract JavaMethodSet<?> getConstructors();
	
	public abstract JavaMethodSet<?> getMethods();
}
