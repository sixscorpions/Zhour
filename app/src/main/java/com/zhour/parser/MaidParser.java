package com.zhour.parser;

import com.zhour.models.MaidModel;
import com.zhour.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by shankar on 7/17/2017.
 */

public class MaidParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        MaidModel mMaidModel = new MaidModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("IsError"))
                mMaidModel.setIsError(jsonObject.optBoolean("IsError"));
            if (jsonObject.has("Message"))
                mMaidModel.setMessage(jsonObject.optString("Message"));

            if (jsonObject.has("Output")) {
                JSONArray jsonArray = jsonObject.optJSONArray("Output");
                JSONObject jsonObject1 = jsonArray.optJSONObject(0);
                mMaidModel.setVisitid(jsonObject1.optString("visitid"));
                mMaidModel.setStaffname(jsonObject1.optString("staffname"));
                mMaidModel.setContact1(jsonObject1.optString("contact1"));
                mMaidModel.setContact2(jsonObject1.optString("contact2"));
                mMaidModel.setDeptname(jsonObject1.optString("deptname"));
                mMaidModel.setIntime(jsonObject1.optString("intime"));
                mMaidModel.setOuttime(jsonObject1.optString("outtime"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mMaidModel;
    }
}