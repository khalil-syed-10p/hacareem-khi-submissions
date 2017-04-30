package com.hacareem.finaldestination.webservice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created on 4/6/17.
 */

public interface GooglePlaceSearchService {

    @GET("https://maps.googleapis.com/maps/api/place/autocomplete/json")
    Call<String> searchForPlaces(@Query("input") String text, @Query("key") String key);
}
