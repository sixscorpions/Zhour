package com.zhour.parser;

import com.zhour.models.Model;
import com.zhour.models.NoticeBoardListModel;
import com.zhour.models.NoticeBoardModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shankar on 7/17/2017.
 */

public class NoticeBoardParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        NoticeBoardListModel mNoticeBoardListModel = new NoticeBoardListModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("IsError"))
                mNoticeBoardListModel.setError(jsonObject.optBoolean("IsError"));
            if (jsonObject.has("Message"))
                mNoticeBoardListModel.setMessage(jsonObject.optString("Message"));
            ArrayList<NoticeBoardModel> noticeBoardModels = new ArrayList<>();
            if (jsonObject.has("Output")) {
                JSONArray jsonArray = jsonObject.optJSONArray("Output");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                    NoticeBoardModel noticeBoardModel = new NoticeBoardModel();
                    noticeBoardModel.setNoticeid(jsonObject1.optString("noticeid"));
                    noticeBoardModel.setNoticetitle(jsonObject1.optString("noticetitle"));
                    noticeBoardModel.setNoticedesc(jsonObject1.optString("noticedesc"));
                    noticeBoardModel.setSentby(jsonObject1.optString("sentby"));
                    noticeBoardModel.setSentdate(jsonObject1.optString("sentdate"));
                    noticeBoardModels.add(noticeBoardModel);
                }
                mNoticeBoardListModel.setNoticeBoardModels(noticeBoardModels);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mNoticeBoardListModel;
    }
}