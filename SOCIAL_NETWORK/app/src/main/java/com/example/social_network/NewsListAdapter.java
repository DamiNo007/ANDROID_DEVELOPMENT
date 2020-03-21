package com.example.social_network;

import android.content.Context;
import android.media.Image;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    private List<News> newsList;

    @Nullable
    private ItemClickListener listener;

    public NewsListAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    public NewsListAdapter(List<News> newsList, @Nullable ItemClickListener listener) {
        this.newsList = newsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, null, false);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        view.setLayoutParams(params);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, final int position) {
        final News news = newsList.get(position);
        Glide.with(holder.avatar.getContext())
                .load(news.getAvaURL())
                .into(holder.avatar);

        Glide.with(holder.contentImg.getContext())
                .load(news.getContentImgURL())
                .into(holder.contentImg);

        if(news.isLiked()){
            Glide.with(holder.imgLike.getContext())
                    .load(R.drawable.liked)
                    .into(holder.imgLike);
        }
        else{
            Glide.with(holder.imgLike.getContext())
                    .load(R.drawable.like)
                    .into(holder.imgLike);
        }

        holder.tvLike.setText(Integer.toString(news.getLikesNo()));
        holder.tvAuthor.setText(news.getAuthor());
        holder.tvDate.setText(news.getDate());
        holder.tvText.setText(news.getText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(listener!=null){
                    listener.itemClick(position,news);
                }
            }
        });

        holder.imgLike.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(listener!=null){
                    if(news.isLiked()){
                        news.setLiked(false);
                        news.setLikesNo(news.getLikesNo()-1);
                        Glide.with(holder.imgLike.getContext())
                                .load(R.drawable.like)
                                .into(holder.imgLike);
                        holder.tvLike.setText(Integer.toString(news.getLikesNo()));
                        Toast toast = Toast.makeText(v.getContext(),
                                "Like removed!",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    else{
                        news.setLiked(true);
                        news.setLikesNo(news.getLikesNo()+1);
                        Glide.with(holder.imgLike.getContext())
                                .load(R.drawable.liked)
                                .into(holder.imgLike);
                        holder.tvLike.setText(Integer.toString(news.getLikesNo()));
                        Toast toast = Toast.makeText(v.getContext(),
                                "Liked!",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView tvAuthor;
        TextView tvDate;
        TextView tvText;
        ImageView contentImg;
        ImageView imgLike;
        TextView tvLike;
        TextView tvComment;
        TextView tvViews;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvText = itemView.findViewById(R.id.text);
            contentImg = itemView.findViewById(R.id.contentImg);
            imgLike = itemView.findViewById(R.id.imgLike);
            tvLike = itemView.findViewById(R.id.tvLike);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvViews = itemView.findViewById(R.id.tvViews);

        }
    }

    interface ItemClickListener {
        void itemClick(int position, News item);
    }
}