package net.dougqh.jak;

import net.dougqh.jak.ConstantPool;
import net.dougqh.jak.FormalArguments;
import net.dougqh.java.meta.types.JavaTypes;

import org.junit.Test;

import static org.junit.Assert.*;
import static net.dougqh.jak.matchers.Matchers.*;

public final class ConstantPoolTest {
	public final @Test void objectTypeNameSignature() {
		String signature = ConstantPool.getMethodSignature(
			void.class,
			FormalArguments.instance( JavaTypes.objectTypeName( "UserDto" ) ) );
		
		assertThat(
			signature,
			is( "(LUserDto;)V" ) );
	}
}
