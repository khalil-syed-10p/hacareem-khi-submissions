package com.hacareem.finaldestination.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by waqas on 4/29/17.
 */
@Data
public class Ride {
    String userId;
    private String rideId;
    private Timestamp pickUpTime;
    private RideLocation pickup;
    private RideLocation dropoff;
}