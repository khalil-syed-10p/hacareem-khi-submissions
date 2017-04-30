package com.hacareem.finaldestination.service.base;

import com.hacareem.finaldestination.domain.Ride;
import org.springframework.stereotype.Service;

/**
 * Created by waqas on 4/30/17.
 */
@Service
public interface RideService{
    void importRide(Ride ride) throws Exception ;
}
