package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Type;
import java.util.HashMap;

import net.dougqh.jak.FormalArguments;
import net.dougqh.jak.JavaVariable;
import net.dougqh.jak.types.Utf8;
import net.dougqh.java.meta.types.JavaTypeVisitor;
import net.dougqh.java.meta.types.JavaTypes;
import static net.dougqh.jak.core.ConstantPoolConstants.*;


final class ConstantPool {	
	private int count;
	
	private final JvmOutputStream out = new JvmOutputStream( 2048 );
	
	private final HashMap< Integer, Integer > integers = 
		new HashMap< Integer, Integer >();
	
	private final HashMap< String, Integer > classes = 
		new HashMap< String, Integer >();
	
	private final HashMap< String, Integer > strings = 
		new HashMap< String, Integer >();
	
	private final HashMap< Integer, Integer > references = 
		new HashMap< Integer, Integer >();
	
	private final HashMap< Integer, Integer > nameAndTypes = 
		new HashMap< Integer, Integer >();
	
	private final HashMap< String, Integer > utf8s = 
		new HashMap< String, Integer >();
		
	ConstantPool() {}
	
	final ConstantEntry addConstant(
		final Type targetType,
		final Object value )
	{
		if ( targetType.equals( boolean.class ) ) {
			return this.addBooleanConstant( (Boolean)value );
		} else 	if ( targetType.equals( byte.class ) ) {
			return this.addByteConstant( (Number)value );
		} else if ( targetType.equals( char.class ) ) {
			return this.addCharConstant( (Character)value );
		} else if ( targetType.equals( short.class ) ) {
			return this.addShortConstant( (Number)value );			
		} else if ( targetType.equals( int.class ) ) {
			return this.addIntegerConstant( (Number)value );
		} else if ( targetType.equals( long.class ) ) {
			return this.addLongConstant( (Number)value );
		} else if ( targetType.equals( float.class ) ) {
			return this.addFloatConstant( (Number)value );
		} else if ( targetType.equals( double.class ) ) {
			return this.addDoubleConstant( (Number)value );			
		} else if ( targetType.equals( String.class ) ) {
			return this.addStringConstant( (CharSequence)value );
		} else {
			throw new IllegalArgumentException( "invalid type " + targetType.toString() );
		}
	}
	
	final ConstantEntry addBooleanConstant( final boolean value ) {
		return this.addIntegerConstant( value ? 1 : 0 );
	}
			
	final ConstantEntry addByteConstant( final Number value ) {
		return this.addIntegerConstant( value.byteValue() );
	}
	
	final ConstantEntry addCharConstant( final char value ) {
		return this.addIntegerConstant( (int)value );
	}
	
	final ConstantEntry addShortConstant( final Number value ) {
		return this.addIntegerConstant( value );
	}
	
	final ConstantEntry addIntegerConstant( final Number value ) {
		int intValue = value.intValue();
		
		Integer existingIndex = this.integers.get( intValue );
		if ( existingIndex != null ) {
			return new ConstantEntry( existingIndex, int.class, intValue );
		} else {
			int newIndex = this.nextIndex();
			this.out.u1( INTEGER ).u4( intValue );
			this.integers.put( intValue, newIndex );
			return new ConstantEntry( newIndex, int.class, intValue );
		}
	}
	
	final ConstantEntry addLongConstant( final Number value ) {
		//TODO: More efficient handling of the repeated value
		long longValue = value.longValue();
		
		int newIndex = this.nextIndex2();
		this.out.u1( LONG ).u8( longValue );
		return new ConstantEntry( newIndex, long.class, longValue );
	}
	
	final ConstantEntry addFloatConstant( final Number value ) {
		//TODO: More efficient handling of the repeated value
		float floatValue = value.floatValue();
		
		int newIndex = this.nextIndex();
		this.out.u1( FLOAT ).u4( floatValue );
		return new ConstantEntry( newIndex, float.class, floatValue );
	}
	
	final ConstantEntry addDoubleConstant( final Number value ) {
		//TODO: More efficient handling of the repeated value
		double doubleValue = value.doubleValue();
		
		int newIndex = this.nextIndex2();
		this.out.u1( DOUBLE ).u8( doubleValue );
		return new ConstantEntry( newIndex, double.class, doubleValue );
	}
	
	final ConstantEntry addStringConstant( final CharSequence value ) {
		String stringValue = value.toString();
		
		Integer existingIndex = this.strings.get( stringValue );
		if ( existingIndex != null ) {
			return new ConstantEntry( existingIndex, String.class, stringValue );
		} else {
			ConstantEntry utf8Entry = this.addUtf8( stringValue );
			int newIndex = this.nextIndex();
			this.out.u1( STRING ).u2( utf8Entry );
			this.strings.put( stringValue, newIndex );
			return new ConstantEntry( newIndex, String.class, stringValue );
		}
	}
	
