package net.dougqh.jak.jvm.assembler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.dougqh.jak.Flags;
import net.dougqh.jak.Jak;
import net.dougqh.jak.JavaAnnotationDescriptor;
import net.dougqh.jak.JavaClassDescriptor;
import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaInterfaceDescriptor;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.TypeDescriptor;
import net.dougqh.jak.assembler.JakEnumWriter;
import static net.dougqh.jak.Jak.*;

public final class JvmEnumWriter implements JakEnumWriter, JvmExtendedTypeWriter {
	private static final int ADDITIONAL_TYPE_FLAGS = Flags.NO_FLAGS;
	private static final int ADDITIONAL_METHOD_FLAGS = Flags.NO_FLAGS;
	private static final int ADDITIONAL_INNER_TYPE_FLAGS = Flags.NO_FLAGS;
	
	private final TypeWriter typeWriter;
	
	private final List< Element > elements = new ArrayList< Element >( 8 );
	
	private boolean initDefined = false;
	private boolean clinitDefined = false;
	
	JvmEnumWriter(
		final TypeWriterGroup writerGroup,
		final TypeDescriptor typeDescriptor )
	{
		this.typeWriter = new TypeWriter(
			writerGroup,
			typeDescriptor,
			ADDITIONAL_TYPE_FLAGS );
	}
	
	@Override
	public final Type thisType() {
		return this.typeWriter.context().thisType;
	}
	
	@Override
	public final Type superType() {
		return this.typeWriter.context().superType;
	}
	
	public final void defineDefaultConstructor() {
		this.define( protected_().init() ).
			this_().
			super_invokespecial( init() ).
			return_();
	}
	
	public final void define( final String element ) {
		if ( this.initDefined || this.clinitDefined ) {
			throw new IllegalStateException( "Must define all elements before <init> or <clinit>" );
		}
		
		JavaField field = public_().static_().final_().field( this.thisType(), element );
		this.elements.add( new Element( element, field ) );
		
		this.define( field );
	}
	
	@Override
	public final void define( final JavaField field ) {
		this.typeWriter.define( field );
	}
	
	@Override
	public final void define( final JavaField field, final boolean value ) {
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define( final JavaField field, final byte value ) {
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define( final JavaField field, final char value ) {
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define( final JavaField field, final short value ) {
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define( final JavaField field, final int value ) {
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define( final JavaField field, final long value ) {
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define( final JavaField field, final float value ) {
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define( final JavaField field, final double value ) {
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define( final JavaField field, final CharSequence value ) {
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define( final JavaField field, final Class< ? > aClass ) {
		this.typeWriter.define( field, aClass );
	}
	
	public final JvmCodeWriter define( final JavaMethodDescriptor method ) {
		//coreWriter will be null when the method is abstract or native pass it on
		JvmCoreCodeWriter coreWriter = this.typeWriter.define(
			method,
			ADDITIONAL_METHOD_FLAGS,
			null );
		
		if ( coreWriter == null ) {
			return null;
		} else {
			return new JvmCodeWriterImpl( method.isStatic(), method.arguments(), coreWriter );
		}
	}
	
	public final JvmCodeWriter define( final Method method ) {
		return this.define( Jak.method( method ) );
	}
	
	@Override
	public final JvmClassWriter define( final JavaClassDescriptor classDescriptor ) {
		return this.typeWriter.defineClass(
			classDescriptor.typeDescriptor(),
			ADDITIONAL_INNER_TYPE_FLAGS );
	}
	
	@Override
	public final JvmInterfaceWriter define( final JavaInterfaceDescriptor interfaceDescriptor ) {
		return this.typeWriter.defineInterface(
			interfaceDescriptor.typeDescriptor(),
			ADDITIONAL_INNER_TYPE_FLAGS );
	}
	
	@Override
	public final JvmAnnotationWriter define( final JavaAnnotationDescriptor annotationDescriptor ) {
		return this.typeWriter.defineAnnotation(
			annotationDescriptor.typeDescriptor(),
			ADDITIONAL_INNER_TYPE_FLAGS );
	}
	
	@Override
	public final <T> Class<T> load() {
		this.finish();
		
		return this.typeWriter.<T>load();
	}

	@SuppressWarnings( "unchecked" )
	public final < T > T newInstance() {
		try {
			return (T)this.load().newInstance();
		} catch ( InstantiationException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	@Override
	public final void writeTo( final File classDir ) throws IOException {
		this.finish();
		
		this.typeWriter.writeTo( classDir );
	}
	
	@Override
	public final void writeTo( final OutputStream out ) throws IOException {
		this.finish();
		
		this.typeWriter.writeTo( out );
	}
	
	@Override
	public final byte[] getBytes() {
		this.finish();
		
		return this.typeWriter.getBytes();
	}
	
	public final void initConfig( final JakConfiguration config ) {
		this.typeWriter.initConfig( config );
	}
	
	final TypeWriter typeWriter() {
		return this.typeWriter;
	}
	
	final void finish() {
		if ( ! this.initDefined ) {
			this.defineDefaultConstructor();
		}
		
		if ( ! this.clinitDefined ) {
			JvmCodeWriter clinitWriter = this.define( clinit() );
			
			for ( ListIterator< Element > iter = this.elements.listIterator(); iter.hasNext(); ) {
				int ordinal = iter.nextIndex();
				Element element = iter.next();
				
				clinitWriter.
					new_( thisType() ).
					dup().
					ldc( element.name ).
					iload( ordinal ).
					invokespecial( thisType(), init( String.class, int.class ) ).
					putstatic( thisType(), element.field );
			}
		}
	}
	
	private static final class Element {
		final String name;
		final JavaField field;
		
		Element( final String name, final JavaField field ) {
			this.name  = name;
			this.field = field;
		}
	}
}
