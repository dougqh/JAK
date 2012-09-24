package net.dougqh.jak.disassembler;

import java.util.Iterator;
import java.util.List;

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
}
