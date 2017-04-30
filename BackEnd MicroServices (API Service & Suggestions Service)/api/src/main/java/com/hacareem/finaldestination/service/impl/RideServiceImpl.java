package com.hacareem.finaldestination.service.impl;

import com.hacareem.finaldestination.domain.Ride;
import com.hacareem.finaldestination.marshaller.base.Marshaller;
import com.hacareem.finaldestination.pubsub.base.MessagePublisher;
import com.hacareem.finaldestination.repository.RideRepository;
import com.hacareem.finaldestination.service.base.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by waqas on 4/30/17.
 */
@Component
@Service
public class RideServiceImpl implements RideService {
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private MessagePublisher messagePublisher;
    @Autowired
    private Marshaller jsonMarshaller;
    @Autowired
    private ChannelTopic suggestionRequestQueue;

    @Override
    public void importRide(final Ride ride) throws Exception {
        ride.getDropoff().setRide(ride);
        ride.getPickup().setRide(ride);
        rideRepository.save(ride);
        messagePublisher.publish(suggestionRequestQueue, jsonMarshaller.from(ride));
    }
}
