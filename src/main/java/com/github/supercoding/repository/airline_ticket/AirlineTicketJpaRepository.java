package com.github.supercoding.repository.airline_ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirlineTicketJpaRepository extends JpaRepository<AirlineTicket, Integer> {
    List<AirlineTicket> findAirlineTicketsByArrivalLocationAndTicketType(String likePlace, String ticketType);
}
