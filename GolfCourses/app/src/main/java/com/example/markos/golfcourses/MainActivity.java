package com.example.markos.golfcourses;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.ItemClicked{

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;
    private String URLPrefix = "http://ptm.fi/jamk/android/golfcourses/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView toolbarImage = (ImageView) findViewById(R.id.app_bar_image);
        toolbarImage.setImageResource(R.drawable.title_image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        collapsingToolbarLayout.setTitle("Golf Courses");

        ParseData data = new ParseData(this);
        data.execute("http://ptm.fi/jamk/android/golfcourses/golf_courses.json");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);
        recyclerManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerManager);
    }

    @Override
    public void itemHasBeenClicked(SportPlace detailInfo) {
        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra("name", detailInfo.getField());
        intent.putExtra("lat", detailInfo.getLatitude());
        intent.putExtra("lng", detailInfo.getLongitude());
        intent.putExtra("address", detailInfo.getAddress());
        intent.putExtra("phone", detailInfo.getPhoneNumber());
        intent.putExtra("email", detailInfo.getEmail());
        intent.putExtra("web", detailInfo.getWeb());
        intent.putExtra("description", detailInfo.getDescription());
        intent.putExtra("type", detailInfo.getType());
        intent.putExtra("picture", detailInfo.getPricturePath());

        startActivity(intent);
    }

    public class ParseData extends AsyncTask<String, Void, Void> {

        private JSONArray array = null;
        private RecyclerAdapter.ItemClicked activity;

        public ParseData(RecyclerAdapter.ItemClicked parentActivity){
            activity = parentActivity;
        }

        @Override
        protected Void doInBackground(String... dataUrl) {
            HttpURLConnection urlConnection = null;
            JSONObject json;
            try {
                URL url = new URL(dataUrl[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                json = new JSONObject(stringBuilder.toString());
                array = json.getJSONArray("kentat");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            recyclerAdapter = new RecyclerAdapter(transformArrayToList(), activity);
            recyclerView.setAdapter(recyclerAdapter);
        }

        private ArrayList<SportPlace> transformArrayToList(){
            ArrayList<SportPlace> parsedDataList = new ArrayList<>();
            SportPlace temp;
            JSONObject object;

            try {
                for (int i = 0; i < array.length(); i++) {
                    object = array.getJSONObject(i);
                    temp = new SportPlace(object.getString("Tyyppi"), object.getDouble("lat"), object.getDouble("lng"),
                            object.getString("Kentta"), object.getString("Osoite"), object.getString("Puhelin"),
                            object.getString("Sahkoposti"), object.getString("Webbi"), URLPrefix+object.getString("Kuva"), object.getString("Kuvaus"));
                    parsedDataList.add(temp);
                }
            }catch (JSONException e){
            }

            return parsedDataList;
        }
    }
}
