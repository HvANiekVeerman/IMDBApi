package com.example.imdbapi.API;

import java.util.List;

import com.example.imdbapi.Model.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieResponse {
    @SerializedName("results")
    @Expose
    private List<Movie> mMovies;

    public List<Movie> getMovies() {
        return mMovies;
    }

}
