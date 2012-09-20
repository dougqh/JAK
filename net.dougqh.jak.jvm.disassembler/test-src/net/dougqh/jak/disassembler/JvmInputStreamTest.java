package net.dougqh.jak.disassembler;

import java.io.EOFException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
	
	@Test
	public final void readBufferWithinBlock() throws EOFException {
		JvmInputStream in = new JvmInputStream(new byte[]{ 0, 1, 2, 3, 4, 5 });
		in.u1();
		assertThat( in.readByteBuffer(4), isBuffer(new byte[]{1, 2, 3, 4}) );
	}
	
	private static final Matcher<Byte> isByte(final int value) {
		return CoreMatchers.is((byte)value);
	}
	
	private static final Matcher<ByteBuffer> isBuffer(final byte[] expected) {
		return new BaseMatcher<ByteBuffer>() {
			@Override
			public final void describeTo(final Description description) {
				description.appendValue(Arrays.asList(expected));
			}
			
			@Override
			public final boolean matches(final Object actualObj) {
				ByteBuffer actual = (ByteBuffer)actualObj;
				
				if ( actual.limit() - actual.position() != expected.length ) {
					return false;
				}
				
				for ( byte expectedByte: expected ) {
					if ( expectedByte != actual.get() ) {
						return false;
					}
				}
				
				return true;
			}
		};
	}
}
