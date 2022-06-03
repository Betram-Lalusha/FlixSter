package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.LinkedList;
import java.util.List;

import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    //Movie to display
    Movie movie;
    List<Movie> movies;

    //the view objects
    TextView tvTitle;
    TextView tvOverView;
    ImageView posterDetail;
    RatingBar rbVoteAverage;

    public static final  String TAG = "MovieDetailActivity";
    String VIDEO_ENDPOINT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //initialise movies
        movies = new LinkedList<>();

        //resolve the view objects
        tvTitle = (TextView) findViewById(R.id.tvTitle2);
        tvOverView = (TextView) findViewById(R.id.tvOverView);
        posterDetail = (ImageView) findViewById(R.id.posterDetail);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s", movie.getTitle()));
        this.VIDEO_ENDPOINT = "https://api.themoviedb.org/3/movie/" + String.valueOf(movie.getId()) + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        //set title and overview
        tvTitle.setText(movie.getTitle());
        tvOverView.setText(movie.getOverView());

        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage);

        //display image
        Glide.with(this).load(movie.getBackdropPath()).into(posterDetail);

        //get video endpoint
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(VIDEO_ENDPOINT, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    movies.addAll(Movie.getKeyFromJson(jsonArray));
                    Log.i(TAG, "movies " + movies.size());
                } catch(JSONException e) {
                    Log.e(TAG, "failed to get results array", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    /**
     *go to movie trailer when poster is clicked
     */
    public void goToTrailer(View v) {
        movie = movies.get(0);
        //prevent going to trailer if no youtube key is found
        if(movie.getKey().isEmpty()) return;

        Intent intent = new Intent(this, MovieTrailerActivity.class);
        intent.putExtra("key", movie.getKey());
        startActivity(intent);
    }
}