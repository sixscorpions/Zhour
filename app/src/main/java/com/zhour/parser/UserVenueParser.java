package com.zhour.parser;

import com.zhour.models.Model;
import com.zhour.models.UserVenueResponseModel;
import com.zhour.models.VenueModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by madhu on 14-Dec-17.
 */

public class UserVenueParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        UserVenueResponseModel userVenueResponseModel = new UserVenueResponseModel();

        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("IsError"))
                userVenueResponseModel.setError(jsonObject.optBoolean("IsError"));
            if (jsonObject.has("Message"))
                userVenueResponseModel.setMessage(jsonObject.optString("Message"));


            ArrayList<VenueModel> userVenuesList = new ArrayList<>();
            if (jsonObject.has("Output")) {
                JSONArray jsonArray = jsonObject.optJSONArray("Output");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                    VenueModel venueModel = new VenueModel();
                    venueModel.setResidentname(jsonObject1.optString("residentname"));
                    venueModel.setAppartmentname(jsonObject1.optString("appartmentname"));
                    venueModel.setFlat(jsonObject1.optString("flat"));
                    userVenuesList.add(venueModel);
                }
                userVenueResponseModel.setVenuesModels(userVenuesList);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userVenueResponseModel;
    }
}
