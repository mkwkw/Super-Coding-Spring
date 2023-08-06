package com.github.supercoding.service;

import com.github.supercoding.repository.airline_ticket.AirlineTicket;
import com.github.supercoding.repository.airline_ticket.AirlineTicketJpaRepository;
import com.github.supercoding.repository.flight.Flight;
import com.github.supercoding.repository.passenger.Passenger;
import com.github.supercoding.repository.passenger.PassengerJpaRepository;
import com.github.supercoding.repository.reservations.ReservationJpaRepository;
import com.github.supercoding.repository.users.UserEntity;
import com.github.supercoding.repository.users.UserJpaRepository;
import com.github.supercoding.service.exception.InvalidValueException;
import com.github.supercoding.service.exception.NotFoundException;
import com.github.supercoding.web.dto.airline.ReservationRequest;
import com.github.supercoding.web.dto.airline.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
class AirReservationServiceUnitTest {

    //AirReservationService를 테스트하려면 여러 의존성이 필요함.
    //@Mock으로 가짜로 가져다 쓸 repository들을 만들고
    //@InjectMocks로 @Mock으로 만든 가짜 repository를 주입할 AirReservation 객체를 생성
    //@BeforeEach로 사전 설정

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private AirlineTicketJpaRepository airlineTicketJpaRepository;

    @Mock
    private PassengerJpaRepository passengerJpaRepository;

    @Mock
    private ReservationJpaRepository reservationJpaRepository;

    @InjectMocks
    private AirReservationService airReservationService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("airlineTicket에 해당하는 user와 항공권들이 모두 있어서 성공하는 경우")
    @Test
    void findUserFavoritePlaceTicketsCase1() {

        //given
        Integer userId = 5;
        String likePlace = "파리";
        String ticketType = "왕복";

        UserEntity userEntity = UserEntity.builder()
                .userId(userId)
                .likeTravelPlace(likePlace)
                .userName("name1")
                .phoneNum("1234")
                .build();

        List<AirlineTicket> airlineTickets = Arrays.asList(
                AirlineTicket.builder()
                        .ticketId(1)
                        .arrivalLocation(likePlace)
                        .ticketType(ticketType)
                        .build(),
                AirlineTicket.builder()
                        .ticketId(2)
                        .arrivalLocation(likePlace)
                        .ticketType(ticketType)
                        .build(),
                AirlineTicket.builder()
                        .ticketId(3)
                        .arrivalLocation(likePlace)
                        .ticketType(ticketType)
                        .build(),
                AirlineTicket.builder()
                        .ticketId(4)
                        .arrivalLocation(likePlace)
                        .ticketType(ticketType)
                        .build()
        );

        //when
        //userJpaRepository에서 어떤 Id를 찾아도 위와 같은 userEntity를 리턴하도록 해라
        when(userJpaRepository.findById(any())).thenReturn(Optional.of(userEntity));
        //airlineTicketJpaRepository에서 이 메소드를 실행시키면 이 리스트를 리턴하도록 해라.
        when(airlineTicketJpaRepository.findAirlineTicketsByArrivalLocationAndTicketType(likePlace, ticketType))
                .thenReturn(airlineTickets);

        //then
        List<Ticket> tickets = airReservationService.findUserFavoritePlaceTickets(userId, ticketType);
        log.info("tickets: " + tickets);
        //불러온 티켓의 목적지가 likeplace와 같은지 아닌지 확인
        assertTrue(tickets.stream().map(Ticket::getArrival).allMatch((arrival) -> arrival.equals(likePlace)));
    }

    @DisplayName("ticket type이 왕복이나 편도가 아닌 경우 exception 발생해야함.")
    @Test
    void findUserFavoritePlaceTicketsCase2() {

        //given
        Integer userId = 5;
        String likePlace = "파리";
        String ticketType = "왕";

        UserEntity userEntity = UserEntity.builder()
                .userId(userId)
                .likeTravelPlace(likePlace)
                .userName("name1")
                .phoneNum("1234")
                .build();

        List<AirlineTicket> airlineTickets = Arrays.asList(
                AirlineTicket.builder()
                        .ticketId(1)
                        .arrivalLocation(likePlace)
                        .ticketType(ticketType)
                        .build(),
                AirlineTicket.builder()
                        .ticketId(2)
                        .arrivalLocation(likePlace)
                        .ticketType(ticketType)
                        .build(),
                AirlineTicket.builder()
                        .ticketId(3)
                        .arrivalLocation(likePlace)
                        .ticketType(ticketType)
                        .build(),
                AirlineTicket.builder()
                        .ticketId(4)
                        .arrivalLocation(likePlace)
                        .ticketType(ticketType)
                        .build()
        );

        //when
        //userJpaRepository에서 어떤 Id를 찾아도 위와 같은 userEntity를 리턴하도록 해라
        when(userJpaRepository.findById(any())).thenReturn(Optional.of(userEntity));
        //airlineTicketJpaRepository에서 이 메소드를 실행시키면 이 리스트를 리턴하도록 해라.
        when(airlineTicketJpaRepository.findAirlineTicketsByArrivalLocationAndTicketType(likePlace, ticketType))
                .thenReturn(airlineTickets);

        //then
        assertThrows(InvalidValueException.class,
                () -> airReservationService.findUserFavoritePlaceTickets(userId, ticketType));
    }

