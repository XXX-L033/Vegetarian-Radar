package com.example.test.mapSystem;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.test.DownloadUrl;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

//async task, <params, progress, result>
public class GetNearbyRes extends AsyncTask<Object, String, String> { //map to show all the restaurants
    //background thread do the asyncho tasks
    String googlePlacesData;
    GoogleMap mGoogleMap;
    String url;
    List<HashMap<String, String>> nearbyPlacesList;

    @Override
    //string - 和result类型保持一致
    protected String doInBackground(Object... params) { //background threads do
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
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        DataParser dataParser = new DataParser();
        nearbyPlacesList = dataParser.parse(result); //arraylist contains information of all restaurants
        ShowNearbyPlaces(nearbyPlacesList);
        getDetailedInfo(nearbyPlacesList);
        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
    }

    //add marker to nearby vege restaurants
    private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            Log.d("onPostExecute", "Entered into showing locations");
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName);
            mGoogleMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            //move map camera
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        }
    }

    public void getDetailedInfo(List<HashMap<String, String>> nearbyPlacesList) {
        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            HashMap<String, String> googlePlaceDetail = nearbyPlacesList.get(i);
            String mLatitude = googlePlaceDetail.get("lat");
            String mLongitude = googlePlaceDetail.get("lng");
            String mDistance = googlePlaceDetail.get("distance");
            String mResName = googlePlaceDetail.get("place_name");
            String mOpenNow = googlePlaceDetail.get("openTime");
            String mCode = googlePlaceDetail.get("code");
            String mRating = googlePlaceDetail.get("rating");
            String mLocation = googlePlaceDetail.get("vicinity");
            MapsActivity.mDBOpenRes.addRes(mResName, mLatitude, mLongitude, mDistance, mCode, mRating, mLocation, mOpenNow);
        }
    }
}