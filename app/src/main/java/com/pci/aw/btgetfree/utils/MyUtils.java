package com.pci.aw.btgetfree.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.pci.aw.btgetfree.BaseActivity;
import com.pci.aw.btgetfree.BaseApplication;
import com.pci.aw.btgetfree.MainActivity;
import com.pci.aw.btgetfree.R;
import com.pci.aw.btgetfree.bean.Update;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;



public class MyUtils {

    public static AlertDialog.Builder builder;
    public static String[] items = {"line-1","line-2","line-3","line-4","line-5"};
    public static boolean isDialogShow = false;

    // 检查是否更新
    public static  void checkUpdate(final String goTag){
        BmobQuery<Update> query = new BmobQuery<>();
        query.findObjects(new FindListener<Update>() {
            @Override
            public void done(List<Update> list, BmobException e) {
                if (e == null){
                    //没有异常
                    if (list.size()>0){
                        String version = list.get(0).getAppVersion();
                        final boolean cancel = list.get(0).isCanCancel();
                        final String url = list.get(0).getUpDateUrl();
                        String msg = list.get(0).getMsg();
                        String myAppVersion = getAppVersion(BaseApplication.context);
                        MyLog.e("app  version  "+myAppVersion);
                        MyLog.e("bmob  version  "+version);
                        if (TextUtils.isEmpty(myAppVersion)){
                            return;
                        }
                        if (!version.equals(myAppVersion)){
                            builder = new AlertDialog.Builder(BaseActivity.context);
                            builder.setTitle(R.string.app_update_title);
                            builder.setMessage(msg);
                            builder.setCancelable(false);
                            builder.setPositiveButton(BaseApplication.context.getResources().getString(R.string.app_update), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //更新跳转
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    Uri uri = Uri.parse(url);
                                    intent.setDataAndType(uri,"text/html");
                                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                    BaseActivity.context.startActivity(intent);
                                    if(!cancel){
                                        System.exit(0);
                                    }
                                }
                            });
                            builder.setNegativeButton(BaseApplication.context.getResources().getString(R.string.app_cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(!cancel){
                                        System.exit(0);
                                    }
                                }
                            });
                            builder.show();
                        }else {
                            //最新版
                            if (!goTag.equals("main")){
                                Toast.makeText(BaseApplication.context, R.string.app_isNew,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else {
                        //后台没有新的包，当作是最新版
                        if (!goTag.equals("main")){
                            Toast.makeText(BaseApplication.context, R.string.app_isNew,Toast.LENGTH_SHORT).show();
                        }
                    }

                }else {
                    MyLog.e(e.getMessage());
                    if (!goTag.equals("main")) {
                        Toast.makeText(BaseApplication.context, R.string.app_check_update_err,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }





    //切换url
    public static void toChangeUrlDialog(Context con){
        if (isDialogShow)return;
        if (builder ==null){
            builder = new AlertDialog.Builder(con);
        }
        builder.setTitle(R.string.app_selelct_lines);
        String index = BaseApplication.SP_get("url");
        int code = TextUtils.isEmpty(index)?0:Integer.parseInt(index);
        builder.setSingleChoiceItems(items, code, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BaseApplication.SP_set("url",which+"");
//                Toast.makeText(BaseApplication.context,"已选择:"+items[which],Toast.LENGTH_SHORT).show();

            }
        });
        builder.setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isDialogShow = false;
            }
        });
        builder.setCancelable(false);
        isDialogShow = true;
        builder.show();

    }

    //本app版本name
    public static String getAppVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        String version = info.versionName;
        return version;
    }
    //打开包名对应app
    public static void openApp(String tag,Activity context){
        PackageManager manager = context.getPackageManager();
        if (isHaveApp(tag,context)){
            Intent intent = manager.getLaunchIntentForPackage(tag);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }else {
            Toast.makeText(context,"Open Failed",Toast.LENGTH_SHORT).show();
        }
    }
    //是否存在包名对应的app
    public static boolean isHaveApp(String tag,Activity context){
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(tag,0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            MyLog.e("NameNotFoundException");
            return false;
        }
        MyLog.e(String.valueOf(info!=null));
        return info != null;
    }
}
