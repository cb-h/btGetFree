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


public class bturl implements Runnable {
//    https://www.bturl.so/search/1080_ctime_1.html
//    XXXXXXXXX
    private String url = "https://www.bturl.so/search/";
    private ArrayList list ;
    private String tag;
    private int page;

    public bturl( String tag,ArrayList list,int page) {
        this.list = list;
        this.tag = tag;
        this.page = page;
    }

    @Override
    public void run() {
        try {
            MyLog.e("bturl running");
            Document document= Jsoup.connect( url + tag + "_ctime_"+ page +".html").timeout(10000).userAgent("UA").validateTLSCertificates(false).get();//10s的请求超时
//            MyLog.e("https bturl  document.html",document.html());
            Element ul = document.select("div.row").get(1);
            MyLog.e(ul.html());
            Elements es = ul.select("table");
            MyLog.e(es.first().html());
            for (Element e:es){
                Element title = e.select("tr").get(0).select("h4").first().select("a").first();
                //title
                String s1 = title.text();
                MyLog.e("msg  s1:",s1);

//                Element on = e.select("h3.T1").first();
                //onlineplay https://www.bturl.cc/ 下一页加载
                String s2 = title.attr("href");
                MyLog.e("msg  s2:",s2);

                Element tr2 = e.select("tr").get(1);
                //size
                String s3 = tr2.select("td").get(1).text();
                MyLog.e("msg  s3:",s3);

                //creattime
                String s4 = tr2.select("td").first().text();
                MyLog.e("msg  s4:",s4);

                //clicktime
                String s5 = tr2.select("td").get(2).text();
                MyLog.e("msg  s5:",s5);

                //hashcode
                String s6 = tr2.select("td").get(3).select("a").first().attr("href");
                MyLog.e("msg  s6:",s6);
                BeanList bean = new BeanList();
                bean.setTitle(s1);

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
