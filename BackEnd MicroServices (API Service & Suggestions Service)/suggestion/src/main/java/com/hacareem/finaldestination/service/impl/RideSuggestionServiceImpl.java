package com.hacareem.finaldestination.service.impl;

import com.hacareem.finaldestination.domain.*;
import com.hacareem.finaldestination.marshaller.base.Marshaller;
import com.hacareem.finaldestination.repository.base.RideSuggestionRepository;
import com.hacareem.finaldestination.search.params.RideSuggestionParams;
import com.hacareem.finaldestination.service.base.RideSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.transaction.Transactional;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by waqas on 4/30/17.
 */
@Service
public class RideSuggestionServiceImpl implements RideSuggestionService {
    @Autowired
    private RideSuggestionRepository rideSuggestionRepository;
    @Autowired
    private Marshaller marshaller;

    @Override
    @Transactional
    public void updateUserSuggestion(final Ride ride) throws Exception {
        DecimalFormat df = getRoundingFormat();
        float pickUpLatitude = Float.valueOf(df.format(ride.getPickup().getLatitude()));
        float pickUpLongitude = Float.valueOf(df.format(ride.getPickup().getLongitude()));
        final TimeShift shift = TimeShift.decideShift(ride.getPickUpTime());
        RideSuggestion rideSuggestion = get(new RideSuggestionParams().withUserId(ride.getUserId()).withLatitude(pickUpLatitude).withLongitude(pickUpLongitude).withTimestamp(ride.getPickUpTime()));
        if (rideSuggestion == null || rideSuggestion.getShift() != shift.getValue()) {
            rideSuggestion = new RideSuggestion();
            rideSuggestion.setPickupLatitude(pickUpLatitude);
            rideSuggestion.setPickupLongitude(pickUpLongitude);
            rideSuggestion.setShift(shift.getValue());
            rideSuggestion.setUserId(ride.getUserId());
            rideSuggestion.setLocations(marshaller.from(new ArrayList<RideLocation>() {{
                add(ride.getDropoff());
            }}));
            rideSuggestionRepository.save(rideSuggestion);
        } else {
            RideLocations locations = marshaller.to(rideSuggestion.getLocations(), RideLocations.class);
            locations.add(ride.getDropoff());
            rideSuggestion.setLocations(marshaller.from(locations));
            rideSuggestionRepository.update(rideSuggestion);
        }
    }

    @Override
    public RideSuggestion get(final RideSuggestionParams params) {
        DecimalFormat df = getRoundingFormat();
        float pickUpLatitude = Float.valueOf(df.format(params.getLatitude()));
        float pickUpLongitude = Float.valueOf(df.format(params.getLongitude()));
        params.withLatitude(pickUpLatitude);
        params.withLongitude(pickUpLongitude);
        return rideSuggestionRepository.get(params);
    }

    private DecimalFormat getRoundingFormat() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df;
    }
}