package com.example.livetvtestjava;


import android.content.Context;


import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livetvtestjava.Entity.FilmItem;
import com.example.livetvtestjava.lib.global;

import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;



public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder>  {
    private static final String TAG = "FilmAdapter ";
    private Context mContext;
    private IDetailListener mlistener;
    static class ViewHolder extends RecyclerView.ViewHolder{

        View v;
        TextView txtfilmName,txtRating,txtDate;
        ImageView imgThumb;
        FilmItem  filmItem;
        Button  btnDetail;

        public ViewHolder(View view){
            super(view);
            v = view;
            txtfilmName = (TextView)view.findViewById(R.id.filmName);
            txtRating = (TextView)view.findViewById(R.id.filmRating);
            txtDate = (TextView)view.findViewById(R.id.filmDate);
            imgThumb=(ImageView)view.findViewById(R.id.picThumb);
            btnDetail=(Button)view.findViewById(R.id.btnFilmDetail);
        }
    }
    private List<FilmItem> flist;
    public FilmAdapter(List<FilmItem> vflist,IDetailListener listener) {
        this.flist = vflist;
        mlistener=listener;
    }
    @NonNull
    @Override
    public FilmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext==null)
        {
            mContext=parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_filmitem,parent,false);
        final FilmAdapter.ViewHolder holder = new FilmAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FilmAdapter.ViewHolder holder, int position) {

        FilmItem item = flist.get(position);

        holder.filmItem = item;
        holder.txtfilmName.setText(holder.filmItem.getName());
        holder.txtRating.setText("评分:"+String.valueOf(holder.filmItem.getRating()));
        String str = new SimpleDateFormat("yyyy-MM-dd").format(holder.filmItem.getCreate_at());
        holder.txtDate.setText("上影:"+str);


        Log.d(TAG, String.valueOf(holder.filmItem.getCreate_at().getDate()));
        Uri uri=  Uri.parse(holder.filmItem.getThumb());

        Glide.with(mContext).load(uri).into(holder.imgThumb);
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                global.filmSelected=holder.filmItem;
                mlistener.onShowDetail();
            }
        });

    }

    @Override
    public int getItemCount() {
        return flist.size();
    }
    public interface  IDetailListener{
        void  onShowDetail();
    }



}
