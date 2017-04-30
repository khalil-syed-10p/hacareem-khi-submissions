package com.hacareem.finaldestination.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * Created on 4/30/17.
 */

public class GeoFenceIntentService extends IntentService {

    public GeoFenceIntentService() {
        super("GeofenceIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            return;
        }
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

    }
}
