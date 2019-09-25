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
                //title
                String s1 = title.select("a").first().attr("title");
                Element dt = e.select("dt").first();
                //onlineplay
                String s2 = dt.select("a").get(0).attr("href");
                //size
                String s3 = dt.select("span").first().text();
                //creattime
                String s4 = dt.select("span").get(1).text();
                //clicktime
                String s5 = dt.select("span").get(2).text();
                Element info = e.select("div.dInfo").first();
                //hashcode
                String s6 = info.text().split("：")[1].substring(1);
                MyLog.e("msg  s1:",s1);
                MyLog.e("msg  s2:",s2);
                MyLog.e("msg  s3:",s3);
                MyLog.e("msg  s4:",s4);
                MyLog.e("msg  s5:",s5);
                MyLog.e("msg  s6:","magnet:?xt=urn:btih:"+s6);
                BeanList bean = new BeanList();
                bean.setTitle(s1);
                bean.setOnlineplay(s2);
                bean.setSize(s3);
                bean.setCreattime(s4);
                bean.setClicktime(s5);
                bean.setHashcode("magnet:?xt=urn:btih:" + s6);
                list.add(bean);
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
