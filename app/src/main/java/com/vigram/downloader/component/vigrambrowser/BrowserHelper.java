/**
 * Class Name: com.vigram.downloader.component.vigrambrowser;
 * App Name: Vigram
 * Package: com.vigram.downloader
 * Created: 10:18 PM GMT+7 1/6/2020
 * Developer: Vigram.cc
 * By: Zuhrul Anam (zuhrul@vigram.cc)
 * Edited: 10:19 PM GMT+7 1/6/2020
 */

package com.vigram.downloader.component.vigrambrowser;

import android.annotation.SuppressLint;
import android.webkit.WebView;

public class BrowserHelper {
    private static WebView webView;
    private static BrowserHelper browserHelper;

    private static BrowserHelper newInstance(WebView mWebview) {
        browserHelper = new BrowserHelper();
        webView = mWebview;
        return browserHelper;
    }

    private static BrowserHelper getInstance() {
        return browserHelper;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupBrowser() {
        webView.setWebViewClient(new BrowserClient.webviewClient());
        webView.setWebChromeClient(new BrowserClient.chromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
    }

    private void instanceLoadUrl(String url) {
        webView.loadUrl(url);
    }

    public static void loadUrl(WebView webView, String url) {
        BrowserHelper.newInstance(webView).setupBrowser();
        BrowserHelper.getInstance().instanceLoadUrl(url);
    }

    public static void goBack() {
        if(webView.canGoBack()) {
            webView.goBack();
        }
    }

}
