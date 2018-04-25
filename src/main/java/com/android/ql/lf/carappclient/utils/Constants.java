package com.android.ql.lf.carappclient.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/10/20 0020.
 */

public class Constants {
    public static final String BASE_IP = "http://wrjt02.sdqlweb.com/";

    private static final String APP_ID = "wrjt123";
    private static final String APP_SEC = "37b082a279e3b7a9403a16b4bb15073b";

    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String APP_PATH = BASE_PATH + "/wrjt/";
    public static final String IMAGE_PATH = APP_PATH + "/image/";
    public static final String FILE_PROVIDE_PATH = "com.android.ql.lf.carapp.fileProvider";

    public static final String TENCENT_ID = "1106763425";
    public static final String WX_APP_ID = "wx46d6e2abf8aefa8e";
    public static final String BUGLY_APP_ID = "c73cdb2f24";

    private static final String APP_TOKEN = APP_ID + APP_SEC;
    private static String md5Token = null;

    public static String md5Token() {
        try {
            if (!TextUtils.isEmpty(md5Token)) {
                return md5Token;
            }
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] bs = digest.digest(APP_TOKEN.getBytes());
            String hexString = "";
            for (byte b : bs) {
                int temp = b & 255;
                if (temp < 16 && temp >= 0) {
                    hexString = hexString + "0" + Integer.toHexString(temp);
                } else {
                    hexString = hexString + Integer.toHexString(temp);
                }
            }
            return md5Token = hexString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
