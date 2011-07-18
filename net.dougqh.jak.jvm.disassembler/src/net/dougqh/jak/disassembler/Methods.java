package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class Methods {
	private final JvmMethod clinit;
	private final List< JvmMethod > constructors;
	private final List< JvmMethod > methods;
	private final List< JvmMethod > allMethods;
	
	Methods( final ConstantPool constantPool, final ByteInputStream in ) 
		throws IOException
	{		
		int count = in.u2();

		JvmMethod clinit = null;
		ArrayList< JvmMethod > constructors = new ArrayList< JvmMethod >( 4 );
		ArrayList< JvmMethod > methods = new ArrayList< JvmMethod >( count );
		ArrayList< JvmMethod > allMethods = new ArrayList< JvmMethod >( count );

		for ( int i = 0; i < count; ++i ) {
			int flags = in.u2();
			int nameIndex = in.u2();
			int descriptorIndex = in.u2();
			Attributes attributes = new Attributes( constantPool, in );
			
			JvmMethod method = new JvmMethod(
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
	
	final JvmMethod getClassInitializer() {
		return this.clinit;
	}
	
	final List< JvmMethod > getConstructors() {
		return this.constructors;
	}
	
	final List< JvmMethod > getMethods() {
		return this.methods;
	}
	
	final List< JvmMethod > getAllMethods() {
		return this.allMethods;
	}
	
	static final List< JvmMethod > unmodifiable( final ArrayList< JvmMethod > methods ) {
		if ( methods.isEmpty() ) {
			return Collections.< JvmMethod >emptyList();
		} else {
			methods.trimToSize();
			return Collections.unmodifiableList( methods );
		}
	}
}
