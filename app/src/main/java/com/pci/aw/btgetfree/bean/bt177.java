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



public class bt177 implements Runnable {
//XXXXXXXX
    public static String url = "http://www.bt177.me/word/";
    private ArrayList list ;
    private String tag;
    private int page;
//    private MainFragment fragment;

    public bt177(String tag,ArrayList list ,int page) {
        this.tag = tag;
        this.list = list;
        this.page = page;

    }

    @Override
    public void run() {
        try {
            MyLog.e("bt177 running");
            Document document= Jsoup.connect( url + tag + "_"+ page + ".html").timeout(10000).get();//10s的请求超时
            Element ul = document.select("ul.mlist").first();
            Elements es = ul.select("li");
            for (Element e:es){
                Element title = e.select("div.T1").first();


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
