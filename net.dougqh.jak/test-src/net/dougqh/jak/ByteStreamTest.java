package net.dougqh.jak;

import static org.junit.Assert.*;
import static net.dougqh.jak.matchers.Matchers.*;

import net.dougqh.jak.Byte2Slot;
import net.dougqh.jak.ByteStream;

import org.junit.Test;

public final class ByteStreamTest {
	public final @Test void slot() {
		ByteStream byteStream = new ByteStream( 32 );
		byteStream.u1( (byte)0xca );
		Byte2Slot slot = byteStream.reserve2Slot();
		byteStream.u1( (byte)0xbe );
		
		slot.u2( 0xfeba );
		
		assertThat(
			byteStream.toByteArray(),
			is( new byte[] { (byte)0xca, (byte)0xfe, (byte)0xba, (byte)0xbe } ) );
	}
	
	public final @Test void magic() {
		ByteStream byteStream = new ByteStream( 32 );
		byteStream.u2( 0xcafe ).u2( 0xbabe );
		
		assertThat(
			byteStream.toByteArray(),
			is( new byte[] { (byte)0xca, (byte)0xfe, (byte)0xba, (byte)0xbe } ) );
	}
}