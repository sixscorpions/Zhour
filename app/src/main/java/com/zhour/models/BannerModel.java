package com.zhour.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 9/1/2017.
 */

public class BannerModel extends Model {
    private boolean IsError;
    private String Message;
    private int banid;
    private ArrayList<String> bannerUrls;

    public int getBanid() {
        return banid;
    }

    public void setBanid(int banid) {
        this.banid = banid;
    }

    public ArrayList<String> getBannerUrls() {
        return bannerUrls;
    }

    public void setBannerUrls(ArrayList<String> bannerUrls) {
        this.bannerUrls = bannerUrls;
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
