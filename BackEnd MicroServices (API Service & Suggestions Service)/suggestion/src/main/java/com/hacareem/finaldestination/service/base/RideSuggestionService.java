package com.hacareem.finaldestination.service.base;

import com.hacareem.finaldestination.domain.Ride;
import com.hacareem.finaldestination.domain.RideSuggestion;
import com.hacareem.finaldestination.domain.TimeShift;
import com.hacareem.finaldestination.search.params.RideSuggestionParams;

import java.util.Date;

/**
 * Created by waqas on 4/30/17.
 */
public interface RideSuggestionService {

    void updateUserSuggestion(Ride ride) throws Exception ;
    RideSuggestion get(RideSuggestionParams params);
}
