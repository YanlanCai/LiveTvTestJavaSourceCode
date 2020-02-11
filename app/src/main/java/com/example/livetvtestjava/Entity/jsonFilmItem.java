package com.example.livetvtestjava.Entity;


public class jsonFilmItem {

    private int drama_id;

    public int getDrama_id() {
        return drama_id;
    }

    public void setDrama_id(int drama_id) {
        this.drama_id = drama_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    private String name;

    public float getTotal_views() {
        return total_views;
    }

    public void setTotal_views(float total_views) {
        this.total_views = total_views;
    }

    private float total_views;
    private String created_at;
    private String thumb;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    private float rating;


}
