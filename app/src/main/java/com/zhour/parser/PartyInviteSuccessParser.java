package com.zhour.parser;

import com.zhour.models.Model;
import com.zhour.models.PartyInviteSuccessModel;

import org.json.JSONObject;

/**
 * Created by shankar on 7/17/2017.
 */

public class PartyInviteSuccessParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        PartyInviteSuccessModel mPartyInviteSuccessModel = new PartyInviteSuccessModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("IsError"))
                mPartyInviteSuccessModel.setError(jsonObject.optBoolean("IsError"));
            if (jsonObject.has("Message"))
                mPartyInviteSuccessModel.setMessage(jsonObject.optString("Message"));
            if (jsonObject.has("Output"))
                mPartyInviteSuccessModel.setOutput(jsonObject.optString("Output"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mPartyInviteSuccessModel;
    }
}