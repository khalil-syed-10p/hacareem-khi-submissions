package com.hacareem.finaldestination.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created on 4/10/17.
 */

public final class DeviceUtility {

    public static boolean isInternetConnectionAvailable (Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo ();

        return (netInfo != null) && netInfo.isAvailable() && netInfo.isConnectedOrConnecting();

    }
}
