package net.dougqh.jak.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.dougqh.jak.operations.Operation;

@Documented
@Target( ElementType.METHOD )
@Retention( RetentionPolicy.RUNTIME )
public @interface Op {
	public abstract Class< ? extends Operation > value();
	
	public abstract boolean repl() default true;
}
