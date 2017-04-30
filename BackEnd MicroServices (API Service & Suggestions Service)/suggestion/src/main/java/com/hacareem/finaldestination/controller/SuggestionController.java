package com.hacareem.finaldestination.controller;

import com.hacareem.finaldestination.domain.RideSuggestion;
import com.hacareem.finaldestination.domain.TimeShift;
import com.hacareem.finaldestination.search.params.RideSuggestionParams;
import com.hacareem.finaldestination.service.base.RideSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by waqas on 4/30/17.
 */
@RestController
@RequestMapping(value = "/suggest", consumes = "application/json", produces = "application/json")
public class SuggestionController {
    @Autowired
    private RideSuggestionService rideSuggestionService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public RideSuggestion getUserSuggestions(@PathVariable String userId, @RequestParam float latitude, @RequestParam float longitude, @RequestParam long timestamp) {
        return rideSuggestionService.get(new RideSuggestionParams().withUserId(userId).withLatitude(latitude).withLongitude(longitude).withTimestamp(new Date(timestamp)));
    }
}
