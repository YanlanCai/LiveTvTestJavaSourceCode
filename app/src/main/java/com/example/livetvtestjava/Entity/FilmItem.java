package com.example.livetvtestjava.Entity;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class FilmItem extends LitePalSupport {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }



    private  float rating;
    private  String thumb;

    public Date getCreate_at() {
        return Create_at;
    }

    public void setCreate_at(Date create_at) {
        Create_at = create_at;
    }

    private Date Create_at;

    public float getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(float totalViews) {
        this.totalViews = totalViews;
    }

    private float totalViews;
}
