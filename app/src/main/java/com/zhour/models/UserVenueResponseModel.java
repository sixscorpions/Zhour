package com.zhour.models;

import java.util.ArrayList;

/**
 * Created by madhu on 14-Dec-17.
 */

public class UserVenueResponseModel extends Model {
    private boolean IsError;
    private String Message;
    private String Error;

    private ArrayList<VenueModel> venuesModels;


    public boolean isError() {
        return IsError;
    }

    public void setError(boolean error) {
        IsError = error;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    @Override
    public String getMessage() {
        return Message;
    }

    @Override
    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<VenueModel> getVenuesModels() {
        return venuesModels;
    }

    public void setVenuesModels(ArrayList<VenueModel> venuesModels) {
        this.venuesModels = venuesModels;
    }
}
