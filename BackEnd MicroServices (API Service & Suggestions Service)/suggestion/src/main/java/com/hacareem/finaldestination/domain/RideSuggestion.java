package com.hacareem.finaldestination.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by waqas on 4/30/17.
 */
@Data
@Entity
public class RideSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userId;
    @Column(columnDefinition = "decimal(6,2)")
    private float pickupLatitude;
    @Column(columnDefinition = "decimal(6,2)")
    private float pickupLongitude;
    @Column(length = 1, columnDefinition = "TINYINT")
    private int shift;
    @Column(length = 1000)
    private String locations;
}
