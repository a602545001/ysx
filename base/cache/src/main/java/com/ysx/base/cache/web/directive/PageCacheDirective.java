package com.ysx.base.cache.web.directive;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ysx.base.core.annotation.Directive;
import com.ysx.base.core.freemaker.AbstractDirective;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Directive(name = "pagecache")
public class PageCacheDirective extends AbstractDirective {
   private static final Logger log = LoggerFactory.getLogger(PageCacheDirective.class);

   private final static String FILE_SEPARATOR = "/";
   private final static char FILE_SEPARATOR_CHAR = FILE_SEPARATOR.charAt(0);

   private final static String KEY = "key";
   private final static String COOKIE = "cookie";

   /**
    * Usable caracters for key generation
    */
   private static final String m_strBase64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

   private static Cache cache = null;

   private String actualKey = null;

   private String content = null;

   public PageCacheDirective() {
   }

   @Override
   public void exec(Environment env, Map<?, ?> params, TemplateModel[] loopVars, TemplateDirectiveBody body)
         throws Exception {

      if (cache == null) {
         body.render(env.getOut());
         return;
      }

      ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
      HttpServletRequest request = attr.getRequest();
      
      SimpleScalar key = (SimpleScalar) params.get(KEY);
      SimpleScalar cookie = (SimpleScalar) params.get(COOKIE);

      actualKey = generateEntryKey(key, cookie, request);

      Element element = cache.get(actualKey);
      if (element != null) {
         content = (String) element.getObjectValue();
      }

      if (content != null) {
         if (log.isInfoEnabled()) {
            log.info("<cache>: Using cached entry : " + actualKey);
         }

         env.getOut().write(content);
         content = null;
      } else {
         if (log.isInfoEnabled()) {
            log.info("<cache>: Missing cached content : " + actualKey);
         }

         StringBuffer tempContent = new StringBuffer();
         body.render(new PageCacheWriter(env.getOut(), tempContent));

         if (log.isDebugEnabled()) {
            log.debug("<cache>: Updating cache entry with new content : " + actualKey);
         }
         cache.put(new Element(actualKey, tempContent.toString()));
      }
   }

   public static void setCache(Cache cache) {
      PageCacheDirective.cache = cache;
   }

   public String generateEntryKey(SimpleScalar key, SimpleScalar cookie, HttpServletRequest request) {

      StringBuffer cBuffer = new StringBuffer(30);

      cBuffer.append(FILE_SEPARATOR).append(request.getServerName());
      
      Cookie[] cookies = request.getCookies();
      if (cookie != null && cookies != null) {
         String[] cookieNames = cookie.getAsString().split(",");
         for(String cookieName : cookieNames){
            for(Cookie c : cookies){
               if(c.getName().equals(cookieName)){
                  cBuffer.append(FILE_SEPARATOR).append(c.getValue());
               }
            }
         }
      }

      if (key != null) {
         cBuffer.append(FILE_SEPARATOR).append(key.getAsString());
      }else{
         String generatedKey = request.getRequestURI();
         if (generatedKey.charAt(0) != FILE_SEPARATOR_CHAR) {
            cBuffer.append(FILE_SEPARATOR_CHAR);
         }
         cBuffer.append(generatedKey);
         cBuffer.append("_").append(request.getMethod()).append("_");

         generatedKey = getSortedQueryString(request);
         if (generatedKey != null) {
            try {
               java.security.MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
               byte[] b = digest.digest(generatedKey.getBytes());
               cBuffer.append('_');
               // Base64 encoding allows for unwanted slash characters.
               cBuffer.append(toBase64(b).replace('/', '_'));
            } catch (Exception e) {
               // Ignore query string
            }
         }
      }
      return cBuffer.toString();
   }

   private String getSortedQueryString(HttpServletRequest request) {
      Map<?, ?> paramMap = request.getParameterMap();
      if (paramMap.isEmpty()) {
         return null;
      }
      Set<?> paramSet = new TreeMap<>(paramMap).entrySet();

      StringBuffer buf = new StringBuffer();

      boolean first = true;

      for (Iterator<?> it = paramSet.iterator(); it.hasNext();) {
         Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
         String[] values = (String[]) entry.getValue();

         for (int i = 0; i < values.length; i++) {
            String key = (String) entry.getKey();

            if ((key.length() != 10) || !"jsessionid".equals(key)) {
               if (first) {
                  first = false;
               } else {
                  buf.append('&');
               }

               buf.append(key).append('=').append(values[i]);
            }
         }
      }
      // We get a 0 length buffer if the only parameter was a jsessionid
      if (buf.length() == 0) {
         return null;
      } else {
         return buf.toString();
      }
   }

   /**
    * Convert a byte array into a Base64 string (as used in mime formats)
    */
   private static String toBase64(byte[] aValue) {
      int byte1;
      int byte2;
      int byte3;
      int iByteLen = aValue.length;
      StringBuffer tt = new StringBuffer();

      for (int i = 0; i < iByteLen; i += 3) {
         boolean bByte2 = (i + 1) < iByteLen;
         boolean bByte3 = (i + 2) < iByteLen;
         byte1 = aValue[i] & 0xFF;
         byte2 = (bByte2) ? (aValue[i + 1] & 0xFF) : 0;
         byte3 = (bByte3) ? (aValue[i + 2] & 0xFF) : 0;

         tt.append(m_strBase64Chars.charAt(byte1 / 4));
         tt.append(m_strBase64Chars.charAt((byte2 / 16) + ((byte1 & 0x3) * 16)));
         tt.append(((bByte2) ? m_strBase64Chars.charAt((byte3 / 64) + ((byte2 & 0xF) * 4)) : '='));
         tt.append(((bByte3) ? m_strBase64Chars.charAt(byte3 & 0x3F) : '='));
      }
      return tt.toString();
   }

}