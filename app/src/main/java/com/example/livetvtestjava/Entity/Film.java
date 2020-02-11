package com.example.livetvtestjava.Entity;

public class Film {
    private String name;

    public String getName() {
        return name;
    }

    public String getThumb() {
        return thumb;
    }

    public String getDate() {
        return date;
    }

    public int getRating() {
        return rating;
    }

    private String thumb;
    private String date;
    private int rating;
    public Film(String vname,String vthumb,String vdate,int vrating){
       name=vname;
       thumb=vthumb;
       date=vdate;
       rating=vrating;
    }

}
