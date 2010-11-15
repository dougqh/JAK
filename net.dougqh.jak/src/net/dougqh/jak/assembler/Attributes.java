package net.dougqh.jak.assembler;

import java.util.ArrayList;

import net.dougqh.jak.assembler.Attribute.Deferred;


final class Attributes {
	private final ByteStream out;
	private int count = 0;
	
	private final ArrayList< Attribute > deferredAttributes = new ArrayList< Attribute >( 4 );
	private boolean prepared = false;
	
	Attributes( final int initialCapacity ) {
		this.out = new ByteStream( initialCapacity );
	}
	
	final void add( final Attribute attribute ) {
		if ( attribute instanceof Deferred ) {
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
	
	final void write( final ByteStream out ) {
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
	
	static final void writeEmpty( final ByteStream out ) {
		out.u2( 0 );
	}
}
