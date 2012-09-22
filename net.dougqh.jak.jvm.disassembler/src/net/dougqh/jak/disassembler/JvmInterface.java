package net.dougqh.jak.disassembler;

import java.util.List;

public final class JvmInterface
	extends JvmType
	implements JavaInterface
{
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
