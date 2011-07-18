package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class instanceof_ extends JvmOperation {
	public static final String ID = "instanceof";
	public static final byte CODE = INSTANCEOF;
	
	public static final instanceof_ prototype() {
		return new instanceof_( Object.class );
	}
	
	private final Class< ? > aClass;
	
	public instanceof_( final Class< ? > aClass ) {
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
	public final Class< ? >[] getCodeOperandTypes() {
		return new Class< ? >[] { Class.class };
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return new Class< ? >[] { Reference.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { boolean.class };
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.instanceof_( this.aClass );
	}
}