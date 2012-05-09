package net.dougqh.jak.jvm.assembler.macros;

import net.dougqh.jak.jvm.assembler.JvmMacro;

public abstract class stmt extends JvmMacro {
	@Override
	protected void write() {
		this.body();
	}
	
	protected abstract void body();
}