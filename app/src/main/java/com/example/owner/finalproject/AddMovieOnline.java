package com.example.owner.finalproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class AddMovieOnline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie_online);
    }
    public void searchOnClick(View view){
        //sending the search string to the HttpRequest as a valid string
        EditText editText=(EditText)findViewById(R.id.editTextSearch);
        String str=editText.getText().toString();
        ListView listView=(ListView)findViewById(R.id.listViewMovies);
        movieOnlineAdapter movieAdapter=new movieOnlineAdapter(listView,this);
        str=wordValidation(str);
        movieAdapter.readAllMoviesBySearch(str);
    }
    private String wordValidation(String word){
        //the httprequest cant convert space to the %20 so we need to convert it manually before using the httprequest
        String legalWord="";
        for(int i=0;i<word.length();i++){
            char c=word.charAt(i);
            if(c==' '){
                legalWord+="%20";
            }
            else{
                legalWord+=c;
            }
        }
        return legalWord;
    }
    public void cancelOnClick(View view){
        super.onBackPressed();
    }
}
