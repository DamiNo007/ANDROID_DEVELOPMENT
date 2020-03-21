package com.example.fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FavoriteNewsListAdapter extends RecyclerView.Adapter<FavoriteNewsListAdapter.FavoriteNewsViewHolder> {

    private List<News> favoriteNewsList;

    @Nullable
    private ItemClickListener listener;

    public FavoriteNewsListAdapter(List<News> newsList) {
        this.favoriteNewsList = newsList;
    }

    public FavoriteNewsListAdapter(List<News> favoriteNewsList, @Nullable ItemClickListener listener) {
        this.favoriteNewsList = favoriteNewsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, null, false);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        view.setLayoutParams(params);
        return new FavoriteNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteNewsViewHolder holder, final int position) {
        final News news = favoriteNewsList.get(position);
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
                        SecondFragment.favoriteItems.remove(news);
                        for (News item : DBUtil.items){
                            if(item.equals(news)){
                                item.setLiked(false);
                                item.setLikesNo(item.getLikesNo()-1);
                            }
                        }
                        notifyDataSetChanged();
                    }

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return favoriteNewsList.size();
    }

    public class FavoriteNewsViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView tvAuthor;
        TextView tvDate;
        TextView tvText;
        ImageView contentImg;
        ImageView imgLike;
        TextView tvLike;
        TextView tvComment;
        TextView tvViews;

        public FavoriteNewsViewHolder(@NonNull View itemView) {
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