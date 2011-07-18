package net.dougqh.jak.disassembler.visitor;

import net.dougqh.jak.disassembler.JavaAnnotation;
import net.dougqh.jak.disassembler.JavaClass;
import net.dougqh.jak.disassembler.JavaEnum;
import net.dougqh.jak.disassembler.JavaInterface;
import net.dougqh.jak.disassembler.JavaReader;
import net.dougqh.jak.disassembler.JavaType;

public abstract class JavaVisitor< R > {
	public final R process( final JavaReader reader ) throws Exception {
		for ( JavaType type : reader.iterate() ) {
			this.visitType( type );
		}
	}
	
	protected final void visitType( final JavaType javaType ) {
		if ( javaType instanceof JavaClass ) {
			this.visitClass( (JavaClass)javaType );
		} else if ( javaType instanceof JavaInterface ) {
			this.visitInterface( (JavaInterface)javaType );
		} else if ( javaType instanceof JavaAnnotation ) {
			this.visitAnnotation( (JavaAnnotation)javaType );
		} else if ( javaType instanceof JavaEnum ) {
			this.visitType( (JavaEnum)javaType );
		} else {
			throw new IllegalStateException();
		}
	}
	
	protected void visitClass( final JavaClass javaClass ) {
		this.visitOtherType( javaClass );
	}
	
	protected void visitInterface( final JavaInterface javaInterface ) {
		this.visitOtherType( javaInterface );
	}
	
	protected void visitAnnotation( final JavaAnnotation javaAnnotation ) {
		this.visitOtherType( javaAnnotation );
	}
	
	protected void visitEnum( final JavaEnum javaEnum ) {
		this.visitOtherType( javaEnum );
	}
	
	protected void visitOtherType( final JavaType javaType ) {
	}
}
