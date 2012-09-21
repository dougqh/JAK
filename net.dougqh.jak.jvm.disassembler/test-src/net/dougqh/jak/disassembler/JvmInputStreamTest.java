package net.dougqh.jak.disassembler;

import java.io.EOFException;
import java.io.IOException;
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
		assertThat( in.isEof(), is(true) );
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
		assertThat( in.isEof(), is(true) );
	}
	
	@Test
	public final void readBytesSingleBlock() throws EOFException {
		JvmInputStream in = new JvmInputStream(new byte[]{ 0, 1 });
		assertThat( in.u1(), isByte(0) );
		assertThat( in.u1(), isByte(1) );
		assertThat( in.isEof(), is(true) );
	}
	
	@Test
	public final void readBytesWithinBlock() throws EOFException {
		JvmInputStream in = new JvmInputStream(new byte[]{ 0, 1, 2, 3 });
		assertThat( in.readBytes(3), is(new byte[]{ 0, 1, 2 }) );
		assertThat( in.isEof(), is(false) );
	}
	
	@Test
	public final void readBytesAllOfBlock() throws EOFException {
		JvmInputStream in = new JvmInputStream(new byte[]{ 0, 1, 2, 3 });
		assertThat( in.readBytes(4), is(new byte[]{ 0, 1, 2, 3 }) );
		assertThat( in.isEof(), is(true) );
	}
	
	@Test
	public final void readBytesMultiBlock() throws EOFException {
		JvmInputStream in = new JvmInputStream(
			new byte[]{ 0, 1 },
			new byte[]{ 2, 3 }
		);
		
		assertThat( in.readBytes(4), is(new byte[]{ 0, 1, 2, 3}) );
		assertThat( in.isEof(), is(true) );
	}
	
	@Test
	public final void readBufferWithinBlock() throws EOFException {
		JvmInputStream in = new JvmInputStream(new byte[]{ 0, 1, 2, 3, 4, 5 });
		in.u1();
		assertThat( in.readByteBuffer(4), isBuffer(new byte[]{1, 2, 3, 4}) );
	}
	
	@Test
	public final void readBufferSpanningBlocks() throws EOFException {
		JvmInputStream in = new JvmInputStream(
			new byte[]{ 0, 1 },
			new byte[]{ 2 },
			new byte[]{ 3, 4 }
		);
		assertThat( in.readByteBuffer(4), isBuffer(new byte[]{0, 1, 2, 3}) );
	}
	
	@Test
	public final void readSubStream() throws EOFException {
		// Most complicated case
		// - partial head block
		// - full middle block
		// - partial tail block
		JvmInputStream in = new JvmInputStream(
			new byte[]{ 0, 1, 2 },
			new byte[]{ 3, 4, 5, 6 },
			new byte[]{ 7, 8, 9, 10, 11 }
		);
		in.u1();
		
		JvmInputStream subIn = in.readSubStream(9);
		assertThat( subIn.readBytes(9), is(new byte[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9 }) );
	}
	
	@Test
	public final void reset() throws IOException {
		JvmInputStream in = new JvmInputStream(
			new byte[]{ 0, 1, 2 },
			new byte[]{ 3, 4, 5, 6 },
			new byte[]{ 7, 8, 9, 10 }
		);
		in.enableReset();
		
		in.u4();
		
		in.reset();
		assertThat( in.readBytes(9), is(new byte[]{ 0, 1, 2, 3, 4, 5, 6, 7, 8 }) );
	}
	
	@Test
	public final void multiMark() throws IOException {
		JvmInputStream in = new JvmInputStream(
			new byte[]{ 0, 1, 2 },
			new byte[]{ 3, 4, 5, 6 },
			new byte[]{ 7, 8, 9 }
		);
		in.enableReset();
		
		in.u2();
		JvmInputStream.Mark mark1 = in.mark();
		
		in.u4();
		JvmInputStream.Mark mark2 = in.mark();
		
		in.u4();
		assertThat( in.isEof(), is(true) );
		
		in.resetTo(mark2);
		assertThat( in.u1(), isByte(6) );
		
		in.resetTo(mark1);
		assertThat( in.u1(), isByte(2) );
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
