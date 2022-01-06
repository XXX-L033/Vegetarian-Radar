package com.example.test;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUrl {
    public String readUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        //初始
        HttpURLConnection urlConnection = null;
        try {
            //转成URL类
            URL url = new URL(strUrl); //url class

            // Create an http connection to communicate with url
            //打开URl链接
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connect to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
