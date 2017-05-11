package com.ysx.fish.ui;

import java.util.Arrays;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

//@Configuration
public class RestConfig {

	@Bean
	public ServletRegistrationBean dispatcherServlet() {
		return new ServletRegistrationBean(new CXFServlet(), "/soap/*");
	}

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		return new SpringBus();
	}

	@Bean
	public SoapService collectService() {
		return new SoapService();
	}

	@Bean
	public Endpoint collect(SoapService collectService) {
		EndpointImpl endpoint = new EndpointImpl(springBus(), collectService);
		endpoint.publish("/collect");
		return endpoint;
	}

	@Bean
	public PeopleRestService peopleRestService() {
		return new PeopleRestService();
	}

	@Bean
	@DependsOn("cxf")
	public Server jaxRsServer() {
		JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
		factory.setServiceBean(peopleRestService());
		factory.setAddress(factory.getAddress());
		factory.setResourceProviders(
				Arrays.<ResourceProvider> asList(new SingletonResourceProvider(peopleRestService())));
		factory.setProviders(Arrays.<Object> asList(new JacksonJsonProvider()));
		return factory.create();
	}
}
