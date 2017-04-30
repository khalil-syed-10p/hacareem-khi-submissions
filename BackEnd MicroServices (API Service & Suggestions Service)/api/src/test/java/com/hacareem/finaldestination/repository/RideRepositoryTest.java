package com.hacareem.finaldestination.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hacareem.finaldestination.domain.Ride;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Created by waqas on 4/29/17.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RideRepositoryTest {
    @Autowired
    private RideRepository rideRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testRideIsSuccessfullyBeenSaveWithThePickupAndDropoffLocation() throws IOException {
        Ride ride = objectMapper.readValue("{\"userId\":\"358a71abf8\",\"rideId\":\"4552b7a9c1884cf3c75eec2dc74aa140\",\"pickUpTime\":1472577534,\"pickup\":{\"display\":\"Cosmopolitan - Main E Street - Block 4, Clifton - Karachi\",\"latitude\":24.8042,\"longitude\":67.0329,\"geohash\":\"tkrtj77yej8w\"},\"dropoff\":{\"display\":\"Florida Homes Apartment - Defence Housing Authority -  - Sindh\",\"latitude\":24.797,\"longitude\":67.041,\"geohash\":\"tkrtjd1v1pf4\"}}", Ride.class);
        rideRepository.save(ride);
        Assert.assertEquals(ride.getRideId(), rideRepository.findOne(ride.getRideId()).getRideId());
    }
}