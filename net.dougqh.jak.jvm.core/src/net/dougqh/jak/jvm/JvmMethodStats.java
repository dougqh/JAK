package net.dougqh.jak.jvm;

public interface JvmMethodStats {
	public int codeLength();
	
	public int maxStack();
	
	public int maxLocals();
}
