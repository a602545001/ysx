package com.ysx.base.cache.annotation.scanner;

import java.io.IOException;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import com.ysx.base.cache.annotation.PageCache;

/**
 * description: 扫描EnablePageCache注解，根据注解配置对页面缓存过滤器进行配置
 * 
 * @author: heshan
 * @version 2016年4月20日 下午3:58:46
 * @see
 */
public class PageCacheScanner extends ClassScanner {

   public PageCacheScanResult scan(String... basePackages) {
      PageCacheScanResult result = new PageCacheScanResult();
      this.getIncludeFilters().add(new AnnotationTypeFilter(PageCache.class));
      for (String basePackage : basePackages) {
         scanPackage(result, basePackage);
      }
      return result;
   }

   private void scanPackage(PageCacheScanResult result, String basePackage) {
      try {
         String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
               + ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage))
               + "/**/*.class";
         Resource[] resources = this.getResourcePatternResolver().getResources(packageSearchPath);
         for (int i = 0; i < resources.length; i++) {
            Resource resource = resources[i];
            scanResource(result, resource);
         }
      } catch (IOException | ClassNotFoundException ex) {
         throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
      }
   }

   private void scanResource(PageCacheScanResult result, Resource resource) throws IOException, ClassNotFoundException {
      if (resource.isReadable()) {
         MetadataReader metadataReader = this.getMetadataReaderFactory().getMetadataReader(resource);
         if ((this.getIncludeFilters().isEmpty()) || matches(metadataReader)) {
            ClassMetadata cm = metadataReader.getClassMetadata();
            Class<?> clazz = Class.forName(cm.getClassName());
            PageCache anno = AnnotationUtils.findAnnotation(clazz, PageCache.class);
            result.addIncludePages(anno.includePages());
            result.addIncludePatterns(anno.includePatterns());
            result.addExcludePages(anno.excludePages());
            result.addExcludePatterns(anno.excludePatterns());
         }
      }
   }

}
