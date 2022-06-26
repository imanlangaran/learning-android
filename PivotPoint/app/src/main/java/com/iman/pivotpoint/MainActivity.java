package com.iman.pivotpoint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    public static final String format0 = "%.0f";
//    public static final String format0 = "%.0f";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MyPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        pagerAdapter.addFragment(new StandardFragment(), "Standard");
        pagerAdapter.addFragment(new WoodieFragment(), "Woodie");
        pagerAdapter.addFragment(new CamarillaFragment(), "Camarilla");
        pagerAdapter.addFragment(new FibonacciFragment(), "Fibonacci");
        pagerAdapter.addFragment(new TomDeMarkFragment(), "Tom DeMark's");
        pagerAdapter.addFragment(new ComparisonFragment(), "All");

        tabLayout.setupWithViewPager(viewPager);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        Locale.setDefault(new Locale("US"));
    }



    private class MyPagerAdapter extends FragmentPagerAdapter{



        List<Fragment> frags;
        List<String> names;

        public MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            frags = new ArrayList<>();
            names = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return frags.get(position);
        }

        @Override
        public int getCount() {
            return frags.size();
        }

        public void addFragment(Fragment fragment, String name){
            frags.add(fragment);
            names.add(name);
            notifyDataSetChanged();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return names.get(position);
        }
    }




}
