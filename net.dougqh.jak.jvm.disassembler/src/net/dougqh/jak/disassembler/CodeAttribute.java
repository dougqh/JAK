package net.dougqh.jak.disassembler;

import java.io.IOException;

final class CodeAttribute {
	private final int maxStack;
	private final int maxLocals;
	
	private final int codeLength;
	private final byte[] code;
	
	CodeAttribute( final ByteInputStream in ) throws IOException {
		this.maxStack = in.u2();
		this.maxLocals = in.u2();
		
		this.codeLength = in.u4();
		this.code = in.read( codeLength );
		
	//	u2 exception_table_length;
	//	{    	u2 start_pc;
	//	      	u2 end_pc;
	//	      	u2  handler_pc;
	//	      	u2  catch_type;
	//	}	exception_table[exception_table_length];
	//	u2 attributes_count;
	//	attribute_info attributes[attributes_count];	
	}
	
	final int length() {
		return this.codeLength;
	}
	
	final int maxStack() {
		return this.maxStack;
	}
	
	final int maxLocals() {
		return this.maxLocals;
	}
}
