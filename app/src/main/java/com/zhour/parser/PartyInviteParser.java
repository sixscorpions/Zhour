package com.zhour.parser;

import com.zhour.models.InvitesModel;
import com.zhour.models.Model;
import com.zhour.models.PartyInviteModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by madhu on 30-Jul-17.
 */

public class PartyInviteParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        PartyInviteModel mPartyInviteModel = new PartyInviteModel();

        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("IsError"))
                mPartyInviteModel.setError(jsonObject.optBoolean("IsError"));
            if (jsonObject.has("Message"))
                mPartyInviteModel.setMessage(jsonObject.optString("Message"));
            if (jsonObject.has("Output")) {
                JSONArray jsonArray = jsonObject.optJSONArray("Output");

                ArrayList<InvitesModel>invitesList = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    InvitesModel invitesModel = new InvitesModel();
                    JSONObject dataObject = jsonArray.optJSONObject(i);
                    invitesModel.setInviteid(dataObject.optString("inviteid"));
                    invitesModel.setInvitetype(dataObject.optString("invitetype"));
                    invitesModel.setVenue(dataObject.optString("venue"));
                    invitesModel.setEventdate(dataObject.optString("eventdate"));
                    invitesModel.setEventtime(dataObject.optString("eventtime"));
                    invitesModel.setInvitenote(dataObject.optString("invitenote"));
                    invitesList.add(invitesModel);
                }
                mPartyInviteModel.setInvitesList(invitesList);

            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        return mPartyInviteModel;
    }
}
