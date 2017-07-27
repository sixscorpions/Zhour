package com.zhour.parser;

import com.zhour.models.AuthenticateUserModel;
import com.zhour.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by shankar on 7/17/2017.
 */

public class AuthenticateUserParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        AuthenticateUserModel mAuthenticateUserModel = new AuthenticateUserModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("IsError"))
                mAuthenticateUserModel.setError(jsonObject.optBoolean("IsError"));
            if (jsonObject.has("Message"))
                mAuthenticateUserModel.setMessage(jsonObject.optString("Message"));
            if (jsonObject.has("Output")) {
                JSONArray jsonArray = jsonObject.optJSONArray("Output");
                JSONObject dataObject = jsonArray.optJSONObject(0);
                mAuthenticateUserModel.setUserid(dataObject.optString("userid"));
                mAuthenticateUserModel.setUsername(dataObject.optString("username"));
                mAuthenticateUserModel.setContactnumber(dataObject.optString("contactnumber"));
                mAuthenticateUserModel.setLastlogin(dataObject.optString("lastlogin"));
                mAuthenticateUserModel.setSesid(dataObject.optString("sesid"));
                mAuthenticateUserModel.setToken(dataObject.optString("token"));

                JSONArray jsonDataArray = dataObject.optJSONArray("communities");
                JSONObject communityObject = jsonDataArray.optJSONObject(0);

                mAuthenticateUserModel.setCommunityid(communityObject.optString("communityid"));
                mAuthenticateUserModel.setCommunityname(communityObject.optString("communityname"));
                mAuthenticateUserModel.setResidentid(communityObject.optString("residentid"));
                mAuthenticateUserModel.setRolename(communityObject.optString("rolename"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mAuthenticateUserModel;
    }
}