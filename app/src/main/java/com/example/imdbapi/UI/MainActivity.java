package com.example.imdbapi.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imdbapi.Model.Movie;
import com.example.imdbapi.API.MovieRepository;
import com.example.imdbapi.API.OnGetMoviesCallback;
import com.example.imdbapi.R;
import com.example.imdbapi.Model.RecyclerTouchListener;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable {

    final private int NUMBER_OF_COLUMNS = 2;
    private MovieRepository mMovieRepository;
    private List<Movie> mMovieList;
    private TextView mYearInput;
    RecyclerViewAdapter mAdapter;
    RecyclerView mRecyclerView;
    Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieRepository = MovieRepository.getInstance();
        mYearInput = findViewById(R.id.edit_text_year_input);
        mSubmitButton = findViewById(R.id.btn_submit);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String value = mYearInput.getText().toString();
                int release = Integer.parseInt(value);

                mMovieRepository.getMovies(new OnGetMoviesCallback() {
                    @Override
                    public void onSuccess(List<Movie> movies) {
                        mMovieList = movies;
                        updateUI();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(MainActivity.this,
                                "Please check your internet connection.", Toast.LENGTH_SHORT)
                                .show();
                    }
                }, release);
            }
        });

        mRecyclerView = findViewById(R.id.main_recycler_view);
        int numberOfColumns = NUMBER_OF_COLUMNS;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView,
                new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                Movie movie = mMovieList.get(position);
                intent.putExtra("movie", movie);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new RecyclerViewAdapter(mMovieList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(mMovieList);
        }
    }
}
