package com.zhour.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 8/8/2017.
 */

public class ComplaintResponseModel extends Model {
    private boolean IsError;
    private String Message;
    private ArrayList<ComplaintListModel> complaintListModels;

    public boolean isError() {
        return IsError;
    }

    public void setError(boolean error) {
        IsError = error;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ComplaintListModel> getComplaintListModels() {
        return complaintListModels;
    }

    public void setComplaintListModels(ArrayList<ComplaintListModel> complaintListModels) {
        this.complaintListModels = complaintListModels;
    }
}
