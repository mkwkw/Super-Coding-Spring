package com.github.supercoding.repository.reservations;

import com.github.supercoding.repository.airline_ticket.AirlineTicket;
import com.github.supercoding.repository.airline_ticket.AirlineTicketJpaRepository;
import com.github.supercoding.repository.passenger.Passenger;
import com.github.supercoding.repository.passenger.PassengerJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.data.relational.core.sql.In;

import javax.swing.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //slice test => Day Layer / Jpa 사용하고 있는 Slice Test
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //h2말고 mysql 쓰겠다.
@Slf4j
class ReservationJpaRepositoryTest {

    //repository는 호출 가능, service는 호출 불가능
    @Autowired
    private ReservationJpaRepository reservationJpaRepository;
    @Autowired
    private PassengerJpaRepository passengerJpaRepository;
    @Autowired
    private AirlineTicketJpaRepository airlineTicketJpaRepository;

    @DisplayName("reservation repository로 항공편 가격과 수수료 검색")
    @Test
    void findFlightPriceAndCharge() {

        //given
        Integer userId = 10;

        //when
        //실제 데이터베이스 데이터 사용해서 가져오기
        List<FlightPriceAndCharge> flightPriceAndCharges = reservationJpaRepository.findFlightPriceAndCharge(userId);

        //then
        log.info("결과 "+flightPriceAndCharges);
    }

    @DisplayName("reservation 예약 진행 - save test")
    @Test
    void saveReservation() {

        //given
        Integer userId = 10;
        Integer ticketId = 5;

        Passenger passenger = passengerJpaRepository.findPassengerByUserUserId(userId).get();
        AirlineTicket airlineTicket = airlineTicketJpaRepository.findById(ticketId).get();

        //when
        //실제 데이터베이스 데이터 사용해서 가져오기
        Reservation reservation = new Reservation(passenger, airlineTicket);
        Reservation res = reservationJpaRepository.save(reservation);

        //then
        log.info("결과 "+res);

        assertEquals(res.getPassenger(), passenger);
        assertEquals(res.getAirlineTicket(), airlineTicket);

    }
}