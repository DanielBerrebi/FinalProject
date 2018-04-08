package com.example.owner.finalproject;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.view.GestureDetector;
import android.support.v4.view.GestureDetectorCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private DataBase db;
    private Movie selectedMovie;//used so we can use it in the switch case of the popup menu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        db=new DataBase(this);
        onCreateListViewMovies();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    public void onCreateListViewMovies(){
        //Creating an array of movies and fill it by the database table
        ArrayList<Movie> arrayList=db.getAllMovies();
        ListView listView=(ListView)findViewById(R.id.listViewMovies);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),arrayList);
        listView.setAdapter(customAdapter);
        //Set an on short item click listener that would send the movies details to the show movie details page and navigate the user there
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                //Converting the object to and movie
                    final Object selectedObject = adapterView.getItemAtPosition(i);
                    if (selectedObject instanceof Movie) {
                        selectedMovie = (Movie) selectedObject;
                    }
                    Intent intent = new Intent(MainActivity.this, ShowMovieDetails.class);
                    intent.putExtra("movieName", selectedMovie.getMovieName());
                    intent.putExtra("movieDescription", selectedMovie.getDescription());
                    intent.putExtra("movieImgURL", selectedMovie.getImgUrl());
                    intent.putExtra("movieID", selectedMovie.getMovieID());
                    startActivity(intent);
            }
        });
        //Set on item long click lisener that will popup an popup menu with the option to edit or delete that movie
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, int i, long l) {
                //Adding the popup menu
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                //Converting the object to and movie
                final Object selectedObject=adapterView.getItemAtPosition(i);
                if(selectedObject instanceof Movie){
                    selectedMovie=(Movie)selectedObject;
                }
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                //Checking what option the user choose
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int itemId = menuItem.getItemId();
                        // Return true to state that the menu event has been handled.
                        // Return false to state that the menu event has not been handled.
                        switch (itemId) {
                            case R.id.editMovie:
                                Intent intent=new Intent(MainActivity.this,AddOrEdit.class);
                                intent.putExtra("movieName",selectedMovie.getMovieName());
                                intent.putExtra("movieDescription",selectedMovie.getDescription());
                                intent.putExtra("movieImgURL",selectedMovie.getImgUrl());
                                intent.putExtra("sendingCode",2);
                                intent.putExtra("movieID",selectedMovie.getMovieID());
                                startActivity(intent);
                                return true;
                            case R.id.deleteThatMovie:
                                Toast.makeText(MainActivity.this, "need to delete", Toast.LENGTH_LONG).show();
                                db.deleteMovie(selectedMovie);
                                onCreateListViewMovies();
                                return true;

                        }
                        return false;
                    }
                });
                popupMenu.show();
                //Return true to show the click event has been handled so we wont go to the short item click listener
                return true;
            }

        });
    }
    // Return true to state that the menu event has been handled.
    // Return false to state that the menu event has not been handled.
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menuAddMovieOnline:
                Toast.makeText(this, "need to go to online add", Toast.LENGTH_LONG).show();
                Intent i =new Intent(this,AddMovieOnline.class);
                startActivity(i);
                return true;
            case R.id.menuAddMovieOffline:
                Toast.makeText(this, "need to go to offline add", Toast.LENGTH_LONG).show();
                Intent j =new Intent(this,AddOrEdit.class);
                j.putExtra("sendingCode",1);
                startActivity(j);
                return true;
            case R.id.menuExit:
                Toast.makeText(this, "need to exit", Toast.LENGTH_LONG).show();
                finish();
                return true;
            case R.id.menuDeleteAllFiles:
                db.deleteAllMovies();
                onCreateListViewMovies();
                return true;
        }

        return false;
    }
}
