package com.hacareem.finaldestination.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by waqas on 4/29/17.
 */
@RunWith(SpringRunner.class)
public class RideControllerTest {
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new RideController()).build();
    }

    @Test
    public void testRideImportReturns200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ride/import").content("{\"userId\":\"358a71abf8\",\"rideId\":\"4552b7a9c1884cf3c75eec2dc74aa140\",\"pickUpTime\":1472577534,\"pickup\":{\"display\":\"Cosmopolitan - Main E Street - Block 4, Clifton - Karachi\",\"latitude\":24.8042,\"longitude\":67.0329,\"geohash\":\"tkrtj77yej8w\"},\"dropoff\":{\"display\":\"Florida Homes Apartment - Defence Housing Authority -  - Sindh\",\"latitude\":24.797,\"longitude\":67.041,\"geohash\":\"tkrtjd1v1pf4\"}}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("imported successfully"));
    }
}
