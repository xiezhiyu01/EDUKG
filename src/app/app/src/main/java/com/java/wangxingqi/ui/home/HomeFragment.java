package com.java.wangxingqi.ui.home;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;

import com.java.wangxingqi.CategoryActivity;
import com.java.wangxingqi.EntitySearchedActivity;
import com.java.wangxingqi.R;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.java.wangxingqi.HomeActivity;
import com.java.wangxingqi.adapter.HomePagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private TabViewModel tabViewModel;
    private HomePagerAdapter homePagerAdapter;
    private Spinner spinner_filter, spinner_sorter;
    private String course, sort;

    private void initView(View view){
        // init Tab view
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.view_pager);

        // bind: fragment -> viewPager -> tabLayout
        homePagerAdapter = new HomePagerAdapter(getParentFragmentManager());
        viewPager.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // init drag layout
        ImageView addTab = view.findViewById(R.id.ic_add);
        addTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click the category button");
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("category", new ArrayList<>(Objects.requireNonNull(tabViewModel.getCategory().getValue())));
                intent.putExtra("delCategory",  new ArrayList<>(Objects.requireNonNull(tabViewModel.getDelCategory().getValue())));
                Log.e("HomeFragment", String.valueOf(HomeActivity.CATEGORY));
                startActivityForResult(intent, HomeActivity.CATEGORY);
            }
        });
        //init Search Bar
        initSearchbar(view);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initView(root);
        tabViewModel = new ViewModelProvider(getActivity()).get(TabViewModel.class);

        tabViewModel.getCategory().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> s) {
                homePagerAdapter.setCategory(s);
                Log.e("HomeFragment", s.toString());
            }
        });

        tabViewModel.getDelCategory().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> s) {
                homePagerAdapter.setDelCategory(s);
                Log.e("HomeFragment", s.toString());
            }
        });
        return root;
    }

    private void initSearchbar(View view) {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        spinner_filter = view.findViewById(R.id.spin_filter);
        spinner_sorter = view.findViewById(R.id.spin_sorter);
        ArrayAdapter<CharSequence> spinnerFilterAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.course, R.layout.my_support_simple_spinner_dropdown_item);
        spinner_filter.setAdapter(spinnerFilterAdapter);
        spinner_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String courseCN = adapterView.getItemAtPosition(position).toString();
//                Toast.makeText(getContext(), "?????????????????????" + courseCN, Toast.LENGTH_SHORT).show();
                switch (courseCN){
                    case "??????":
                        course = "chinese";
                        break;
                    case "??????":
                        course = "math";
                        break;
                    case "??????":
                        course = "english";
                        break;
                    case "??????":
                        course = "physics";
                        break;
                    case "??????":
                        course = "chemistry";
                        break;
                    case "??????":
                        course = "biology";
                        break;
                    case "??????":
                        course = "history";
                        break;
                    case "??????":
                        course = "politics";
                        break;
                    case "??????":
                        course = "geo";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<CharSequence> spinnerSorterAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sorters, R.layout.my_support_simple_spinner_dropdown_item);
        spinner_sorter.setAdapter(spinnerSorterAdapter);
        spinner_sorter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String courseCN = adapterView.getItemAtPosition(position).toString();
//                Toast.makeText(getContext(), "?????????????????????" + courseCN, Toast.LENGTH_SHORT).show();
                switch (courseCN){
                    case "??????":
                        sort = "normal";
                        break;
                    case "?????????":
                        sort = "abc";
                        break;
                    case "??????":
                        sort = "length";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final SearchView mSearchView = view.findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // submit query text & go to next page
                Log.e("HomeActivity", "TextSubmit : " + s);
                mSearchView.setIconified(true);
                Intent intent = new Intent(getActivity(), EntitySearchedActivity.class);
                intent.putExtra("keyword", s);
                System.out.println("in HomeFragment, course:"+course);
                intent.putExtra("course", course);
                intent.putExtra("sort", sort);

                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }
}
