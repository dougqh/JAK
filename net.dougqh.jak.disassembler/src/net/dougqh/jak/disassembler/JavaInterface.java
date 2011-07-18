package net.dougqh.jak.disassembler;

import java.util.List;

public final class JavaInterface extends JavaType {
	JavaInterface( final TypeInternals typeReader ) {
		super( typeReader );
	}
	
	public final List< JavaField > getFields() {
		return this.type.getFields();
	}
	
	public final List< JavaMethod > getMethods() {
		return this.type.getMethods();
	}
}
