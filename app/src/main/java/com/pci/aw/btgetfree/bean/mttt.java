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


public class mttt implements Runnable {

    private String url = "https://www.mttt.org/mt/";
    private ArrayList list ;
    private String tag;
    private int page;

    public mttt( String tag,ArrayList list,int page) {
        this.list = list;
        this.tag = tag;
        this.page = page;
    }

    @Override
    public void run() {
        try {
            MyLog.e("mttt running");
            Document document= Jsoup.connect( url + tag + "/1-1-1-"+ page + ".html").timeout(10000).userAgent("UA").validateTLSCertificates(false).get();//10s的请求超时
            MyLog.e("https bturl  document.html",document.html());
            Element ul = document.select("div.content").first();
            Elements es = ul.select("dl.item");
            MyLog.e("   es   "+es.first().html());
            for (Element e:es){
                Element title = e.select("dd.flist").first();
                //title
                String s1 = title.select("span.name").first().text();
                MyLog.e("msg  s1:",s1);

//                Element on = e.select("h3.T1").first();
                //onlineplay https://www.bturl.cc/ 下一页加载
                String s2 = "";
                MyLog.e("msg  s2:",s2);

                Elements con = e.select("dd.attr");

                //size
                String s3 = con.select("span").get(1).text();
                MyLog.e("msg  s3:",s3);

                //creattime
                String s4 = con.select("span").first().text();
                MyLog.e("msg  s4:",s4);

                //clicktime
                String s5 = con.select("span").get(4).text();
                MyLog.e("msg  s5:",s5);

                Element code = con.select("span").get(5).select("a").first();
                //hashcode
                String s6 = code.attr("href");
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
