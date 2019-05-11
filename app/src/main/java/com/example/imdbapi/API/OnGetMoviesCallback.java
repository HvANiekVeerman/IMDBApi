package com.example.imdbapi.API;

import com.example.imdbapi.Model.Movie;

import java.util.List;

public interface OnGetMoviesCallback {

    void onSuccess(List<Movie> movies);
    void onError();

}
