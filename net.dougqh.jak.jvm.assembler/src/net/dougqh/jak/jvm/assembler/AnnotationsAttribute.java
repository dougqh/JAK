package net.dougqh.jak.jvm.assembler;

import java.lang.annotation.RetentionPolicy;

public final class AnnotationsAttribute extends Attribute {
//TODO: Finish implementation
	
//	RuntimeVisibleAnnotations_attribute {
//		u2 attribute_name_index;
//		u4 attribute_length;
//		u2 num_annotations;
//		annotation annotations[num_annotations];
//	}
	
	private int numAnnotations;
	
	AnnotationsAttribute(
		final ConstantPool constantPool,
		final RetentionPolicy retentionPolicy )
	{
		super( constantPool, name( retentionPolicy ) );
		
		this.numAnnotations = 0;
	}
	
	@Override
	final boolean isEmpty() {
		return ( this.numAnnotations == 0 );
	}
	
	@Override
	final int length() {
		return 0;
	}
	
	@Override
	final void writeBody( final JvmOutputStream out ) {
		// TODO Auto-generated method stubb
	}
	
	private static final String name( final RetentionPolicy retentionPolicy ) {
		switch ( retentionPolicy ) {
			case CLASS:
			return "RuntimeInvsibleAnnotations";
			
			case RUNTIME:
			return "RuntimeVisibleAnnotations";
			
			default:
			throw new IllegalStateException();
		}
	}
}
