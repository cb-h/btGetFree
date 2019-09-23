package com.pci.aw.btgetfree.bean;

import cn.bmob.v3.BmobObject;


public class Update extends BmobObject{

    public  String upDateUrl;//更新url
    public  String appVersion;//app版本
    public  boolean canCancel;//是否可以取消更新
    public String msg;//更新提示信息

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUpDateUrl() {
        return upDateUrl;
    }

    public void setUpDateUrl(String upDateUrl) {
        this.upDateUrl = upDateUrl;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }
}