	final ConstantEntry addClassInfo( final Type type ) {
		return this.addClassInfo( JavaTypes.getRawClassName( type ) );
	}
	
	final ConstantEntry addFieldDescriptor( final Type type ) {
		return this.addUtf8( getFieldSignature( type ) );
	}
	
	final ConstantEntry addGenericFieldDescriptor( final Type type ) {
		String genericSignature = getGenericFieldSignature( type );
		if ( genericSignature == null ) {
			return null;
		} else {
			return this.addUtf8( genericSignature );
		}
	}
	
	final ConstantEntry addMethodDescriptor( 
		final Type returnType,
		final FormalArguments arguments )
	{
		return this.addUtf8( getMethodSignature( returnType, arguments ) );
	}
	
	final ConstantEntry addGenericMethodDescriptor(
		final Type[] genericTypes,
		final Type returnType,
		final FormalArguments arguments )
	{
		String genericSignature = getGenericMethodSignature( genericTypes, returnType, arguments );
		if ( genericSignature == null ) {
			return null;
		} else {
			return this.addUtf8( genericSignature );
		}
	}
	
	final ConstantEntry addGenericClassDescriptor(
		final Type parentType,
		final Type[] interfaceTypes )
	{
		String genericSignature = getGenericClassSignature( parentType, interfaceTypes );
		if ( genericSignature == null ) {
			return null;
		} else {
			return this.addUtf8( genericSignature );
		}
	}
	
	final ConstantEntry addFieldReference(
		final Type targetType,
		final Type fieldClass,
		final String fieldName )
	{
		ConstantEntry classEntry = this.addClassInfo( targetType );
		ConstantEntry nameAndTypeEntry = this.addNameAndType(
			fieldClass,
			fieldName );
		
		return this.addReference(
			FIELD_REF,
			classEntry,
			nameAndTypeEntry );
	}
	
	final ConstantEntry addMethodReference(
		final Type targetType,
		final Type returnType,
		final String methodName,
		final FormalArguments arguments )
	{
		ConstantEntry classEntry = this.addClassInfo( targetType );
		ConstantEntry nameAndTypeEntry = this.addNameAndType(
			returnType,
			methodName,
			arguments );
		
		return this.addReference(
			METHOD_REF,
			classEntry,
			nameAndTypeEntry );
	}
	
	final ConstantEntry addInterfaceMethodReference(
		final Type targetType,
		final Type returnType,
		final String methodName,
		final FormalArguments arguments )
	{
		ConstantEntry classEntry = this.addClassInfo( targetType );
		ConstantEntry nameAndTypeEntry = this.addNameAndType(
			returnType,
			methodName,
			arguments );
		
		return this.addReference(
			INTERFACE_METHOD_REF,
			classEntry,
			nameAndTypeEntry );
	}
	
	private final ConstantEntry addReference(
		final byte refType,
		final ConstantEntry classEntry,
		final ConstantEntry nameAndTypeEntry )
	{
		//TODO: Fill-in type information and value in ConstantEntry in a meaningful fashion
		int key = key( classEntry, nameAndTypeEntry );
		Integer existingIndex = this.references.get( key );
		if ( existingIndex != null ) {
			return new ConstantEntry( existingIndex, Void.class, null );
		} else {
			int newIndex = this.nextIndex();
			this.out.u1( refType ).u2( classEntry ).u2( nameAndTypeEntry );
			this.references.put( key, newIndex );
			return new ConstantEntry( newIndex, Void.class, null );
		}		
	}
	
	final ConstantEntry addNameAndType(
		final Type fieldClass,
		final String fieldName )
	{
		ConstantEntry nameEntry = this.addUtf8( fieldName );
		ConstantEntry typeEntry = this.addUtf8( getFieldSignature( fieldClass ) );
		
		return this.addNameAndType( nameEntry, typeEntry );
	}
	
	final ConstantEntry addNameAndType(
		final Type returnType,
		final String methodName,
		final FormalArguments arguments )
	{
		ConstantEntry nameEntry = this.addUtf8( methodName );
		ConstantEntry typeEntry = this.addUtf8( getMethodSignature( returnType, arguments ) );
		
		return this.addNameAndType( nameEntry, typeEntry );
	}
	
