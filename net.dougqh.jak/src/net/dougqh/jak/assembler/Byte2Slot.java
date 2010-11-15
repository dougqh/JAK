package net.dougqh.jak.assembler;


abstract class Byte2Slot {
	abstract int offset();
	
	abstract void u2( final int value );
}
