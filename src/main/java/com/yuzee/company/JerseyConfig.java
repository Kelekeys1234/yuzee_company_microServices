package com.yuzee.company;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.UriConnegFilter;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

@Configuration("jerseyConfig")
public class JerseyConfig extends ResourceConfig  {

	 
	/**
	 * Constructor for Configuring JAX-RS
	 */
	public JerseyConfig() {
		super();
		register(RequestContextFilter.class);
		register(JacksonFeature.class);
		property(ServletProperties.FILTER_FORWARD_ON_404, true);
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
		// Controller, feature and provider classes being loaded as part of
		// component scan:
		// Alternative to packages() function.
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Service.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(Component.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(Repository.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(Resource.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(Configuration.class));

		super.registerClasses(scanner.findCandidateComponents("com.yuzee").stream().map(beanDefinition -> ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), super.getClassLoader())).collect(Collectors.toSet()));

		Map<String, MediaType> mediaTypes = new HashMap<>();
		mediaTypes.put("xml", MediaType.APPLICATION_XML_TYPE);
		mediaTypes.put("json", MediaType.APPLICATION_JSON_TYPE);
		register(new UriConnegFilter(mediaTypes, null));
	}
}
