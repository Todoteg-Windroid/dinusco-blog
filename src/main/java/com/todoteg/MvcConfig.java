package com.todoteg;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/temp/**")
                .addResourceLocations("file:/ImageServer/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                    	Path newLocation = Path.of(location.getURI());
                    	String fileName =resourcePath.split("/")[1];
                        Path targetLocation = newLocation.resolve(resourcePath.split("/")[0]).resolve(fileName);
                        location = new UrlResource(targetLocation.toUri());
                    	System.out.println(resourcePath);
                    	System.out.println(location.exists()? location : null);
                        return location.exists() && location.isReadable() ? location : null;
                    }
                });
        registry.addResourceHandler("/image/**")
		        .addResourceLocations("file:/ImageServer/")
		        .resourceChain(true)
		        .addResolver(new PathResourceResolver() {
		            @Override
		            protected Resource getResource(String resourcePath, Resource location) throws IOException {
		            	location = location.createRelative(resourcePath);
		                return location.exists() && location.isReadable() ? location : null;
		            }
		        });
    }
    
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locale.US);
	    return slr;
	}
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(localeChangeInterceptor());
	}
}

