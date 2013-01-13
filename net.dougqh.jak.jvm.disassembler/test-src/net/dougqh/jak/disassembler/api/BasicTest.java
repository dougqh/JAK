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

import testdata.NonTrivialClass;
import testdata.TrivialAnnotation;
import testdata.TrivialClass;
import testdata.TrivialEnum;
import testdata.TrivialInterface;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public final class BasicTest {
	@Test
	public final void trivialClass() throws IOException {
		JvmReader reader = new JvmReader();
		JvmClass type = reader.read(TrivialClass.class);
		
		assertThat( type.getName(), is(TrivialClass.class.getCanonicalName()) );
		assertThat( type.getParentType(), isType( Object.class ) );
		
		assertThat( type.getConstructors().size(), is(1) );
		assertThat( type.getClassInitializer(), nullValue(JvmMethod.class));
		assertThat( type.getFields().isEmpty(), is(true) );
		assertThat( type.getMethods().isEmpty(), is(true) );
	}
	
	@Test
	public final void constructor() throws IOException {
		JvmReader reader = new JvmReader();
		JvmClass aClass = reader.read(TrivialClass.class);
		
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
		JvmInterface type = reader.read(TrivialInterface.class);
		
		assertThat( type, is( JvmInterface.class ) );		
		assertThat( type.getName(), is(TrivialInterface.class.getCanonicalName()) );
		assertThat( type.getParentType(), isType( Object.class ) );
	}
	
	@Test
	public final void trivialEnum() throws IOException {
		JvmReader reader = new JvmReader();
		JvmEnum type = reader.read(TrivialEnum.class);
		
		assertThat( type.getName(), is(TrivialEnum.class.getCanonicalName()) );
		assertThat( type.getParentType(), isType( Enum.class ) );
	}

	@Test
	public final void trivialAnnotation() throws IOException {
		JvmReader reader = new JvmReader();
		JvmAnnotation type = reader.read(TrivialAnnotation.class);
		
		assertThat( type, is( JvmAnnotation.class ) );		
		assertThat( type.getName(), is(TrivialAnnotation.class.getCanonicalName()) );
		assertThat( type.getParentType(), isType( Object.class ) );
		
		assertThat( type.getInterfaces(), areTypes( Annotation.class ) );
	}
	
	@Test
	public final void nonTrivialClass() throws IOException {
		JvmReader reader = new JvmReader();
		JvmClass type = reader.read(NonTrivialClass.class);
		
		assertThat( type.getName(), is(NonTrivialClass.class.getCanonicalName()) );
		assertThat( type.getParentType(), isType( Object.class ) );
		
		assertThat(
			type.getInterfaces(),
			areTypes( Serializable.class, Runnable.class ) );
		
		//assertThat( type.getMethods(), contains("run") );
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
