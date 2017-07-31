package com.zhour.models;

/**
 * Created by madhu on 29-Jul-17.
 */

public class NoticeBoardModel {

    private String noticeid;
    private String noticetitle;
    private String noticedesc;
    private String sentby;
    private String sentdate;

    public String getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(String noticeid) {
        this.noticeid = noticeid;
    }

    public String getNoticetitle() {
        return noticetitle;
    }

    public void setNoticetitle(String noticetitle) {
        this.noticetitle = noticetitle;
    }

    public String getNoticedesc() {
        return noticedesc;
    }

    public void setNoticedesc(String noticedesc) {
        this.noticedesc = noticedesc;
    }

    public String getSentby() {
        return sentby;
    }

    public void setSentby(String sentby) {
        this.sentby = sentby;
    }

    public String getSentdate() {
        return sentdate;
    }

    public void setSentdate(String sentdate) {
        this.sentdate = sentdate;
    }
}
