package com.ysx.base.cache.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ysx.base.cache.annotation.scanner.EhcacheScanner;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;

@Configuration
public class EhcacheConfig {

   @Value("${c3_cache.ehcache.packagescan:}")
   private String packagescan;

   @Value("${c3_cache.ehcache.managerName:c3}")
   private String cacheManagerName;

   @Bean(name = "ehCacheManager")
   public EhCacheCacheManager ehCacheCacheManager() {
      EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
      CacheManager cacheManager = CacheManager.getCacheManager(cacheManagerName);
      if (cacheManager == null) {
         net.sf.ehcache.config.Configuration managerConfig = new net.sf.ehcache.config.Configuration();
         managerConfig.setName(cacheManagerName);
         cacheManager = CacheManager.create(managerConfig);
      }
      EhcacheScanner scanner = new EhcacheScanner();
      Set<CacheConfiguration> configurations = scanner.scan(packagescan.split(","));
      for (CacheConfiguration configuration : configurations) {
         Cache cache = new Cache(configuration);
         if (!cacheManager.cacheExists(cache.getName())) {
            cacheManager.addCache(cache);
         }
      }
      ehCacheCacheManager.setCacheManager(cacheManager);
      return ehCacheCacheManager;
   }

}