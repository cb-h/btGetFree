package com.pci.aw.btgetfree.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.pci.aw.btgetfree.BaseApplication;
import com.pci.aw.btgetfree.R;
import com.pci.aw.btgetfree.adapter.MainFragmentAdapter;
import com.pci.aw.btgetfree.bean.BeanList;
import com.pci.aw.btgetfree.bean.Btgat;
import com.pci.aw.btgetfree.bean.bt177;
import com.pci.aw.btgetfree.bean.feijibt;
import com.pci.aw.btgetfree.bean.haidaowan;
import com.pci.aw.btgetfree.bean.mttt;
import com.pci.aw.btgetfree.utils.MyLog;
import com.pci.aw.btgetfree.utils.MyUtils;


import java.util.ArrayList;



@SuppressLint("ValidFragment")
public class MainFragment extends BaseFragment {

    private ListView listView;
    private static Button search_bt;
    private EditText search_edit;
    private ImageButton viewPager;
    private View empty;
    private Thread thread;

    private ArrayList<BeanList> list = new ArrayList<>();
    private static MainFragmentAdapter adapter;
    private static Activity context;
    public String tag = "";
    private static boolean isGetMore = false;
    public static int page = 1;
    public static ProgressDialog dialog ;

    public MainFragment(){}
    public MainFragment(Activity context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_fragment,container,false);
        listView =  view.findViewById(R.id.main_list);
        adapter = new MainFragmentAdapter(list,context);
        empty = view.findViewById(R.id.main_fragment_empty);
        listView.setAdapter(adapter);


        //dialog
        dialog = new ProgressDialog(context);
        dialog.setMessage("Searching");
        dialog.setCancelable(true);
        viewPager =  view.findViewById(R.id.main_viewPager);
        search_bt =  view.findViewById(R.id.search_button);
        search_edit =  view.findViewById(R.id.search_edit);
        search_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empty.setVisibility(View.GONE);
                search_bt.setClickable(true);
            }
        });

        //进入之后先请求下获取权限
        // 如果api >= 23 需要显式申请权限
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (ContextCompat.checkSelfPermission(context,Manifest.permission.READ_PHONE_STATE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.READ_PHONE_STATE}     , 0);
//            }
//        }

//        try{
//            ViewGroup viewGroup = view.findViewById(R.id.xiaomiAd_main);
//            IAdWorker worker = AdWorkerFactory.getAdWorker(context,viewGroup,new xiaomiAd(), AdType.AD_BANNER);
//            worker.loadAndShow("1ceee0f81e21cfb45c01376f82efc66f");
//        }catch (Exception e){
//            MyLog.e(e.getMessage());
//        }

        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //小米需要获取权限
                // 如果api >= 23 需要显式申请权限
//                if (Build.VERSION.SDK_INT >= 23) {
//                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.READ_PHONE_STATE}  , 0);
//                    }
//                }


                search_bt.setClickable(false);
                page = 1;
                listView.setVisibility(View.VISIBLE);
                listView.setEmptyView(empty);
                ///收起软键盘
                InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(),0);
                //隐藏ad
//                adView.setVisibility(View.GONE);
                tag = search_edit.getText().toString();
                list.clear();
                adapter.notifyDataSetChanged();
                if (!TextUtils.isEmpty(tag)){

                    getList(tag,list,1);

                }else {
                    Toast.makeText(context, R.string.app_need_input,Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                MyLog.e("firstVisibleItem",""+firstVisibleItem);
                MyLog.e("visibleItemCount",""+visibleItemCount);
                MyLog.e("totalItemCount",""+totalItemCount);
                if ( firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount!=0){
                    MyLog.e("isGetMore",isGetMore+"  ");
                    if( !isGetMore ){
                        //加载更多

                        isGetMore =true;
                        page++;
                        if (TextUtils.isEmpty(tag))return;
                        getList(tag,list,page);


                    }
                }
            }
        });

        return view;

    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //这里已经获取到了摄像头的权限，想干嘛干嘛了可以
                  //  newStartLighr();
                }else {
                    //这里是拒绝给APP摄像头权限，给个提示什么的说明一下都可以。
                  //  Toast.makeText(MainActivity.this,"请手动打开相机权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }

    /**
     * 加载数据
     * @param tag
     * @param list
     */

    public void getList(String tag, ArrayList list,int page){
        if (thread!=null&&thread.isAlive()){
            thread.interrupt();
        }
        String url = BaseApplication.SP_get("url");
        MyLog.e("---getList --- url--",url);
        dialog.show();
        if ( TextUtils.isEmpty( url )){

            thread = new Thread(new Btgat(tag,list,page));
            thread.start();
            BaseApplication.SP_set("url","0");

        } else {

            switch (url){
                case "0":
                    thread = new Thread(new Btgat(tag,list,page));
                    break;
                case "1":
                    thread = new Thread(new feijibt(tag,list,page));
                    break;
                case "2":
                    thread = new Thread(new bt177(tag,list,page));
                    break;
                case "3":
                    thread = new Thread(new mttt(tag,list,page));
                    break;
                case "4":
                    thread = new Thread(new haidaowan(tag,list,page));
                    break;
                default:
                    break;
            }
            if (thread !=null)thread.start();

        }
    }

    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (dialog.isShowing())dialog.dismiss();
            isGetMore = false;
            search_bt.setClickable(true);
            switch (msg.what){

                case 1:
                    adapter.notifyDataSetChanged();
                    break;

                case 404:
                    Toast.makeText(BaseApplication.context, R.string.app_err_to_changeLine,Toast.LENGTH_SHORT).show();
                    MyUtils.toChangeUrlDialog(context);
                    break;

            }
            super.handleMessage(msg);
        }
    };

}
