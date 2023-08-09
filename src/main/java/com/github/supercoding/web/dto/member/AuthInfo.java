package com.github.supercoding.web.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor(staticName = "of")
public class AuthInfo {
    private Long memberId; //멤버 아이디 식별자를 담는 역할
    private Long memberGenerationId;
}
