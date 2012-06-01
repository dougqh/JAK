package net.dougqh.jak.jvm.assembler.api;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.assembler.JvmEnumWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Ignore;
import org.junit.Test;

import static net.dougqh.jak.Jak.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public final class EnumTest {
	@Test @Ignore
	public final void trivialEnum() throws NoSuchFieldException {
		JvmEnumWriter enumWriter = new JvmWriter().define( public_().final_().enum_( "MyEnum" ) );
		
		enumWriter.define( "FOO" );
		enumWriter.define( "BAR" );
		enumWriter.define( "BAZ" );
		
		Class< Enum<?> > enumClass = enumWriter.< Enum<?> >load();
		
		assertThat( enumClass.getField( "FOO" ).getType(), isType( "MyEnum" ) );
		assertThat( enumClass.getField( "BAR" ).getType(), isType( "MyEnum" ) );
		assertThat( enumClass.getField( "BAZ" ).getType(), isType( "MyEnum" ) );
		
		Enum<?>[] constants = enumClass.getEnumConstants();
		assertThat( constants.length, is( 3 ) );
	}
	
	private static final Matcher<Type> isType( final String typeName ) {
		return new BaseMatcher<Type>() {
			@Override
			public final void describeTo( final Description description ) {
				description.appendValue( typeName );
			}
			
			@Override
			public final boolean matches( final Object value ) {
				Class<?> type = (Class<?>)value;
				return type.getName().equals( typeName );
			}
		};
	}
}
