package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.util.List;

public final class JvmInterface
	extends JvmType
	implements JavaInterface
{	
	public static final JvmClass read(final Class<?> aClass) throws IOException {
		JvmReader reader = new JvmReader().addClassLoader(aClass.getClassLoader());
		return reader.<JvmClass>read(aClass);
	}
	
	JvmInterface( final JvmTypeInternals typeReader ) {
		super( typeReader );
	}
	
	public final List<? extends JvmField> getFields() {
		return this.type.getFields();
	}
	
	@Override
	public final List<? extends JavaMethod> getMethods() {
		return this.type.getMethods();
	}
}
