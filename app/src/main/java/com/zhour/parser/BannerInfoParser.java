package com.zhour.parser;

import com.zhour.models.BannerModel;
import com.zhour.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shankar on 7/17/2017.
 */

public class BannerInfoParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        BannerModel mBannerModel = new BannerModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("IsError"))
                mBannerModel.setError(jsonObject.optBoolean("IsError"));
            if (jsonObject.has("Message"))
                mBannerModel.setMessage(jsonObject.optString("Message"));
            ArrayList<String> strings = new ArrayList<>();
            if (jsonObject.has("Output")) {
                JSONArray jsonArray = jsonObject.optJSONArray("Output");
                JSONObject jsonObject1 = jsonArray.optJSONObject(0);
                int size = jsonObject1.optInt("bancount");

                for (int i = 0; i < size; i++) {
                    strings.add(jsonObject1.optString("banurl" + (i + 1)));
                }
                mBannerModel.setBannerUrls(strings);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mBannerModel;
    }
}