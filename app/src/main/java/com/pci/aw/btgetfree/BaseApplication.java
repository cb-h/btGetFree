package com.pci.aw.btgetfree;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


import cn.bmob.v3.Bmob;



public class BaseApplication extends Application {

    public static Context context;
    static SharedPreferences.Editor editor;
    static SharedPreferences sharedPreferences;
    public BaseApplication() {


    }

    @Override
    public void onCreate() {
        //崩溃信息收集
//        CrashHandler handler = CrashHandler.getInstance();
//        handler.init(this);
        Bmob.initialize(this,"7fa03ac1a2cffd960ff74f897828a76e");
//        MimoSdk.setEnableUpdate(true);
//        MimoSdk.setDebugOn();
//        MimoSdk.setStageOn();
//        MimoSdk.init(this, "2882303761517901912", "fake_app_key", "fake_app_token");
        super.onCreate();
        context=this;
//        UMConfigure.init(this,"5bd818b3f1f556d5810002c5","Umeng",UMConfigure.DEVICE_TYPE_PHONE,null);
        sharedPreferences = getSharedPreferences("btGet",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    //    权限申请
    //    tag 需要申请的权限
    public static void getPermission(String tag){
        if (Build.VERSION.SDK_INT>22) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{tag}, 1);
        }
    }
    //判断是否有权限
    public static boolean havePermission(String tag){
        if (ContextCompat.checkSelfPermission(context,tag) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    public static String SP_get(String tag){

        return sharedPreferences.getString(tag,"");

    }
    public static void SP_set(String tag , String value){

        editor.putString(tag,value);
        editor.commit();
    }

}
