package com.example.owner.finalproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class movieOnlineAdapter implements HttpRequest.Callbacks{
    private View view;
    private Context context;
    private ArrayList <Movie> movies;
    public  movieOnlineAdapter(View view,Context context)    {
        this.view=view;
        this.context=context;
    }
        // Read all movies from the server by the search string:
        public void readAllMoviesBySearch(String search) {
            HttpRequest httpRequest = new HttpRequest(this);
            httpRequest.execute("http://api.themoviedb.org/3/search/movie?api_key=fd5e28b290b77700d90e4dabfbe8dcd4&query="+search.toString());
        }

        // Got all movie that contain the string from the server - set all in the ListView:
        public void onSuccess(String downloadedText) {

            try {

                // Translate all to a JSON array:
                JSONObject  jsonObject1=new JSONObject(downloadedText);

                // Create a new array list to hold all movies:
                movies = new ArrayList<Movie>();
                //results its an Array that contain all the movies that we find in the search
                JSONArray jsonArray = jsonObject1.getJSONArray("results");
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                //Starting the adding the movies to the movies array
                for (int j = 0; j < jsonArray.length(); j++) {
                    //Show the progress of the adding movies to the user
                    double progress=((double)j/(double)jsonArray.length()*100);
                    progressDialog.setProgress((int)progress);
                    JSONObject jsonObjectResult = jsonArray.getJSONObject(j);
                    String name = jsonObjectResult.getString("title");
                    String description = jsonObjectResult.getString("overview");
                    String imgurl = jsonObjectResult.getString("poster_path");
                    Movie movie = new Movie();
                    movie.setMovieName(name);
                    movie.setDescription(description);
                    //For the link of the picture we need to add the string below so its will be a full link
                    movie.setImgUrl("http://image.tmdb.org/t/p/w500/"+imgurl);
                    // Add the movie object into the movies array:
                    movies.add(movie);
                }
                progressDialog.setProgress(100);
                progressDialog.dismiss();
                // Set adapter for the ListView:
                CustomAdapter customAdapter = new CustomAdapter(context,movies);
                ListView listView=(ListView)view;
                // Display all:
                listView.setAdapter(customAdapter);

                //Adding event listener to the ListView
                listView.setOnItemClickListener(
                        //On item click we will send the movies details to the add movie page and navigate the user there
                        new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent=new Intent(context,AddOrEdit.class);
                                intent.putExtra("movieName",movies.get(i).getMovieName());
                                intent.putExtra("movieDescription",movies.get(i).getDescription());
                                intent.putExtra("movieImgURL",movies.get(i).getImgUrl());
                                intent.putExtra("sendingCode",3);
                                context.startActivity(intent);
                            }
                        }
                );
            }
            catch (JSONException ex) {
                Toast.makeText(context, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
//
//            // Dismiss dialog:
//            progressDialog.dismiss();
        }
    public void onAboutToStart() {
    }

    // Got error from server - show toast and dismiss dialog:
    public void onError(String errorMessage) {
        Toast.makeText(context, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
    }

}

