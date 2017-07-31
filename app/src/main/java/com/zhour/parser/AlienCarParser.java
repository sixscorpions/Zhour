package com.zhour.parser;

import com.zhour.models.AlienCarModel;
import com.zhour.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by shankar on 7/17/2017.
 */

public class AlienCarParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        AlienCarModel mAlienCarModel = new AlienCarModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("IsError"))
                mAlienCarModel.setIsError(jsonObject.optBoolean("IsError"));
            if (jsonObject.has("Message"))
                mAlienCarModel.setMessage(jsonObject.optString("Message"));

            if (jsonObject.has("Output")) {
                JSONArray jsonArray = jsonObject.optJSONArray("Output");
                JSONObject jsonObject1 = jsonArray.optJSONObject(0);
                mAlienCarModel.setVehid(jsonObject1.optString("vehid"));
                mAlienCarModel.setResidentid(jsonObject1.optString("residentid"));
                mAlienCarModel.setResidentname(jsonObject1.optString("residentname"));
                mAlienCarModel.setVehtypeid(jsonObject1.optString("vehtypeid"));
                mAlienCarModel.setVehtype(jsonObject1.optString("vehtype"));
                mAlienCarModel.setVehnumber(jsonObject1.optString("vehnumber"));
                mAlienCarModel.setContact1(jsonObject1.optString("contact1"));
                mAlienCarModel.setContact2(jsonObject1.optString("contact2"));
                mAlienCarModel.setFlatnumber(jsonObject1.optString("flatnumber"));
                mAlienCarModel.setIsvisitorvehicle(jsonObject1.optBoolean("isvisitorvehicle"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mAlienCarModel;
    }
}