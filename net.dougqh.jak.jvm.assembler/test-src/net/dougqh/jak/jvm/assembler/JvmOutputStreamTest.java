package net.dougqh.jak.jvm.assembler;

import static net.dougqh.jak.matchers.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public final class JvmOutputStreamTest {
	public final @Test void slot() {
		JvmOutputStream byteStream = new JvmOutputStream( 32 );
		byteStream.u1( (byte)0xca );
		Byte2Slot slot = byteStream.reserve2Slot();
		byteStream.u1( (byte)0xbe );
		
		slot.u2( 0xfeba );
		
		assertThat(
			byteStream.toByteArray(),
			is( new byte[] { (byte)0xca, (byte)0xfe, (byte)0xba, (byte)0xbe } ) );
	}
	
	public final @Test void magic() {
		JvmOutputStream byteStream = new JvmOutputStream( 32 );
		byteStream.u2( 0xcafe ).u2( 0xbabe );
		
		assertThat(
			byteStream.toByteArray(),
			is( new byte[] { (byte)0xca, (byte)0xfe, (byte)0xba, (byte)0xbe } ) );
	}
	
	public final @Test void writeTo() {
		JvmOutputStream outer = new JvmOutputStream( 2 );
		outer.u1( 5 );
		
		JvmOutputStream inner = new JvmOutputStream( 8 );
		inner.u2( 0xcafe ).u2( 0xbabe );
		
		inner.writeTo( outer );
		
		assertThat(
			outer.toByteArray(),
			is( new byte[] { (byte)5, (byte)0xca, (byte)0xfe, (byte)0xba, (byte)0xbe } ) );
	}
}