package com.ysx.base.cache.web;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

/**
 * 
 * description:页面缓存过滤器
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:31:10
 * @see
 */
public class PageCacheFilter extends SimplePageCachingFilter {

   private final PathMatcher pathMatcher = new AntPathMatcher();
   private Set<String> excludePages = new HashSet<>();
   private Set<String> excludePatterns = new HashSet<>();

   private CacheManager cacheManager;

   public PageCacheFilter(CacheManager cacheManager) {
      super();
      this.cacheManager = cacheManager;
   }

   public void setExcludePages(Set<String> excludePages) {
      this.excludePages = excludePages;
   }

   public void setExcludePatterns(Set<String> excludePatterns) {
      this.excludePatterns = excludePatterns;
   }
   
   @Override
   protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
         throws Exception {
      HttpServletRequest req = (HttpServletRequest) request;
      if (isExcludedUrl(excludePages, excludePatterns, req.getServletPath())) {
         chain.doFilter(request, response);
      } else {
         super.doFilter(request, response, chain);
      }
   }

   private boolean isExcludedUrl(Set<String> pages, Set<String> patterns, String url) {
      if (pages != null && !pages.isEmpty() && isExcludedPage(pages, url)) {
         return true;
      }
      if (patterns != null && !patterns.isEmpty() && isExcludedPatterns(patterns, url)) {
         return true;
      }
      return false;
   }

   private boolean isExcludedPage(Set<String> pages, String url) {
      for (String page : pages) {
         if (url.equals(page)) {
            return true;
         }
      }
      return false;
   }

   private boolean isExcludedPatterns(Set<String> patterns, String url) {
      for (String pattern : patterns) {
         if (pathMatcher.match(pattern, url)) {
            return true;
         }
      }
      return false;
   }

   @Override
   protected CacheManager getCacheManager() {
      return cacheManager;
   }

}