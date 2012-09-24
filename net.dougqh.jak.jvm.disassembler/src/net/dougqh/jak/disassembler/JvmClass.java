package net.dougqh.jak.disassembler;


public final class JvmClass extends JvmType implements JavaClass {
	JvmClass( final JvmTypeInternals typeReader ) {
		super( typeReader );
	}

	public final JvmFieldSet getFields() {
		return new JvmFieldSet(this.type.getFields());
	}
	
	public final JvmMethod getClassInitializer() {
		return this.type.getClassInitializer();
	}
	
	public final JvmMethodSet getConstructors() {
		return new JvmMethodSet(this.type.getConstructors());
	}
	
	@Override
	public final JvmMethodSet getMethods() {
		return new JvmMethodSet(this.type.getMethods());
	}
}
