package com.zhour.parser;

import com.zhour.models.LogoutModel;
import com.zhour.models.Model;

import org.json.JSONObject;

/**
 * Created by madhu on 30-Jul-17.
 */

public class LogoutParser implements Parser<Model> {
    @Override
    public Model parse(String s) {

        LogoutModel logoutModel = new LogoutModel();

        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("IsError"))
                logoutModel.setError(jsonObject.optBoolean("IsError"));
            if (jsonObject.has("Message"))
                logoutModel.setMessage(jsonObject.optString("Message"));
            if (jsonObject.has("Output")) {
                logoutModel.setOutput(jsonObject.optString("Output"));
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


        return logoutModel;
    }
}
