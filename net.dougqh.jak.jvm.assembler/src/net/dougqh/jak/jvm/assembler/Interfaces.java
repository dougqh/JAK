package net.dougqh.jak.jvm.assembler;


final class Interfaces {	
	private final JvmOutputStream out = new JvmOutputStream( 16 );
	private int count = 0;
	
	private final WritingContext context;
	
	Interfaces( final WritingContext context ) {
		this.context = context;
	}
	
	final Interfaces add( final Class< ? > anInterface ) {
		++this.count;
		this.out.u2( this.context.constantPool.addClassInfo( anInterface ) );
		return this;
	}
	
	final void write( final JvmOutputStream out ) {
		out.u2( this.count );
		this.out.writeTo( out );
	}
}
