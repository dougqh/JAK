package net.dougqh.jak.disassembler;

import java.util.List;

public final class JavaClass extends JavaType {
	JavaClass( final TypeInternals typeReader ) {
		super( typeReader );
	}

	public final List< JavaField > getFields() {
		return this.type.getFields();
	}
	
	public final JavaMethod getClassInitializer() {
		return this.type.getClassInitializer();
	}
	
	public final List< JavaMethod > getConstructors() {
		return this.type.getConstructors();
	}
	
	public final List< JavaMethod > getMethods() {
		return this.type.getMethods();
	}
}
