package com.lc.demo.config;

import com.lc.demo.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

// 使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
// @EnableWebMvc  一般情况下不要添加这个注解，如果添加了此注解，会全面接管SpringMVC的配置，
// 可能会导致Spring Boot自动配置的一些SpringMVC相关功能失效，这里应该是希望基于Spring Boot原有的自动配置基础上进行扩展，所以不使用它
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    // 以下是原本可以重写的addViewControllers方法，但在当前代码中被注释掉了。
    // 如果取消注释并进行实现，可以用来配置简单的视图控制器映射，将特定的请求路径直接映射到相应的视图名称，
    // 而不需要编写对应的Controller类中的方法来处理请求并返回视图。
    // 例如，可以像下面这样配置（当前代码中注释掉了这种方式）：
    // @Override
    // public void addViewControllers(ViewControllerRegistry registry) {
    //    // super.addViewControllers(registry);  调用父类的方法（如果有需要的话），当前代码注释掉表示不调用父类的默认行为
    //    registry.addViewController("/").setViewName("login");  // 将根路径（"/"）请求直接映射到名为"login"的视图
    // }

    // 所有的WebMvcConfigurerAdapter组件都会一起起作用，即多个实现了WebMvcConfigurerAdapter的类中的配置方法都会生效，共同对SpringMVC进行配置扩展。
    @Bean // 将组件注册在Spring容器中，使得Spring能够管理这个组件的生命周期等操作
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            // 重写addViewControllers方法来配置视图控制器映射
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {

                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/admmain.html").setViewName("/adm/adminindex");
                registry.addViewController("/stumain.html").setViewName("/stu/stuindex");
                registry.addViewController("/teamain.html").setViewName("/tea/teaindex");
                registry.addViewController("/stuadmin.html").setViewName("/adm/stulist");
                registry.addViewController("/addstudent.html").setViewName("/adm/addstu");
                registry.addViewController("/updateSucc.html").setViewName("/stu/stuUpdateSucc");
                registry.addViewController("/updateTeaSucc.html").setViewName("/tea/teaUpdateSucc");

            }

            // 注册拦截器的方法，用于添加自定义的拦截器，并配置拦截器的拦截路径和排除路径等规则
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                super.addInterceptors(registry);
                // 以下是添加自定义拦截器的相关配置说明：

                // 首先创建了一个LoginHandlerInterceptor类型的拦截器实例，通常这个拦截器用于处理登录相关的验证逻辑，
                // 比如判断用户是否已经登录，如果未登录则进行相应的拦截操作（如跳转到登录页面等）。
                // 然后通过addPathPatterns("/**")配置这个拦截器要拦截的路径模式，这里的"/**"表示拦截所有的请求路径，
                // 意味着几乎所有进入应用的请求都会先经过这个拦截器进行处理。

                // 接着通过excludePathPatterns方法配置了一些排除拦截的路径，也就是这些路径不会被该拦截器拦截，
                // 例如"/index.html"、"/"、"/stu/login"、"/adm/login"、"/tea/login"这些路径，
                // 可能是因为这些路径本身就是登录页面相关的路径或者不需要进行登录验证的公共访问路径，
                // 所以不需要经过登录验证拦截器的处理，直接可以访问。

                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/index.html", "/", "/stu/login", "/adm/login", "/tea/login");
            }
        };
        return adapter;
    }
}