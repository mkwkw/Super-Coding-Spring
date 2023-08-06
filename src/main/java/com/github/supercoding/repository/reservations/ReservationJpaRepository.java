package com.github.supercoding.repository.reservations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReservationJpaRepository extends JpaRepository<Reservation, Integer> {

    @Query(value = "SELECT new com.github.supercoding.repository.reservations.FlightPriceAndCharge(f.flightPrice, f.charge) " +
            "FROM Reservation r " +
            "JOIN r.passenger p " +
            "JOIN r.airlineTicket a " +
            "JOIN a.flightList f " +
            "WHERE p.user.userId = :userId ", nativeQuery = true)
    List<FlightPriceAndCharge> findFlightPriceAndCharge(Integer userId);

    Reservation findByPassengerPassengerIdAndAirlineTicketTicketId(Integer passengerId, Integer ticketId);
}
