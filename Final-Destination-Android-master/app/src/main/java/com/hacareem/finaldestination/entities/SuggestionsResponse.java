package com.hacareem.finaldestination.entities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hacareem.finaldestination.entities.base.WebServiceResponse;
import com.hacareem.finaldestination.utilities.JsonUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 4/30/17.
 */

public class SuggestionsResponse implements WebServiceResponse {


    List<LocationDetails> list;

    @Override
    public void loadJson(JsonElement jsonElement) {

        list = new ArrayList<>();

        if(JsonUtility.isJsonElementNull(jsonElement)) {
            return;
        }

        JsonObject rootObject = jsonElement.getAsJsonObject();
        String jsonString = JsonUtility.getString(rootObject, "locations");
        JsonArray jsonArray = JsonUtility.parseToJsonArray(jsonString);
        for(int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = JsonUtility.getJsonObject(jsonArray, i);
            LocationDetails details = new LocationDetails();
            details.loadJson(jsonObject);
            list.add(details);
        }
    }

    public List<LocationDetails> getList() {
        return list;
    }
}
