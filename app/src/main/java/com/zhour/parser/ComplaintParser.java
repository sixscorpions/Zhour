package com.zhour.parser;

import com.zhour.models.ComplaintModel;
import com.zhour.models.EventInviteSuccessModel;
import com.zhour.models.Model;

import org.json.JSONObject;

/**
 * Created by madhu on 03-Aug-17.
 * Edited by shankar Pilli
 */

public class ComplaintParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        ComplaintModel mComplaintModel = new ComplaintModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("IsError"))
                mComplaintModel.setError(jsonObject.optBoolean("IsError"));
            if (jsonObject.has("Message"))
                mComplaintModel.setMessage(jsonObject.optString("Message"));
            if (jsonObject.has("Output"))
                mComplaintModel.setOutput(jsonObject.optString("Output"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mComplaintModel;
    }
}