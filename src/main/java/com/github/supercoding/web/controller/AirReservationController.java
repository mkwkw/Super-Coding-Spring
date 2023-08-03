package com.github.supercoding.web.controller;

import com.github.supercoding.service.AirReservationService;
import com.github.supercoding.service.exception.InvalidValueException;
import com.github.supercoding.service.exception.NotAcceptException;
import com.github.supercoding.service.exception.NotFoundException;
import com.github.supercoding.web.dto.airline.ReservationRequest;
import com.github.supercoding.web.dto.airline.ReservationResult;
import com.github.supercoding.web.dto.airline.Ticket;
import com.github.supercoding.web.dto.airline.TicketResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/tickets")
    public ResponseEntity findAirlineTickets(@RequestParam("user-Id") Integer userId,

                                             @RequestParam("airline-ticket-type") String ticketType ){
        try{
            List<Ticket> tickets = airReservationService.findUserFavoritePlaceTickets(userId, ticketType);
            TicketResponse ticketResponse = new TicketResponse(tickets);
            return new ResponseEntity(ticketResponse, HttpStatus.OK);
        }
        catch (InvalidValueException e){
            log.error("client 요청에 문제가 있어 다음과 같이 출력합니다."+e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (NotFoundException e){
            log.error("client 요청 이후에 문제가 있어 다음과 같이 출력합니다."+e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/reservations")
    public ResponseEntity makeReservation(@RequestBody ReservationRequest reservationRequest) {
        try {
            ReservationResult reservationResult = airReservationService.makeReservation(reservationRequest);
            return new ResponseEntity(reservationResult, HttpStatus.CREATED);
        }
        catch (NotFoundException e){
            log.error("client 요청 이후에 문제가 있어 다음과 같이 출력합니다."+e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (NotAcceptException e){
            log.error("client 요청이 거부됩니다."+e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
