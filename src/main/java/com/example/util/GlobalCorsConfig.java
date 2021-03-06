package com.example.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//重写WebMvcConfigurer的addCorsMappings方法 实现 跨域资源共享（CORS）（这是一全局跨域配置)
@Configuration
public class GlobalCorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/cyy/**")    //添加映射路径，“/**”表示对所有的路径实行全局跨域访问权限的设置
                        .allowedOrigins("*")    //开放哪些ip、端口、域名的访问权限（也就是允许哪些url访问跨域资源，这里应该写相对应的前端资源url）
                        .allowCredentials(true)  //是否允许发送Cookie信息
                        .allowedMethods("GET","POST", "HEAD", "PUT", "DELETE","OPTIONS", "TRACE")     //开放哪些Http方法，允许跨域访问
                        .allowedHeaders("*")     //允许HTTP请求中的携带哪些Header信息
                        .exposedHeaders("Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");   //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
            }
        };
    }
}
