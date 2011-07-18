package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class Methods {
	private final JavaMethod clinit;
	private final List< JavaMethod > constructors;
	private final List< JavaMethod > methods;
	private final List< JavaMethod > allMethods;
	
	Methods( final ConstantPool constantPool, final ByteInputStream in ) 
		throws IOException
	{		
		int count = in.u2();

		JavaMethod clinit = null;
		ArrayList< JavaMethod > constructors = new ArrayList< JavaMethod >( 4 );
		ArrayList< JavaMethod > methods = new ArrayList< JavaMethod >( count );
		ArrayList< JavaMethod > allMethods = new ArrayList< JavaMethod >( count );

		for ( int i = 0; i < count; ++i ) {
			int flags = in.u2();
			int nameIndex = in.u2();
			int descriptorIndex = in.u2();
			Attributes attributes = new Attributes( constantPool, in );
			
			JavaMethod method = new JavaMethod(
				constantPool,
				flags,
				nameIndex,
				descriptorIndex,
				attributes );
			
			allMethods.add( method );
			
			if ( method.isClassInitializer() ) {
				clinit = method;
			} else if ( method.isConstructor() ) {
				constructors.add( method );
			} else {
				methods.add( method );
			}
		}
		
		this.clinit = clinit;
		this.constructors = unmodifiable( constructors );
		this.methods = unmodifiable( methods );
		
		this.allMethods = unmodifiable( allMethods );
	}
	
	final JavaMethod getClassInitializer() {
		return this.clinit;
	}
	
	final List< JavaMethod > getConstructors() {
		return this.constructors;
	}
	
	final List< JavaMethod > getMethods() {
		return this.methods;
	}
	
	final List< JavaMethod > getAllMethods() {
		return this.allMethods;
	}
	
	static final List< JavaMethod > unmodifiable( final ArrayList< JavaMethod > methods ) {
		if ( methods.isEmpty() ) {
			return Collections.< JavaMethod >emptyList();
		} else {
			methods.trimToSize();
			return Collections.unmodifiableList( methods );
		}
	}
}
