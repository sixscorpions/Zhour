package com.zhour.parser;

import com.zhour.models.EventInviteSuccessModel;
import com.zhour.models.Model;

import org.json.JSONObject;

/**
 * Created by shankar on 7/17/2017.
 */

public class EventInviteSuccessParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        EventInviteSuccessModel mEventInviteSuccessModel = new EventInviteSuccessModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("IsError"))
                mEventInviteSuccessModel.setError(jsonObject.optBoolean("IsError"));
            if (jsonObject.has("Message"))
                mEventInviteSuccessModel.setMessage(jsonObject.optString("Message"));
            if (jsonObject.has("Output"))
                mEventInviteSuccessModel.setOutput(jsonObject.optString("Output"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mEventInviteSuccessModel;
    }
}