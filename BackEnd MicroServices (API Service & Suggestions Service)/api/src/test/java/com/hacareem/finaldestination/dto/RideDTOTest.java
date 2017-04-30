package com.hacareem.finaldestination.dto;

import com.hacareem.finaldestination.config.TestConfig;
import com.hacareem.finaldestination.domain.Ride;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by waqas on 4/30/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class RideDTOTest {
    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void whenConvertRideEntityToRideDTO_thenCorrect() {
        Ride ride = new Ride();
        ride.setUserId("abcdef");
        ride.setRideId("acbsadedafdsgfsgagagg");

        RideDTO rideDto = modelMapper.map(ride, RideDTO.class);
        assertEquals(ride.getUserId(), rideDto.getUserId());
        assertEquals(ride.getRideId(), rideDto.getRideId());
    }

    @Test
    public void whenConvertRideDTOToRideEntity_thenCorrect() {
        RideDTO rideDto = new RideDTO();
        rideDto.setUserId("abcdef");
        rideDto.setRideId("acbsadedafdsgfsgagagg");

        Ride ride = modelMapper.map(rideDto, Ride.class);
        assertEquals(rideDto.getRideId(), ride.getRideId());
        assertEquals(rideDto.getUserId(), ride.getUserId());
    }
}
