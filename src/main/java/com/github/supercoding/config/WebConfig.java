package com.github.supercoding.config;

import com.github.supercoding.web.controller.interceptor.RequestTimeLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RequestTimeLoggingInterceptor requestTimeLoggingInterceptor;
    private final MemberAuthArgumentResolver memberAuthArgumentResolver;

    //인터셉터 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestTimeLoggingInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(memberAuthArgumentResolver);
    }
}
