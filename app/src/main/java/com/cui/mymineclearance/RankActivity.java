package com.cui.mymineclearance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class RankActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ArrayList<Fragment> fragmentArrayList;

    private MyFragmentTitleAdapter myFragmentTitleAdapter;
    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        initView();
        initData();
//        new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL)
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rank_back:
                finish();
                break;
        }
    }

    private void initData() {
        VPHomeFragment fragment1 = VPHomeFragment.newInstance("0", "初级", "");
        VPHomeFragment fragment2 = VPHomeFragment.newInstance("1", "中级", "");
        VPHomeFragment fragment3 = VPHomeFragment.newInstance("2", "高级", "");
        fragmentArrayList.add(fragment1);
        fragmentArrayList.add(fragment2);
        fragmentArrayList.add(fragment3);
        titles.add("初级");
        titles.add("中级");
        titles.add("高级");
        myFragmentTitleAdapter = new MyFragmentTitleAdapter(getSupportFragmentManager(), fragmentArrayList, titles);
        viewPager.setAdapter(myFragmentTitleAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initView() {
        viewPager = findViewById(R.id.home_VP);
        tabLayout = findViewById(R.id.tab_layout);
        fragmentArrayList = new ArrayList<>();
        titles = new ArrayList<>();
    }
}