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



public class btcili implements Runnable {

    private String url = "https://www.btcili.org/search/torrent/";
    private ArrayList list ;
    private String tag;
    private int page;

    public btcili( String tag,ArrayList list, int page) {
        this.list = list;
        this.tag = tag;
        this.page = page;
    }

    @Override
    public void run() {
        try {
            MyLog.e("btcili running");
            Document document= Jsoup.connect( url + tag + "/"+ page + ".html").timeout(10000).get();//10s的请求超时
//            MyLog.e("https  btcili  document.html",document.html());
            Elements rs = document.select("div.r");
            for (Element e:rs){
                Element title = e.select("h5.h").first();
                //title
                String s1 = title.text();
                MyLog.e("msg  s1:",s1);

                Element dt = e.select("div.media-more").first();
                //onlineplay 磁力
                String s2 = e.select("a").first().attr("href");
                MyLog.e("msg  s2:",s2);

                //size
                String s3 = dt.select("span.label-warning").first().text();
                MyLog.e("msg  s3:",s3);

                //creattime
                String s4 = dt.select("span.label-success").first().text();
                MyLog.e("msg  s4:",s4);

                //clicktime hot热度
                String s5 = dt.select("span.label-primary").first().text();
                MyLog.e("msg  s5:",s5);

                //hashcode
                String s6 = dt.select("a").first().attr("href");
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

        } catch (Exception e){
            MyLog.e("err",e.toString()+"");

            Message message = new Message();
            message.what = 404;
            MainFragment.handler.sendMessage(message);
        }
    }
}
