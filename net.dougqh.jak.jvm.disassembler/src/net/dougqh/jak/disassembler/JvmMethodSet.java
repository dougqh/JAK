package net.dougqh.jak.disassembler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.dougqh.jak.JavaFilter;


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
	
	@Override
	public final JvmMethodSet filter(
		final JavaFilter<? super JvmMethod> predicate)
	{
		ArrayList<JvmMethod> matched = new ArrayList<JvmMethod>(this.methods.size());
		for ( JvmMethod method: this ) {
			if ( predicate.matches(method) ) {
				matched.add(method);
			}
		}
		return new JvmMethodSet(matched);
	}
}
