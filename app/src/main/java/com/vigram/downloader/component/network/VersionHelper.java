package com.vigram.downloader.component.network;

import android.content.Context;
import android.os.AsyncTask;

import com.vigram.downloader.component.model.MainModels;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class VersionHelper extends AsyncTask<String, String, String> {
    private String result;

    public static VersionHelper newInstance() {
        VersionHelper versionHelper = new VersionHelper();
        return versionHelper;
    }

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
        MainModels.setVersionFromServer(s);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public static String getVersionServer(String json) {
        String data = "";
        if(json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                data = jsonObject.getString("version");

            }
            catch (Exception e) {

            }
        }
        return data;
    }
}
