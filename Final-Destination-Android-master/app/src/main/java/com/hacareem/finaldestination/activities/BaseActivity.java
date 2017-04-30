package com.hacareem.finaldestination.activities;

import android.support.v7.app.AppCompatActivity;

import com.hacareem.finaldestination.FinalDestinationApp;
import com.hacareem.finaldestination.webservice.ServiceFactory;

/**
 * Created on 4/30/17.
 */

public class BaseActivity extends AppCompatActivity {

    protected ServiceFactory getServiceFactory() {
        return FinalDestinationApp.getInstance().getServiceFactory();
    }
}
