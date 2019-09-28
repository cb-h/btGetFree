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



public class cltt9 implements Runnable {
//http://feijibt.xyz/list/1080/2/0/0.html
//    XXXX
    private String url = "https://www.cltt9.xyz/";
    private ArrayList list ;
    private String tag;
    private int page;

    public cltt9(String tag, ArrayList list, int page) {
        this.list = list;
        this.tag = tag;
        this.page = page;
    }

    @Override
    public void run() {
        try {
            MyLog.e("cltt9 running");
            Document document= Jsoup.connect( url+ "search/" + tag + "_ctime_"+ page + ".html").timeout(10000).get();//10s的请求超时
            Elements es = document.select("h5.item-title");
            for (Element e:es){
                Element title = e.select("a").first();
                //title
                String s1 = title.text();
                MyLog.e("msg  s1:",s1);

                //size
                String s2 = title.attr("href");
                MyLog.e("msg  s2:",s2);

                BeanList bean = new BeanList();
                bean.setTitle(s1);
                bean.setMsgUrl(url+s2);

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
