package com.hacareem.finaldestination.dto;

import lombok.Data;

/**
 * Created by waqas on 4/29/17.
 */
@Data
public class RideLocationDTO {
    private long id;
    private String display;
    private float latitude;
    private float longitude;
    private String geohash;
}
