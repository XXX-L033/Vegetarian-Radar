package com.example.test.likeSystem;

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

import com.example.test.R;
import com.example.test.listSystem.DetailedInfo;
import com.example.test.listSystem.ListActivity;
import com.example.test.mapSystem.MapsActivity;

import java.util.ArrayList;

public class MyFavourite extends android.app.ListActivity implements View.OnClickListener {
    private ListView lv;
    private ArrayList<PersonLike> likeList;
    public String lat;
    public String lng;
    public String rating;
    public String open;
    public String location;
    public String name;
    public PersonLike pl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_list);

        likeList = new ArrayList<>();
        DBPersonRes mDBPersonRes = new DBPersonRes(this);
        likeList = mDBPersonRes.getAllData();

        lv = (ListView) findViewById(android.R.id.list);
        lv.setAdapter(new BaseAdapter() {

            /*
             * set a adapter for listView
             * getCount()---number getView()--title
             */
            @Override
            public int getCount() {
                return likeList.size();
            }

            @Override
            public Object getItem(int position) {
                // return RestaurantData.get(position);
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v;
                if (convertView == null) {
                    LayoutInflater inflater = MyFavourite.this.getLayoutInflater();
                    v = inflater.inflate(R.layout.list_place, null);
                    //view = View.inflate(getBaseContext(),R.layout.item,null);
                } else {
                    v = convertView;
                }

                //get all the data

                pl = likeList.get(position);

                TextView name = (TextView) v.findViewById(R.id.vr_name);
                TextView location = (TextView) v.findViewById(R.id.vr_location);
                TextView rating = (TextView) v.findViewById(R.id.vr_rating);
                TextView open = (TextView) v.findViewById(R.id.vr_open);


                name.setText(pl.getName());
                location.setText(pl.getLocation());
                rating.setText(pl.getRating());
                open.setText(pl.getOpen());

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        PersonLike pl = likeList.get(position);
                        Intent intentPhone = getIntent();
                        String phone = intentPhone.getStringExtra("phone");
                        Intent intentClike = new Intent(parent.getContext(), DetailedInfo.class);
                        intentClike.putExtra("phone", phone);
                        intentClike.putExtra("name", pl.getName());
                        intentClike.putExtra("location", pl.getLocation());
                        intentClike.putExtra("rating", pl.getRating());
                        intentClike.putExtra("open", pl.getOpen());
                        intentClike.putExtra("lat", pl.getLat());
                        intentClike.putExtra("lng", pl.getLng());
                        startActivity(intentClike);
                        finish();
                    }
                });

                return v;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // register
            case R.id.bottom_bar_image_map:
            case R.id.bottom_bar_text_map:
                Intent intent3 = new Intent(this, MapsActivity.class);
                startActivity(intent3);
                finish();
                Toast.makeText(this, "Map", Toast.LENGTH_SHORT).show();
                break;
            // check login
            case R.id.bottom_bar_text_list:
            case R.id.bottom_bar_image_list:
                Intent intent = new Intent(this, ListActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(this, "List", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_bar_exercises_like:
            case R.id.bottom_bar_image_like:
                Toast.makeText(this, "Favourite Restaurants", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
