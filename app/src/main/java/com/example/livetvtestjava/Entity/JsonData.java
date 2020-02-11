package com.example.livetvtestjava.Entity;

import java.util.List;

public class JsonData {
    public List<jsonFilmItem> getData() {
        return data;
    }

    public void setData(List<jsonFilmItem> data) {
        this.data = data;
    }

    private List<jsonFilmItem> data;
}
