package net.dougqh.jak.disassembler.api;

import java.io.IOException;

import net.dougqh.jak.disassembler.JvmClass;
import net.dougqh.jak.disassembler.JvmReader;

import org.junit.Test;

import testdata.NonTrivialClass;
import static net.dougqh.jak.Jak.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public final class NonTrivialClassTest {
	@Test
	public final void testFields() throws IOException {
		JvmClass aClass = new JvmReader().read(NonTrivialClass.class);
		assertThat( aClass.getFields().size(), is(5) );
		
		assertThat( aClass.getFields(public_()).size(), is(2) );
		assertThat( aClass.getFields(static_()).size(), is(3) );
		assertThat( aClass.getFields(public_().static_()).size(), is(2) );
		
		assertThat( aClass.getFields(private_()).size(), is(3) );
		assertThat( aClass.getFields(private_().static_()).size(), is(1) );
	}
}
