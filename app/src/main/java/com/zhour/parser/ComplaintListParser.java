package com.zhour.parser;

import com.zhour.models.ComplaintListModel;
import com.zhour.models.ComplaintResponseModel;
import com.zhour.models.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shankar on 7/17/2017.
 */

public class ComplaintListParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        ComplaintResponseModel mComplaintResponseModel = new ComplaintResponseModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("IsError"))
                mComplaintResponseModel.setError(jsonObject.optBoolean("IsError"));
            if (jsonObject.has("Message"))
                mComplaintResponseModel.setMessage(jsonObject.optString("Message"));
            ArrayList<ComplaintListModel> complaintListModels = new ArrayList<>();
            if (jsonObject.has("Output")) {
                JSONArray jsonArray = jsonObject.optJSONArray("Output");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                    ComplaintListModel complaintListModel = new ComplaintListModel();

                    complaintListModel.setComplaintid(jsonObject1.optString("complaintid"));
                    complaintListModel.setComplainttype(jsonObject1.optString("complainttype"));
                    complaintListModel.setComplainttypedesc(jsonObject1.optString("complainttypedesc"));
                    complaintListModel.setComplaintdesc(jsonObject1.optString("complaintdesc"));
                    complaintListModel.setComments(jsonObject1.optString("comments"));
                    complaintListModel.setComplaintstatus(jsonObject1.optString("complaintstatus"));
                    complaintListModel.setResidentid(jsonObject1.optString("residentid"));
                    complaintListModel.setResidentname(jsonObject1.optString("residentname"));
                    complaintListModel.setFlatnumber(jsonObject1.optString("flatnumber"));
                    complaintListModel.setAppartmentname(jsonObject1.optString("appartmentname"));
                    complaintListModel.setContact1(jsonObject1.optString("contact1"));
                    complaintListModel.setCommunityid(jsonObject1.optString("communityid"));
                    complaintListModel.setUpdatedby(jsonObject1.optString("updatedby"));
                    complaintListModel.setUpdateddate(jsonObject1.optString("updateddate"));

                    complaintListModels.add(complaintListModel);
                }
                mComplaintResponseModel.setComplaintListModels(complaintListModels);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mComplaintResponseModel;
    }
}