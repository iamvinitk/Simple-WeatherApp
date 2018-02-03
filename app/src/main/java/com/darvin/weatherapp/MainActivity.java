package com.darvin.weatherapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView location, main, desc, temp;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image_view);
        location = findViewById(R.id.locationtext);
        main = findViewById(R.id.maintext);
        desc = findViewById(R.id.descriptiontext);
        temp = findViewById(R.id.temptext);
        progressBar = findViewById(R.id.progress_bar);

        new FetchData().execute();
    }

    @SuppressLint("StaticFieldLeak")
    class FetchData extends AsyncTask<Object, Weather, Weather> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);

        }


        @Override
        protected Weather doInBackground(Object... objects) {
            try {
                return NetworkStuff.getData("Paris");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return new Weather();
            }
        }

        @Override
        protected void onPostExecute(Weather weather) {
            progressBar.setVisibility(View.GONE);
            Glide.with(MainActivity.this).load("http://openweathermap.org/img/w/" + weather.getIcon() + ".png").into(imageView);
            location.setText(weather.getLocation());
            main.setText(weather.getMain());
            temp.setText(weather.getTemp());
            desc.setText(weather.getDescription());

        }
    }
}




















