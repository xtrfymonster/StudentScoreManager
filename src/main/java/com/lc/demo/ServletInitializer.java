package com.lc.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {
    // 重写configure方法，该方法用于配置SpringApplicationBuilder
    @Override
    // 返回一个配置好的SpringApplicationBuilder，其中指定了Spring Boot应用的主启动类为DemoApplication.class
    // 这样在Servlet容器启动时，就能够正确地加载和启动Spring Boot应用
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }
}