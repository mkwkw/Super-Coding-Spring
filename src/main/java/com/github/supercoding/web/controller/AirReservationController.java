package com.github.supercoding.web.controller;

import com.github.supercoding.service.AirReservationService;
import com.github.supercoding.service.exception.InvalidValueException;
import com.github.supercoding.service.exception.NotAcceptException;
import com.github.supercoding.service.exception.NotFoundException;
import com.github.supercoding.web.dto.airline.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/air-reservation")
@RequiredArgsConstructor
@Slf4j
public class AirReservationController {

    private final AirReservationService airReservationService;

    @ApiOperation("userId와 ticketType으로 사용자의 선호 목적지에 해당하는 항공편 찾기")
    @GetMapping("/tickets")
    public TicketResponse findAirlineTickets(@ApiParam(name = "사용자 id", value = "user id", example = "123")@RequestParam("user-Id") Integer userId,
                                             @ApiParam(name = "항공권 타입", value = "airline ticket type", example = "왕복, 편도 중 하나")@RequestParam("airline-ticket-type") String ticketType ){

            List<Ticket> tickets = airReservationService.findUserFavoritePlaceTickets(userId, ticketType);
            return new TicketResponse(tickets);

        //NotAcceptException, NotFoundException 발생하면 ExceptionControllerAdvice가 처리할 것이다!
    }

    @ApiOperation("User와 Ticket ID로 예약 진행")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reservations")
    public ReservationResult makeReservation(@RequestBody ReservationRequest reservationRequest) {

        return airReservationService.makeReservation(reservationRequest);

        //NotAcceptException, NotFoundException 발생하면 ExceptionControllerAdvice가 처리할 것이다!
    }

    @ApiOperation("userId의 예약한 항공편과 수수료 총합")
    @GetMapping("/users-sum-price")
    public Double findUserFlightPrice(
            @ApiParam(name = "user-Id", value = "유저 ID", example = "1") @RequestParam("user-id") Integer userId
    ){
        Double sum = airReservationService.findUserFlightSumPrice(userId);
        return sum;
    }

    @ApiOperation("user id, ticket id에 해당하는 항공권 결제")
    @PostMapping("/payments")
    public String payTickets(@RequestBody PaymentRequest paymentRequest){
        log.info("결제 성공한 건수만 출력");
        return "요청하신 결제 중 "+airReservationService.payTickets(paymentRequest)+"건 진행완료 되었습니다.";
    }

    @ApiOperation("항공권의 타입에 따라 달라지는 Pageable한 API")
    @GetMapping("/flight-pageable")
    public Page<FlightInfo> findFlightWithTicketType(@RequestParam("type") String ticketType, Pageable pageable){
        return airReservationService.findFlightWithTicketTypeAndPageable(ticketType, pageable);
    }
}
