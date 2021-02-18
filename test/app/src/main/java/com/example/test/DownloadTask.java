package com.example.test;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

public class DownloadTask extends AsyncTask<Object, String, String> {
    String googlePlacesData;
    GoogleMap mGoogleMap;
    String url;
    //private DBOpenRes mDBOpenRes;

    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            mGoogleMap = (GoogleMap) params[0];
            url = (String) params[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ShowRoute route = new ShowRoute();
        route.execute(result);
    }
}
