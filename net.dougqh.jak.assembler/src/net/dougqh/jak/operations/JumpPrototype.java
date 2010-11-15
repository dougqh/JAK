package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter.Jump;

final class JumpPrototype extends Jump {
	@Override
	public final Integer pos() {
		return 0;
	}
	
	@Override
	public final String toString() {
		return "prototype";
	}
}
