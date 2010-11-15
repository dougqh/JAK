package net.dougqh.jak.assembler;

import java.util.HashMap;
import java.util.Iterator;

final class TypeWriterGroup {
	private final HashMap< String, TypeWriter > writers =
		new HashMap< String, TypeWriter >( 4 );
	
	private DynamicClassLoader classLoader = null;
	
	TypeWriterGroup() {}
	
	final Class< ? > load(
		final ClassLoader parentLoader,
		final String name )
	{
		if ( this.classLoader != null ) {
			this.classLoader.check( parentLoader );
			try {
				return this.classLoader.loadClass( name );
			} catch ( ClassNotFoundException e ) {
				throw new IllegalStateException( e );
			}
		}
		
		try {
			this.classLoader = new DynamicClassLoader( parentLoader );
			try {
				return this.classLoader.loadClass( name );
			} finally {
				this.classLoader.loadRemainingClasses();
			}
		} catch ( ClassNotFoundException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	final JavaClassWriter createClassWriter( final TypeDescriptor typeDescriptor ) {
		JavaClassWriter classWriter = new JavaClassWriter( this, typeDescriptor );
		this.register( classWriter.typeWriter() );
		return classWriter;
	}
	
	final JavaInterfaceWriter createInterfaceWriter( final TypeDescriptor typeDescriptor ) {
		JavaInterfaceWriter interfaceWriter = new JavaInterfaceWriter( this, typeDescriptor );
		this.register( interfaceWriter.typeWriter() );
		return interfaceWriter;
	}
	
	final JavaAnnotationWriter createAnnotationWriter( final TypeDescriptor typeDescriptor ) {
		JavaAnnotationWriter annotationWriter = new JavaAnnotationWriter( this, typeDescriptor );
		this.register( annotationWriter.typeWriter() );
		return annotationWriter;
	}
	
	final void register( final TypeWriter writer ) {
		this.writers.put( writer.name(), writer );
	}
	
	final class DynamicClassLoader extends ClassLoader {
		private final ClassLoader parentLoader;
		
		DynamicClassLoader( final ClassLoader parentLoader ) {
			super( parentLoader );
			this.parentLoader = parentLoader;
		}
		
		protected final void check( final ClassLoader parentLoader ) {
			if ( parentLoader != this.parentLoader ) {
				throw new IllegalStateException( "Different ClassLoader" );
			}
		}
		
		@Override
		public final Class< ? > loadClass( final String name )
			throws ClassNotFoundException
		{
			TypeWriter typeWriter = TypeWriterGroup.this.writers.remove( name );
			if ( typeWriter == null ) {
				return super.loadClass( name );
			} else {
				String packageName = typeWriter.packageName();
				if ( packageName != null ) {
					try {
						Package aPackage = this.getPackage( name );
						if ( aPackage == null ) {
							typeWriter.definePackage( this );
						}
					} catch ( IllegalArgumentException e ) {
						//DQH - Weird case - IllegalArgumentException is raised 
						//when the package already exists in the parentLoader?
					}
				}	
				return typeWriter.defineType( this );
			}
		}
		
		protected final Package definePackage( final String name ) {
			return this.definePackage(
				name,
				null,
				null,
				null,
				null, 
				null, 
				null,
				null );
		}
		
		protected final Class< ? > defineType(
			final String name,
			final byte[] bytes,
			final int start,
			final int length )
		{
			return this.defineClass( name, bytes, start, length );
		}
		
		protected final void loadRemainingClasses() {
			for ( Iterator< TypeWriter > iter = TypeWriterGroup.this.writers.values().iterator();
				iter.hasNext(); )
			{
				TypeWriter typeWriter = iter.next();
				iter.remove();
				
				typeWriter.defineType( this );
			}
		}
	}
}
