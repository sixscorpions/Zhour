package com.zhour.parser;

import com.zhour.models.AboutUsModel;
import com.zhour.models.AlienCarModel;
import com.zhour.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by shankar on 7/17/2017.
 */

public class AboutUsParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        AboutUsModel mAboutUsModel = new AboutUsModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("API"))
                mAboutUsModel.setAPI(jsonObject.optString("API"));
            if (jsonObject.has("Version"))
                mAboutUsModel.setVersion(jsonObject.optString("Version"));
            if (jsonObject.has("Message"))
                mAboutUsModel.setMessage(jsonObject.optString("Message"));
            if (jsonObject.has("Company"))
                mAboutUsModel.setCompany(jsonObject.optString("Company"));
            if (jsonObject.has("Contact"))
                mAboutUsModel.setContact(jsonObject.optString("Contact"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mAboutUsModel;
    }
}