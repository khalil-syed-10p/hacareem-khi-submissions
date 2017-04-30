package com.hacareem.finaldestination.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by waqas on 4/29/17.
 */
@Data
@Entity
public class Ride {
    @Column(nullable = false)
    String userId;
    @Id
    @Column(name = "ride_id", unique = true, nullable = false)
    private String rideId;
    @Column(nullable = false)
    private Timestamp pickUpTime;
    @OneToOne(mappedBy = "ride", cascade = CascadeType.ALL)
    private PickupLocation pickup;
    @OneToOne(mappedBy = "ride", cascade = CascadeType.ALL)
    private DropoffLocation dropoff;
}