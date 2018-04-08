package com.example.owner.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ArrowKeyMovementMethod;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ShowMovieDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie_details);
        fillFields();
    }
    private void fillFields(){
        //Filling the fields of the selected movie
        TextView textViewName=(TextView)findViewById(R.id.textViewShowMovieName);
        textViewName.setText("Movie name:"+getIntent().getStringExtra("movieName"));
        TextView textViewDescription=(TextView)findViewById(R.id.textViewShowMovieDescription);
        textViewDescription.setText("Description:"+getIntent().getStringExtra("movieDescription"));
        ImageView imageView=(ImageView)findViewById(R.id.imageViewMoviePicture);
        String movieURL=getIntent().getStringExtra("movieImgURL");
        if(movieURL.isEmpty()){
            Picasso.get().load(R.drawable.imagenotvalid).placeholder(R.drawable.placeholder).into(imageView);
        }
        else {
            Picasso.get().load(movieURL.toString()).placeholder(R.drawable.placeholder).error(R.drawable.imagenotvalid).into(imageView);
        }
    }
    public void cancelOnClick(View view){
        super.onBackPressed();
    }
}
