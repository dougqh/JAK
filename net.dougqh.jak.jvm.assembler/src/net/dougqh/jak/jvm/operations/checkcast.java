package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class checkcast extends JvmOperation {
	public static final String ID = "checkcast";
	public static final byte CODE = CHECKCAST;
	
	public static final checkcast prototype() {
		return new checkcast( Object.class );
	}
	
	private final Class< ? > aClass;
	
	public checkcast( final Class< ? > aClass ) {
		this.aClass = aClass;
	}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final boolean isPolymorphic() {
		return true;
	}
	
	@Override
	public final Class< ? >[] getCodeOperandTypes() {
		return new Class< ? >[] { Class.class };
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return new Class< ? >[] { Reference.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { Reference.class };
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.checkcast( this.aClass );
	}
}