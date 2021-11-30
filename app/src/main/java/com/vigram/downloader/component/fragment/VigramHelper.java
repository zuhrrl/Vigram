/**
 * Class Name: com.vigram.downloader.component.fragment;
 * App Name: Vigram
 * Content: Core Downloader
 * Package: com.vigram.downloader
 * Created: 10:18 PM GMT+7 1/6/2020
 * Developer: Vigram.cc
 * By: Zuhrul Anam (zuhrul@vigram.cc)
 * Edited: 10:19 PM GMT+7 1/6/2020
 */

package com.vigram.downloader.component.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.vigram.downloader.component.model.MainModels;
import com.vigram.downloader.component.network.MediaDownloader;
import com.vigram.downloader.ui.MenuDownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class VigramHelper {
    private static Context mContext;

    public static VigramHelper newInstance(Context context) {
        VigramHelper vigramHelper = new VigramHelper();
        mContext = context;
        return vigramHelper;
    }

    public static Context getmContext() {
        return mContext;
    }

    public static void fetchImageVideos(String url){
        fetchImageVideos fetchImageVideos = new fetchImageVideos();
        String[] splitMedia = MenuDownloader.getMedia_url_input().getText().toString().split("/");
        String media_url = "https://www.instagram.com/p/"+splitMedia[4];
        fetchImageVideos.execute(media_url+"?__a=1");
    }

    public static void glideMedia(String json) throws JSONException {
        // load json from ig
        Log.d(MainModels.getVigramHelperTag(), json);
        JSONObject jsonObject = new JSONObject(json);
        JSONObject graphql = jsonObject.getJSONObject("graphql");
        JSONObject media = graphql.getJSONObject("shortcode_media");
        JSONObject media_caption = media.getJSONObject("edge_media_to_caption");
        JSONArray media_caption_edges = media_caption.getJSONArray("edges");
        JSONObject media_caption_edges_0 = media_caption_edges.getJSONObject(0);
        JSONObject media_caption_edge_0_node = media_caption_edges_0.getJSONObject("node");
        String media_caption_text = media_caption_edge_0_node.getString("text");
        // Username
        JSONObject media_owner = media.getJSONObject("owner");
        String username = media_owner.getString("username");
        String mediaImage = media.getString("display_url");
        Glide.with(getmContext()).load(mediaImage).into(MenuDownloader.getImageFetched());
        if(media != null) {
            if(mediaImage != null) {
                MainModels.getVigramProcess().setVisibility(View.GONE);
                MainModels.getPreviewImageVideoLayout().setVisibility(View.GONE);
                MenuDownloader.setVigramVideo(false);
                MenuDownloader.setMedia_url(mediaImage);
                if(media_caption_text != null) {
                    MenuDownloader.setPostDesription(media_caption_text);
                    MenuDownloader.setPostUsername("@"+username);
                }
            }

            if(media.getString("video_url") != null) {
                MainModels.getVigramProcess().setVisibility(View.GONE);
                MenuDownloader.setVigramVideo(true);
                MenuDownloader.setMedia_url(media.getString("video_url"));

            }
        }

        else {

        }

    }


    public static class fetchImageVideos extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpsURLConnection httpsURLConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.connect();
                InputStream stream = httpsURLConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpsURLConnection != null) {
                    httpsURLConnection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MenuDownloader.setTempJson(s);
        }
    }

    public static class ConfigHelper {
        private static SharedPreferences mSharedPreferences;
        private static SharedPreferences.Editor mEditor;
        private static String mKey;
        private static String mData;
        private static String savedData, proxyAddressSaved, proxyPortSaved;


        // Setup data values

        public static void saveConfig(String key, String data, Context mContext, int neverMind) {
            mKey = key;
            mData = data;
            mSharedPreferences = mContext.getSharedPreferences("valueSaved", 0);
            mEditor = mSharedPreferences.edit();
            if(neverMind == 0 ) {
                mEditor.putBoolean(mKey, true);
            }
            else {
                mEditor.putString(mKey, mData);
            }
            mEditor.apply();

        }

        public static String getDataConfig(String key, Context context) {
            mSharedPreferences = context.getSharedPreferences("valueSaved", 0);
            savedData = mSharedPreferences.getString(key, null);
            return savedData;
        }

        public static boolean getNeverMind(Context context) {
            mSharedPreferences = context.getSharedPreferences("valueSaved", 0);
            boolean neverMind;
            neverMind = mSharedPreferences.getBoolean("neverMind", false);
            return neverMind;
        }

        public static String getProxyAddressSaved(Context context) {
            proxyAddressSaved = getDataConfig("proxy", context);
            return proxyAddressSaved;
        }


    }

    public static void rateVigram(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id="+context.getPackageName()));
        context.startActivity(intent);
    }

}
