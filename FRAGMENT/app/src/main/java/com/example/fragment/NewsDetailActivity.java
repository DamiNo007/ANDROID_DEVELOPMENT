package com.example.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class NewsDetailActivity extends FragmentActivity {

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
    private int pagerState;

    public void onBackPressed() {

        Intent intent = new Intent(NewsDetailActivity.this, MainActivity.class);
        intent.putExtra("pagerState", 1);
        startActivity(intent);
        overridePendingTransition(0, 0);
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
                    if(SecondFragment.favoriteItems.contains(news)){
                        SecondFragment.favoriteItems.remove(news);
                    }
                    for(News item:DBUtil.items){
                        if(item.equals(news)){
                            item.setLiked(false);
                            item.setLikesNo(item.getLikesNo()-1);
                        }
                    }
                    news.setLiked(false);
                    imgLike.setImageResource(R.drawable.like);
                    news.setLikesNo(news.getLikesNo()-1);
                    tvLike.setText(Integer.toString(news.getLikesNo()));


                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Like removed!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
                else{
                    for (News item:DBUtil.items){
                        if(item.equals(news)){
                            item.setLiked(true);
                            item.setLikesNo(item.getLikesNo()+1);
                        }
                    }
                    news.setLiked(true);
                    imgLike.setImageResource(R.drawable.liked);
                    news.setLikesNo(news.getLikesNo()+1);
                    tvLike.setText(Integer.toString(news.getLikesNo()));
                    if(!SecondFragment.favoriteItems.contains(news))
                        SecondFragment.favoriteItems.add(news);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Liked!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsDetailActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }

        });


    }


}