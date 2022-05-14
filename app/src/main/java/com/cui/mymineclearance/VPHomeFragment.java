package com.cui.mymineclearance;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class VPHomeFragment extends Fragment {

    private ArrayList<RankBean> list;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_v_p_home, container, false);
    }

    private String mParam1;
    private String mParam2;
    private RecyclerView rlv;

    public VPHomeFragment() {
    }

    public static VPHomeFragment newInstance(String param1, String param2, String param3) {
        VPHomeFragment fragment = new VPHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rlv = view.findViewById(R.id.rlv);
        list = new ArrayList<>();
        if (mParam1.equals("0")) {
            list = DBManager.getRankList(0);
        }
        if (mParam1.equals("1")) {
            list = DBManager.getRankList(1);
        }
        if (mParam1.equals("2")) {
            list = DBManager.getRankList(2);
        }
        RankAdapter rankAdapter = new RankAdapter(list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        rlv.setLayoutManager(linearLayoutManager);
        rlv.setAdapter(rankAdapter);
//        rankAdapter.notifyDataSetChanged();
    }
}