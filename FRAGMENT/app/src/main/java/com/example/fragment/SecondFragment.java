package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {
    public static News updated_news = null;
    public static List<News> favoriteItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private int positionL = 0;
    private FavoriteNewsListAdapter adapter;

    private FavoriteNewsListAdapter.ItemClickListener listener = null;
    boolean doubleBackToExitPressedOnce = false;

    public static final String TAG = "SecondFragmentTag";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = new FavoriteNewsListAdapter.ItemClickListener() {
            @Override
            public void itemClick(int position, News item) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("news", item);
                startActivity(intent);
            }
        };
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FavoriteNewsListAdapter(newsGenerator(), listener);
        recyclerView.setAdapter(adapter);
        return view;
    }


    public SecondFragment() {
        // Required empty public constructor
    }
    private List<News> newsGenerator() {
        return favoriteItems;
    }

}
