package com.example.imdbapi.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.imdbapi.Model.Movie;
import com.example.imdbapi.R;

import java.io.Serializable;

public class MovieDetailActivity extends AppCompatActivity implements Serializable{
    final private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";
    private ImageView mBanner;
    private ImageView mCover;
    private TextView mMovieTitle;
    private TextView mOverview;
    private TextView mRating;
    private TextView mRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mBanner = findViewById(R.id.image_view_banner);
        mCover = findViewById(R.id.image_view_cover);
        mMovieTitle = findViewById(R.id.text_view_movie_title);
        mOverview = findViewById(R.id.text_view_overview);
        mRating = findViewById(R.id.text_view_rating);
        mRelease = findViewById(R.id.text_view_release);

        Intent intent = getIntent();
        final Movie movie = (Movie) intent.getSerializableExtra("movie");

        mMovieTitle.setText(movie.getTitle());
        mOverview.setText(movie.getOverview());
        mRating.setText(String.valueOf(movie.getRating()));
        mRelease.setText(movie.getReleaseDate());

        String TopImageUrl = IMAGE_BASE_URL + movie.getBackdropPath();
        String coverUrl = IMAGE_BASE_URL + movie.getPosterPath();

        Glide.with(mBanner).load(TopImageUrl).into(mBanner);
        Glide.with(mCover).load(coverUrl).into(mCover);

        mOverview.setMovementMethod(new ScrollingMovementMethod());
    }
}