	final ConstantEntry addNameAndType(
		final ConstantEntry nameEntry,
		final ConstantEntry typeEntry )
	{
		//TODO: Fill-in type information and value in ConstantEntry in a meaningful fashion
		int key = key( nameEntry, typeEntry );
		Integer existingIndex = this.nameAndTypes.get( key );
		if ( existingIndex != null ) {
			return new ConstantEntry( existingIndex, Void.class, null );
		} else {
			int newIndex = this.nextIndex();
			this.out.u1( NAME_AND_TYPE ).u2( nameEntry ).u2( typeEntry );
			this.nameAndTypes.put( key, newIndex );
			return new ConstantEntry( newIndex, Void.class, null );
		}
	}
		
	final ConstantEntry addClassInfo( final String value ) {
		Integer existingIndex = this.classes.get( value );
		if ( existingIndex != null ) {
			return new ConstantEntry( existingIndex, Class.class, value );
		} else {
			ConstantEntry nameEntry = this.addUtf8( value.replace( '.', '/' ) );
			int newIndex = this.nextIndex();
			this.out.u1( CLASS ).u2( nameEntry );
			this.classes.put( value, newIndex );
			return new ConstantEntry( newIndex, Class.class, value );
		}
	}
	
	final ConstantEntry addUtf8( final String value ) {
		Integer existingIndex = this.utf8s.get( value );
		if ( existingIndex != null ) {
			return new ConstantEntry( existingIndex, Utf8.class, value );
		} else {
			int newIndex = this.nextIndex();
			this.out.u1( UTF8 ).utf8( value );			
			this.utf8s.put( value, newIndex );
			return new ConstantEntry( newIndex, Utf8.class, value );
		}
	}
	
	private final int nextIndex() {
		return ++this.count;
	}
	
	private final int nextIndex2() {
		int index = this.nextIndex();
		this.nextIndex();
		return index;
	}
	
	private static final String getFieldSignature( final Type type ) {
		SignatureTypeVistor visitor = new SignatureTypeVistor();
		visitor.visit( JavaTypes.getRawType( type ) );
		return visitor.getSignature();
	}

	static final String getMethodSignature(
		final Type returnType,
		final FormalArguments arguments )
	{
		SignatureTypeVistor visitor = new SignatureTypeVistor();
		visitor.startArguments();
		for ( JavaVariable variable : arguments ) {
			visitor.visit( JavaTypes.getRawType( variable.getType() ) );
		}
		visitor.endArguments();
		visitor.visit( JavaTypes.getRawType( returnType ) );
		
		return visitor.getSignature();
	}
	
	static final String getGenericFieldSignature(
		final Type type )
	{
		if ( ! isGenericFieldSignature( type ) ) {
			return null;
		} else {
			SignatureTypeVistor genericSigBuilder = 
				new SignatureTypeVistor();
			genericSigBuilder.visit( type );
			return genericSigBuilder.getSignature();
		}
	}
	
	static final String getGenericMethodSignature(
		final Type[] genericTypes,
		final Type returnType,
		final FormalArguments arguments )
	{
		if ( isGenericMethodSignature( genericTypes, returnType, arguments ) ) {
			SignatureTypeVistor genericSigBuilder = new SignatureTypeVistor();
			
			if ( genericTypes.length != 0 ) {
				genericSigBuilder.startParameterization();
				for ( Type genericType: genericTypes ) {
					genericSigBuilder.visit( genericType );
				}
				genericSigBuilder.endParameterization();
			}
			
			genericSigBuilder.startArguments();
			for ( JavaVariable variable : arguments ) {
				Type type = variable.getType();
				genericSigBuilder.visit( type );
			}
			genericSigBuilder.endArguments();
			
			genericSigBuilder.visit( returnType );

			return genericSigBuilder.getSignature();
		} else {
			return null;
		}
	}
	
	static final boolean isGenericMethodSignature(
		final Type[] genericTypes,
		final Type returnType,
		final FormalArguments arguments )
	{
		Type resolvedReturnedType = JavaTypes.resolve( returnType );
		
		if ( genericTypes.length != 0 ) {
			return true;
		} else if ( ! ( resolvedReturnedType instanceof Class ) ) {
			return true;
		} else {
			for ( JavaVariable variable : arguments ) {
				Type type = variable.getType();
				if ( ! ( type instanceof Class ) ) {
					return true;
				}
			}
			return false;
		}
	}
	
	static final String getGenericClassSignature(
		final Type parentType,
		final Type[] interfaceTypes )
	{
		if ( ! isGenericClassSignature( parentType, interfaceTypes ) ) {
			return null;
		} else {
			SignatureTypeVistor genericSigBuilder = 
				new SignatureTypeVistor();
			genericSigBuilder.visit( parentType );
			for ( Type interfaceType : interfaceTypes ) {
				genericSigBuilder.visit( interfaceType );
			}
			return genericSigBuilder.getSignature();
		}
	}

