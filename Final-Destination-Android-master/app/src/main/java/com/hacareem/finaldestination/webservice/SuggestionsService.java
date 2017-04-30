package com.hacareem.finaldestination.webservice;

import com.hacareem.finaldestination.entities.SuggestionsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created on 4/30/17.
 */

public interface SuggestionsService {

    @GET("http://192.168.8.100:8090/suggest/{id}")
    Call<SuggestionsResponse> fetchSuggestions(@Path("id") String userId, @Query("latitude") float lat, @Query("longitude") float lng, @Query("timestamp") long timestamp);
}
