package com.linlin.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * spring初始化类
 */
public class SpingIconInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    //  root ioc容器配置类
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{DataSourceJavaConfig.class, MapperJavaConfig.class, ServiceJavaConfig.class};
    }

    //  web ioc容器配置类
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvcJavaConfig.class};
    }

    //  dispatcherServlet的拦截路径
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