	static final boolean isGenericClassSignature(
		final Type parentType,
		final Type[] interfaceTypes )
	{
		Type resolvedParentType = JavaTypes.resolve( parentType );
		
		if ( ! ( resolvedParentType instanceof Class ) ) {
			return true;
		} else {
			for ( Type interfaceType : interfaceTypes ) {
				Type resolvedInterfaceType = JavaTypes.resolve( interfaceType );
				if ( ! ( resolvedInterfaceType instanceof Class ) ) {
					return true;
				}
			}
			return false;
		}
	}
	
	static final boolean isGenericFieldSignature( final Type type ) {
		Type resolvedType = JavaTypes.resolve( type );
		return ! ( resolvedType instanceof Class );
	}
	
	private static final int key(
		final ConstantEntry entry1,
		final ConstantEntry entry0 )
	{
		return key( entry1.index(), entry0.index() );
	}

	private static final int key(
		final int byte1,
		final int byte0 )
	{
		return ( byte1 << 8 ) | byte0;
	}
	
	final void write( final JvmOutputStream out ) {
		//Specification requires + 1, since counting starts at 1
		out.u2( this.count + 1 );
		this.out.writeTo( out );
	}
	
	static final class SignatureTypeVistor extends JavaTypeVisitor {
		private final StringBuilder builder = new StringBuilder();
		private boolean parameterizing = false;
		
		protected final String getSignature() {
			return this.builder.toString();
		}
		
		@Override
		protected final String transformName( final String className ) {
			return "L" + className.replace( '.', '/' );
		}
		
		@Override
		protected final String getName( final Class< ? > aClass ) {
			if ( aClass.isArray() ) {
				return aClass.getName();
			} else if ( Object.class.isAssignableFrom( aClass ) ) {
				return "L" + aClass.getName().replace( '.', '/' );
			} else if ( aClass.equals( boolean.class ) ) {
				return "Z";
			} else if ( aClass.equals( byte.class ) ) {
				return "B";
			} else if ( aClass.equals( char.class ) ) {
				return "C";
			} else if ( aClass.equals( short.class ) ) {
				return "S";
			} else if ( aClass.equals( int.class ) ) {
				return "I";
			} else if ( aClass.equals( long.class ) ) {
				return "J";
			} else if ( aClass.equals( float.class ) ) {
				return "F";
			} else if ( aClass.equals( double.class ) ) {
				return "D";
			} else if ( aClass.equals( void.class ) ) {
				return "V";
			} else {
				throw new IllegalArgumentException( "Unexpected void.class" );
			}
		}
		
		final void startParameterization() {
			this.parameterizing = true;
			
			this.builder.append( '<' );
		}
		
		final void endParameterization() {
			this.builder.append( '>' );
			
			this.parameterizing = false;
		}
		
		@Override
		protected void visitTypeVariable(
			final String variableName,
			final Type[] bounds )
		{
			if ( this.parameterizing ) {
				super.visitTypeVariable( variableName, bounds );
			} else {
				this.builder.append( 'T' ).append( variableName ).append( ';' );
			}
		}
		
		@Override
		protected final void startTypeVariable( final String variableName ) {
			this.builder.append( variableName ).append( ':' );
		}
		
		@Override
		protected final void endTypeVariable( final String variableName ) {
		}
		
		final void startArguments() {
			this.builder.append( '(' );
		}
		
		final void endArguments() {
			this.builder.append( ')' );
		}
		
		@Override
		protected final void startArray() {
			this.builder.append( '[' );
		}
		
		@Override
		protected final void endArray() {
		}
		
		@Override
		protected final void startGenericArray() {
			this.builder.append( '[' );
		}
		
		@Override
		protected final void endGenericArray() {
		}
		
		@Override
		protected final void startParameterizedType( final String rawClassName ) {
			this.builder.append( rawClassName ).append( '<' );
		}
		
		@Override
		protected final void endParameterizedType( final String rawClassName ) {
			this.builder.append( ">;" );
		}
		
		@Override
		protected final void startWildcardExtends() {
			this.builder.append( '+' );
		}
		
		@Override
		protected final void endWildcardExtends() {
		}
		
		@Override
		protected final void startWildcardSuper() {
			this.builder.append( '-' );
		}
		
		@Override
		protected void endWildcardSuper() {
		}
		
		@Override
		protected final void visitObjectClass( final String name ) {
			this.builder.append( name ).append( ';' );
		}
		
		@Override
		protected final void visitPrimitiveClass( final String name ) {
			this.builder.append( name );
		}
		
		@Override
		public final String toString() {
			return this.builder.toString();
		}
	}
}
