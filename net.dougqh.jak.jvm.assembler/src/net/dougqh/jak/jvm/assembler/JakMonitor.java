package net.dougqh.jak.jvm.assembler;

public abstract class JakMonitor {
	static final JakMonitor NULL = new JakMonitor() {};
	
	public JvmCoreCodeWriter monitor( final JvmCoreCodeWriter wrappedWriter ) {
		return wrappedWriter;
	}
	
	public JvmLocals monitor( final JvmLocals locals ) {
		return locals;
	}
	
	public JvmStack monitor( final JvmStack stack ) {
		return stack;
	}
}
