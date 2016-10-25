package com.ysx.base.cache.annotation.scanner;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cache.ehcache.EhCacheMessageLogger;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import com.ysx.base.cache.annotation.L2Cache;

import net.sf.ehcache.Cache;

/**
 * description: 扫描L2CacheEhCache注解，根据注解配置生成Cache并加入到CacheManager
 * 
 * @author: heshan
 * @version 2016年4月20日 下午3:58:46
 * @see
 */
public class L2CacheScanner extends ClassScanner {
   private static final EhCacheMessageLogger LOG = Logger.getMessageLogger(EhCacheMessageLogger.class,
         L2CacheScanner.class.getName());

   public Set<Cache> scan(String... basePackages) {
      Set<Cache> caches = new HashSet<>();
      this.getIncludeFilters().add(new AnnotationTypeFilter(L2Cache.class));
      for (String basePackage : basePackages) {
         scanPackage(caches, basePackage);
      }
      return caches;
   }

   private void scanPackage(Set<Cache> caches, String basePackage) {
      try {
         String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
               + ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage))
               + "/**/*.class";
         Resource[] resources = this.getResourcePatternResolver().getResources(packageSearchPath);
         for (int i = 0; i < resources.length; i++) {
            Resource resource = resources[i];
            scanResource(caches, resource);
         }
      } catch (IOException | ClassNotFoundException ex) {
         throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
      }
   }

   private void scanResource(Set<Cache> caches, Resource resource) throws IOException, ClassNotFoundException {
      if (resource.isReadable()) {
         MetadataReader metadataReader = this.getMetadataReaderFactory().getMetadataReader(resource);
         if (this.getIncludeFilters().isEmpty() || matches(metadataReader)) {
            ClassMetadata cm = metadataReader.getClassMetadata();
            Class<?> clazz = Class.forName(cm.getClassName());
            L2Cache anno = AnnotationUtils.findAnnotation(clazz, L2Cache.class);
            String cacheName = anno.name();
            // 未定义cache的名字,则使用类得名字
            if (StringUtils.isBlank(cacheName)) {
               cacheName = clazz.getName();
               LOG.debug("未定义缓存名称,取类名" + cacheName);
            }
            Cache cache = new Cache(cacheName, anno.maxEntriesLocalHeap(), anno.overflowToDisk(), anno.eternal(),
                  anno.timeToLiveSeconds(), anno.timeToIdleSeconds());
            caches.add(cache);
         }
      }
   }

}
