package com.hacareem.finaldestination;

import android.app.Application;

import com.hacareem.finaldestination.webservice.ServiceFactory;

/**
 * Created on 4/30/17.
 */

public class FinalDestinationApp extends Application {

    private static FinalDestinationApp instance;
    private ServiceFactory serviceFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initServiceFactory();
    }

    public void initServiceFactory() {
        if(serviceFactory != null) {
            return;
        }
        serviceFactory = new ServiceFactory();
        serviceFactory.initialize(getApplicationContext());
    }

    public ServiceFactory getServiceFactory() {
        if(serviceFactory == null) {
            initServiceFactory();
        }
        return serviceFactory;
    }

    public static FinalDestinationApp getInstance() {
        return instance;
    }
}
