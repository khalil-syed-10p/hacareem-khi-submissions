package com.hacareem.finaldestination.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by waqas on 4/29/17.
 */
@Data
public class RideDTO {
    private String userId;
    private String rideId;
    private Timestamp pickUpTime;
    private RideLocationDTO pickup;
    private RideLocationDTO dropoff;
}
