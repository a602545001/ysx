package com.ysx.base.cache.annotation.scanner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * description: 页面缓存扫描结果集
 * 
 * @author: heshan
 * @version 2016年5月17日 下午2:02:28
 * @see
 */
public class PageCacheScanResult {

   private Set<String> includePages = new HashSet<>();

   private Set<String> includePatterns = new HashSet<>();

   private Set<String> excludePages = new HashSet<>();

   private Set<String> excludePatterns = new HashSet<>();

   public String[] getIncludePages() {
      return includePages.toArray(new String[] {});
   }

   public void addIncludePages(String[] includePages) {
      this.includePages.addAll(Arrays.asList(includePages));
   }

   public String[] getIncludePatterns() {
      return includePatterns.toArray(new String[] {});
   }

   public void addIncludePatterns(String[] includePatterns) {
      this.includePatterns.addAll(Arrays.asList(includePatterns));
   }

   public Set<String> getExcludePages() {
      return excludePages;
   }

   public void addExcludePages(String[] excludePages) {
      this.excludePages.addAll(Arrays.asList(excludePages));
   }

   public Set<String> getExcludePatterns() {
      return excludePatterns;
   }

   public void addExcludePatterns(String[] excludePatterns) {
      this.excludePatterns.addAll(Arrays.asList(excludePatterns));
   }

}