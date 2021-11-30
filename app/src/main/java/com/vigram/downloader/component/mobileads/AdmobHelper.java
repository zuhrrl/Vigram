package com.vigram.downloader.component.mobileads;

import android.app.ActivityManager;
import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class AdmobHelper {
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private Context mContext;
    private String mTestDevice;
    private ActivityManager.MemoryInfo memoryInfo;

    // New instance this class

    public static AdmobHelper newInstance() {
        AdmobHelper admobHelper = new AdmobHelper();
        return admobHelper;
    }

    /* Before load adview please call this method first
    * setComponent(String testDevice, Context context)
    * */

    public void setComponent(String testDevice, Context context) {
        mTestDevice = testDevice;
        mContext = context;
    }
    // Method to load Adview ads Admob
    public void loadAdView(AdView adView) {
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });
        adView.loadAd(new AdRequest.Builder().addTestDevice(mTestDevice)
                .build());
    }

    // Method to load Interstitial Ads

    public void loadInterstitial(Context context, String adUnit, String testDevice) {
        InterstitialAd mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(adUnit);
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();

            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();

            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

            }
        });

        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(testDevice)
                .addTestDevice("AA631DE3EE099766ABCAD2332EFDDDB6")
                .build());

    }

}
