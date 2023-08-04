package com.github.supercoding.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest //전체 통합 테스트
@AutoConfigureMockMvc //포스트맨 대신 사용
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //h2말고 mysql 쓸거다.
@Slf4j
class AirReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Find Airline Tickets 성공")
    @Test
    void findAirlineTickets() throws Exception {
        //given
        Integer userId = 5;
        String ticketType = "왕복";

        //when & then
        //전체 URI 넣고 파라미터 넣고, 컨텐트 타입 넣고, 예상 결과값 넣기
        String content = mockMvc.perform(
                get("/v1/api/air-reservation/tickets")
                        .param("user-Id", userId.toString())
                        .param("airline-ticket-type", ticketType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info("결과 "+ content);
    }
}