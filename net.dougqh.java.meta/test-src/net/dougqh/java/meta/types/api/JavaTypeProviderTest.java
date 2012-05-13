package net.dougqh.java.meta.types.api;

import static org.junit.Assert.*;

import java.lang.reflect.Type;

import net.dougqh.java.meta.types.JavaTypeProvider;
import net.dougqh.java.meta.types.JavaTypes;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Test;

public class JavaTypeProviderTest {
	@Test
	public final void resolve() {
		JavaTypeProvider intProvider = new JavaTypeProvider() {
			@Override
			public final Type get() {
				return int.class;
			}
		};
		 
		assertThat( JavaTypes.resolve(intProvider), is(int.class) );
	}
	
	private static final Matcher<Type> is(final Type type) {
		return CoreMatchers.is(type);
	}
}
