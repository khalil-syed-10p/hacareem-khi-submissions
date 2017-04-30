package com.hacareem.finaldestination.controller;

import com.hacareem.finaldestination.domain.Ride;
import com.hacareem.finaldestination.dto.RideDTO;
import com.hacareem.finaldestination.service.base.RideService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by waqas on 4/29/17.
 */
@RestController
@RequestMapping(value = "/ride", consumes = "application/json", produces = "application/json")
public class RideController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RideService rideService;

    @RequestMapping(value = "/complete", method = RequestMethod.POST)
    public void importRide(@RequestBody final RideDTO rideDTO) throws Exception{
        rideService.importRide(modelMapper.map(rideDTO, Ride.class));
    }
}
