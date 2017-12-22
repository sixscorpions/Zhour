package com.zhour.parser;

import com.zhour.models.Model;
import com.zhour.models.OtherInvitesModel;
import com.zhour.models.OtherInvitesResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by madhu on 15-Dec-17.
 */

public class OtherInvitesParser implements Parser<Model> {
    @Override
    public Model parse(String s) {

        OtherInvitesResponseModel otherInvitesResponseModel = new OtherInvitesResponseModel();


        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("IsError"))
                otherInvitesResponseModel.setError(jsonObject.optBoolean("IsError"));
            if (jsonObject.has("Message"))
                otherInvitesResponseModel.setMessage(jsonObject.optString("Message"));
            ArrayList<OtherInvitesModel> otherInvitesModels = new ArrayList<>();

            if (jsonObject.has("Output")) {
                JSONArray jsonArray = jsonObject.optJSONArray("Output");
                for (int i = 0; i <jsonArray.length() ; i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    OtherInvitesModel otherInvitesModel = new OtherInvitesModel();
                    otherInvitesModel.setIcon(jsonObject1.optString("icon"));
                    otherInvitesModel.setInvitedesc(jsonObject1.optString("invitedesc"));
                    otherInvitesModel.setInvitedisplayname(jsonObject1.optString("invitedisplayname"));
                    otherInvitesModel.setInviteid(jsonObject1.optString("inviteid"));
                    otherInvitesModel.setMobicon(jsonObject1.optString("mobicon"));
                    otherInvitesModel.setInvitename(jsonObject1.optString("invitename"));
                    otherInvitesModels.add(otherInvitesModel);
                }
                otherInvitesResponseModel.setOtherInvitesModels(otherInvitesModels);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return otherInvitesResponseModel;
    }
}
