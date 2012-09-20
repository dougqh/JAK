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
		JvmInputStream in = new JvmInputStream(new byte[]{ 0, 1 });
		assertThat( in.u1(), isByte(0) );
		assertThat( in.u1(), isByte(1) );
		assertThat( in.isDone(), is(true) );
	}
	
	@Test
	public final void readMultiBlock() throws EOFException {
		JvmInputStream in = new JvmInputStream(
			new byte[]{ 0, 1 },
			new byte[]{ 2, 3 }
		);
		
		assertThat( in.u1(), isByte(0) );
		assertThat( in.u1(), isByte(1) );
		assertThat( in.u1(), isByte(2) );
		assertThat( in.u1(), isByte(3) );
		assertThat( in.isDone(), is(true) );
	}
	
	@Test
	public final void readBytesSingleBlock() throws EOFException {
		JvmInputStream in = new JvmInputStream(new byte[]{ 0, 1 });
		assertThat( in.u1(), isByte(0) );
		assertThat( in.u1(), isByte(1) );
		assertThat( in.isDone(), is(true) );
	}
	
	@Test
	public final void readBytesWithinBlock() throws EOFException {
		JvmInputStream in = new JvmInputStream(new byte[]{ 0, 1, 2, 3 });
		assertThat( in.readBytes(3), is(new byte[]{ 0, 1, 2 }) );
		assertThat( in.isDone(), is(false) );
	}
	
	@Test
	public final void readBytesAllOfBlock() throws EOFException {
		JvmInputStream in = new JvmInputStream(new byte[]{ 0, 1, 2, 3 });
		assertThat( in.readBytes(4), is(new byte[]{ 0, 1, 2, 3 }) );
		assertThat( in.isDone(), is(true) );
	}
	
	@Test
	public final void readBytesMultiBlock() throws EOFException {
		JvmInputStream in = new JvmInputStream(
			new byte[]{ 0, 1 },
			new byte[]{ 2, 3 }
		);
		
		assertThat( in.readBytes(4), is(new byte[]{ 0, 1, 2, 3}) );
		assertThat( in.isDone(), is(true) );
	}
	
	private static final Matcher<Byte> isByte(int value) {
		return CoreMatchers.is((byte)value);
	}
}
