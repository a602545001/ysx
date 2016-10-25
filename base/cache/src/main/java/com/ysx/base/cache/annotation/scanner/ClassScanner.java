package com.ysx.base.cache.annotation.scanner;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

/**
 * description:类扫描器
 * 
 * @author: heshan
 * @version 2016年4月20日 下午3:58:46
 * @see
 */
public abstract class ClassScanner implements ResourceLoaderAware {

   private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

   private final List<TypeFilter> includeFilters = new LinkedList<>();

   private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

   public void setResourceLoader(ResourceLoader resourceLoader) {
      this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
      this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
   }

   public final ResourceLoader getResourceLoader() {
      return this.resourcePatternResolver;
   }

   protected boolean matches(MetadataReader metadataReader) throws IOException {
      for (TypeFilter tf : this.includeFilters) {
         if (tf.match(metadataReader, this.metadataReaderFactory)) {
            return true;
         }
      }
      return false;
   }

   public ResourcePatternResolver getResourcePatternResolver() {
      return resourcePatternResolver;
   }

   public void setResourcePatternResolver(ResourcePatternResolver resourcePatternResolver) {
      this.resourcePatternResolver = resourcePatternResolver;
   }

   public List<TypeFilter> getIncludeFilters() {
      return includeFilters;
   }

   public MetadataReaderFactory getMetadataReaderFactory() {
      return metadataReaderFactory;
   }

   public void setMetadataReaderFactory(MetadataReaderFactory metadataReaderFactory) {
      this.metadataReaderFactory = metadataReaderFactory;
   }

}