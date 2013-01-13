package net.dougqh.jak.jvm.operations;


public abstract class BaseJvmOperation
	implements JvmOperation, JvmOperation.Internals
{
	protected static final Class< ? >[] NO_ARGS = {};
	protected static final Class< ? >[] NO_RESULTS = {};
	
	protected Integer pos;
	
	protected BaseJvmOperation() {}
	
	@Override
	public final Internals internals() {
		return this;
	}
	
	@Override
	public final void initPos(final int pos) {
		this.pos = pos;
	}
	
	@Override
	public final Integer pos() {
		return this.pos;
	}
	
	@Override
	public String getOperator() {
		return null;
	}
	
	@Override
	public boolean isPolymorphic() {
		return false;
	}
}
