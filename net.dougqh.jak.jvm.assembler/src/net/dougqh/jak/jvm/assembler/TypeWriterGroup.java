package net.dougqh.jak.jvm.assembler;

import java.util.HashMap;
import java.util.Iterator;

import net.dougqh.jak.TypeDescriptor;

final class TypeWriterGroup {
	private final HashMap< String, TypeWriter > writers =
		new HashMap< String, TypeWriter >( 4 );
	
	private final DynamicClassLoader classLoader;
	
	TypeWriterGroup() {
		this.classLoader = new DynamicClassLoader( TypeWriterGroup.class.getClassLoader() );
	}
	
	TypeWriterGroup( final ClassLoader classLoader ) {
		this.classLoader = new DynamicClassLoader( classLoader );
	}
	
	final Class< ? > load( final String name ) {
		try {
			try {
				return this.classLoader.loadClass( name );
			} finally {
				this.classLoader.loadRemainingClasses();
			}
		} catch ( ClassNotFoundException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	final JvmPackageWriter createPackageWriter( final String name ) {
		return new JvmPackageWriter( this, name );
	}
	
	final JvmClassWriter createClassWriter( final TypeDescriptor typeDescriptor ) {
		JvmClassWriter classWriter = new JvmClassWriter( this, typeDescriptor );
		this.register( classWriter.typeWriter() );
		return classWriter;
	}
	
	final JvmInterfaceWriter createInterfaceWriter( final TypeDescriptor typeDescriptor ) {
		JvmInterfaceWriter interfaceWriter = new JvmInterfaceWriter( this, typeDescriptor );
		this.register( interfaceWriter.typeWriter() );
		return interfaceWriter;
	}
	
	final JvmAnnotationWriter createAnnotationWriter( final TypeDescriptor typeDescriptor ) {
		JvmAnnotationWriter annotationWriter = new JvmAnnotationWriter( this, typeDescriptor );
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
