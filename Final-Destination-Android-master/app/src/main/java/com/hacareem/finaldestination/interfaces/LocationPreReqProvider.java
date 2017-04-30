package com.hacareem.finaldestination.interfaces;

import android.app.Activity;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created on 4/29/17.
 */

public interface LocationPreReqProvider {

    Activity getActivity();
    GoogleApiClient getGoogleApiClient();
}
