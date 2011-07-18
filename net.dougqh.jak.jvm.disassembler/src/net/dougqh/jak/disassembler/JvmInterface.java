package net.dougqh.jak.disassembler;

import java.util.List;

public final class JvmInterface
	extends JvmType
	implements JavaInterface
{
	JvmInterface( final JvmTypeInternals typeReader ) {
		super( typeReader );
	}
	
	public final List< JvmField > getFields() {
		return this.type.getFields();
	}
	
	@Override
	public final List< JavaMethod > getMethods() {
		return asJava( this.type.getMethods() );
	}
}
