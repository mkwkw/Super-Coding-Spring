package com.github.supercoding.web.controller.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
