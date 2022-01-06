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

public class ListActivity extends android.app.ListActivity implements View.OnClickListener {
    private ListView lv;
    private ArrayList<VegeRestaurant> resList;
    public String lat;
    public String lng;
    public String rating;
    public String location;
    public String name;
    public VegeRestaurant vr;
    public ViewHolder holder;

    static class ViewHolder{
        TextView name;
        TextView location;
        TextView rating;
        TextView open;
        TextView distance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        resList = new ArrayList<>();
        DBOpenRes mDBOpenRes = new DBOpenRes(this);
        resList = mDBOpenRes.getAllData();

        lv = (ListView) findViewById(android.R.id.list);
        lv.setAdapter(new BaseAdapter() {

            /*
             * set a adapter for listView
             * getCount()---number getView()--title
             */
            @Override
            public int getCount() {
                return resList.size();
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
                /*if convertView == null, create a new page
                 * convertView != show up
                 */
                if (convertView == null) { //same page, create a page
                    LayoutInflater inflater = ListActivity.this.getLayoutInflater();
                    v = inflater.inflate(R.layout.list_place, null);
                    //view = View.inflate(getBaseContext(),R.layout.item,null);
                } else {
                    v = convertView;
                }

                if(holder == null){
                    holder = new ViewHolder();
                    holder.name = (TextView) v.findViewById(R.id.vr_name);
                    holder.location = (TextView) v.findViewById(R.id.vr_location);
                    holder.rating = (TextView) v.findViewById(R.id.vr_rating);
                    holder.open = (TextView) v.findViewById(R.id.vr_open);
                    holder.distance = (TextView) v.findViewById(R.id.vr_dis);
                }

                //get all the data

                vr = resList.get(position);

                holder.name.setText(vr.getName());
                holder.location.setText(vr.getLocation());
                holder.rating.setText(vr.getRating());
                holder.open.setText(vr.getOpen());
                holder.distance.setText(vr.getDistance());


                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        VegeRestaurant vr = resList.get(position);
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

                return v;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // map interface
            case R.id.bottom_bar_image_map:
            case R.id.bottom_bar_text_map:
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(this, "Map", Toast.LENGTH_SHORT).show();
                break;
            // list interface
            case R.id.bottom_bar_text_list:
            case R.id.bottom_bar_image_list:
                Toast.makeText(this, "List", Toast.LENGTH_SHORT).show();
                break;
            // like interface
            case R.id.bottom_bar_exercises_like:
            case R.id.bottom_bar_image_like:
                Intent intent3 = new Intent(this, MyFavourite.class);
                startActivity(intent3);
                finish();
                Toast.makeText(this, "Favourite Restaurants", Toast.LENGTH_SHORT).show();
                break;
            case R.id.show_rating:
                Toast.makeText(this, "Ranking by Rating", Toast.LENGTH_SHORT).show();
                break;
            case R.id.show_distance:
                Intent intentPhone = getIntent();
                String phone = intentPhone.getStringExtra("phone");
                Intent intent2 = new Intent(this, ListActivityDis.class);
                intent2.putExtra("phone", phone);
                startActivity(intent2);
                finish();
                Toast.makeText(this, "Ranking by Distance", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

