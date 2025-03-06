package com.core.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;

public interface WebConfigInterface {
    public void addCorsMappings(CorsRegistry registry);
}
