package net.dougqh.jak.disassembler;

import net.dougqh.functional.Filter;

public abstract class JvmTypeSet extends JavaTypeSet<JvmType> {
	@Override
	public abstract JvmTypeSet filter(final Filter<? super JvmType> filter);
	
	public final JvmClass getClass(final String name) {
		return (JvmClass)this.get(name);
	}
	
	public final JvmInterface getInterface(final String name) {
		return (JvmInterface)this.get(name);
	}
	
	@Override
	public final JvmAnnotation getAnnotation(final String name) {
		return (JvmAnnotation)this.getAnnotation(name);
	}
	
	@Override
	public final JvmEnum getEnum(final String name) {
		return (JvmEnum)this.getEnum(name);
	}
}
