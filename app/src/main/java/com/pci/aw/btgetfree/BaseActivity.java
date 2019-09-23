package com.pci.aw.btgetfree;


import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;




public class BaseActivity extends AppCompatActivity {


    private NetWorkReceiver receiver;
    public static Context context;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (receiver==null){
            receiver = new NetWorkReceiver();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟
//        MobclickAgent.onResume(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver,filter);

    }
    @Override
    protected void onPause() {
        super.onPause();
        //友盟
//        MobclickAgent.onPause(this);
        unregisterReceiver(receiver);

    }
}
