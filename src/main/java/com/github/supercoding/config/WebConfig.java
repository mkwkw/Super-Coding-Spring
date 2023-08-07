package com.github.supercoding.config;

import com.github.supercoding.web.controller.interceptor.RequestTimeLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RequestTimeLoggingInterceptor requestTimeLoggingInterceptor;

    //인터셉터 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestTimeLoggingInterceptor);
    }
}
