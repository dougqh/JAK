package net.dougqh.jak;

abstract class Attribute {
	protected final ConstantPool constantPool;
	protected final String name;
	
	Attribute(
		final ConstantPool constantPool,
		final String name )
	{
		this.constantPool = constantPool;
		this.name = name;
	}

	abstract int length();
	
	abstract boolean isEmpty();
	
	final void writeHeader( final ByteStream out ) {
		ConstantEntry nameEntry = this.constantPool.addUtf8( this.name );	
		out.u2( nameEntry ).u4( this.length() );
	}
	
	abstract void writeBody( final ByteStream out );
	
	static interface Deferred {}
}
