package com.hacareem.finaldestination.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.hacareem.finaldestination.R;
import com.hacareem.finaldestination.helpers.LocationHelper;
import com.hacareem.finaldestination.interfaces.LocationAvailableListener;
import com.hacareem.finaldestination.interfaces.LocationPreReqProvider;
import com.hacareem.finaldestination.services.ReverseGeoCodeService;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationAvailableListener, LocationPreReqProvider{

    GoogleApiClient googleApiClient;
    GoogleMap googleMap;
    LocationHelper locationHelper;

    TextView txtAddress;
    Button btnRideNow;

    TextView txtAddressDropoff;
    RelativeLayout layoutDropoff;
    RelativeLayout layoutPickup;

    Location pickupLocation;

    //region Activity Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        initializeLocationHelper();
        initializeGoogleApiClient();
        initializeMap();
    }

    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(locationHelper == null) {
            return;
        }

        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(locationHelper == null) {
            return;
        }

        locationHelper.onActivityResult(requestCode, resultCode, data);
    }
    //endregion

    //region Initialization Methods
    private void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initializeGoogleApiClient() {
        if (googleApiClient != null) {
            return;
        }

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void initializeLocationHelper() {
        if(locationHelper == null) {
            locationHelper = new LocationHelper(this, this);
        }
    }

    private void initializeViews() {
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtAddressDropoff = (TextView) findViewById(R.id.txtAddressDropOff);
        layoutDropoff = (RelativeLayout) findViewById(R.id.layoutDropoff);
        layoutPickup = (RelativeLayout) findViewById(R.id.layoutPickup);
        btnRideNow = (Button) findViewById(R.id.btnRideNow);

        txtAddress.setText(R.string.text_loading);
        btnRideNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rideNow();
            }
        });
        layoutDropoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSelectLocationActivity();
            }
        });
    }
    //endregion

    //region Location Available Listener
    public void onLocationAvailable(Location location) {

        this.pickupLocation = location;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));

        locationHelper.reverseGeoCodeAddress(location, new AddressResultReceiver(new Handler()));
    }

    //endregion

    //region Google Maps Callbacks
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnCameraIdleListener(this);
    }


    @Override
    public void onCameraIdle() {
        LatLng target = googleMap.getCameraPosition().target;
        Location location = new Location("");
        location.setLatitude(target.latitude);
        location.setLongitude(target.longitude);

        locationHelper.reverseGeoCodeAddress(location, new AddressResultReceiver(new Handler()));
    }
    //endregion

    //region Google Play Services Callbacks

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(locationHelper == null) {
            return;
        }

        locationHelper.fetchLocationIfPermitted();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    //endregion

    //region Location Pre Requisites Provider
    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }
    //endregion

    private void rideNow() {

        layoutDropoff.setVisibility(View.VISIBLE);
        btnRideNow.setText(R.string.ride_now);
    }

    private void navigateToSelectLocationActivity() {
        Intent intent = new Intent(this, SelectLocationActivity.class);
        intent.putExtra(SelectLocationActivity.PICKUP_LOCATION_EXTRA, pickupLocation);
        startActivity(intent);
    }

    class AddressResultReceiver extends ResultReceiver {

        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            String address = resultData.getString(ReverseGeoCodeService.KEY_ADDRESS_DATA);

            if(txtAddress == null) {
                return;
            }
            txtAddress.setText(address);

        }
    }
}
