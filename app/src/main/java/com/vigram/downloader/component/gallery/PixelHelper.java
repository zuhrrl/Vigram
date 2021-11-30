/**
 * Class Name: com.vigram.downloader.component.gallery;
 * App Name: Vigram
 * Package: com.vigram.downloader
 * Created: 10:18 PM GMT+7 1/6/2020
 * Developer: Vigram.cc
 * By: Zuhrul Anam (zuhrul@vigram.cc)
 * Edited: 10:19 PM GMT+7 1/6/2020
 */

package com.vigram.downloader.component.gallery;

import android.content.Context;

public class PixelHelper {
    private static int Dp;

    public static int getDp(int px, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        float dp = px / density;
        Dp = Math.round(dp);
        return Dp;
    }

    public static int getPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        float px = dp * density;
        Px = Math.round(px);
        return Px;
    }

    private static int Px;
}
