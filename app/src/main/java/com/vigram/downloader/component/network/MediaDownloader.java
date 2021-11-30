/**
 * Class Name: com.vigram.downloader.component.network;
 * App Name: Vigram
 * Package: com.vigram.downloader
 * Created: 10:18 PM GMT+7 1/6/2020
 * Developer: Vigram.cc
 * Content: To Download Media from Instagram
 * By: Zuhrul Anam (zuhrul@vigram.cc)
 * Edited: 10:19 PM GMT+7 1/6/2020
 */

package com.vigram.downloader.component.network;

import android.app.Activity;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.vigram.downloader.component.gallery.GalleryHelper;
import com.vigram.downloader.component.model.MainModels;
import com.vigram.downloader.ui.MenuGallery;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class MediaDownloader extends AsyncTask<String, String, String> {
    private static String mediaPageId;
    String fileName;
    private static Context mContext;
    private static boolean vigramVideo;
    private static Activity sActivity;

    //Intialize Instance

    public static MediaDownloader newInstance(Context context, Activity activity) {
        MediaDownloader mediaDownloader = new MediaDownloader();
        mContext = context;
        sActivity = activity;
        return mediaDownloader;
    }


    @Override
    protected String doInBackground(String... strings) {
        File scrapedMedia = null;
        if(strings[0].startsWith("https://")) {
            // Get page ID and Image Name
            String[] fileNames = mediaPageId.split("/");
            fileName = "Vigram_"+fileNames[4];
        }
        File file = new File("/storage/emulated/0/"+mContext.getPackageName());
        if(vigramVideo) {
            scrapedMedia = new File("/storage/emulated/0/"+mContext.getPackageName()+"/"+fileName+".mp4");
        }

        if(!vigramVideo) {
            scrapedMedia = new File("/storage/emulated/0/"+mContext.getPackageName()+"/"+fileName+".jpg");
        }

        if(!file.exists()) {
            file.mkdir();
        }
        downloadMedia(strings[0], scrapedMedia);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        invalidateGallery(mContext, sActivity);
    }


    // Refresh gallery
    public void invalidateGallery(Context context, Activity activity) {
        String[] fileNames = mediaPageId.split("/");
        File media = null;
        fileName = "Vigram_"+fileNames[4];
        if(vigramVideo) {
            media = new File("/storage/emulated/0/"+mContext.getPackageName()+"/"+fileName+".mp4");

        }
        else {
            media = new File("/storage/emulated/0/"+mContext.getPackageName()+"/"+fileName+".jpg");

        }
        MediaScannerConnection mediaScannerConnection = new MediaScannerConnection(context, new MediaScannerConnection.MediaScannerConnectionClient() {
            @Override
            public void onMediaScannerConnected() {

            }

            @Override
            public void onScanCompleted(String path, Uri uri) {


            }
        });
        mediaScannerConnection.scanFile(context, new String[]{
                media.toString()
        }, null, (path, uri) -> {

        });

        new Handler().postDelayed(() -> {
            Log.d(MainModels.getMediaDownloaderTag(), "show yay context");
            MenuGallery.refreshGallery(activity, context);
            GalleryHelper.newInstance(context).notifyDataSetChanged();
            Toast.makeText(context, "Yay, Saved on Gallery ^_^", Toast.LENGTH_SHORT).show();
        }, 4000);
    }

    private static void downloadMedia(String url, File outputFile) {
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch(FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public String getFileName() {
        return fileName;
    }


    public static void setMediaPageId(String mediaPageId) {
        MediaDownloader.mediaPageId = mediaPageId;
    }

    public void setVigramVideo(boolean vigramVideo) {
        MediaDownloader.vigramVideo = vigramVideo;
    }
}
