package com.github.supercoding.service;

import com.github.supercoding.repository.airline_ticket.AirlineTicket;
import com.github.supercoding.repository.airline_ticket.AirlineTicketAndFlightInfo;
import com.github.supercoding.repository.airline_ticket.AirlineTicketJpaRepository;
import com.github.supercoding.repository.airline_ticket.AirlineTicketRepository;
import com.github.supercoding.repository.flight.Flight;
import com.github.supercoding.repository.flight.FlightJpaRepository;
import com.github.supercoding.repository.passenger.Passenger;
import com.github.supercoding.repository.passenger.PassengerJpaRepository;
import com.github.supercoding.repository.passenger.PassengerRepository;
import com.github.supercoding.repository.reservations.FlightPriceAndCharge;
import com.github.supercoding.repository.reservations.Reservation;
import com.github.supercoding.repository.reservations.ReservationJpaRepository;
import com.github.supercoding.repository.reservations.ReservationRepository;
import com.github.supercoding.repository.users.UserEntity;
import com.github.supercoding.repository.users.UserJpaRepository;
import com.github.supercoding.repository.users.UserRepository;
import com.github.supercoding.service.exception.InvalidValueException;
import com.github.supercoding.service.exception.NotAcceptException;
import com.github.supercoding.service.exception.NotFoundException;
import com.github.supercoding.service.mapper.TicketMapper;
import com.github.supercoding.web.dto.airline.ReservationRequest;
import com.github.supercoding.web.dto.airline.ReservationResult;
import com.github.supercoding.web.dto.airline.Ticket;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AirReservationService {

//    private UserRepository userRepository;
//    private AirlineTicketRepository airlineTicketRepository;
//    private PassengerRepository passengerRepository;
//    private ReservationRepository reservationRepository;

    private UserJpaRepository userJpaRepository;
    private AirlineTicketJpaRepository airlineTicketJpaRepository;
    private PassengerJpaRepository passengerJpaRepository;
    private ReservationJpaRepository reservationJpaRepository;
    private FlightJpaRepository flightJpaRepository;

    public AirReservationService(UserJpaRepository userJpaRepository, AirlineTicketJpaRepository airlineTicketJpaRepository, PassengerJpaRepository passengerJpaRepository, ReservationJpaRepository reservationJpaRepository, FlightJpaRepository flightJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.airlineTicketJpaRepository = airlineTicketJpaRepository;
        this.passengerJpaRepository = passengerJpaRepository;
        this.reservationJpaRepository = reservationJpaRepository;
        this.flightJpaRepository = flightJpaRepository;
    }

    //선호하는 여행지 찾기
    public List<Ticket> findUserFavoritePlaceTickets(Integer userId, String ticketType) {
        // 1. 유저를 userId 로 가져와서, 선호하는 여행지 도출
        // 2. 선호하는 여행지와 ticketType으로 AirlineTIcket table 질의 해서 필요한 AirlineTicket 가져오기
        // 3. 이 둘의 정보를 조합해서 Ticket DTO를 만든다.


        Set<String> ticketTypeSet = new HashSet<>(Arrays.asList("편도", "왕복"));
        if(!ticketTypeSet.contains(ticketType)){
            throw new InvalidValueException("해당 ticket type "+ticketType+"은 지원하지 않습니다.");
        }

        //UserEntity userEntity = userRepository.findUserById(userId).orElseThrow(() -> new NotFoundException("해당 유저를 찾을 수 없습니다."));
        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow(() -> new NotFoundException("해당 유저를 찾을 수 없습니다."));
        String likePlace = userEntity.getLikeTravelPlace();

        //List<AirlineTicket> airlineTickets
        //        = airlineTicketRepository.findAllAirlineTicketsWithPlaceAndTicketType(likePlace, ticketType);
        List<AirlineTicket> airlineTickets = airlineTicketJpaRepository.findAirlineTicketsByArrivalLocationAndTicketType(likePlace, ticketType);
        if(airlineTickets.isEmpty()) throw new NotFoundException("해당 likePlace "+"와 ticket type "+ticketType+"에 맞는 티켓이 없습니다.");

        List<Ticket> tickets = airlineTickets.stream().map(TicketMapper.INSTANCE::airlineTicketToTicket).collect(Collectors.toList());
        return tickets;
    }

    //예약 진행하기
    @Transactional(transactionManager = "tm2")
    public ReservationResult makeReservation(ReservationRequest reservationRequest) {

        // 0. userId,airline_ticket_id
        Integer userId = reservationRequest.getUserId();
        Integer airlineTicketId= reservationRequest.getAirlineTicketId();
        AirlineTicket airlineTicket = airlineTicketJpaRepository.findById(airlineTicketId).orElseThrow(()-> new RuntimeException("ticketId에 해당하는 티켓이 없습니다."));
        // 1. Passenger I
        Passenger passenger = passengerJpaRepository.findPassengerByUserUserId(userId)
                                .orElseThrow(()->new NotFoundException("요청하신 userId가 없습니다."));
        Integer passengerId= passenger.getPassengerId();

        // 2. price 등의 정보 불러오기 - airline_ticket과 flight 테이블 Join 이용
        //List<AirlineTicketAndFlightInfo> airlineTicketAndFlightInfos
        //        = airlineTicketRepository.findAllAirLineTicketAndFlightInfo(airlineTicketId);

        //1:N 관계 매핑해준 것에 따라 자동으로 join 됨.
        List<Flight> flightList = airlineTicket.getFlights();

        if(flightList.isEmpty()){
            throw new NotFoundException("ticket Id "+airlineTicketId+"에 해당하는 티켓이 없습니다.");
        }

        // 3. reservation 생성
        Reservation reservation = new Reservation(passenger, airlineTicket);
        Boolean isSuccess = false;
        try{
            //isSuccess = reservationRepository.saveReservation(reservation);
            reservationJpaRepository.save(reservation);
            isSuccess = true;
        } catch (RuntimeException e){
            throw new NotAcceptException("Reservation이 등록되는 과정이 거부되었습니다.");
        }

        List<Integer> prices = flightList.stream().map(Flight::getFlightPrice).map(Double::intValue).collect(Collectors.toList());
        List<Integer> charges = flightList.stream().map(Flight::getCharge).map(Double::intValue).collect(Collectors.toList());
        Integer tax = airlineTicket.getTax().intValue();
        Integer totalPrice = airlineTicket.getTotalPrice().intValue();

        return new ReservationResult(prices, charges, tax, totalPrice, isSuccess);
    }

    public Double findUserFlightSumPrice(Integer userId) {
        //1. flight_price, charge 구하기
        List<FlightPriceAndCharge> flightPriceAndCharges = reservationJpaRepository.findFlightPriceAndCharge(userId);
        //2. 모든 flight_price와 charge의 각각 합을 구하고
        Double flightSum = flightPriceAndCharges.stream().mapToDouble(FlightPriceAndCharge::getFlightPrice).sum();
        Double chargeSum = flightPriceAndCharges.stream().mapToDouble(FlightPriceAndCharge::getCharge).sum();
        //3. 두 개의 합을 다시 구하고 리턴
        return flightSum+chargeSum;
    }
}
