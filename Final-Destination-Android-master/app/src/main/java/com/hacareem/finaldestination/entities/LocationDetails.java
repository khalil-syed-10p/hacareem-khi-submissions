package com.hacareem.finaldestination.entities;

import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hacareem.finaldestination.entities.base.AddressableEntity;
import com.hacareem.finaldestination.entities.base.WebServiceResponse;
import com.hacareem.finaldestination.utilities.JsonUtility;

/**
 * Created on 4/30/17.
 */

public class LocationDetails implements WebServiceResponse, AddressableEntity{

    String display;
    float latitude;
    float longitude;
    String geoHash;

    @Override
    public void loadJson(JsonElement jsonElement) {
        if(JsonUtility.isJsonElementNull(jsonElement)) {
            return;
        }

        JsonObject rootObject = jsonElement.getAsJsonObject();
        display = JsonUtility.getString(rootObject, "display");
        latitude = (float) JsonUtility.getDouble(rootObject, "latitude");
        longitude = (float) JsonUtility.getDouble(rootObject, "longitude");
        geoHash = JsonUtility.getString(rootObject, "geohash");
    }

    public String getAddress() {
        return display;
    }
}
