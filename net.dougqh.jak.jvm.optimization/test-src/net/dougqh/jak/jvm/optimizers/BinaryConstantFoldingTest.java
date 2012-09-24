package net.dougqh.jak.jvm.optimizers;

import net.dougqh.jak.jvm.JvmOperationRewritingFilter;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.bipush;
import net.dougqh.jak.jvm.operations.iconst_1;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Test;

import static org.junit.Assert.*;

public final class BinaryConstantFoldingTest {
	@Test
	public final void iadd_to_iconst() {
		TestJvmOperationHydrator hydrator = new TestJvmOperationHydrator();
		
		JvmOperationRewritingFilter rewritingFilter = new JvmOperationRewritingFilter(hydrator);
		rewritingFilter.set(new BinaryConstantFolding());

		rewritingFilter.iconst_0();
		rewritingFilter.iconst_1();
		rewritingFilter.iadd();
		
		assertThat( hydrator.get(), is(iconst_1.instance()));
	}
	
	@Test
	public final void iadd_to_bipush() {
		TestJvmOperationHydrator hydrator = new TestJvmOperationHydrator();
		
		JvmOperationRewritingFilter rewritingFilter = new JvmOperationRewritingFilter(hydrator);
		rewritingFilter.set(new BinaryConstantFolding());

		rewritingFilter.iconst_5();
		rewritingFilter.iconst_1();
		rewritingFilter.iadd();
		
		assertThat( hydrator.get(), is(new bipush((byte)6)) );		
	}
	
	private static final Matcher< JvmOperation > is( final JvmOperation jvmOp ) {
		return CoreMatchers.is(jvmOp);
	}
}
