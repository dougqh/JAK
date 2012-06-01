package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class new_ extends BaseJvmOperation {
	public static final String ID = "new";
	public static final byte CODE = NEW;
	
	public static final new_ prototype() {
		return new new_( Object.class );
	}
	
	private final Class< ? > aClass;
	
	public new_( final Class< ? > aClass ) {
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
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { Reference.class };
	}
	
	@Override
	public final void write( final JvmOperationProcessor writer ) {
		writer.new_( this.aClass );
	}
}