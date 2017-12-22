package com.zhour.models;

import java.util.ArrayList;

/**
 * Created by madhu on 15-Dec-17.
 */

public class OtherInvitesResponseModel extends Model {

    private boolean IsError;
    private String Message;
    private ArrayList<OtherInvitesModel> otherInvitesModels;


    public boolean isError() {
        return IsError;
    }

    public void setError(boolean error) {
        IsError = error;
    }

    @Override
    public String getMessage() {
        return Message;
    }

    @Override
    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<OtherInvitesModel> getOtherInvitesModels() {
        return otherInvitesModels;
    }

    public void setOtherInvitesModels(ArrayList<OtherInvitesModel> otherInvitesModels) {
        this.otherInvitesModels = otherInvitesModels;
    }
}
