package com.hacareem.finaldestination.helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.hacareem.finaldestination.BuildConfig;
import com.hacareem.finaldestination.R;
import com.hacareem.finaldestination.interfaces.LocationAvailableListener;
import com.hacareem.finaldestination.interfaces.LocationPreReqProvider;
import com.hacareem.finaldestination.services.GeoFenceIntentService;
import com.hacareem.finaldestination.services.ReverseGeoCodeService;

import java.util.ArrayList;

/**
 * Created on 4/29/17.
 */

public final class LocationHelper implements LocationListener, ResultCallback<Status> {

    public static final int REQUEST_CHECK_SETTINGS = 1001;
    private static final int PERMISSION_REQUEST_CODE = 999;

    LocationAvailableListener locationListener;
    LocationPreReqProvider preReqProvider;
    PendingIntent geofencePendingIntent;

    boolean fetchingLocation;
    Location lastKnowLocation;


    public LocationHelper(LocationAvailableListener locationListener, LocationPreReqProvider preReqProvider) {
        this.locationListener = locationListener;
        this.preReqProvider = preReqProvider;
    }

    //region Activity Methods
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != REQUEST_CHECK_SETTINGS) {
            return;
        }

        if (resultCode == Activity.RESULT_CANCELED) {
            showLocationSettingsAlert();
            return;
        }

        if (resultCode == Activity.RESULT_OK) {
            resumeWork();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if ((grantResults.length < 2)
                || (grantResults[0] != 0)
                || (grantResults[1] != 0)) {

            showLocationSettingsAlert();
            return;
        }
        resumeWork();
    }
    //endregion

    private void resumeWork() {
        if(fetchingLocation) {
            fetchLastLocation();
            return;
        }
        setupGeoFence(lastKnowLocation);
    }

    //region Location Methods
    private void onLocationAvailable(Location location) {
        this.lastKnowLocation = location;
        if (locationListener == null) {
            return;
        }

        locationListener.onLocationAvailable(location);
    }

    public void fetchLocationIfPermitted() {
        fetchingLocation = true;
        if (!hasLocationPermission()) {
            return;
        }

        fetchLastLocation();
    }

    private void fetchLastLocation() {
        try {
            Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(preReqProvider.getGoogleApiClient());
            if (currentLocation == null) {
                requestLocationUpdates();
                return;
            }
            onLocationAvailable(currentLocation);
        } catch (SecurityException ex) {
            ex.printStackTrace();
            showLocationSettingsAlert();
        }
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(preReqProvider.getGoogleApiClient(), this);
    }

    @SuppressWarnings("MissingPermission")
    private void requestLocationUpdates() {
        final LocationRequest locationRequest = getLocationRequest();


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(preReqProvider.getGoogleApiClient(),
                        builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(LocationSettingsResult locationResult) {
                Status status = locationResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        LocationServices.FusedLocationApi.requestLocationUpdates(
                                preReqProvider.getGoogleApiClient(), locationRequest, LocationHelper.this);
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        showLocationSettingsAlert();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(
                                    preReqProvider.getActivity(),
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });

    }

    @NonNull
    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
    //endregion

    //region Location Permission methods
    @SuppressLint("NewApi")
    private boolean hasLocationPermission() {

        Activity activity = preReqProvider.getActivity();

        if ((ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    && !activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                showLocationSettingsAlert();
                return false;
            }
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private void showLocationSettingsAlert() {
        final Context context = preReqProvider.getActivity();
        new AlertDialog.Builder(context)
                .setMessage(R.string.error_location_required)
                .setPositiveButton("GO TO SETTINGS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        context.startActivity(
                                new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }
    //endregion

    //region Location Service Callback
    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            return;
        }

        onLocationAvailable(location);
    }
    //endregion

    //region Geofence Methods
    public void setupGeoFence(Location location) {
        this.fetchingLocation = false;
        this.lastKnowLocation = location;
        if (!hasLocationPermission()) {
            return;
        }
        try {
            LocationServices.GeofencingApi.addGeofences(
                    preReqProvider.getGoogleApiClient(),
                    getGeofencingRequest(location),
                    getGeofencePendingIntent()
            ).setResultCallback(this);
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }

    private Geofence getGeoFence(Location location) {
        return new Geofence.Builder()

                .setRequestId("Geofence-hacareem")
                .setCircularRegion(
                        location.getLatitude(),
                        location.getLongitude(),
                        200
                )
                .setExpirationDuration(-1)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
    }

    private GeofencingRequest getGeofencingRequest(Location location) {
        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(getGeoFence(location))
                .build();
    }

    private PendingIntent getGeofencePendingIntent() {

        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(preReqProvider.getActivity(), GeoFenceIntentService.class);
        return PendingIntent.getService(preReqProvider.getActivity(), 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }
    //endregion

    //region Geofence ResultCallback
    @Override
    public void onResult(@NonNull Status status) {

    }
    //endregion

    public void reverseGeoCodeAddress(Location location, ResultReceiver resultReceiver) {
        if(!preReqProvider.getGoogleApiClient().isConnected()) {
            return;
        }
        Intent intent = new Intent(preReqProvider.getActivity(), ReverseGeoCodeService.class);
        intent.putExtra(ReverseGeoCodeService.KEY_RESULT_RECEIVER_EXTRA, resultReceiver);
        intent.putExtra(ReverseGeoCodeService.KEY_LOCATION_EXTRA, location);
        preReqProvider.getActivity().startService(intent);
    }
}
