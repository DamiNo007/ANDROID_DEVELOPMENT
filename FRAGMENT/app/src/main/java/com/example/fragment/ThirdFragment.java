package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class ThirdFragment extends Fragment {
    public static final String TAG = "FirstFragmentTag";
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final News news = (News) getActivity().getIntent().getParcelableExtra("news");
        View view = inflater.inflate(R.layout.second_fragment, container, false);
        back = (ImageView) view.findViewById(R.id.back);
        avatar = (ImageView) view.findViewById(R.id.avatar);
        tvAuthor = (TextView)view.findViewById(R.id.tvAuthor);
        tvDate = (TextView)view.findViewById(R.id.tvDate);
        tvText = (TextView)view.findViewById(R.id.text);
        contentImg = (ImageView) view.findViewById(R.id.contentImg);
        imgLike = (ImageView) view.findViewById(R.id.imgLike1);
        tvLike = (TextView)view.findViewById(R.id.tvLike1);
        tvRedir = (TextView)view.findViewById(R.id.tvRedir);
        tvViews = (TextView)view.findViewById(R.id.tvViews);

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
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                            "Like removed!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    DBUtil.items.set(news.getId(),news);

                    SecondFragment.favoriteItems.remove(news.getId());


                }
                else{
                    news.setLiked(true);
                    imgLike.setImageResource(R.drawable.liked);
                    news.setLikesNo(news.getLikesNo()+1);
                    tvLike.setText(Integer.toString(news.getLikesNo()));
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                            "Liked!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    DBUtil.items.set(news.getId(),news);
                    if(!SecondFragment.favoriteItems.contains(news))
                        SecondFragment.favoriteItems.add(news);
                }
            }
        });
        return view;
    }
}
