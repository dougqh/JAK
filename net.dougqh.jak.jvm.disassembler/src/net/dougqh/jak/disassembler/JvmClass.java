package net.dougqh.jak.disassembler;

import java.util.List;

public final class JvmClass extends JvmType implements JavaClass {
	JvmClass( final JvmTypeInternals typeReader ) {
		super( typeReader );
	}

	public final List<? extends JvmField> getFields() {
		return this.type.getFields();
	}
	
	public final JvmMethod getClassInitializer() {
		return this.type.getClassInitializer();
	}
	
	public final List<? extends JvmMethod> getConstructors() {
		return this.type.getConstructors();
	}
	
	@Override
	public final List<? extends JavaMethod> getMethods() {
		return this.type.getMethods();
	}
}
