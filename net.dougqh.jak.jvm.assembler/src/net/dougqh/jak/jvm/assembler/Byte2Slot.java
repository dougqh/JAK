package net.dougqh.jak.jvm.assembler;


abstract class Byte2Slot {
	abstract int offset();
	
	abstract void u2( final int value );
}
