package com.pci.aw.btgetfree;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;


public class NetWorkReceiver extends BroadcastReceiver {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        Network[] info = manager.getAllNetworks();
        for (int i=0; i < info.length; i++){
            //获取ConnectivityManager对象对应的NetworkInfo对象
            NetworkInfo networkInfo = manager.getNetworkInfo(info[i]);
            if (networkInfo.isConnected()){
               // Toast.makeText(context,"正在使用 "+networkInfo.getTypeName(),Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, context.getResources().getString(R.string.app_internet_err) ,Toast.LENGTH_SHORT).show();
            }
        }
        if (info.length==0){
            Toast.makeText(context, context.getResources().getString(R.string.app_internet_err),Toast.LENGTH_SHORT).show();
        }
    }
}
