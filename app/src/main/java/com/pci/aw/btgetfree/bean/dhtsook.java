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



public class dhtsook implements Runnable {

    private String url = "http://www.dhtsook.top/search/";
    private ArrayList list ;
    private String tag;
    private int page;

    public dhtsook( String tag,ArrayList list, int page) {
        this.list = list;
        this.tag = tag;
        this.page = page;
    }

    @Override
    public void run() {
        try {
            MyLog.e("dhtsook running");
            Document document= Jsoup.connect( url + tag + "/"+ page + "-1.html").timeout(10000).get();//10s的请求超时
            Elements es = document.select("div.search-item");
            for (Element e:es){
                Element title = e.select("div.item-list").first();
                //title
//                String s1 = title.select("li").get(0).text();
                String s1 = tag;
//                Element dt = e.select("dt").first();
                //onlineplay
//                String s2 = title.select("a").get(0).attr("href");
                String s2 ="";
                Element con = e.select("div.item-bar").first();
                //size
                String s3 = con.select("span").get(2).text();
                //creattime
                String s4 = con.select("span").get(1).text();
                //clicktime
                String s5 = con.select("span").get(3).text();
                Element info = con.select("a").first();
                //hashcode
                String s6 = info.attr("href");
                MyLog.e("msg  s1:",s1);
                MyLog.e("msg  s2:",s2);
                MyLog.e("msg  s3:",s3);
                MyLog.e("msg  s4:",s4);
                MyLog.e("msg  s5:",s5);
                MyLog.e("msg  s6:",s6);
                BeanList bean = new BeanList();
                bean.setTitle(s1);
                bean.setOnlineplay(s2);
                bean.setSize(s3);
                bean.setCreattime(s4);
                bean.setClicktime(s5);
                bean.setHashcode(s6);
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
