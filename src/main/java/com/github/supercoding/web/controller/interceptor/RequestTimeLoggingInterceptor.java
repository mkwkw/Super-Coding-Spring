package com.github.supercoding.web.controller.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component //인터셉터 등록
@Slf4j
public class RequestTimeLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime", startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("requestStartTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime-startTime;

        log.info("{} {} executed in {} ms", request.getMethod(), request.getRequestURI(), executeTime);
    }
}
