package com.ysx.base.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * description: 自定义注解指�?
 *
 * @version 2016�?�?�?上午10:37:12
 * @see
 * modify content------------author------------date
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Directive {
	
	String name() default "";

	String value() default "";

}