    @DisplayName("user를 찾을 수 없는 경우, exception 발생해야함.")
    @Test
    void findUserFavoritePlaceTicketsCase3() {

        //given
        Integer userId = 5;
        String likePlace = "파리";
        String ticketType = "왕복";

        UserEntity userEntity = null;

        List<AirlineTicket> airlineTickets = Arrays.asList(
                AirlineTicket.builder()
                        .ticketId(1)
                        .arrivalLocation(likePlace)
                        .ticketType(ticketType)
                        .build(),
                AirlineTicket.builder()
                        .ticketId(2)
                        .arrivalLocation(likePlace)
                        .ticketType(ticketType)
                        .build(),
                AirlineTicket.builder()
                        .ticketId(3)
                        .arrivalLocation(likePlace)
                        .ticketType(ticketType)
                        .build(),
                AirlineTicket.builder()
                        .ticketId(4)
                        .arrivalLocation(likePlace)
                        .ticketType(ticketType)
                        .build()
        );

        //when
        //userJpaRepository에서 어떤 Id를 찾아도 위와 같은 userEntity를 리턴하도록 해라
        when(userJpaRepository.findById(any())).thenReturn(Optional.ofNullable(userEntity));
        //airlineTicketJpaRepository에서 이 메소드를 실행시키면 이 리스트를 리턴하도록 해라.
        when(airlineTicketJpaRepository.findAirlineTicketsByArrivalLocationAndTicketType(likePlace, ticketType))
                .thenReturn(airlineTickets);

        //then
        assertThrows(NotFoundException.class,
                () -> airReservationService.findUserFavoritePlaceTickets(userId, ticketType));
    }

    @DisplayName("userId로 passenger를 못 찾는 경우")
    @Test
    void findPassengerByUserId(){
        //given
        Integer userId = 5;
        Integer ticketId = 5;
        ReservationRequest reservationRequest = new ReservationRequest(userId, ticketId);

        AirlineTicket airlineTicket = AirlineTicket.builder()
                .ticketType("왕복")
                .arrivalLocation("런던")
                .departureLocation("서울")
                .departureAt(LocalDateTime.now())
                .returnAt(LocalDateTime.now())
                .ticketId(ticketId)
                .tax(0.0)
                .totalPrice(10.0)
                .build();

        List<Flight> flightList = Arrays.asList(
                new Flight(1, airlineTicket, LocalDateTime.now(), LocalDateTime.now(), "서울", "파리", 20000.0, 5000.0),
                new Flight(2, airlineTicket, LocalDateTime.now(), LocalDateTime.now(), "서울", "파리", 20000.0, 5000.0),
                new Flight(3, airlineTicket, LocalDateTime.now(), LocalDateTime.now(), "서울", "파리", 20000.0, 5000.0),
                new Flight(4, airlineTicket, LocalDateTime.now(), LocalDateTime.now(), "서울", "파리", 20000.0, 5000.0),
                new Flight(5, airlineTicket, LocalDateTime.now(), LocalDateTime.now(), "서울", "파리", 20000.0, 5000.0),
                new Flight(6, airlineTicket, LocalDateTime.now(), LocalDateTime.now(), "서울", "파리", 20000.0, 5000.0)
        );

        airlineTicket.setFlights(flightList);

        Passenger passenger = null;

        //when
        when(airlineTicketJpaRepository.findById(any())).thenReturn(Optional.ofNullable(airlineTicket));
        when(passengerJpaRepository.findPassengerByUserUserId(userId)).thenReturn(Optional.ofNullable(passenger));
        when(reservationJpaRepository.save(any())).thenReturn(null);

        //then
        assertThrows(NotFoundException.class, () -> airReservationService.makeReservation(reservationRequest));
    }

}