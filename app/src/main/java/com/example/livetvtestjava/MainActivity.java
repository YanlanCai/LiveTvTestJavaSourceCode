package com.example.livetvtestjava;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livetvtestjava.Entity.JsonData;
import com.example.livetvtestjava.Entity.FilmItem;
import com.example.livetvtestjava.Entity.jsonFilmItem;
import com.example.livetvtestjava.lib.global;
import com.example.livetvtestjava.lib.httpHelper;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import com.google.gson.Gson;

import org.litepal.LitePal;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView txtErrMsg;
    private Handler MsgHandler;
    private String MsgStr;
    private String  httpRespondStr;
    private RecyclerView UIFilmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Button btn=findViewById(R.id.btnGetData);
        btn.setOnClickListener(this);
        btn=findViewById(R.id.btnSetEmpty);
        btn.setOnClickListener(this);
        btn=findViewById(R.id.btnSearch);
        btn.setOnClickListener(this);
        txtErrMsg=findViewById(R.id.txtErrorMsg);
        txtErrMsg.setText("start");
        UIFilmList=(RecyclerView)findViewById(R.id.RecycleViewFilmList);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        UIFilmList.setLayoutManager(layoutManager);
        SharedPreferences sp=getSharedPreferences("filmData",MODE_PRIVATE);
        String keyword=sp.getString("keyword","");
        keyword=keyword.trim();
        EditText txt=(EditText)findViewById(R.id.txtSearch);
        txt.setText(keyword);
        if(keyword=="")
        {
            RefreshFilmList();
        }else{
            doQueryFilm(keyword);
        }

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnGetData:
                MsgHandler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                    //region MsgHandler process
                        String jsonStr=(String)msg.obj;
                        switch (msg.what) {
                            case global.MSG_OK:
                                txtErrMsg.setText(jsonStr);
                                Log.d(TAG, "handleMessage: msg.Msg_ok " + jsonStr);
                                RefreshFilmList();
                                break;
                            case global.MSG_ERROR:

                                //main_errorInfo.setText((String)msg.obj);
                                Log.d(TAG, "handleMessage: "+(String)msg.obj);

                                String txt=(String)msg.obj;
                                txtErrMsg.setText(txt);

                                break;
                        }
                //endregion
                    }
                };
                httpRequestGetData();
                break;
            case R.id.btnSetEmpty:
                LitePal.deleteAll(FilmItem.class);
                RefreshFilmList();
                break;
            case R.id.btnSearch:
                EditText txt=(EditText)findViewById(R.id.txtSearch);
                String keyWord=txt.getText().toString();
                doQueryFilm(keyWord);
                SharedPreferences.Editor editor=getSharedPreferences("filmData",MODE_PRIVATE).edit();
                editor.putString("keyword",keyWord);
                editor.apply();



        }
    }

    public void httpRequestGetData()
    {
        EditText edtTxtUrl=(EditText)findViewById(R.id.edtServerUrl);
        String url=edtTxtUrl.getText().toString();
        if(url==""){
            txtErrMsg.setText("ServerUrl 不可我空值！");
            return;
        }

        httpHelper.sendokhttprequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MsgStr=e.getMessage();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg=new Message();
                        msg.what=global.MSG_ERROR;
                        msg.obj=MsgStr;
                        if(MsgHandler!=null)
                        {
                            MsgHandler.sendMessage(msg);
                        }
                    }
                }).start();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                httpRespondStr=response.body().string();
                Log.d(TAG,"httpRespondStr:"+httpRespondStr);
                final int filmCnt=ParseFileJsonStr(httpRespondStr);
                MsgStr=String.format("Number of films:%d",filmCnt);
                if(filmCnt>-1)
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message msg=new Message();
                            msg.what=global.MSG_OK;
                            msg.obj=MsgStr;
                            if(MsgHandler!=null)
                            {
                                MsgHandler.sendMessage(msg);
                            }

                        }
                    }).start();
            }
        }});

    }

    public int ParseFileJsonStr(String jsonStr)
    {
       int retint=-1;
        Gson gson =new Gson();
        Log.d(TAG, "ParseFileJsonStr: start parseJson=======================");
        JsonData dataobj=gson.fromJson(jsonStr,new TypeToken<JsonData>(){}.getType());

        LitePal.deleteAll(FilmItem.class,"") ;
        retint=0;
        for(jsonFilmItem b:dataobj.getData())
           {
               Log.d(TAG, "ParseFileJsonStr: "+b.getName());
               FilmItem i=new FilmItem();
               i.setCreate_at(global.UTC2GST(b.getCreated_at()));
               Log.d(TAG, "ParseFileJsonStr: crate-at"+i.getCreate_at().toString());
               i.setName(b.getName());
               i.setRating(b.getRating());
               i.setThumb(b.getThumb());
               i.setTotalViews(b.getTotal_views());
               i.save();
               retint++;

           }



        return retint;
    }
    public void doShowDetail()
    {
        Intent intent=new Intent(this,FilmDetail.class);
        startActivity(intent);
    }
    public void RefreshFilmList()
    {
        List<FilmItem> filmItems=LitePal.findAll(FilmItem.class);

        FilmAdapter adapter=new FilmAdapter(filmItems, new FilmAdapter.IDetailListener() {
            @Override
            public void onShowDetail() {
                doShowDetail();
            }
        });
        UIFilmList.setAdapter(adapter);

    }
    public void doQueryFilm(String keywrod)
    {
        keywrod=keywrod.trim();
        if(keywrod=="")
        {
            RefreshFilmList();
        }else
        {
            List<FilmItem> filmItems= LitePal.where("name like ?","%"+keywrod+"%").order("rating").find(FilmItem.class);
            FilmAdapter adapter=new FilmAdapter(filmItems, new FilmAdapter.IDetailListener() {
                @Override
                public void onShowDetail() {
                    doShowDetail();
                }
            });
            UIFilmList.setAdapter(adapter);
        }

    }

}
