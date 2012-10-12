package net.dougqh.jak.disassembler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.dougqh.jak.JavaFilter;

public class JvmFieldSet implements JavaFieldSet<JvmField> {
	private final List<JvmField> fields;
	
	JvmFieldSet(final List<JvmField> fields) {
		this.fields = fields;
	}
	
	@Override
	public final JvmField get(final int index) {
		return this.fields.get(index);
	}
	
	@Override
	public final boolean isEmpty() {
		return this.fields.isEmpty();
	}
	
	@Override
	public final int size() {
		return this.fields.size();
	}
	
	@Override
	public final Iterator<JvmField> iterator() {
		return this.fields.iterator();
	}
	
	@Override
	public final JvmFieldSet filter(
		final JavaFilter<? super JvmField> predicate)
	{
		ArrayList<JvmField> matched = new ArrayList<JvmField>(this.fields.size());
		for ( JvmField field: this ) {
			if ( predicate.matches(field) ) {
				matched.add(field);
			}
		}
		return new JvmFieldSet(matched);
	}
}
