package com.example.livetvtestjava.lib;

public interface httpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
