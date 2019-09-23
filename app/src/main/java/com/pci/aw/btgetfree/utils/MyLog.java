package com.pci.aw.btgetfree.utils;

import android.util.Log;



public class MyLog {
    public static boolean isLog = true;

    public static void e(String e){
        if (isLog){
            Log.e("↓↓↓↓↓↓↓↓↓↓↓↓","↓↓↓↓↓↓↓↓↓↓↓↓");
            Log.e("   ---   TAG   ---",e);
            Log.e("↑↑↑↑↑↑↑↑↑↑↑↑","↑↑↑↑↑↑↑↑↑↑↑↑");
        }
    }
    public static void e(String tag,String e){
        if (isLog){
            Log.e("↓↓↓↓↓↓↓↓↓↓↓↓","↓↓↓↓↓↓↓↓↓↓↓↓");
            Log.e("     "+tag,e);
            Log.e("↑↑↑↑↑↑↑↑↑↑↑↑","↑↑↑↑↑↑↑↑↑↑↑↑");
        }
    }
}
