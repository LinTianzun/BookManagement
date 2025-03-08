package com.linlin.config;


import com.linlin.interceptors.LoginInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 控制层的配置类 (controller,springmvc)
 * <p>
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
@ComponentScan({"com.linlin.controller", "com.linlin.exception"})
public class WebMvcJavaConfig implements WebMvcConfigurer {

//    @Autowired
//    private LoginInterceptor loginInterceptor;

    /**
     * 开启静态资源
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * jsp 视图解析器前后缀
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    /**
     * 拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //  登录和注册接口不拦截
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns("/user/login", "/user/register");
    }
}
