package com.example.owner.finalproject;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Movie> movies;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
//sending custom adaptor that contain movie name and movie picture if its exist else he will show a no picture picture
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activitylistview, null);
        TextView movieName = (TextView) view.findViewById(R.id.textViewName);
        ImageView photo = (ImageView) view.findViewById(R.id.imageViewPhoto);
        movieName.setText(movies.get(i).getMovieName().toString());
        Picasso.get().load(movies.get(i).getImgUrl()).placeholder(R.drawable.placeholder).error(R.drawable.noposter).into(photo);
        return view;
    }
}