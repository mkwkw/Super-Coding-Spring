package com.github.supercoding.web.controller.member;

import com.github.supercoding.service.member.JwtService;
import com.github.supercoding.web.dto.member.AuthInfo;
import com.github.supercoding.web.dto.member.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test/jwt")
public class JWTTestController {

    private final JwtService jwtService;

    @GetMapping("/encode")
    public String jwtEncoding(@RequestParam("id") Long memberId, @RequestParam("idx") Long idx){
        return jwtService.encode(memberId, idx);
    }

    @GetMapping("/decode")
    public Map<String, Long> jwtDecoding(@RequestParam("token") String jwtToken){
        return jwtService.decode(jwtToken);
    }

    @GetMapping
    public void getMemberGeneratedId(@Token AuthInfo authInfo){
        log.info("authinfo={}", authInfo);
        log.info("memberId={}", authInfo.getMemberId());
        log.info("memberIdx={}", authInfo.getMemberGenerationId());
        //로그는 잘 뜨는데 왜 리턴은 안될까
        //return authInfo.getMemberGenerationId().toString();
    }

}
