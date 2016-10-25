package com.ysx.base.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ehcache {

   String name();

   long cacheLoaderTimeoutMillis() default 0;

   /**
    * 设定内存中创建对象的最大值
    * 
    * @return
    */
   int maxEntriesLocalHeap() default 10000;

   int maxElementsOnDisk() default 0;

   boolean clearOnFlush() default true;

   /**
    * 设置元素是否永久驻留
    * 
    * @return
    */
   boolean eternal() default false;

   /**
    * 设置某个元素消亡前的停顿时间, 0意味着元素可以停顿无穷长的时间
    * 
    * @return
    */
   long timeToIdleSeconds() default 0;

   /**
    * 为元素设置消亡前的生存时间
    * 
    * @return
    */
   long timeToLiveSeconds() default 0;

   int diskSpoolBufferSizeMB() default 30;

   int diskAccessStripes() default 1;

   long diskExpiryThreadIntervalSeconds() default 120;

   boolean logging() default false;

   /**
    * localRestartable, distributed, localTempSwap, none
    * 
    * @return
    */
   String persistenceStrategy() default "none";

   String memoryStoreEvictionPolicy() default "LRU";

}