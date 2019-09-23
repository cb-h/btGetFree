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



public class feijibt implements Runnable {
//http://feijibt.xyz/list/1080/2/0/0.html
    private String url = "http://feijibt.xyz/list/";
    private ArrayList list ;
    private String tag;
    private int page;

    public feijibt( String tag,ArrayList list, int page) {
        this.list = list;
        this.tag = tag;
        this.page = page;
    }

    @Override
    public void run() {
        try {
            MyLog.e("feijibt running");
            Document document= Jsoup.connect( url + tag + "/"+ page + "/0/0.html").timeout(10000).get();//10s的请求超时
            Elements es = document.select("div.pbox").last().select("div.rs");
            for (Element e:es){
                Element title = e.select("div.title").first().select("a").first();
                //title
                String s1 = title.text();
                MyLog.e("msg  s1:",s1);

                String s2 ="";
                Element con = e.select("div.sbar").first();
                //size
                String s3 = con.select("span").get(3).select("b").first().text();
                MyLog.e("msg  s3:",s3);

                //creattime
                String s4 = con.select("span").get(2).select("b").first().text();
                MyLog.e("msg  s4:",s4);

                //clicktime
                String s5 = con.select("span").get(5).select("b").first().text();
                MyLog.e("msg  s5:",s5);

                //hashcode
                String s6 = con.select("span").get(0).select("a").first().attr("href");
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
