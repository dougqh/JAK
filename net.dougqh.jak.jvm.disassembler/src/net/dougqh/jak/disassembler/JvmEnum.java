package net.dougqh.jak.disassembler;

public final class JvmEnum
	extends JvmType
	implements JavaEnum
{
	JvmEnum( final JvmTypeInternals typeReader ) {
		super( typeReader );
	}
}
