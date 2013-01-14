package net.dougqh.jak.disassembler;

import java.io.IOException;

import net.dougqh.jak.JavaFilter;



public final class JvmClass extends JvmType implements JavaClass {
	public static final JvmClass read(final Class<?> aClass) throws IOException {
		JvmReader reader = new JvmReader(aClass.getClassLoader());
		return reader.<JvmClass>read(aClass);
	}
	
	JvmClass( final JvmTypeInternals typeReader ) {
		super( typeReader );
	}

	public final JvmFieldSet getFields() {
		return new JvmFieldSet(this.type.getFields());
	}
	
	public final JvmFieldSet getFields( final JavaFilter<? super JvmField> filter ) {
		return this.getFields().filter(filter);
	}
	
	public final JvmMethod getClassInitializer() {
		return this.type.getClassInitializer();
	}
	
	public final JvmMethodSet getConstructors() {
		return new JvmMethodSet(this.type.getConstructors());
	}
	
	public final JvmMethodSet getConstructors( final JavaFilter<? super JvmMethod> filter ) {
		return this.getConstructors().filter(filter);
	}
	
	@Override
	public final JvmMethodSet getMethods() {
		return new JvmMethodSet(this.type.getMethods());
	}
	
	public final JvmMethodSet getMethods( final JavaFilter<? super JvmMethod> filter ) {
		return this.getMethods().filter(filter);
	}
}
