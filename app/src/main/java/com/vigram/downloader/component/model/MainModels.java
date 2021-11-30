/**
 * Class Name: com.vigram.downloader.component.gallery;
 * App Name: Vigram
 * Package: com.vigram.downloader
 * Created: 10:18 PM GMT+7 1/6/2020
 * Developer: Vigram.cc
 * By: Zuhrul Anam (zuhrul@vigram.cc)
 * Edited: 10:19 PM GMT+7 1/6/2020
 */

package com.vigram.downloader.component.model;

import android.webkit.WebView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.ViewPager;

import com.vigram.downloader.MainActivity;

public class MainModels {
    private static final String MainActivityTag = "MainActivity";
    private static final String MenuDownloaderTag = "MenuDownloader";
    private static final String MediaDownloaderTag = "MediaDownloader";
    private static final String GalleryHelperTag = "GalleryHelper";
    private static final String VigramHelperTag = "VigramHelper";
    private static LinearLayoutCompat tagsCheckBoxLayout;
    private static WebView webView;
    private static NestedScrollView nestedScrollView;
    private static boolean tabAvailable;
    private static final int requestRead = 1;
    private static final int requestWrite = 2;
    private static ViewPager viewPager;
    private static ProgressBar vigramProcess;
    private static GridView gridView;
    private static LinearLayoutCompat previewImageVideoLayout;
    private static boolean urlInstagram;
    private static ImageView bntClearText;
    private static String versionFromServer;

    public static boolean isTabAvailable() {
        return tabAvailable;
    }

    public static void setTabAvailable(boolean tabAvailable) {
        MainModels.tabAvailable = tabAvailable;
    }

    public static int getRequestRead() {
        return requestRead;
    }

    public static int getRequestWrite() {
        return requestWrite;
    }

    public static ViewPager getViewPager() {
        viewPager = MainActivity.getViewPager();
        return viewPager;
    }

    public static ProgressBar getVigramProcess() {
        return vigramProcess;
    }

    public static void setVigramProcess(ProgressBar vigramProcess) {
        MainModels.vigramProcess = vigramProcess;
    }

    public static GridView getGridView() {
        return gridView;
    }

    public static void setGridView(GridView gridView) {
        MainModels.gridView = gridView;
    }

    public static String getMainActivityTag() {
        return MainActivityTag;
    }

    public static String getMenuDownloaderTag() {
        return MenuDownloaderTag;
    }

    public static String getMediaDownloaderTag() {
        return MediaDownloaderTag;
    }

    public static boolean isUrlInstagram() {
        return urlInstagram;
    }

    public static void setUrlInstagram(boolean urlInstagram) {
        MainModels.urlInstagram = urlInstagram;
    }

    public static ImageView getBntClearText() {
        return bntClearText;
    }

    public static void setBntClearText(ImageView bntClearText) {
        MainModels.bntClearText = bntClearText;
    }

    public static String getGalleryHelperTag() {
        return GalleryHelperTag;
    }



    public static LinearLayoutCompat getPreviewImageVideoLayout() {
        return previewImageVideoLayout;
    }

    public static void setPreviewImageVideoLayout(LinearLayoutCompat previewImageVideoLayout) {
        MainModels.previewImageVideoLayout = previewImageVideoLayout;
    }

    public static String getVigramHelperTag() {
        return VigramHelperTag;
    }

    public static WebView getWebView() {
        return webView;
    }

    public static void setWebView(WebView webView) {
        MainModels.webView = webView;
    }

    public static NestedScrollView getNestedScrollView() {
        return nestedScrollView;
    }

    public static void setNestedScrollView(NestedScrollView nestedScrollView) {
        MainModels.nestedScrollView = nestedScrollView;
    }


    public static String getVersionFromServer() {
        return versionFromServer;
    }

    public static void setVersionFromServer(String versionFromServer) {
        MainModels.versionFromServer = versionFromServer;
    }

    public static LinearLayoutCompat getTagsCheckBoxLayout() {
        return tagsCheckBoxLayout;
    }

    public static void setTagsCheckBoxLayout(LinearLayoutCompat tagsCheckBoxLayout) {
        MainModels.tagsCheckBoxLayout = tagsCheckBoxLayout;
    }
}
