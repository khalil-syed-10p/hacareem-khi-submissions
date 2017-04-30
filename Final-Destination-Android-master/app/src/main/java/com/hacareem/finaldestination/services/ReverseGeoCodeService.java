package com.hacareem.finaldestination.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hacareem.finaldestination.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created on 4/30/17.
 */

public class ReverseGeoCodeService extends IntentService {

    public static final String KEY_LOCATION_EXTRA = "Location";
    public static final String KEY_RESULT_RECEIVER_EXTRA = "ResultReceiver";
    public static final String KEY_ADDRESS_DATA = "Address";

    public ReverseGeoCodeService() {
        super("ReverseGeoCodeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if(intent == null) {
            return;
        }
        Location location = intent.getParcelableExtra(KEY_LOCATION_EXTRA);

        if(location == null) {
            return;
        }

        ResultReceiver resultReceiver = intent.getParcelableExtra(KEY_RESULT_RECEIVER_EXTRA);

        List<Address> addressList = null;
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        deliverResult(getAddress(addressList), resultReceiver);
    }

    private String getAddress(List<Address> addressList) {
        if ((addressList == null) || addressList.isEmpty()) {
            return getString(R.string.error_address);
        }
        Address address = addressList.get(0);
        Collection<String> addressFragments = new ArrayList<String>();
        //Deliberately ignored the last address fragment
        for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
            addressFragments.add(address.getAddressLine(i));
        }
        return TextUtils.join(",", addressFragments);
    }

    private void deliverResult(String address, ResultReceiver resultReceiver) {
        if(resultReceiver == null) {
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString(KEY_ADDRESS_DATA, address);
        resultReceiver.send(0, bundle);
    }
}
