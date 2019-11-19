package com.example.ckraft.myapplication;

import android.app.Application;
import android.text.TextUtils;

import java.net.CookieManager;
import java.net.HttpURLConnection;

/**
 * Created by ckraft on 7/18/2016.
 */
public class AppContext extends Application {

    private static String BASE_URL = "http://quirkboard.net";

    public static String getBASE_URL(){
        return BASE_URL;
    }






    private static CookieManager cookieManager = new CookieManager();

    public static CookieManager getCookieManager() {
        return cookieManager;
    }

    public static void setCookieManager(CookieManager cookieManager) {
        cookieManager = cookieManager;
    }

    public static HttpURLConnection applyCookie(HttpURLConnection connection){
        CookieManager cookieManager = getCookieManager();
        if(cookieManager.getCookieStore().getCookies().size() > 0)
        {
            //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
            connection.setRequestProperty("Cookie",
                    TextUtils.join(";",  cookieManager.getCookieStore().getCookies()));
        }
        return connection;
    }

}
