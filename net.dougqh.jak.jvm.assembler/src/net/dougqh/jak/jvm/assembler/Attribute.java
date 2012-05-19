package net.dougqh.jak.jvm.assembler;

abstract class Attribute {
	protected final WritingContext context;
	protected final ConstantPool constantPool;
	protected final String name;
	
	Attribute(
		final WritingContext context,
		final String name )
	{
		this.context = context;
		this.constantPool = this.context.constantPool;
		this.name = name;
	}

	abstract int length();
	
	abstract boolean isEmpty();
	
	final void writeHeader( final JvmOutputStream out ) {
		ConstantEntry nameEntry = this.constantPool.addUtf8( this.name );	
		out.u2( nameEntry ).u4( this.length() );
	}
	
	void prepare() {
	}
	
	abstract void writeBody( final JvmOutputStream out );
	
	static interface DeferredAttribute {}
}
