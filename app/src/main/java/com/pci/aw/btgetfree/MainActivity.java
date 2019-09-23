package com.pci.aw.btgetfree;


import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.pci.aw.btgetfree.fragment.HelpFragment;
import com.pci.aw.btgetfree.fragment.MainFragment;
import com.pci.aw.btgetfree.utils.MyUtils;

import java.util.UUID;

public class MainActivity extends BaseActivity implements View.OnClickListener{


    private Button b1,b2;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private MainFragment mainFragment;
    private HelpFragment taoFragment;
//    private OwnerFragment ownerFragment;
    private long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        if (TextUtils.isEmpty(BaseApplication.SP_get("uuid"))){
            UUID uuid = UUID.randomUUID();
            BaseApplication.SP_set("uuid",uuid.toString());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);////设置固定状态栏常驻，覆盖app布局
            getWindow().setStatusBarColor(Color.parseColor("#00000000"));//设置状态栏全透明颜色
        }
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        find();
        //设置b1按钮点击一下
        b1.performClick();
        //启动检测更新
        MyUtils.checkUpdate("main");

    }
    //设置字体的颜色
    private void setTextColor() {
        b1.setTextColor(getResources().getColor(R.color.colorTextNoChacked));
        b2.setTextColor(getResources().getColor(R.color.colorTextNoChacked));
//        b3.setTextColor(getResources().getColor(R.color.colorTextNoChacked));
//        b4.setTextColor(getResources().getColor(R.color.colorTextNoChacked));

    }
    //默认选定mainfragment
    private void setDefultFragment() {
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        mainFragment = new MainFragment(this);
        transaction.replace(R.id.fragment,mainFragment);
        transaction.commit();
    }

    private void find() {
        b1= (Button) findViewById(R.id.b1);
        b1.setOnClickListener(this);

        b2= (Button) findViewById(R.id.b2);
        b2.setOnClickListener(this);

//        b3= (Button) findViewById(R.id.b3);
//        b3.setOnClickListener(this);

//        b4= (Button) findViewById(R.id.b4);
//        b4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        setTextColor();
        //先把所有的fragment都hide隐藏，看点击了谁让谁显示
        //去除了replace这个方法，使用hide show方法保存fragment的内容。
        hideAllFragment();
        switch (v.getId()){
            case R.id.b1:
                b1.setTextColor(getResources().getColor(R.color.colorMain));
                if (mainFragment==null){
                    mainFragment = new MainFragment(this);
                    transaction.add(R.id.fragment,mainFragment);
                }
                //  transaction.replace(R.id.fragment,mainFragment);

                transaction.show(mainFragment);
                break;
            case R.id.b2:
                b2.setTextColor(getResources().getColor(R.color.colorMain));
                if (taoFragment==null){
                    taoFragment =new HelpFragment(this);
                    transaction.add(R.id.fragment,taoFragment);
                }
                //transaction.replace(R.id.fragment,taoFragment);

                transaction.show(taoFragment);
                break;
//            case R.id.b3:
//                b3.setTextColor(getResources().getColor(R.color.colorTextChacked));
//                if (tab3Fragment==null){
//                    tab3Fragment = new AlittleFragment(this);
//                    transaction.add(R.id.fragment,tab3Fragment);
//                }
//               // transaction.replace(R.id.fragment,tab3Fragment);
//                transaction.show(tab3Fragment);
//                break;
//            case R.id.b4:
//                b4.setTextColor(getResources().getColor(R.color.colorTextChacked));
//                if (ownerFragment==null){
//                    ownerFragment=new OwnerFragment(this);
//                    transaction.add(R.id.fragment,ownerFragment);
//                }
//                transaction.show(ownerFragment);
//                //transaction.replace(R.id.fragment,ownerFragment);
//                Log.e("---",v.getId()+"");
//                break;


        }
        //transaction.addToBackStack("");
        //事务提交。
        transaction.commit();

    }

    private void hideAllFragment() {
        if (mainFragment!=null){
            transaction.hide(mainFragment);
        }
        if (taoFragment!=null){
            transaction.hide(taoFragment);
        }
//        if (tab3Fragment!=null){
//            transaction.hide(tab3Fragment);
//        }
//        if (ownerFragment!=null){
//            transaction.hide(ownerFragment);
//        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){

            if (System.currentTimeMillis() - time > 2000) {
                //获得当前的时间
                time = System.currentTimeMillis();
                Toast.makeText(MainActivity.this, R.string.app_toExit,Toast.LENGTH_SHORT).show();
            } else {
                //点击在两秒以内
                finishAndRemoveTask();
                // System.exit(0);
            }
            return true;
        }
        return true;
    }

}
