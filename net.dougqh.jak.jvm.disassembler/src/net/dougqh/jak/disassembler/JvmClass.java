package net.dougqh.jak.disassembler;

import net.dougqh.jak.JavaFilter;


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
	
	public final JvmMethod getMethod(final String name) {
		return this.getMethods().get(new JavaFilter<JvmMethod>() {
			@Override
			public final boolean matches(final JvmMethod method) {
				return method.getName().equals(name);
			}
		});
	}
}
