package net.dougqh.jak.jvm.assembler;


final class Interfaces {	
	private final JvmOutputStream out = new JvmOutputStream( 16 );
	private int count = 0;
	
	private final ConstantPool constantPool;
	
	Interfaces( final ConstantPool constantPool ) {
		this.constantPool = constantPool;
	}
	
	final Interfaces add( final Class< ? > anInterface ) {
		++this.count;
		this.out.u2( this.constantPool.addClassInfo( anInterface ) );
		return this;
	}
	
	final void write( final JvmOutputStream out ) {
		out.u2( this.count );
		this.out.writeTo( out );
	}
}
