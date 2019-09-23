package com.pci.aw.btgetfree.fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.pci.aw.btgetfree.BaseActivity;
import com.pci.aw.btgetfree.R;
import com.pci.aw.btgetfree.utils.MyUtils;


public class ExplainActivity extends BaseActivity {


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explain_layout);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("搜点");
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


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
