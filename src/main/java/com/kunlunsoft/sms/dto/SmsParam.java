package com.kunlunsoft.sms.dto;

public class SmsParam {
    private int appid;
    private String appkey;
    private String mobile;
    private String msg;

    public int getAppid() {
        return appid;
    }

    public SmsParam setAppid(int appid) {
        this.appid = appid;
        return this;
    }

    public String getAppkey() {
        return appkey;
    }

    public SmsParam setAppkey(String appkey) {
        this.appkey = appkey;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public SmsParam setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public SmsParam setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
