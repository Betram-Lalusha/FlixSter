package com.example.flixster.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.LinkedList;
import java.util.List;

@Parcel
public class Movie {
        String title;
        String overView;
        String posterPath;
        String backdropPath;

        //required by parcel
        public Movie() {
        }

        public Movie(JSONObject jsonObject) throws JSONException {
                this.title = jsonObject.getString("title");
                this.overView = jsonObject.getString("overview");
                this.posterPath = jsonObject.getString("poster_path");
                this.backdropPath = jsonObject.getString("backdrop_path");
        }

        public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
                List<Movie> movies = new LinkedList<>();
                for(int i = 0; i < movieJsonArray.length(); i++) {
                        movies.add(new Movie(movieJsonArray.getJSONObject(i)));
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
}
