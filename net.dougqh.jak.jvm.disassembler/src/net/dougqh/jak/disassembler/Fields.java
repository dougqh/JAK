package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class Fields {
	private final JvmField[] fields;
	
	Fields( final ConstantPool constantPool, final JvmInputStream in ) 
		throws IOException
	{
		int count = in.u2();
		this.fields = new JvmField[ count ];
		
		for ( int i = 0; i < count; ++i ) {
			int flags = in.u2();
			int nameIndex = in.u2();
			int descriptorIndex = in.u2();
			Attributes attributes = new Attributes( constantPool, in );
			
			this.fields[ i ] = new JvmField(
				constantPool,
				flags,
				nameIndex,
				descriptorIndex,
				attributes );
		}
	}
	
	final List< JvmField > getFields() {
		return Collections.unmodifiableList( Arrays.asList( this.fields ) );
	}
}
