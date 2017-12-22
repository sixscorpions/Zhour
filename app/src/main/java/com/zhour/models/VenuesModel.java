package com.zhour.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by madhu on 16-Dec-17.
 */

public class VenuesModel {


    private String residentid;
    private String residentname;
    private String appartmentname;
    private String flat;

    public VenuesModel(JSONObject obj) throws JSONException {

        if (obj.has("residentid")) {

            setResidentid(obj.optString("residentid"));

        }
        if (obj.has("residentname")) {

            setResidentname(obj.optString("residentname"));

        }
        if (obj.has("appartmentname")) {

            setAppartmentname(obj.optString("appartmentname"));

        }
        if (obj.has("flat")) {

            setFlat(obj.optString("flat"));

        }


    }


    public String getResidentid() {
        return residentid;
    }

    public void setResidentid(String residentid) {
        this.residentid = residentid;
    }

    public String getResidentname() {
        return residentname;
    }

    public void setResidentname(String residentname) {
        this.residentname = residentname;
    }

    public String getAppartmentname() {
        return appartmentname;
    }

    public void setAppartmentname(String appartmentname) {
        this.appartmentname = appartmentname;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }


}
