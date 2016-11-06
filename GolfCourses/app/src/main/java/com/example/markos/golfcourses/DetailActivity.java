package com.example.markos.golfcourses;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.markos.golfcourses.RecyclerAdapter.ImageDownload;

/**
 * Created by Markos on 5. 11. 2016.
 */

public class DetailActivity extends AppCompatActivity {

    private double latitude;
    private double longitude;
    private String webURL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity_layout);

        ImageView detailImage = (ImageView)findViewById(R.id.detailImage);
        TextView detailName = (TextView)findViewById(R.id.detailName);
        TextView detailType = (TextView)findViewById(R.id.detailType);
        TextView detailAddress = (TextView)findViewById(R.id.detailAddress);
        TextView detailGPS = (TextView)findViewById(R.id.detailGPS);
        TextView detailPhone = (TextView)findViewById(R.id.detailPhone);
        TextView detailEmail = (TextView)findViewById(R.id.detailEmail);
        TextView detailWeb = (TextView)findViewById(R.id.detailWeb);
        TextView detailDescription = (TextView)findViewById(R.id.detailDescription);

        Bundle extras = getIntent().getExtras();

        String name = extras.getString("name");
        double lat = extras.getDouble("lat");
        double lng = extras.getDouble("lng");
        String phone = extras.getString("phone");
        String address = extras.getString("address");
        String email = extras.getString("email");
        String web = extras.getString("web");
        String description  = extras.getString("description");
        String type = extras.getString("type");
        String picture = extras.getString("picture");

        latitude = lat;
        longitude = lng;
        webURL = web;

        detailName.setText(name);
        detailGPS.setText(lat+", "+lng);
        detailType.setText(type);
        detailAddress.setText(address);
        detailPhone.setText(phone);
        detailEmail.setText(email);
        detailWeb.setText(web);
        detailDescription.setText(description);

        ImageDownload imageDownload = new ImageDownload(detailImage);
        imageDownload.execute(picture);
    }

    public void mapIntent(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:"+latitude+", "+longitude));
        startActivity(intent);
    }

    public void webPageOpen(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(webURL));
        startActivity(intent);
    }
}
