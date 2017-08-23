package com.zhour.models;

/**
 * Created by shankar on 7/17/2017.
 */

public class AlienCarModel extends Model {
    private String vehid;
    private String residentid;
    private String residentname;
    private String vehtypeid;
    private String vehtype;
    private String vehnumber;
    private String contact1;
    private String contact2;
    private String flatnumber;
    private boolean isvisitorvehicle;

    public String getVehid() {
        return vehid;
    }

    public void setVehid(String vehid) {
        this.vehid = vehid;
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

    public String getVehtypeid() {
        return vehtypeid;
    }

    public void setVehtypeid(String vehtypeid) {
        this.vehtypeid = vehtypeid;
    }

    public String getVehtype() {
        return vehtype;
    }

    public void setVehtype(String vehtype) {
        this.vehtype = vehtype;
    }

    public String getVehnumber() {
        return vehnumber;
    }

    public void setVehnumber(String vehnumber) {
        this.vehnumber = vehnumber;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getFlatnumber() {
        return flatnumber;
    }

    public void setFlatnumber(String flatnumber) {
        this.flatnumber = flatnumber;
    }

    public boolean getIsvisitorvehicle() {
        return isvisitorvehicle;
    }

    public void setIsvisitorvehicle(boolean isvisitorvehicle) {
        this.isvisitorvehicle = isvisitorvehicle;
    }
}
