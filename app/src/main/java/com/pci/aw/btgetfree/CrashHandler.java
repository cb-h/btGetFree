package com.pci.aw.btgetfree;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.pci.aw.btgetfree.bean.ErrorCatch;
import com.pci.aw.btgetfree.utils.MyLog;
import com.pci.aw.btgetfree.utils.MyUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class CrashHandler implements UncaughtExceptionHandler{


    //系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static CrashHandler instance = new CrashHandler();
    //程序的Context对象
    private Context context;
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    /** 保证只有一个CrashHandler实例 */
    private CrashHandler() {
    }

    /** 获取CrashHandler实例 ,单例模式 */
    public static CrashHandler getInstance() {
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        this.context = context;
    	//获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e("CrashHandler", "error : ", e);
            }
            //退出程序

            System.exit(0);
//            android.os.Process.killProcess(android.os.Process.myPid());

        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //获取异常信息
        saveCrashInfo2File(ex);
        return true;
    }


    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return  保存错误信息到文件中   返回文件名称,
     */
    private String saveCrashInfo2File(Throwable ex) {
        //sb 错误信息
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        //appVersion 软件版本
        String appVersion = MyUtils.getAppVersion(context);

        //phoneKey 手机识别码
        String uuid = BaseApplication.SP_get("uuid");
        //phoneMsg手机信息，厂家型号等。
        String phoneMsg = Build.BRAND+"- -"+Build.MODEL;

        //androidversion Android版本
        String android = Build.MANUFACTURER +"-"+Build.VERSION.RELEASE;
        // 上传error数据
        ErrorCatch bean = new ErrorCatch();
        bean.setAndroidVersion(android);
        bean.setAppVersion(appVersion);
        bean.setPhoneKey(uuid);
        bean.setPhoneMsg(phoneMsg);
        bean.setErrorMsg(sb.toString());
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                  MyLog.e("error commit");
                }
            }
        });



        return null;
    }
}