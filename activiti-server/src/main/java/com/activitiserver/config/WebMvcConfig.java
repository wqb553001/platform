package com.activitiserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.List;

// 未达到预期效果
@EnableWebMvc
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF8"));
        return converter;
    }

//    @Override
//    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        // 解决controller返回字符串中文乱码问题
//        for (HttpMessageConverter<?> converter : converters) {
//            if (converter instanceof StringHttpMessageConverter) {
//                ((StringHttpMessageConverter)converter).setDefaultCharset(StandardCharsets.UTF_8);
//            }
//        }
//    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false); //支持后缀匹配
    }


    // 如果不加，则静态资源会被拦截，导致访问不到静态资源
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
        super.addResourceHandlers(registry);
    }

}