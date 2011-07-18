package net.dougqh.jak.disassembler.api;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.dougqh.jak.disassembler.JavaAnnotation;
import net.dougqh.jak.disassembler.JavaClass;
import net.dougqh.jak.disassembler.JavaEnum;
import net.dougqh.jak.disassembler.JavaField;
import net.dougqh.jak.disassembler.JavaInterface;
import net.dougqh.jak.disassembler.JavaMethod;
import net.dougqh.jak.disassembler.JvmReader;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

public final class BasicTest {
	private static final String TEST_DATA_PACKAGE = "testdata.";
	
	public final @Test void trivialClass() throws IOException {
		JvmReader reader = new JvmReader();
		JavaClass type = (JavaClass)reader.read( TEST_DATA_PACKAGE + "TrivialClass" );
		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "TrivialClass" ) );
		assertThat( type.getSuperName(), is( "java.lang.Object" ) );
		
		assertThat( type.getFields(), isEmpty() );
		assertThat( type.getMethods(), isEmpty() );
	}

	public final @Test void trivialInterface() throws IOException {
		JvmReader reader = new JvmReader();
		JavaInterface type = (JavaInterface)reader.read( TEST_DATA_PACKAGE + "TrivialInterface" );
		
		assertThat( type, is( JavaInterface.class ) );		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "TrivialInterface" ) );
		assertThat( type.getSuperName(), is( "java.lang.Object" ) );
	}
	
	public final @Test void trivialEnum() throws IOException {
		JvmReader reader = new JvmReader();
		JavaEnum type = (JavaEnum)reader.read( TEST_DATA_PACKAGE + "TrivialEnum" );
		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "TrivialEnum" ) );
		assertThat( type.getSuperName(), is( "java.lang.Enum" ) );
	}

	public final @Test void trivialAnnotation() throws IOException {
		JvmReader reader = new JvmReader();
		JavaAnnotation type = (JavaAnnotation)reader.read( TEST_DATA_PACKAGE + "TrivialAnnotation" );
		
		assertThat( type, is( JavaAnnotation.class ) );		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "TrivialAnnotation" ) );
		assertThat( type.getSuperName(), is( "java.lang.Object" ) );
		
		assertThat(
			type.getInterfaceNames(),
			is( list( "java.lang.annotation.Annotation" ) ) );
	}
	
	public final @Test void nonTrivialClass() throws IOException {
		JvmReader reader = new JvmReader();
		JavaClass type = (JavaClass)reader.read( TEST_DATA_PACKAGE + "NonTrivialClass" );
		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "NonTrivialClass" ) );
		assertThat( type.getSuperName(), is( "java.lang.Object" ) );
		
		assertThat(
			type.getInterfaceNames(),
			is( list( "java.io.Serializable", "java.lang.Runnable" ) ) );
		
		assertThat( type.getMethods(), contains( "run" ) );
	}
	
	private static final List< String > list( final String... names ) {
		return Arrays.asList( names );
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
			if ( object instanceof JavaField ) {
				JavaField field = (JavaField)object;
				return field.getName();
			} else if ( object instanceof JavaMethod ) {
				JavaMethod method = (JavaMethod)object;
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
