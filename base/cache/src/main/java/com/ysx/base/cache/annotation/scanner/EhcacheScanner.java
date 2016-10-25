package com.ysx.base.cache.annotation.scanner;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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

import com.ysx.base.cache.annotation.Ehcache;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;

public class EhcacheScanner extends ClassScanner {
   private static final EhCacheMessageLogger LOG = Logger.getMessageLogger(EhCacheMessageLogger.class,
         EhcacheScanner.class.getName());

   public Set<CacheConfiguration> scan(String... basePackages) {
      Set<CacheConfiguration> configurations = new HashSet<>();
      this.getIncludeFilters().add(new AnnotationTypeFilter(Ehcache.class));
      for (String basePackage : basePackages) {
         scanPackage(configurations, basePackage);
      }
      return configurations;
   }

   private void scanPackage(Set<CacheConfiguration> configurations, String basePackage) {
      try {
         String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
               + ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage))
               + "/**/*.class";
         Resource[] resources = this.getResourcePatternResolver().getResources(packageSearchPath);
         for (int i = 0; i < resources.length; i++) {
            Resource resource = resources[i];
            scanResource(configurations, resource);
         }
      } catch (IOException | ClassNotFoundException ex) {
         throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
      }
      LOG.debug("esan ehcache init finished.");
   }

   @SuppressWarnings("deprecation")
   private void scanResource(Set<CacheConfiguration> configurations, Resource resource)
         throws IOException, ClassNotFoundException {
      if (resource.isReadable()) {
         MetadataReader metadataReader = this.getMetadataReaderFactory().getMetadataReader(resource);
         if (this.getIncludeFilters().isEmpty() || matches(metadataReader)) {
            ClassMetadata cm = metadataReader.getClassMetadata();
            Class<?> clazz = Class.forName(cm.getClassName());
            Ehcache anno = AnnotationUtils.findAnnotation(clazz, Ehcache.class);
            CacheConfiguration configuration = new CacheConfiguration();
            configuration.setName(anno.name());
            configuration.setCacheLoaderTimeoutMillis(anno.cacheLoaderTimeoutMillis());
            configuration.setMaxEntriesLocalHeap(anno.maxEntriesLocalHeap());
            configuration.setMaxElementsOnDisk(anno.maxElementsOnDisk());
            configuration.setClearOnFlush(anno.clearOnFlush());
            configuration.setEternal(anno.eternal());
            configuration.setTimeToIdleSeconds(anno.timeToIdleSeconds());
            configuration.setTimeToLiveSeconds(anno.timeToLiveSeconds());
            configuration.setDiskSpoolBufferSizeMB(anno.diskSpoolBufferSizeMB());
            configuration.setDiskAccessStripes(anno.diskAccessStripes());
            configuration.setDiskExpiryThreadIntervalSeconds(anno.diskExpiryThreadIntervalSeconds());
            configuration.setLogging(anno.logging());
            configuration.setMemoryStoreEvictionPolicy(anno.memoryStoreEvictionPolicy());
            PersistenceConfiguration pc = new PersistenceConfiguration();
            pc.setStrategy(anno.persistenceStrategy());
            configuration.addPersistence(pc);
            configurations.add(configuration);
         }
      }
   }
}
