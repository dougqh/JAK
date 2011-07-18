package net.dougqh.jak.assembler;

import net.dougqh.jak.JavaField;

public interface JakExtendedTypeWriter extends JakTypeWriter {
	public abstract void define( final JavaField field );
	
	public abstract void define( final JavaField field, final boolean value );
	
	public abstract void define( final JavaField field, final byte value );
	
	public abstract void define( final JavaField field, final char value );
	
	public abstract void define( final JavaField field, final short value );
	
	public abstract void define( final JavaField field, final int value );
	
	public abstract void define( final JavaField field, final long value );
	
	public abstract void define( final JavaField field, final float value );
	
	public abstract void define( final JavaField field, final double value );
	
	public abstract void define( final JavaField field, final CharSequence value );
	
	public abstract void define( final JavaField field, final Class< ? > aClass );
}
