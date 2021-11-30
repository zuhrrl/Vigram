/*
  Class Name: com.vigram.downloader.component.network;
  App Name: Vigram
  Package: com.vigram.downloader
  Created: 10:18 PM GMT+7 1/6/2020
  Developer: Vigram.cc
  By: Zuhrul Anam (zuhrul@vigram.cc)
  Edited: 10:19 PM GMT+7 1/6/2020
 */

package com.vigram.downloader.component.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkCheck {
    public static boolean Online(Context context) {
        boolean connected = false;
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    connected = true;
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    connected = true;
                }
            } else {
                connected = false;
            }
        }
        catch (Exception e) {
        }

        return connected;
    }
}
