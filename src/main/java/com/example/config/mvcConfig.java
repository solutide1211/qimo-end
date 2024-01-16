package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Configuration
public class mvcConfig extends WebMvcConfigurationSupport {
    private static final String IMG_PATH = System.getProperty("user.dir") + "/news/";
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/news/**").addResourceLocations("file:" + IMG_PATH );
        super.addResourceHandlers(registry);
    }
}
