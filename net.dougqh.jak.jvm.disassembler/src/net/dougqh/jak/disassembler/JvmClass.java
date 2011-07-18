package net.dougqh.jak.disassembler;

import java.util.List;

public final class JvmClass extends JvmType implements JavaClass {
	JvmClass( final JvmTypeInternals typeReader ) {
		super( typeReader );
	}

	public final List< JvmField > getFields() {
		return this.type.getFields();
	}
	
	public final JvmMethod getClassInitializer() {
		return this.type.getClassInitializer();
	}
	
	public final List< JavaMethod > getConstructors() {
		return asJava( this.type.getConstructors() );
	}
	
	@Override
	public final List< JavaMethod > getMethods() {
		return asJava( this.type.getMethods() );
	}
}
