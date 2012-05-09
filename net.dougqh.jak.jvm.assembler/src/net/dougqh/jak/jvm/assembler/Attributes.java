package net.dougqh.jak.jvm.assembler;

import java.util.ArrayList;

import net.dougqh.jak.jvm.assembler.Attribute.DeferredAttribute;


final class Attributes {
	private final JvmOutputStream out;
	private int count = 0;
	
	private final ArrayList< Attribute > deferredAttributes = new ArrayList< Attribute >( 4 );
	private boolean prepared = false;
	
	Attributes( final int initialCapacity ) {
		this.out = new JvmOutputStream( initialCapacity );
	}
	
	final void add( final Attribute attribute ) {
		if ( attribute instanceof DeferredAttribute ) {
			this.deferredAttributes.add( attribute );
		} else {
			this.write( attribute );
		}
	}
	
	private final void write( final Attribute attribute ) {
		if ( ! attribute.isEmpty() ) {
			++this.count;
			attribute.prepare();
			attribute.writeHeader( this.out );
			attribute.writeBody( this.out );
		}
	}
	
	final void write( final JvmOutputStream out ) {
		this.prepareForWrite();
		
		out.u2( this.count );
		this.out.writeTo( out );
	}
	
	final void prepareForWrite() {
		if ( this.prepared ) {
			return;
		}
		
		for ( Attribute attribute : this.deferredAttributes ) {
			this.write( attribute );
		}
		this.prepared = true;
	}
	
	static final void writeEmpty( final JvmOutputStream out ) {
		out.u2( 0 );
	}
}
