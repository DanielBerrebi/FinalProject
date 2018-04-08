package com.example.owner.finalproject;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AddOrEdit extends AppCompatActivity {
    private static final int OFFLINE_NEW_MOVIE = 1;
    private static final int EDIT_MOVIE = 2;
    private static final int ONLINE_NEW_MOVIE = 3;
    private int sendingCode;
    private  EditText editTextUrl;
    private EditText editTextDescription;
    private EditText editTextMovieName;
    private DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editTextUrl = (EditText) findViewById(R.id.editTextURL);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextMovieName = (EditText) findViewById(R.id.editTextMovieName);
        setContentView(R.layout.activity_add_or_edit);
        db=new DataBase(this);
        sendingCode = getIntent().getIntExtra("sendingCode", 1);
        if (sendingCode != OFFLINE_NEW_MOVIE) {
            fillViewsByCode();
        }
    }

    public void showImage(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextURL);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewMovie);
        if(editText.getText().toString().isEmpty()){
            Picasso.get().load(R.drawable.imagenotvalid).placeholder(R.drawable.placeholder).into(imageView);
        }
        else {
            Picasso.get().load(editText.getText().toString()).placeholder(R.drawable.placeholder).error(R.drawable.imagenotvalid).into(imageView);
        }
    }

    public void fillViewsByCode() {
        //when the sending code is 2 or 3 its mean that it editing or adding by api so we an fill the views for the user
        editTextUrl = (EditText) findViewById(R.id.editTextURL);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextMovieName = (EditText) findViewById(R.id.editTextMovieName);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewMovie);
        editTextUrl.setText(getIntent().getStringExtra("movieImgURL"));
        Picasso.get().load(editTextUrl.getText().toString()).placeholder(R.drawable.placeholder).error(R.drawable.imagenotvalid).into(imageView);
        editTextDescription.setText(getIntent().getStringExtra("movieDescription"));
        editTextMovieName.setText(getIntent().getStringExtra("movieName"));
    }

    public void addOrEditOkOnClick(View view) {
        //Creating a new movie and fill its details
        Movie movie = new Movie();
        editTextUrl = (EditText) findViewById(R.id.editTextURL);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextMovieName = (EditText) findViewById(R.id.editTextMovieName);
        String movieName = editTextMovieName.getText().toString();
        if (movieName.matches("")) {
            Toast.makeText(this, R.string.error_empty_movie_name, Toast.LENGTH_SHORT).show();
            return;
        }
        movie.setMovieName(editTextMovieName.getText().toString());
        if(!editTextDescription.getText().toString().isEmpty()) {
            movie.setDescription(editTextDescription.getText().toString());
        }
        if(!editTextUrl.getText().toString().isEmpty()) {
            movie.setImgUrl(editTextUrl.getText().toString());
        }
        //going to the database to add movie or to edit a movie
            if (sendingCode != EDIT_MOVIE) {
                db.addMovie(movie);
            } else {
                movie.setMovieID(getIntent().getIntExtra("movieID", 1));
                db.updateMovie(movie);
            }
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

    }
    public void cancelOnClick(View view){
        super.onBackPressed();
    }
}
