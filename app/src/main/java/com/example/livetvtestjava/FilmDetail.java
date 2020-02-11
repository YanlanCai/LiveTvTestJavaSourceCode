package com.example.livetvtestjava;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.livetvtestjava.lib.global;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FilmDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutfilmdetail);

        TextView txt=(TextView) findViewById(R.id.filmNameD);
        txt.setText(global.filmSelected.getName());
        txt =(TextView)findViewById(R.id.filmRatingD);
        txt.setText("评分"+String.valueOf(global.filmSelected.getRating()));
        txt=(TextView)findViewById(R.id.filmTotalViews);
        txt.setText("浏览量:"+String.valueOf(global.filmSelected.getTotalViews()));
        ImageView imgThumb=(ImageView)findViewById(R.id.picThumbD) ;

        Uri uri=  Uri.parse(global.filmSelected.getThumb());
        Log.d(TAG, "onBindViewHolder: url="+uri.toString());
        Glide.with(getBaseContext()).load(uri).into(imgThumb);
        Button btn=(Button)findViewById(R.id.btnClose);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
