package net.dougqh.jak.jvm.rewriters;

import net.dougqh.jak.jvm.JvmOperationRewritingFilter;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.istore;
import net.dougqh.jak.jvm.operations.istore_2;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Test;

import static org.junit.Assert.*;

public final class RewritingTest {
	@Test
	public final void normalization() {
		TestJvmOperationHydrator hydrator = new TestJvmOperationHydrator();
		
		JvmOperationRewritingFilter rewritingFilter = new JvmOperationRewritingFilter(hydrator);
		rewritingFilter.set(new Normalizer());
		
		rewritingFilter.istore(2);
		assertThat( hydrator.last(), is( istore_2.instance() ) );
		
		rewritingFilter.istore(6);
		assertThat( hydrator.last(), is(new istore(6)) );
	}
	
	private static final Matcher< JvmOperation > is( final JvmOperation jvmOp ) {
		return CoreMatchers.is(jvmOp);
	}
}
