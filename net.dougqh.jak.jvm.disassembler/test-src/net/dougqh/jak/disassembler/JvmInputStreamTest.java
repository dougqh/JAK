package net.dougqh.jak.disassembler;

import java.io.EOFException;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public final class JvmInputStreamTest {
	@Test
	public final void readSingleBlock() throws EOFException {
		JvmInputStream in = new JvmInputStream(new byte[] { 0, 1 });
		assertThat( in.u1(), isByte(0) );
		assertThat( in.u1(), isByte(1) );
		assertThat( in.isDone(), is(true) );
	}
	
	@Test
	public final void readMultiBlock() throws EOFException {
		JvmInputStream in = new JvmInputStream(
			new byte[] { 0, 1 },
			new byte[] { 2, 3 }
		);
		
		assertThat( in.u1(), isByte(0) );
		assertThat( in.u1(), isByte(1) );
		assertThat( in.u1(), isByte(2) );
		assertThat( in.u1(), isByte(3) );
		assertThat( in.isDone(), is(true) );
	}
	
	private static final Matcher<Byte> isByte(int value) {
		return CoreMatchers.is((byte)value);
	}
}
