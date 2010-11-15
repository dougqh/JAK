package net.dougqh.jak.assembler;

import static org.junit.Assert.*;
import static net.dougqh.jak.assembler.JavaAssembler.*;
import static net.dougqh.jak.matchers.Matchers.*;

import org.junit.Test;

public final class FlagsTest {
	public final @Test void flags() {
		assertThat(
			final_().private_(),
			is( private_().final_() ) );
		
		assertThat(
			abstract_().protected_(),
			is( protected_().abstract_() ) );
		
		assertThat(
			native_().public_(),
			is( public_().native_() ) );
		
		assertThat(
			transient_().volatile_(),
			is( volatile_().transient_() ) );
		
		assertThat(
			varargs().strictfp_(),
			is( strictfp_().varargs() ) );
		
		assertThat(
			synchronized_().final_(),
			is( final_().synchronized_() ) );
	}
}
