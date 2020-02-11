package com.example.livetvtestjava.lib;



import okhttp3.OkHttpClient;
import okhttp3.Request;

public class httpHelper {
    public static  void sendokhttprequest(String url,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }


}
