package com.github.supercoding.web.controller.sample;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class SessionTokenSampleController {

    //세션 정의 - 브라우저는 이 내용을 담은 쿠키를 갖게 됨.
    @GetMapping("/set-session")
    public String setSession(HttpSession session){
        session.setAttribute("user", "조인성");
        session.setAttribute("gender", "남자");
        session.setAttribute("job", "배우");
        return "Session Set successfully";
    }

    //세션 정의
    @GetMapping("/set-session2")
    public String setSession2(HttpSession session){
        session.setAttribute("user", "송혜교");
        session.setAttribute("gender", "여자");
        session.setAttribute("job", "배우");
        return "Session Set successfully";
    }

    //세션 가져오기
    @GetMapping("/get-session")
    public String getSession(HttpSession session){
        String user = (String) session.getAttribute("user");
        String gender = (String) session.getAttribute("gender");
        String job = (String) session.getAttribute("job");
        return String.format("안녕하세요, 직업: %s 성별: %s인 %s 입니다.", job, gender, user);
    }

    //jwt 토큰 생성 - 서버에서 생성해서 클라이언트에게 보내기
    @GetMapping("/generate-token")
    public String generateToken(HttpServletResponse httpServletResponse){
        String jwt = Jwts.builder()
                .setSubject("token1")
                .claim("user", "조인성")
                .claim("gender", "남자")
                .claim("job", "배우")
                .compact();
        httpServletResponse.addHeader("Token", jwt); //응답의 header에 "Token" : jwt 넣기
        return "JWT set Successfully";
    }

    //클라이언트에서 보낸 jwt 토큰 해석
    @GetMapping("/show-token")
    public String showToken(@RequestHeader("Token") String token){
        Claims claims = Jwts.parser()
                .parseClaimsJwt(token)
                .getBody();

        String user = (String) claims.get("user");
        String gender = (String) claims.get("gender");
        String job = (String) claims.get("job");

        return String.format("안녕하세요, 직업: %s 성별: %s인 %s 입니다.", job, gender, user);
    }

}
