package com.apress.pss.springsecurity.configuration;



import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class AppInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {

        System.out.println("..............");
        return new Class[]{UserConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        System.out.println("..............");
        return new Class[]{SpringSecurityConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        System.out.println("..............");
        return new String[]{"/"};
    }
}
