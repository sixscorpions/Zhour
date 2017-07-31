package com.zhour.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 7/31/2017.
 */

public class NoticeBoardListModel extends Model {
    private boolean IsError;
    private String Message;
    private ArrayList<NoticeBoardModel> noticeBoardModels;

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

    public ArrayList<NoticeBoardModel> getNoticeBoardModels() {
        return noticeBoardModels;
    }

    public void setNoticeBoardModels(ArrayList<NoticeBoardModel> noticeBoardModels) {
        this.noticeBoardModels = noticeBoardModels;
    }
}
