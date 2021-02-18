package com.example.test.listSystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.likeSystem.MyFavourite;
import com.example.test.R;
import com.example.test.mapSystem.DBOpenRes;
import com.example.test.mapSystem.MapsActivity;
import com.example.test.mapSystem.VegeRestaurant;

import java.util.ArrayList;

public class ListActivityDis extends android.app.ListActivity implements View.OnClickListener {
    private ListView lv;
    private ArrayList<VegeRestaurant> resListDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_distance);

        resListDis = new ArrayList<>();
        DBOpenRes mDBOpenRes = new DBOpenRes(this);
        resListDis = mDBOpenRes.getAllDataInDis();

        lv = (ListView) findViewById(android.R.id.list);
        lv.setAdapter(new BaseAdapter() {
            /*
             * set a adapter for listView
             * getCount()---number getView()--title
             */
            @Override
            public int getCount() {
                return resListDis.size();
            }

            @Override
            public Object getItem(int position) {
                // return RestaurantData.get(position);
                return null;
            }

            @Override
            public long getItemId(int position) {
                // return position;
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v;
                if (convertView == null) {
                    LayoutInflater inflater = ListActivityDis.this.getLayoutInflater();
                    v = inflater.inflate(R.layout.list_place, null);
                    //view = View.inflate(getBaseContext(),R.layout.item,null);
                } else {
                    v = convertView;
                }

                //get all the data

                VegeRestaurant vr = resListDis.get(position);

                TextView name = (TextView) v.findViewById(R.id.vr_name);
                TextView location = (TextView) v.findViewById(R.id.vr_location);
                TextView rating = (TextView) v.findViewById(R.id.vr_rating);
                TextView open = (TextView) v.findViewById(R.id.vr_open);
                TextView distance = (TextView) v.findViewById(R.id.vr_dis);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        VegeRestaurant vr = resListDis.get(position);
                        Intent intentPhone = getIntent();
                        String phone = intentPhone.getStringExtra("phone");
                        Intent intentClike = new Intent(parent.getContext(), DetailedInfo.class);
                        intentClike.putExtra("phone", phone);
                        intentClike.putExtra("name", vr.getName());
                        intentClike.putExtra("location", vr.getLocation());
                        intentClike.putExtra("rating", vr.getRating());
                        intentClike.putExtra("open", vr.getOpen());
                        intentClike.putExtra("lat", vr.getLat());
                        intentClike.putExtra("lng", vr.getLng());
                        startActivity(intentClike);
                        finish();
                    }
                });

                name.setText(vr.getName());
                location.setText(vr.getLocation());
                rating.setText(vr.getRating());
                open.setText(vr.getOpen());
                distance.setText(vr.getDistance());
                return v;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // map
            case R.id.bottom_bar_image_map:
            case R.id.bottom_bar_text_map:
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(this, "Map", Toast.LENGTH_SHORT).show();
                break;
            //list interface
            case R.id.bottom_bar_text_list:
            case R.id.bottom_bar_image_list:
                Toast.makeText(this, "List", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_bar_exercises_like:
            case R.id.bottom_bar_image_like:
                Intent intent3 = new Intent(this, MyFavourite.class);
                startActivity(intent3);
                finish();
                Toast.makeText(this, "Favourite Restaurants", Toast.LENGTH_SHORT).show();
                break;
            case R.id.show_rating:
                Intent intentPhone = getIntent();
                String phone = intentPhone.getStringExtra("phone");
                Intent intent2 = new Intent(this, ListActivity.class);
                intent2.putExtra("phone", phone);
                startActivity(intent2);
                finish();
                Toast.makeText(this, "Ranking by Rating", Toast.LENGTH_SHORT).show();
                break;
            case R.id.show_distance:
                Toast.makeText(this, "Ranking by Distance", Toast.LENGTH_SHORT).show();
        }
    }
}
