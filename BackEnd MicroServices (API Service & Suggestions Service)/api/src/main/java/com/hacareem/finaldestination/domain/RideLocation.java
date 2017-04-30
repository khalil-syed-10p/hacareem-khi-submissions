package com.hacareem.finaldestination.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by waqas on 4/29/17.
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class RideLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String display;
    private float latitude;
    private float longitude;
    private String geohash;

    @OneToOne
    @JoinColumn(name = "ride_id", referencedColumnName = "ride_id")
    @JsonIgnore
    private Ride ride;
}
