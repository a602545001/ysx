package com.ysx.base.core.config;

import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration.FreeMarkerWebConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import com.ysx.base.core.annotation.Directive;
import com.ysx.base.core.freemaker.AbstractDirective;

@Configuration
@ConditionalOnClass(Servlet.class)
@ConditionalOnWebApplication
public class FreeMarkerWebConfig extends FreeMarkerWebConfiguration {
	
	/**
	 * 配置
	 */
	@Bean
	@ConditionalOnMissingBean(FreeMarkerConfig.class)
	@Override
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		applyProperties(configurer);
		return configurer;
	}


}
