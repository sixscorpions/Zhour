package com.zhour.parser;

import com.zhour.models.AlienCarListModel;
import com.zhour.models.AlienCarModel;
import com.zhour.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shankar on 7/17/2017.
 */

public class AlienCarParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        AlienCarListModel mAlienCarListModel = new AlienCarListModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("IsError"))
                mAlienCarListModel.setIsError(jsonObject.optBoolean("IsError"));
            if (jsonObject.has("Message"))
                mAlienCarListModel.setMessage(jsonObject.optString("Message"));
            ArrayList<AlienCarModel> alienCarModels = new ArrayList<>();
            if (jsonObject.has("Output")) {
                JSONArray jsonArray = jsonObject.optJSONArray("Output");
                for (int i = 0; i < jsonArray.length(); i++) {
                    AlienCarModel mAlienCarModel = new AlienCarModel();
                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);
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
                    alienCarModels.add(mAlienCarModel);
                }
            }
            mAlienCarListModel.setAlienCarModels(alienCarModels);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mAlienCarListModel;
    }
}