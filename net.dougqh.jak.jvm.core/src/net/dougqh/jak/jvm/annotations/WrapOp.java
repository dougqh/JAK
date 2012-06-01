package net.dougqh.jak.jvm.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.dougqh.jak.jvm.operations.JvmOperation;

@Documented
@Target( ElementType.METHOD )
@Retention( RetentionPolicy.RUNTIME )
public @interface WrapOp {
	public abstract Class<? extends JvmOperation> value();
	
	public abstract Class<?>[] stackOperandTypes() default {};
	
	public abstract Class<?>[] stackResultTypes() default {};
	
	public abstract boolean repl() default true;
}
