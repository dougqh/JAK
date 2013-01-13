package net.dougqh.jak.disassembler;

import java.lang.reflect.Type;
import java.util.List;

import net.dougqh.jak.AnalysisHelper;
import net.dougqh.jak.Flags;
import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.SimpleJvmOperationProcessor;
import net.dougqh.jak.jvm.operations.JvmOperation;
import static net.dougqh.jak.Methods.*;

public final class JvmMethod implements JavaMethod {
	private final ConstantPool constantPool;
	
	private final int flags;
	private final int nameIndex;
	private final int descriptorIndex;
	private final Attributes attributes;
	
	private volatile MethodAnalysisHelper analysisHelper = null;
	
	JvmMethod(
		final ConstantPool constantPool,
		final int flags,
		final int nameIndex,
		final int descriptorIndex,
		final Attributes attributes )
	{
		this.constantPool = constantPool;
		
		this.flags = flags;
		this.nameIndex = nameIndex;
		this.descriptorIndex = descriptorIndex;
		this.attributes = attributes;
	}
	
	@Override
	public final String getName() {
		return this.constantPool.utf8( this.nameIndex );
	}
	
	@Override
	public final boolean isConstructor() {
		return this.getName().equals( INIT );
	}
	
	@Override
	public final boolean isClassInitializer() {
		return this.getName().equals( CLINIT );
	}
	
	@Override
	public final boolean isPublic() {
		return Flags.isPublic( this.flags );
	}
	
	@Override
	public final boolean isDefault() {
		return Flags.isDefault( this.flags );
	}
	
	@Override
	public final boolean isProtected() {
		return Flags.isProtected( this.flags );
	}
	
	@Override
	public final boolean isPrivate() {
		return Flags.isPrivate( this.flags );
	}
	
	@Override
	public final Type getReturnType() {
		throw new UnsupportedOperationException( "incomplete" );
	}
	
	@Override
	public final List< Type > getParameterTypes() {
		throw new UnsupportedOperationException( "incomplete" );
	}
	
	private final CodeAttribute getCodeAttribute() {
		return this.attributes.getCode();
	}
	
	public final Iterable<JvmOperation> getOperations() {
		return this.getCodeAttribute().operations();
	}
	
	public final void processOperations(final JvmOperationProcessor processor) {
		this.getCodeAttribute().process(processor);
	}
	
	public final boolean hasCode() {
		return ( this.getCodeAttribute() != null );
	}
	
	public final int getCodeLength() {
		CodeAttribute code = this.getCodeAttribute();
		return ( code == null ) ? -1 : code.length();
	}
	
	public final int getMaxStack() {
		CodeAttribute code = this.getCodeAttribute();
		return ( code == null ) ? 0 : code.maxStack();
	}

	public final int getMaxLocals() {
		CodeAttribute code = this.getCodeAttribute();
		return ( code == null ) ? 0 : code.maxLocals();
	}
	
	public final void process( final JvmOperationProcessor processor ) {
		this.getCodeAttribute().process(processor);
	}
	
	public final void process( final SimpleJvmOperationProcessor processor ) {
		this.getCodeAttribute().process(processor);
	}
	
	public final Iterable< JvmOperation > operations() {
		return this.getCodeAttribute().operations();
	}
	
	public final <V> V get(final Class<V> analysisClass) {
		// The lack of coarser locking here, means that that a stray analysis helper
		// instance could be needlessly created, but that's okay.
		if ( this.analysisHelper == null ) {
			this.analysisHelper = new MethodAnalysisHelper(this);
		}
		return this.analysisHelper.get(analysisClass);
	}
	
	public final String toString() {
		return this.getName();
	}
	
	private static final class MethodAnalysisHelper extends AnalysisHelper<JvmMethod> {
		public MethodAnalysisHelper(final JvmMethod method) {
			super(method);
		}

		@SuppressWarnings("unchecked")
		private static final Class<? super JvmMethod>[] SEARCH_TYPES = new Class[]{
			JvmMethod.class,
			JavaMethod.class
		};
		
		@Override
		protected final Class<? super JvmMethod>[] getSearchTypes() {
			return SEARCH_TYPES;
		}
	}
}
