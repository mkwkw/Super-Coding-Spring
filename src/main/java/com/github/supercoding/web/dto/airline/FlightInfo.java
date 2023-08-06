package com.github.supercoding.web.dto.airline;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightInfo {

    private Integer flightId;
    private String departAt;
    private String arrivalAt;
    private String departureLocation;
    private String arrivalLocation;

}
