package com.github.zhalabkevich.config;


import com.sun.faces.config.FacesInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class WebAppInitializer extends FacesInitializer implements WebApplicationInitializer {
    public void onStartup(ServletContext sc) throws ServletException {
        AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.register(SecurityConfig.class);
        sc.addListener(new ContextLoaderListener(root));
    }
}

