package net.dougqh.jak.disassembler.api;

import java.io.IOException;

import net.dougqh.jak.disassembler.JvmClass;
import net.dougqh.jak.disassembler.JvmReader;

import org.junit.Test;

import testdata.NonTrivialClass;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public final class NonTrivialClassTest {
	@Test
	public final void testStatics() throws IOException {
		JvmClass aClass = new JvmReader().read(NonTrivialClass.class);
		assertThat( aClass.getFields().size(), is(5) );
	}
}
