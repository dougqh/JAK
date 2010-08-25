package net.dougqh.jak.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target( ElementType.METHOD )
@Retention( RetentionPolicy.RUNTIME )
public @interface WrapOp {
	public abstract Class< ? > value();
	
	public abstract Class< ? >[] stackOperandTypes() default {};
	
	public abstract Class< ? >[] stackResultTypes() default {};
}
