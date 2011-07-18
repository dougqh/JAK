package net.dougqh.jak.jvm.assembler;

import static net.dougqh.jak.matchers.Matchers.*;
import static org.junit.Assert.*;
import net.dougqh.jak.FormalArguments;
import net.dougqh.java.meta.types.JavaTypes;

import org.junit.Test;

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
