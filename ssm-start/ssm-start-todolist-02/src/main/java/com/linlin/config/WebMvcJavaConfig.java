package com.linlin.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 控制层的配置类 (controller,springmvc)
 *
 * 1.controller
 * 2.全局异常处理
 * 3.handlerMapping handlerAdpter
 * 4.静态资源处理
 * 5.jsp 视图解析器前后缀
 * 6.json转化器
 * 7.拦截器
 */
@EnableWebMvc
@Configuration
@ComponentScan({"com.linlin.controller"})
public class WebMvcJavaConfig implements WebMvcConfigurer {

    /**
     * 开启静态资源
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * jsp 视图解析器前后缀
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    /**
     * 拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new );
    }
}
