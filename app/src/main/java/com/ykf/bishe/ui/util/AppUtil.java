package com.ykf.bishe.ui.util;

import android.os.Environment;

/**
 * Created by ykf on 16/5/16.
 */
public class AppUtil {


    /*
     */
    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

}
