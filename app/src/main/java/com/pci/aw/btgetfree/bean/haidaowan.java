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



public class haidaowan implements Runnable {
    //https://thepiratebay.cr/search/1080/1/99/0
    private String url = "https://thepiratebay.cr/search/";
    private ArrayList list ;
    private String tag;
    private int page;

    public haidaowan( String tag,ArrayList list, int page) {
        this.list = list;
        this.tag = tag;
        this.page = page;
    }

    @Override
    public void run() {
        try {
            MyLog.e("haidaowan running");
            Document document= Jsoup.connect( url + tag + "/"+ page + "/99/0").timeout(10000).get();//10s的请求超时
            Elements es = document.select("tbody").first().select("tr");
            for (Element e:es){
                try{
                    Element msg = e.select("td").get(1);
                    //title
                    String s1 = msg.select("a").first().text();
                    MyLog.e("msg  s1:",s1);

                    String s2 ="";
                    Element con = msg.select("a").get(1);
                    //size
                    String s3 = "";
                    //creattime
                    String s4 = "";
                    //clicktime
                    String s5 = e.select("td").get(2).text();
                    MyLog.e("msg  s5:",s5);

                    //hashcode
                    String s6 = con.attr("href");
                    MyLog.e("msg  s6:",s6);

                    BeanList bean = new BeanList();
                    bean.setTitle(s1);

                    bean.setHashcode(s6);
                    list.add(bean);
                }catch (Exception e1){
                    continue;
                }

            }
            Message message = new Message();
            message.what = 1;
            MainFragment.handler.sendMessage(message);

        }catch (InterruptedIOException e){
            MyLog.e("InterruptedIOException----  :",e.toString()+"");

        }catch (Exception e){
            MyLog.e("err :",e.toString()+"");

            Message message = new Message();
            message.what = 404;
            MainFragment.handler.sendMessage(message);
        }
    }
}
