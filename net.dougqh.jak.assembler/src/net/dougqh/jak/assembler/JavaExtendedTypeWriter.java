package net.dougqh.jak.assembler;


public interface JavaExtendedTypeWriter extends JavaTypeWriter {
	public abstract void define( final JavaFieldDescriptor field );
	
	public abstract void define( final JavaFieldDescriptor field, final boolean value );
	
	public abstract void define( final JavaFieldDescriptor field, final byte value );
	
	public abstract void define( final JavaFieldDescriptor field, final char value );
	
	public abstract void define( final JavaFieldDescriptor field, final short value );
	
	public abstract void define( final JavaFieldDescriptor field, final int value );
	
	public abstract void define( final JavaFieldDescriptor field, final long value );
	
	public abstract void define( final JavaFieldDescriptor field, final float value );
	
	public abstract void define( final JavaFieldDescriptor field, final double value );
	
	public abstract void define( final JavaFieldDescriptor field, final CharSequence value );
	
	public abstract void define( final JavaFieldDescriptor field, final Class< ? > aClass );
}
