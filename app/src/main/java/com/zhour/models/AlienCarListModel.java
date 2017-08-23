package com.zhour.models;

import java.util.ArrayList;

/**
 * Created by shankar on 7/17/2017.
 */

public class AlienCarListModel extends Model {
    private boolean IsError;
    private String Message;
    private ArrayList<AlienCarModel> alienCarModels;

    public boolean getIsError() {
        return IsError;
    }

    public void setIsError(boolean IsError) {
        this.IsError = IsError;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public boolean isError() {
        return IsError;
    }

    public void setError(boolean error) {
        IsError = error;
    }

    public ArrayList<AlienCarModel> getAlienCarModels() {
        return alienCarModels;
    }

    public void setAlienCarModels(ArrayList<AlienCarModel> alienCarModels) {
        this.alienCarModels = alienCarModels;
    }
}
