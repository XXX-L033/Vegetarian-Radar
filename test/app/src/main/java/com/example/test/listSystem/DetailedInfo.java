package com.example.test.listSystem;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.DownloadTask;
import com.example.test.R;
import com.example.test.likeSystem.DBPersonRes;
import com.example.test.mapSystem.MapsActivity;
import com.example.test.nevigateSystem.MapDirect;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class DetailedInfo extends AppCompatActivity implements View.OnClickListener,
        OnMapReadyCallback {
    protected DBPersonRes mDBPersonRes;
    private ImageView mRestaurant_back;
    private ImageView mbutton_like;
    private ImageView mvr_google_go;
    private ImageView mbutton_camera;
    public static GoogleMap mGoogleMap;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
    Location mLastLocation;
    String name;
    String location;
    String rating;
    String open;
    public static String lat;
    public static String lng;
    String phone;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.restaurant_info);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        initView();

        TextView mName = findViewById(R.id.vr_name);
        TextView mLocation = findViewById(R.id.vr_location);
        TextView mRating = findViewById(R.id.vr_rating);
        TextView mOpen = findViewById(R.id.vr_open);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        location = intent.getStringExtra("location");
        rating = intent.getStringExtra("rating");
        open = intent.getStringExtra("open");
        lat = intent.getStringExtra("lat");
        lng = intent.getStringExtra("lng");
        phone = intent.getStringExtra("phone");

        mName.setText(name);
        mLocation.setText(location);
        mRating.setText(rating);
        mOpen.setText(open);

        mDBPersonRes = new DBPersonRes(this);
    }

    private void initView() {
        mRestaurant_back = findViewById(R.id.restaurant_back);
        mbutton_like = findViewById(R.id.button_like);
        mvr_google_go = findViewById(R.id.vr_google_go);
        mbutton_camera = findViewById(R.id.button_camera);

        mRestaurant_back.setOnClickListener(this);
        mbutton_like.setOnClickListener(this);
        mvr_google_go.setOnClickListener(this);
        mbutton_camera.setOnClickListener(this);
    }

    public String getDirectionUrl(LatLng current, LatLng destination) {
        String word = "https://maps.googleapis.com/maps/api/directions/";
        String str_dest = "destination=" + destination.latitude + "," + destination.longitude;
        String str_current = "origin=" + current.latitude + "," + current.longitude;
        String key = "key=AIzaSyDJrAUAdMX80XmvBru6WbZ43KynzF1P-cE";
        String parameter = str_current + "&" + str_dest + "&" + "&" + key;
        String output = "json";  //format of output
        String url = word + output + "?" + parameter;

        Log.d("getUrl", url);

        return url;
    }


    private void srceenDialog() {
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());

        if (bitmap != null) {
            //Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show();
            // 获取内置SD卡路径
            File sdCardPath = new File(Environment.getExternalStorageDirectory(), "image");

            if (!sdCardPath.exists()) {
                sdCardPath.mkdir();
            }

            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(sdCardPath, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                Log.d("a7888", "save");

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            this.sendBroadcast(intent);


            Toast.makeText(this, "save successfully", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restaurant_back: //back to the login page
                Intent intent1 = new Intent(this, ListActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.button_like:
                v.setBackgroundColor(Color.rgb(255, 228, 225));
                Toast.makeText(this, "Good Restaurant", Toast.LENGTH_SHORT).show();
                mDBPersonRes.addRes(name, lat, lng, rating, location, open, phone);
                break;
            case R.id.button_camera:
                srceenDialog();
                // Toast.makeText(this, "Save successfully", Toast.LENGTH_SHORT).show();
                break;
            case R.id.vr_google_go:
                MapDirect googleDirection = new MapDirect();
                googleDirection.NaviGoogle(getApplicationContext(), lat, lng);
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        //show the normal map
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Toast.makeText(this,MapsActivity.latLngCurrent+"",Toast.LENGTH_SHORT).show();
        double current_lat = MapsActivity.currentLocate.latitude;
        double current_lng = MapsActivity.currentLocate.longitude;
        double lat_double = Double.parseDouble(lat);
        double lng_double = Double.parseDouble(lng);
        double ave_lat = (current_lat + lat_double) / 2;
        double ave_lng = (current_lng + lng_double) / 2;
        LatLng center = new LatLng(ave_lat, ave_lng);
        LatLng currentLocate = new LatLng(current_lat, current_lng);
        LatLng destLocate = new LatLng(lat_double, lng_double);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocate);
        markerOptions.title("Current Position");

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(destLocate);
        markerOptions2.title("Destination");

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions2);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 10));
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setScrollGesturesEnabled(true);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
        mGoogleMap.animateCamera(zoom);

        String url = getDirectionUrl(currentLocate, destLocate);
        Object[] DataTransfer = new Object[2];
        DataTransfer[0] = mGoogleMap;
        DataTransfer[1] = url;
        Log.d("onClick", url);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(DataTransfer);
    }
}

