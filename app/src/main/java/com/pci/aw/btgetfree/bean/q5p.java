package com.pci.aw.btgetfree.bean;

import android.os.Message;

import com.pci.aw.btgetfree.fragment.MainFragment;
import com.pci.aw.btgetfree.utils.MyLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InterruptedIOException;
import java.util.ArrayList;



public class q5p implements Runnable {

    public static String url = "http://cili.q5p.cc/plus/s/index.asp?keyword=";
    private ArrayList list ;
    private String tag;
    private int page;


    public q5p(String tag, ArrayList list , int page) {
        this.tag = tag;
        this.list = list;
        this.page = page;

    }

    @Override
    public void run() {
        try {
            MyLog.e("q5p running");
            Document document= Jsoup.connect( url + tag + "&p="+ page).timeout(10000).get();//10s的请求超时
            Elements es= document.select("div h2 a");

            for (Element e:es){
                String title = e.text();
                String msgUrl = e.attr("href");
                MyLog.e(title);
                MyLog.e(msgUrl);
            }
            Message message = new Message();
            message.what = 1;
            MainFragment.handler.sendMessage(message);

        }catch (InterruptedIOException e){
            MyLog.e("InterruptedIOException----  :",e.toString()+"");

        }catch (Exception e){
            MyLog.e("err",e.toString()+"");

            Message message = new Message();
            message.what = 404;
            MainFragment.handler.sendMessage(message);
        }
    }
}
