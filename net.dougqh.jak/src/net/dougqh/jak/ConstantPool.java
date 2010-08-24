package net.dougqh.jak;

import java.lang.reflect.Type;
import java.util.HashMap;

import net.dougqh.java.meta.types.JavaTypeVisitor;
import net.dougqh.java.meta.types.JavaTypes;


final class ConstantPool {
	private static final byte CLASS = 7;
	private static final byte FIELD_REF = 9;
	private static final byte METHOD_REF = 10;
	private static final byte INTERFACE_METHOD_REF = 11;
	private static final byte STRING = 8;
	private static final byte INTEGER = 3;
	private static final byte FLOAT = 4;
	private static final byte LONG = 5;
	private static final byte DOUBLE = 6;
	private static final byte NAME_AND_TYPE = 12;
	private static final byte UTF8 = 1;
	
	private int count;
	
	private final ByteStream out = new ByteStream( 2048 );
	
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
	
	final int addConstant(
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
	
	final int addBooleanConstant( final boolean value ) {
		return this.addIntegerConstant( value ? 1 : 0 );
	}
			
	final int addByteConstant( final Number value ) {
		return this.addIntegerConstant( value.byteValue() );
	}
	
	final int addCharConstant( final char value ) {
		return this.addIntegerConstant( (int)value );
	}
	
	final int addShortConstant( final Number value ) {
		return this.addIntegerConstant( value );
	}
	
	final int addIntegerConstant( final Number value ) {
		int intValue = value.intValue();
		
		Integer existingIndex = this.integers.get( intValue );
		if ( existingIndex != null ) {
			return existingIndex;
		} else {
			int newIndex = this.nextIndex();
			this.out.u1( INTEGER ).u4( intValue );
			this.integers.put( intValue, newIndex );
			return newIndex;
		}
	}
	
	final int addLongConstant( final Number value ) {
		//TODO: More efficient handling of the repeated value
		int newIndex = this.nextIndex2();
		this.out.u1( LONG ).u8( value.longValue() );
		return newIndex;
	}
	
	final int addFloatConstant( final Number value ) {
		//TODO: More efficient handling of the repeated value
		int newIndex = this.nextIndex();
		this.out.u1( FLOAT ).u4( value.floatValue() );
		return newIndex;
	}
	
	final int addDoubleConstant( final Number value ) {
		//TODO: More efficient handling of the repeated value
		int newIndex = this.nextIndex2();
		this.out.u1( DOUBLE ).u8( value.doubleValue() );
		return newIndex;
	}
	
	final int addStringConstant( final CharSequence value ) {
		String stringValue = value.toString();
		
		Integer existingIndex = this.strings.get( stringValue );
		if ( existingIndex != null ) {
			return existingIndex;
		} else {
			int utf8Index = this.addUtf8( stringValue );
			int newIndex = this.nextIndex();
			this.out.u1( STRING ).u2( utf8Index );
			this.strings.put( stringValue, newIndex );
			return newIndex;
		}
	}
	
	final int addClassInfo( final Type type ) {
		return this.addClassInfo( JavaTypes.getRawClassName( type ) );
	}
	
	final int addFieldDescriptor( final Type type ) {
		return this.addUtf8( getFieldSignature( type ) );
	}
	
	final Integer addGenericFieldDescriptor( final Type type ) {
		String genericSignature = getGenericFieldSignature( type );
		if ( genericSignature == null ) {
			return null;
		} else {
			return this.addUtf8( genericSignature );
		}
	}
	
	final int addMethodDescriptor( 
		final Type returnType,
		final FormalArguments arguments )
	{
		return this.addUtf8( getMethodSignature( returnType, arguments ) );
	}
	
	final Integer addGenericMethodDescriptor(
		final Type returnType,
		final FormalArguments arguments )
	{
		String genericSignature = getGenericMethodSignature( returnType, arguments );
		if ( genericSignature == null ) {
			return null;
		} else {
			return this.addUtf8( genericSignature );
		}
	}
	
	final Integer addGenericClassDescriptor(
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
	
	final int addFieldReference(
		final Type targetType,
		final Type fieldClass,
		final String fieldName )
	{
		int classIndex = this.addClassInfo( targetType );
		int nameAndTypeIndex = this.addNameAndType(
			fieldClass,
			fieldName );
		
		return this.addReference(
			FIELD_REF,
			classIndex,
			nameAndTypeIndex );
	}
	
	final int addMethodReference(
		final Type targetType,
		final Type returnType,
		final String methodName,
		final FormalArguments arguments )
	{
		int classIndex = this.addClassInfo( targetType );
		int nameAndTypeIndex = this.addNameAndType(
			returnType,
			methodName,
			arguments );
		
		return this.addReference(
			METHOD_REF,
			classIndex,
			nameAndTypeIndex );
	}
	
	final int addInterfaceMethodReference(
		final Type targetType,
		final Type returnType,
		final String methodName,
		final FormalArguments arguments )
	{
		int classIndex = this.addClassInfo( targetType );
		int nameAndTypeIndex = this.addNameAndType(
			returnType,
			methodName,
			arguments );
		
		return this.addReference(
			INTERFACE_METHOD_REF,
			classIndex,
			nameAndTypeIndex );
	}
	
	private final int addReference(
		final byte refType,
		final int classIndex,
		final int nameAndTypeIndex )
	{
		int key = key( classIndex, nameAndTypeIndex );
		Integer existingIndex = this.references.get( key );
		if ( existingIndex != null ) {
			return existingIndex;
		} else {
			int newIndex = this.nextIndex();
			this.out.u1( refType ).u2( classIndex ).u2( nameAndTypeIndex );
			this.references.put( key, newIndex );
			return newIndex;
		}		
	}
	
	final int addNameAndType(
		final Type fieldClass,
		final String fieldName )
	{
		int nameIndex = this.addUtf8( fieldName );
		int typeIndex = this.addUtf8( getFieldSignature( fieldClass ) );
		
		return this.addNameAndType( nameIndex, typeIndex );
	}
	
	final int addNameAndType(
		final Type returnType,
		final String methodName,
		final FormalArguments arguments )
	{
		int nameIndex = this.addUtf8( methodName );
		int typeIndex = this.addUtf8( getMethodSignature( returnType, arguments ) );
		
		return this.addNameAndType( nameIndex, typeIndex );
	}
	
	final int addNameAndType(
		final int nameIndex,
		final int typeIndex )
	{
		int key = key( nameIndex, typeIndex );
		
		Integer existingIndex = this.nameAndTypes.get( key );
		if ( existingIndex != null ) {
			return existingIndex;
		} else {
			int newIndex = this.nextIndex();
			this.out.u1( NAME_AND_TYPE ).u2( nameIndex ).u2( typeIndex );
			this.nameAndTypes.put( key, newIndex );
			return newIndex;
		}
	}
		
	final int addClassInfo( final String value ) {
		Integer existingIndex = this.classes.get( value );
		if ( existingIndex != null ) {
			return existingIndex;
		} else {
			int nameIndex = this.addUtf8( value.replace( '.', '/' ) );
			int newIndex = this.nextIndex();
			this.out.u1( CLASS ).u2( nameIndex );
			this.classes.put( value, newIndex );
			return newIndex;
		}
	}
	
	final int addUtf8( final String value ) {
		Integer existingIndex = this.utf8s.get( value );
		if ( existingIndex != null ) {
			return existingIndex;
		} else {
			int newIndex = this.nextIndex();
			this.out.u1( UTF8 ).utf8( value );			
			this.utf8s.put( value, newIndex );
			return newIndex;
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
			visitor.visit( JavaTypes.getRawType( variable.type() ) );
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
		final Type returnType,
		final FormalArguments arguments )
	{
		if ( isGenericMethodSignature( returnType, arguments ) ) {
			SignatureTypeVistor genericSigBuilder = 
				new SignatureTypeVistor();
			genericSigBuilder.startArguments();
			for ( JavaVariable variable : arguments ) {
				Type type = variable.type();
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
		final Type returnType,
		final FormalArguments arguments )
	{
		Type resolvedReturnedType = JavaTypes.resolve( returnType );
		
		if ( ! ( resolvedReturnedType instanceof Class ) ) {
			return true;
		} else {
			for ( JavaVariable variable : arguments ) {
				Type type = variable.type();
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
		final int byte1,
		final int byte0 )
	{
		return ( byte1 << 8 ) | byte0;
	}
	
	final void write( final ByteStream out ) {
		//Specification requires + 1, since counting starts at 1
		out.u2( this.count + 1 );
		this.out.writeTo( out );
	}
	
	static final class SignatureTypeVistor extends JavaTypeVisitor {
		private final StringBuilder builder = new StringBuilder();
		
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
		protected final void startTypeVariable( final String variableName ) {
			this.builder.append( variableName ).append( ':' );
		}
		
		@Override
		protected final void endTypeVariable( final String variableName ) {
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
	}
}
