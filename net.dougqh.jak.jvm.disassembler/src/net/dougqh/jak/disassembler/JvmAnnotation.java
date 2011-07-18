package net.dougqh.jak.disassembler;

public final class JvmAnnotation
	extends JvmType
	implements JavaAnnotation
{
	JvmAnnotation( final JvmTypeInternals typeReader ) {
		super( typeReader );
	}
}
