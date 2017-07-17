package com.zhour.models;

/**
 * Created by shankar on 7/17/2017.
 */

public class AuthenticateUserModel extends Model {
    private boolean IsError;
    private String Message;
    private String userid;
    private String username;
    private String contactnumber;
    private String rolename;
    private String lastlogin;
    private String communityid;
    private String communityname;
    private String residentid;
    private String sesid;
    private String token;

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getCommunityid() {
        return communityid;
    }

    public void setCommunityid(String communityid) {
        this.communityid = communityid;
    }

    public String getCommunityname() {
        return communityname;
    }

    public void setCommunityname(String communityname) {
        this.communityname = communityname;
    }

    public String getResidentid() {
        return residentid;
    }

    public void setResidentid(String residentid) {
        this.residentid = residentid;
    }

    public String getSesid() {
        return sesid;
    }

    public void setSesid(String sesid) {
        this.sesid = sesid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
