package com.zhour.models;

import java.util.ArrayList;

/**
 * Created by madhu on 14-Jul-17.
 */

public class PartyInviteModel extends Model {
    private boolean IsError;
    private String Message;

    private ArrayList<InvitesModel> invitesList = new ArrayList<>();

    public ArrayList<InvitesModel> getInvitesList() {
        return invitesList;
    }

    public void setInvitesList(ArrayList<InvitesModel> invitesList) {
        this.invitesList = invitesList;
    }


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
}
