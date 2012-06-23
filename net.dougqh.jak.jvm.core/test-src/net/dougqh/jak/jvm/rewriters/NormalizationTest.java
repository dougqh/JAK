package net.dougqh.jak.jvm.rewriters;

import net.dougqh.jak.jvm.JvmOperationRewritingFilter;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.bipush;
import net.dougqh.jak.jvm.operations.iconst_4;
import net.dougqh.jak.jvm.operations.iconst_m1;
import net.dougqh.jak.jvm.operations.istore;
import net.dougqh.jak.jvm.operations.istore_2;
import net.dougqh.jak.jvm.operations.ldc;
import net.dougqh.jak.jvm.operations.sipush;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Test;

import static org.junit.Assert.*;

public final class NormalizationTest {
	@Test
	public final void istore() {
		TestJvmOperationHydrator hydrator = new TestJvmOperationHydrator();
		
		JvmOperationRewritingFilter rewritingFilter = new JvmOperationRewritingFilter(hydrator);
		rewritingFilter.set(new Normalizer());
		
		rewritingFilter.istore(2);
		assertThat( hydrator.get(), is(istore_2.instance()) );
		
		rewritingFilter.istore(6);
		assertThat( hydrator.get(), is(new istore(6)) );
	}
	
	@Test
	public final void ldc() {
		TestJvmOperationHydrator hydrator = new TestJvmOperationHydrator();
		
		JvmOperationRewritingFilter rewritingFilter = new JvmOperationRewritingFilter(hydrator);
		rewritingFilter.set(new Normalizer());
		
		rewritingFilter.ldc(-1);
		assertThat( hydrator.get(), is(iconst_m1.instance()) );
		
		rewritingFilter.ldc(4);
		assertThat( hydrator.get(), is(iconst_4.instance()) );
		
		rewritingFilter.ldc(27);
		assertThat( hydrator.get(), is(new bipush((byte)27)) );
		
		rewritingFilter.ldc(-300);
		assertThat( hydrator.get(), is(new sipush((short)-300)) );
		
		rewritingFilter.ldc(50000);
		assertThat( hydrator.get(), is(new ldc(50000)) );
	}
	
	private static final Matcher< JvmOperation > is( final JvmOperation jvmOp ) {
		return CoreMatchers.is(jvmOp);
	}
}
