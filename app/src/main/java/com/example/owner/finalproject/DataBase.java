package com.example.owner.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context contex) {
        super(contex, "moviesdb", null, 1);
    }
//Creating movie table
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Movies\n" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "title TEXT NOT NULL,\n" +
                "description TEXT,\n" +
                "imageURL TEXT)");
    }

    // Will be invoked when database version will be different (like in an update app version)
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Note: Android will save (in some cases) and won't delete the old version from the device, even if we'll uninstall the app!
        // Thus it is important in the onUpgrade to delete the previous tables and to create them again, or the old versions will still be in use.
        db.execSQL("DROP TABLE Movies");
        onCreate(db);
    }

    // Add a new movie to the table
    public void addMovie(Movie movie) {
        ContentValues con = new ContentValues();
        con.put("title", movie.getMovieName());
        con.put("description", movie.getDescription());
        con.put("imageURL", movie.getImgUrl());
        SQLiteDatabase db = getWritableDatabase(); // Open connection.
        db.insert("Movies",null,con);
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        cursor.moveToNext();
        int id = cursor.getInt(0);
        movie.setMovieID(id);
        cursor.close();
        db.close(); // Close connection.
    }

    // Update a movie column by the movie id
    public void updateMovie(Movie movie) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues con = new ContentValues();
        con.put("title", movie.getMovieName());
        con.put("description", movie.getDescription());
        con.put("imageURL", movie.getImgUrl());
        db.update("Movies", con, "_id" + "=" + movie.getMovieID(),null);
        db.close();
    }
    // Delete a movie
    public void deleteMovie(Movie movie) {
        String sql = String.format("DELETE FROM Movies WHERE _id=%d", movie.getMovieID());
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }
    //Delete all the movies
    public void deleteAllMovies() {
        String sql = String.format("DELETE FROM Movies");
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    // Get all movies
    public ArrayList<Movie> getAllMovies() {

        ArrayList<Movie> movies = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Movies", null);

        // Take indices of all columns:
        int idIndex = cursor.getColumnIndex("_id");
        int titleIndex = cursor.getColumnIndex("title");
        int descriptionIndex = cursor.getColumnIndex("description");
        int imageURLIndex = cursor.getColumnIndex("imageURL");

        // Run on all rows, create Movie from each row:
        while (cursor.moveToNext()) {
            int id = cursor.getInt(idIndex);
            String title = cursor.getString(titleIndex);
            String description = cursor.getString(descriptionIndex);
            String imageURL = cursor.getString(imageURLIndex);
            Movie Movie = new Movie(id,title, description, imageURL);
            movies.add(Movie);
        }

        cursor.close();
        db.close();

        return movies;
    }
}