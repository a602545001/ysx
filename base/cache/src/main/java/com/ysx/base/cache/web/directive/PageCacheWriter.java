package com.ysx.base.cache.web.directive;

import java.io.IOException;
import java.io.Writer;

public class PageCacheWriter extends Writer {
   private final Writer out;
   private StringBuffer content;

   PageCacheWriter(Writer out, StringBuffer content) {
      this.out = out;
      this.content = content;
   }

   @Override
   public void write(char[] cbuf, int off, int len) throws IOException {
      content.append(cbuf, off, len);
      out.write(cbuf, off, len);
   }

   @Override
   public void flush() throws IOException {
      out.flush();
   }

   @Override
   public void close() throws IOException {
      out.close();
   }

}