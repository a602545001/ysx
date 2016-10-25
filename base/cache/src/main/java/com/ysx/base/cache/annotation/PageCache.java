package com.ysx.base.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: 开启页面缓存
 * 
 * @author: heshan
 * @version 2016年4月20日 下午3:53:38
 * @see
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageCache {

   String[] includePages() default {};

   String[] includePatterns() default {};

   String[] excludePages() default {};

   String[] excludePatterns() default {};

}