package net.dougqh.jak.jvm.rewriters;

import net.dougqh.jak.jvm.JvmOperationRewritingFilter;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.iconst_m1;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Test;

import static org.junit.Assert.*;

public final class UnaryConstantFoldingTest {
	@Test
	public final void ineg() {
		TestJvmOperationHydrator hydrator = new TestJvmOperationHydrator();
		
		JvmOperationRewritingFilter rewritingFilter = new JvmOperationRewritingFilter(hydrator);
		rewritingFilter.set(new UnaryConstantFolding());

		rewritingFilter.iconst_1();
		rewritingFilter.ineg();
		
		assertThat( hydrator.last(), is(iconst_m1.instance()) );
	}
	
	private static final Matcher< JvmOperation > is( final JvmOperation jvmOp ) {
		return CoreMatchers.is(jvmOp);
	}
}
