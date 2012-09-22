package net.dougqh.jak.disassembler.api;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.dougqh.jak.disassembler.JvmAnnotation;
import net.dougqh.jak.disassembler.JvmEnum;
import net.dougqh.jak.disassembler.JvmClass;
import net.dougqh.jak.disassembler.JvmField;
import net.dougqh.jak.disassembler.JvmInterface;
import net.dougqh.jak.disassembler.JvmMethod;
import net.dougqh.jak.disassembler.JvmReader;

import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

public final class BasicTest {
	private static final String TEST_DATA_PACKAGE = "testdata.";
	
	public final @Test void trivialClass() throws IOException {
		JvmReader reader = new JvmReader();
		JvmClass type = (JvmClass)reader.read( TEST_DATA_PACKAGE + "TrivialClass" );
		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "TrivialClass" ) );
		assertThat( type.getParentType(), isType( Object.class ) );
		
		assertThat( type.getConstructors().size(), is(1) );
		assertThat( type.getClassInitializer(), nullValue(JvmMethod.class));
		assertThat( type.getFields(), isEmpty() );
		assertThat( type.getMethods(), isEmpty() );
	}

	public final @Test void trivialInterface() throws IOException {
		JvmReader reader = new JvmReader();
		JvmInterface type = (JvmInterface)reader.read( TEST_DATA_PACKAGE + "TrivialInterface" );
		
		assertThat( type, is( JvmInterface.class ) );		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "TrivialInterface" ) );
		assertThat( type.getParentType(), isType( Object.class ) );
	}
	
	public final @Test void trivialEnum() throws IOException {
		JvmReader reader = new JvmReader();
		JvmEnum type = (JvmEnum)reader.read( TEST_DATA_PACKAGE + "TrivialEnum" );
		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "TrivialEnum" ) );
		assertThat( type.getParentType(), isType( Enum.class ) );
	}

	public final @Test void trivialAnnotation() throws IOException {
		JvmReader reader = new JvmReader();
		JvmAnnotation type = (JvmAnnotation)reader.read( TEST_DATA_PACKAGE + "TrivialAnnotation" );
		
		assertThat( type, is( JvmAnnotation.class ) );		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "TrivialAnnotation" ) );
		assertThat( type.getParentType(), isType( Object.class ) );
		
		assertThat( type.getInterfaces(), areTypes( Annotation.class ) );
	}
	
	public final @Test void nonTrivialClass() throws IOException {
		JvmReader reader = new JvmReader();
		JvmClass type = (JvmClass)reader.read( TEST_DATA_PACKAGE + "NonTrivialClass" );
		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "NonTrivialClass" ) );
		assertThat( type.getParentType(), isType( Object.class ) );
		
		assertThat(
			type.getInterfaces(),
			areTypes( Serializable.class, Runnable.class ) );
		
		assertThat( type.getMethods(), contains( "run" ) );
	}
		
	private static final Matcher< Type > isType( final Type type ) {
		return CoreMatchers.is( type );
	}
	
	private static final Matcher< List< Type > > areTypes( final Type... types ) {
		return CoreMatchers.is( Arrays.asList( types ) );
	}
	
	private static final Matcher< Collection< ? > > isEmpty() {
		return new IsEmptyMatcher();
	}
	
	private static final Matcher< Collection< ? > > contains( final String... names ) {
		return new ContainsMatcher( names );
	}
	
	private static final class IsEmptyMatcher extends BaseMatcher< Collection< ? > > {
		@Override
		public final boolean matches( final Object obj ) {
			Collection< ? > collection = (Collection< ? >)obj;
			return collection.isEmpty();
		}
		
		@Override
		public final void describeTo( final Description description ) {
			description.appendText( "Collection should be empty" );
		}
	}
	
	private static final class ContainsMatcher extends BaseMatcher< Collection< ? > > {
		private final HashSet< String > names;
		
		ContainsMatcher( final String... names ) {
			this.names = new HashSet< String >( Arrays.asList( names ) );
		}
		
		@Override
		public final boolean matches( final Object obj ) {
			Collection< ? > collection = (Collection< ? >)obj;
			
			int matchCount = 0;
			for ( Object object : collection ) {
				String name = name( object );
				
				if ( this.names.contains( name ) ) {
					++matchCount;
				}
			}
			
			return ( matchCount == collection.size() );
		}
		
		private static final String name( final Object object ) {
			if ( object instanceof JvmField ) {
				JvmField field = (JvmField)object;
				return field.getName();
			} else if ( object instanceof JvmMethod ) {
				JvmMethod method = (JvmMethod)object;
				return method.getName();
			} else {
				throw new IllegalStateException();
			}
		}
		
		@Override
		public final void describeTo( final Description description ) {
			description.appendValue( this.names );
		}
	}
}
