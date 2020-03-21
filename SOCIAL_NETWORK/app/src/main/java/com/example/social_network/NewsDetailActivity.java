package com.example.social_network;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NewsDetailActivity extends AppCompatActivity {

    private ImageView avatar;
    private TextView tvAuthor;
    private TextView tvDate;
    private TextView tvText;
    private ImageView contentImg;
    private ImageView imgLike;
    private TextView tvLike;
    private TextView tvRedir;
    private TextView tvViews;
    private ImageView back;
    private boolean isLiked;
    private int likesCount;

    public void onBackPressed() {

        Intent intent = new Intent(NewsDetailActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        back = findViewById(R.id.back);
        avatar = findViewById(R.id.avatar);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvDate = findViewById(R.id.tvDate);
        tvText = findViewById(R.id.text);
        contentImg = findViewById(R.id.contentImg);
        imgLike = findViewById(R.id.imgLike1);
        tvLike = findViewById(R.id.tvLike1);
        tvRedir = findViewById(R.id.tvRedir);
        tvViews = findViewById(R.id.tvViews);



        final News news = (News) getIntent().getParcelableExtra("news");

        isLiked = news.isLiked();
        likesCount = news.getLikesNo();
        avatar.setImageResource(news.getAvaURL());
        contentImg.setImageResource(news.getContentImgURL());

        if(news.isLiked()){
            imgLike.setImageResource(R.drawable.liked);
        }
        else{
            imgLike.setImageResource(R.drawable.like);
        }

        tvLike.setText(Integer.toString(news.getLikesNo()));
        tvAuthor.setText(news.getAuthor());
        tvDate.setText(news.getDate());
        tvText.setText(news.getText());

        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(news.isLiked()){
                    news.setLiked(false);
                    imgLike.setImageResource(R.drawable.like);
                    news.setLikesNo(news.getLikesNo()-1);
                    tvLike.setText(Integer.toString(news.getLikesNo()));
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Like removed!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    DBUtil.items.set(news.getId(),news);
                }
                else{
                    news.setLiked(true);
                    imgLike.setImageResource(R.drawable.liked);
                    news.setLikesNo(news.getLikesNo()+1);
                    tvLike.setText(Integer.toString(news.getLikesNo()));
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Liked!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    DBUtil.items.set(news.getId(),news);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUtil.items.set(news.getId(),news);
                Intent intent = new Intent(NewsDetailActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("position", news.getId());
                startActivity(intent);
            }

        });


    }


}