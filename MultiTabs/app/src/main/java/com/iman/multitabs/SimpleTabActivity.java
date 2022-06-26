package com.iman.multitabs;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class SimpleTabActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    SlidePagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);


        setUpViewPager();
        tabLayout.setupWithViewPager(viewPager);

        String type = getIntent().getStringExtra("type");
        if ("simple".equals(type)){
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }else if ("scrollable".equals(type)){
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }else if ("IconAndText".equals(type)){
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            setUpTabIcon();
        }else if ("onlyIcon".equals(type)){
            tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            for (int i = 0 ; i < tabLayout.getTabCount() ; i++)
                tabLayout.getTabAt(i).setText("");
            setUpTabIcon();
        }
    }

    private void setUpTabIcon() {
        if (tabLayout.getTabCount() < 4)    return;
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_food);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_movie);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_discount);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_travel);
    }

    private void setUpViewPager() {
        pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        prepareSlides();

    }

    private void prepareSlides() {

        String[] titles = getResources().getStringArray(R.array.titles);
        String[] descriptions = getResources().getStringArray(R.array.descriptions);
        int [] bgColors = new int[] {R.color.bgColor1, R.color.bgColor2,
                R.color.bgColor3, R.color.bgColor4};
        int [] imageIds = new int[] {R.drawable.ic_food, R.drawable.ic_movie,
                R.drawable.ic_discount, R.drawable.ic_travel};



        for(int i = 0; i < 4 ; i++){
            pagerAdapter.addFragment(FirstFragment.newInstance(ContextCompat.getColor(this, bgColors[i]),
                            imageIds[i], titles[i], descriptions[i]),titles[i]);
        }


    }

    public class SlidePagerAdapter extends FragmentPagerAdapter{

        List<Fragment> fragments;
        List<String> tabTitles;

        public SlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
            tabTitles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment , String title){
            fragments.add(fragment);
            tabTitles.add(title);
            notifyDataSetChanged();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }
    }

}
