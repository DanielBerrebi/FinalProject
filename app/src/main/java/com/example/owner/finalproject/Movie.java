package com.example.owner.finalproject;


public class Movie {
    private  int movieID;
    private String movieName;
    private String description="there is no description";
    private String imgUrl="no photo";
    public Movie(){
    }
    public Movie(int id, String name,String description,String imageURL){
        setMovieID(id);
        setMovieName(name);
        setDescription(description);
        setImgUrl(imageURL);
    }
    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
