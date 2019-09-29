package com.pci.aw.btgetfree.fragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pci.aw.btgetfree.R;
import com.pci.aw.btgetfree.utils.MyLog;
import com.pci.aw.btgetfree.utils.MyUtils;

import static com.pci.aw.btgetfree.BaseApplication.context;

/**
 *
 */

public class MainFragment_web_Activity extends AppCompatActivity {

    public TextView titleView,margent;
    public Button open , copy;
    public String hashcode;
    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_webview);
        //把actionbar上面的返回按钮打开需要重写 onOptionsItemSelected
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("搜点");
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        titleView = (TextView) findViewById(R.id.main_title);
        margent = (TextView)findViewById(R.id.main_margent);
        open = (Button) findViewById(R.id.main_open);
        copy = (Button) findViewById(R.id.main_copy);

        Intent intent = getIntent();
        hashcode = intent.getStringExtra("hashcode");
        String title = intent.getStringExtra("title");

        String onlineplay = intent.getStringExtra("onlineplay");

        titleView.setText( title);
        margent.setText(hashcode);

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClip(hashcode,true);
            }
        });
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClip(hashcode,false);
                MyUtils.openApp("com.xunlei.downloadprovider",MainFragment_web_Activity.this);
                MyLog.e("open other app");
            }
        });
    }

    // 剪切板复制
    public void copyToClip(String tag,boolean toast){
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData cData = ClipData.newPlainText("copy",tag);
        cm.setPrimaryClip(cData);
        if (toast)Toast.makeText(this, R.string.app_copied,Toast.LENGTH_SHORT).show();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
