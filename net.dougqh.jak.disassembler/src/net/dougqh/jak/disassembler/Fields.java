package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class Fields {
	private final JavaField[] fields;
	
	Fields( final ConstantPool constantPool, final ByteInputStream in ) 
		throws IOException
	{
		int count = in.u2();
		this.fields = new JavaField[ count ];
		
		for ( int i = 0; i < count; ++i ) {
			int flags = in.u2();
			int nameIndex = in.u2();
			int descriptorIndex = in.u2();
			Attributes attributes = new Attributes( constantPool, in );
			
			this.fields[ i ] = new JavaField(
				constantPool,
				flags,
				nameIndex,
				descriptorIndex,
				attributes );
		}
	}
	
	final List< JavaField > getFields() {
		return Collections.unmodifiableList( Arrays.asList( this.fields ) );
	}
}
