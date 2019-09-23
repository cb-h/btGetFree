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


public class Btgat implements Runnable {

    private String url = "http://www.btgat.com/search/";
    private ArrayList list ;
    private String tag;
    private int page;

    public Btgat(String tag, ArrayList list, int page) {
        this.list = list;
        this.tag = tag;
        this.page = page;
    }

    @Override
    public void run() {
        try {
            MyLog.e("Btgat running");
            String myurl = url + tag + "-first-asc-"+ page;
            MyLog.e(myurl);

            Document document= Jsoup.connect(myurl).timeout(10000).get();//10s的请求超时
            Elements rs = document.select("div.search-item");
            MyLog.e("~~~~~~~~~~~~~~~ ",rs.first().html());

            for (Element e:rs){
                Element title = e.select("a.text-success").first();
                //title
                String s1 = title.text();
                String s6 = title.attr("href");
                MyLog.e("msg  s1:",s1);
                MyLog.e("msg  s6:",s6);

//                Element dt = e.select("div.media-more").first();
//                //onlineplay 磁力
//                String s2 = e.select("a").first().attr("href");
//                MyLog.e("msg  s2:",s2);

                //size
                Elements myEs = e.select("tr").get(1).select("td");
                String s3 = myEs.first().select("strong").get(0).text();
                MyLog.e("msg  s3:",s3);

                //creattime
                String s4 = myEs.get(1).select("strong").get(0).text();
                MyLog.e("msg  s4:",s4);

                //clicktime hot热度
                String s5 = myEs.get(2).select("strong").get(0).text();
                MyLog.e("msg  s5:",s5);

                //hashcode
                String s7 = myEs.get(3).select("a").get(0).attr("href");
                MyLog.e("msg  s7:",s7);



                BeanList bean = new BeanList();
                bean.setTitle(s1);
                bean.setOnlineplay("");
                bean.setSize(s3);
                bean.setCreattime(s4);
                bean.setClicktime("");
                bean.setHashcode(s6);
                list.add(bean);
            }
            Message message = new Message();
            message.what = 1;
            MainFragment.handler.sendMessage(message);

        }catch (InterruptedIOException e){
            MyLog.e("InterruptedIOException----  :",e.toString()+"");

        } catch (Exception e){
            MyLog.e("err",e.toString()+"");

            Message message = new Message();
            message.what = 404;
            MainFragment.handler.sendMessage(message);
        }
    }
}
