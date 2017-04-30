package com.hacareem.finaldestination.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hacareem.finaldestination.domain.Ride;
import com.hacareem.finaldestination.repository.RideRepository;
import com.hacareem.finaldestination.service.base.RideService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Created by waqas on 4/30/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RideServiceTest {
    @Autowired
    private RideService rideService;
    @Autowired
    private RideRepository rideRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testRideIsSuccessfullyBeenSaveWithThePickupAndDropoffLocation() throws Exception {
        Ride ride = objectMapper.readValue("{\"userId\":\"358a71abf8\",\"rideId\":\"4552b7a9c1884cf3c75eec2dc74aa140\",\"pickUpTime\":1472577534,\"pickup\":{\"display\":\"Cosmopolitan - Main E Street - Block 4, Clifton - Karachi\",\"latitude\":24.8042,\"longitude\":67.0329,\"geohash\":\"tkrtj77yej8w\"},\"dropoff\":{\"display\":\"Florida Homes Apartment - Defence Housing Authority -  - Sindh\",\"latitude\":24.797,\"longitude\":67.041,\"geohash\":\"tkrtjd1v1pf4\"}}", Ride.class);
        rideService.importRide(ride);
        Assert.assertEquals(ride.getRideId(), rideRepository.findOne(ride.getRideId()).getRideId());
    }
}
