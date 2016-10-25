package com.ysx.base.cache.config;

import java.io.IOException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.ysx.base.cache.annotation.scanner.PageCacheScanResult;
import com.ysx.base.cache.annotation.scanner.PageCacheScanner;
import com.ysx.base.cache.web.PageCacheFilter;
import com.ysx.base.cache.web.directive.PageCacheDirective;

import net.sf.ehcache.CacheManager;

/**
 * 
 * description:注册页面缓存过滤器
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:31:30
 * @see
 */
@Configuration
@ConditionalOnProperty(prefix = "c3_cache", name = "pagecache.enable", matchIfMissing = false)
public class PageCacheConfig {

   @Value("${c3_cache.pagecache.file:}")
   private String file;

   @Value("${c3_cache.pagecache.packagescan:}")
   private String packagescan;

   @Value("${c3_cache.pagecache.filterCacheName:}")
   private String filterCacheName;

   @Value("${c3_cache.pagecache.tagCacheName:}")
   private String tagCacheName;

   @Autowired
   private AutowireCapableBeanFactory beanFactory;

   @Bean(name = "pageCacheManager")
   public CacheManager cacheManager() {
      URL url = Thread.currentThread().getContextClassLoader().getResource(file);
      CacheManager cacheManager = CacheManager.create(url);
      PageCacheDirective.setCache(cacheManager.getCache(tagCacheName));
      return cacheManager;
   }

   @Bean
   @DependsOn("pageCacheManager")
   public FilterRegistrationBean registPageCacheFilter(CacheManager cacheManager) {
      FilterRegistrationBean registration = new FilterRegistrationBean();
      
      PageCacheScanner scanner = new PageCacheScanner();
      PageCacheScanResult scanResult = scanner.scan(packagescan.split(","));
      
      //没有任何需要缓存的页面
      if (scanResult == null
            || (scanResult.getIncludePages().length == 0 && scanResult.getIncludePatterns().length == 0)) {
         registration.setFilter(new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
            }
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                  throws IOException, ServletException {
               chain.doFilter(request, response);
            }
            @Override
            public void destroy() {
            }
         });
         return registration;
      }else{
         PageCacheFilter pageCacheFilter = new PageCacheFilter(cacheManager);
         pageCacheFilter.setExcludePages(scanResult.getExcludePages());
         pageCacheFilter.setExcludePatterns(scanResult.getExcludePatterns());
         beanFactory.autowireBean(pageCacheFilter);
         registration.setFilter(pageCacheFilter);
         registration.addInitParameter("cacheName", filterCacheName);
         registration.addUrlPatterns(scanResult.getIncludePages());
         registration.addUrlPatterns(scanResult.getIncludePatterns());
         return registration;
      }

   }

}
