package net.dougqh.jak;

import static net.dougqh.jak.JavaAssembler.*;
import static org.junit.Assert.*;

import org.junit.Test;

public final class FlagsTest {
	public final @Test void flags() {
		assertEquals(
			final_().private_(),
			private_().final_() );
		
		assertEquals(
			abstract_().protected_(),
			protected_().abstract_() );
		
		assertEquals(
			native_().public_(),
			public_().native_() );
		
		assertEquals(
			transient_().volatile_(),
			volatile_().transient_() );
		
		assertEquals(
			varargs().strictfp_(),
			strictfp_().varargs() );
		
		assertEquals(
			synchronized_().final_(),
			final_().synchronized_() );
	}
}
