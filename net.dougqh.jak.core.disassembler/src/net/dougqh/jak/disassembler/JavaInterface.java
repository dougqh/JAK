package net.dougqh.jak.disassembler;

import java.util.List;

public interface JavaInterface extends JavaType {
	public abstract List<? extends JavaMethod> getMethods();
}
