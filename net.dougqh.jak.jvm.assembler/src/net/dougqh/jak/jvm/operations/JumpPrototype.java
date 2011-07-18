package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter.Jump;

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
