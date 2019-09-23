package com.pci.aw.btgetfree.bean;

import cn.bmob.v3.BmobObject;



public class ErrorCatch extends BmobObject {

    //程序版本
    public String appVersion;
    //错误信息
    public String errorMsg;
    //手机识别码
    public String phoneKey;
    //安卓版本
    public String androidVersion;
    //手机信息，厂家型号等
    public String phoneMsg;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getPhoneKey() {
        return phoneKey;
    }

    public void setPhoneKey(String phoneKey) {
        this.phoneKey = phoneKey;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getPhoneMsg() {
        return phoneMsg;
    }

    public void setPhoneMsg(String phoneMsg) {
        this.phoneMsg = phoneMsg;
    }
}
