package com.pci.aw.btgetfree.bean;

import android.os.Message;

import com.pci.aw.btgetfree.BaseActivity;
import com.pci.aw.btgetfree.fragment.MainFragment;
import com.pci.aw.btgetfree.utils.MyLog;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ciliguo implements Runnable {
//
    private String url = "https://ciliguo.cc/search?q=";
    private ArrayList list ;
    private String tag;
    private int page;

    public ciliguo(String tag, ArrayList list, int page) {
        this.list = list;
        this.tag = tag;
        this.page = page;
    }

    @Override
    public void run() {
        try {
            MyLog.e("ciliguo running");
            String myurl = url + tag + "&p="+ page;
            MyLog.e(myurl);

            Document document= Jsoup.connect(myurl).timeout(10000).get();//10s的请求超时
            Elements rs = document.select("div.card-body");
            MyLog.e("~~~~~~~~~~~~~~~ ",rs.first().html());

            for (Element e:rs){
                Element title = e.select("a").first().select("span").first();
                //title
                String s1 = title.text();
                MyLog.e("msg  s1:",s1);



                //size
                Elements myEs = e.select("div.link-button-group").first().select("button");
                String s3 = myEs.first().attr("data-src");
                MyLog.e("msg  s3:",s3);





                BeanList bean = new BeanList();
                bean.setTitle(s1);

                bean.setHashcode("");
                list.add(bean);
            }
            Message message = new Message();
            message.what = 1;
            MainFragment.handler.sendMessage(message);

        }catch (InterruptedIOException e){
            MyLog.e("InterruptedIOException----  :",e.toString()+"");
            Message message = new Message();
            message.what = 404;
            MainFragment.handler.sendMessage(message);

        } catch (Exception e){
            MyLog.e("err",e.toString()+"");

            Message message = new Message();
            message.what = 404;
            MainFragment.handler.sendMessage(message);
        }
    }
}
