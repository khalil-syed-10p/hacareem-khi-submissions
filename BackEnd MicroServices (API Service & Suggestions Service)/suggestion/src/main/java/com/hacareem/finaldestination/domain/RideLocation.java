package com.hacareem.finaldestination.domain;

import lombok.Data;

/**
 * Created by waqas on 4/30/17.
 */
@Data
public class RideLocation {
    private long id;
    private String display;
    private float latitude;
    private float longitude;
    private String geohash;

    @Override
    public int hashCode() {
        return geohash.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof RideLocation && ((RideLocation) obj).geohash.equals(geohash);
    }
}
