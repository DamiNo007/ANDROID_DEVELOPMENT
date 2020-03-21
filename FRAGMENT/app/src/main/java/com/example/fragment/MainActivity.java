package com.example.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;

    private  FragmentManager fragmentManager;
    private  FragmentTransaction transaction;

    private   CustomViewPager pager;

    private  PagerAdapter pagerAdapter;
    private  NewsListAdapter.ItemClickListener listener = null;
    private  List<Fragment> list = new ArrayList<>();
    private int pagerState;
    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, firstFragment, FirstFragment.TAG);
        transaction.commit();
        Log.i(TAG, "MyClass.getView() — get item number " + pagerState);

        BottomNavigationView bottomNav = findViewById(R.id.nav_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        list.add(new FirstFragment());
        list.add(new SecondFragment());
        pager = (CustomViewPager) findViewById(R.id.container);
        pager.setPagingEnabled(false);;

        pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(pagerAdapter);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    pagerAdapter.notifyDataSetChanged();
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            pager.setCurrentItem(0,false);
                            break;
                        case R.id.nav_favorite:
                            pager.setCurrentItem(1,false);
                            break;
                    }

                    return false;
                }
            };
}
