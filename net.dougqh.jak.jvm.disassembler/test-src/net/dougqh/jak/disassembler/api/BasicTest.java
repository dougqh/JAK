package net.dougqh.jak.disassembler.api;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.dougqh.jak.Jak;
import net.dougqh.jak.disassembler.JvmAnnotation;
import net.dougqh.jak.disassembler.JvmClass;
import net.dougqh.jak.disassembler.JvmEnum;
import net.dougqh.jak.disassembler.JvmInterface;
import net.dougqh.jak.disassembler.JvmMethod;
import net.dougqh.jak.disassembler.JvmReader;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.aload_0;
import net.dougqh.jak.jvm.operations.invokespecial;
import net.dougqh.jak.jvm.operations.return_;

import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public final class BasicTest {
	private static final String TEST_DATA_PACKAGE = "testdata.";
	
	@Test
	public final void trivialClass() throws IOException {
		JvmReader reader = new JvmReader();
		JvmClass type = reader.read( TEST_DATA_PACKAGE + "TrivialClass" );
		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "TrivialClass" ) );
		assertThat( type.getParentType(), isType( Object.class ) );
		
		assertThat( type.getConstructors().size(), is(1) );
		assertThat( type.getClassInitializer(), nullValue(JvmMethod.class));
		assertThat( type.getFields().isEmpty(), is(true) );
		assertThat( type.getMethods().isEmpty(), is(true) );
	}
	
	@Test
	public final void constructor() throws IOException {
		JvmReader reader = new JvmReader();
		JvmClass aClass = reader.read( TEST_DATA_PACKAGE + "TrivialClass" );
		
		JvmMethod constructor = aClass.getConstructors().get(0);
		assertThat( constructor.isConstructor(), is(true) );
		assertThat( constructor.isPublic(), is(true) );
		
		assertThat( constructor.getMaxLocals(), is(1) );
		assertThat( constructor.getMaxStack(), is(1) );
		assertThat( constructor.hasCode(), is(true) );
		
		assertThat( constructor.getReturnType(), isType(void.class) );
		assertThat( constructor.getParameterTypes(), areTypes());
		
		assertThat( constructor, hasCode(
			aload_0.instance(),
			new invokespecial(Object.class, Jak.init()),
			return_.instance()
		) );
	}

	@Test
	public final void trivialInterface() throws IOException {
		JvmReader reader = new JvmReader();
		JvmInterface type = reader.read( TEST_DATA_PACKAGE + "TrivialInterface" );
		
		assertThat( type, is( JvmInterface.class ) );		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "TrivialInterface" ) );
		assertThat( type.getParentType(), isType( Object.class ) );
	}
	
	@Test
	public final void trivialEnum() throws IOException {
		JvmReader reader = new JvmReader();
		JvmEnum type = reader.read( TEST_DATA_PACKAGE + "TrivialEnum" );
		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "TrivialEnum" ) );
		assertThat( type.getParentType(), isType( Enum.class ) );
	}

	@Test
	public final void trivialAnnotation() throws IOException {
		JvmReader reader = new JvmReader();
		JvmAnnotation type = reader.read( TEST_DATA_PACKAGE + "TrivialAnnotation" );
		
		assertThat( type, is( JvmAnnotation.class ) );		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "TrivialAnnotation" ) );
		assertThat( type.getParentType(), isType( Object.class ) );
		
		assertThat( type.getInterfaces(), areTypes( Annotation.class ) );
	}
	
	@Test
	public final void nonTrivialClass() throws IOException {
		JvmReader reader = new JvmReader();
		JvmClass type = reader.read( TEST_DATA_PACKAGE + "NonTrivialClass" );
		
		assertThat( type.getName(), is( TEST_DATA_PACKAGE + "NonTrivialClass" ) );
		assertThat( type.getParentType(), isType( Object.class ) );
		
		assertThat(
			type.getInterfaces(),
			areTypes( Serializable.class, Runnable.class ) );
		
		//assertThat( type.getMethods(), contains( "run" ) );
	}
		
	private static final Matcher< Type > isType( final Type type ) {
		return CoreMatchers.is( type );
	}
	
	private static final Matcher< List< Type > > areTypes( final Type... types ) {
		return CoreMatchers.is( Arrays.asList( types ) );
	}
	
	private static final Matcher< JvmMethod > hasCode( final JvmOperation... expectedOps ) {
		return new BaseMatcher<JvmMethod>() {
			@Override
			public final void describeTo(final Description description) {
				description.appendValue(Arrays.asList(expectedOps));
			}
			
			@Override
			public final boolean matches(final Object obj) {
				JvmMethod actual = (JvmMethod)obj;
				Iterator<JvmOperation> opIter = actual.getOperations().iterator();
				
				for ( JvmOperation expectedOp: expectedOps ) {
					if ( ! opIter.hasNext() ) {
						return false;
					}
					
					JvmOperation actualOp = opIter.next();
					if ( ! expectedOp.equals( actualOp ) ) {
						return false;
					}
				}
				
				if ( opIter.hasNext() ) {
					return false;
				}
				
				return true;
			}
		};
	}
}
