package com.github.supercoding.web.controller.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    //우리 필터 집어넣기
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String method = request.getMethod();
        String uri = request.getRequestURI();

        log.info(method+" "+uri+" 요청이 들어왔습니다.");

        //필터 들어가기 전
        filterChain.doFilter(request, response);
        //필터에서 나온 후
        log.info(method+" "+"가 상태 "+response.getStatus()+"로 응답이 나갑니다.");

    }
}
