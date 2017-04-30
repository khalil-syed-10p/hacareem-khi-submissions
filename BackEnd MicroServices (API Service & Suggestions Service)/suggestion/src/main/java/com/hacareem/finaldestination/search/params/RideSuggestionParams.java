package com.hacareem.finaldestination.search.params;

import lombok.Getter;

import java.util.Date;

/**
 * Created by waqas on 4/30/17.
 */
@Getter
public class RideSuggestionParams {
    private String userId;
    private float latitude;
    private float longitude;
    private Date timestamp;

    public RideSuggestionParams withUserId(final String userId){
        this.userId = userId;
        return this;
    }

    public RideSuggestionParams withLatitude(final float latitude){
        this.latitude = latitude;
        return this;
    }

    public RideSuggestionParams withLongitude(final float longitude){
        this.longitude = longitude;
        return this;
    }

    public RideSuggestionParams withTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
