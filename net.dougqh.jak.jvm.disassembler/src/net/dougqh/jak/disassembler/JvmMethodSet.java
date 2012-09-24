package net.dougqh.jak.disassembler;

import java.util.Iterator;
import java.util.List;


public final class JvmMethodSet implements JavaMethodSet<JvmMethod> {
	private final List<JvmMethod> methods;
	
	JvmMethodSet(final List<JvmMethod> methods) {
		this.methods = methods;
	}
	
	@Override
	public final JvmMethod get(final int index) {
		return this.methods.get(index);
	}
	
	@Override
	public final boolean isEmpty() {
		return this.methods.isEmpty();
	}
	
	@Override
	public final int size() {
		return this.methods.size();
	}
	
	@Override
	public final Iterator<JvmMethod> iterator() {
		return this.methods.iterator();
	}
}
