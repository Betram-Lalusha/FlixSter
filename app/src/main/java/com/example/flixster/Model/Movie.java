package com.example.flixster.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.LinkedList;
import java.util.List;

@Parcel
public class Movie {
        int id;
        String key;
        String title;
        String overView;
        String posterPath;
        Double voteAverage;
        String backdropPath;

        //required by parcel
        public Movie() {
        }

        //constructor for movie details activity to get key
        public Movie(JSONObject jsonObject, Boolean forStream) throws JSONException {
                this.key = jsonObject.getString("key");
        }

        public Movie(JSONObject jsonObject) throws JSONException {
                this.id = jsonObject.getInt("id");
                this.title = jsonObject.getString("title");
                this.overView = jsonObject.getString("overview");
                this.posterPath = jsonObject.getString("poster_path");
                this.voteAverage = jsonObject.getDouble("vote_average");
                this.backdropPath = jsonObject.getString("backdrop_path");
        }

        public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
                List<Movie> movies = new LinkedList<>();
                for(int i = 0; i < movieJsonArray.length(); i++) {
                        movies.add(new Movie(movieJsonArray.getJSONObject(i)));
                }

                return  movies;
        }

        public static List<Movie> getKeyFromJson(JSONArray movieJsonArray) throws JSONException {
                List<Movie> movies = new LinkedList<>();
                for(int i = 0; i < movieJsonArray.length(); i++) {
                        movies.add(new Movie(movieJsonArray.getJSONObject(i), true));
                        if(!movies.isEmpty()) break; //omly one object is needed
                }

                return  movies;
        }

        public String getTitle() {
                return title;
        }

        public String getOverView() {
                return overView;
        }

        public String getPosterPath() {
                return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
        }

        public String getBackdropPath() {
                return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
        }

        public Double getVoteAverage() {
                return voteAverage;
        }

        public int getId() {
                return id;
        }

        public String getKey() {
                return key;
        }
}
