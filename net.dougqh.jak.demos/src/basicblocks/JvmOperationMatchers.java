package basicblocks;

import net.dougqh.jak.jvm.operations.BranchOperation;
import net.dougqh.jak.jvm.operations.ConstantOperation;
import net.dougqh.jak.jvm.operations.IfOperation;
import net.dougqh.jak.jvm.operations.InvocationOperation;
import net.dougqh.jak.jvm.operations.LoadOperation;
import net.dougqh.jak.jvm.operations.StoreOperation;

public final class JvmOperationMatchers {
	public static final Class<BranchOperation> BRANCH = BranchOperation.class;
	public static final Class<IfOperation> IF = IfOperation.class;
	
	public static final Class<ConstantOperation> CONST = ConstantOperation.class;
	
	public static final Class<StoreOperation> STORE = StoreOperation.class;
	public static final Class<LoadOperation> LOAD = LoadOperation.class;
	
	public static final Class<InvocationOperation> INVOKE = InvocationOperation.class;
	
	public static final JvmOperationMatcher<LoadOperation> load(final int slot) {
		return new JvmOperationMatcher<LoadOperation>(LoadOperation.class) {
			@Override
			public final boolean matchesCasted(final LoadOperation op) {
				return ( op.slot() == slot );
			}
		};
	}
	
	public static final JvmOperationMatcher<StoreOperation> store(final int slot) {
		return new JvmOperationMatcher<StoreOperation>(StoreOperation.class) {
			@Override
			public final boolean matchesCasted(final StoreOperation op) {
				return ( op.slot() == slot );
			}
		};
	}
}
