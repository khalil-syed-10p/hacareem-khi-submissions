package com.hacareem.finaldestination.activities;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;

import com.hacareem.finaldestination.R;
import com.hacareem.finaldestination.adapters.LocationRecyclerAdapter;
import com.hacareem.finaldestination.entities.SuggestionsResponse;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hacareem.finaldestination.constants.Constants.MOCK_USER_ID;

/**
 * Created on 4/30/17.
 */

public class SelectLocationActivity extends BaseActivity {

    public static final String PICKUP_LOCATION_EXTRA = "pickup";
    public static final int DEBOUNCE_DELAY = 300;

    AppCompatEditText editTxtSearchLocations;
    RecyclerView locationRecyclerView;

    Timer timerForSearchRequest;
    Location pickupLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        pickupLocation = getIntent().getParcelableExtra(PICKUP_LOCATION_EXTRA);
        initializeEditText();
        initializeRecyclerView();

        if(pickupLocation == null) {
            return;
        }

        fetchSuggestions();
    }

    //region Initialization Methods
    private void initializeEditText() {
        editTxtSearchLocations = (AppCompatEditText) findViewById(R.id.editTxtSearchLocations);

        editTxtSearchLocations.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                scheduleTimerForSearchRequest();
            }
        });
    }

    private void initializeRecyclerView() {
        locationRecyclerView = (RecyclerView) findViewById(R.id.locationsRecyclerView);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    //endregion

    //region Search For Places method
    private void scheduleTimerForSearchRequest() {
        if(timerForSearchRequest != null) {
            timerForSearchRequest.cancel();
        }
        timerForSearchRequest = new Timer();
        timerForSearchRequest.schedule(new TimerTask(){

            public void run() {
                searchForLocations(editTxtSearchLocations.getText().toString());
            }
        }, DEBOUNCE_DELAY);
    }

    private void fetchSuggestions() {
        if(pickupLocation == null) {
            return;
        }

        getServiceFactory().getSuggestionsService().fetchSuggestions(MOCK_USER_ID, (float)pickupLocation.getLatitude(),
                (float)pickupLocation.getLongitude(), System.currentTimeMillis()).enqueue(new Callback<SuggestionsResponse>() {
            @Override
            public void onResponse(Call<SuggestionsResponse> call, Response<SuggestionsResponse> response) {
                if(response == null || response.body() == null) {
                    return;
                }
                onSuggestionsReceived(response.body());
            }

            @Override
            public void onFailure(Call<SuggestionsResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void onSuggestionsReceived(SuggestionsResponse suggestionsResponse) {
        if((suggestionsResponse == null)
                || (suggestionsResponse.getList() == null)
                || suggestionsResponse.getList().isEmpty()) {
            return;
        }


        locationRecyclerView.setAdapter(new LocationRecyclerAdapter(suggestionsResponse.getList(), this));
    }

    private void searchForLocations(String text) {
        if(TextUtils.isEmpty(text)) {
            fetchSuggestions();
            return;
        }
        getServiceFactory().getGooglePlaceSearchService().searchForPlaces(text, getString(R.string.google_maps_key)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    //endregion
}
