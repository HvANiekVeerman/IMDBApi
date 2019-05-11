package com.example.imdbapi.API;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    private static MovieRepository sRepository;
    private TmdbApi mTmdbApi;

    private MovieRepository(TmdbApi api) {
        this.mTmdbApi = api;
    }

    public static MovieRepository getInstance() {
        if (sRepository == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            sRepository = new MovieRepository(retrofit.create(TmdbApi.class));
        }
        return sRepository;
    }

    public void getMovies(final OnGetMoviesCallback callback, int ReleaseYear) {
        mTmdbApi.getPopularMovies("APIKEY", ReleaseYear, LANGUAGE, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call,
                                           @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                callback.onSuccess(moviesResponse.getMovies());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

}
