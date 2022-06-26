package com.iman.introsliderproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntroSliderActivity extends AppCompatActivity {

    private Button btnNext, btnSkip;
    private LinearLayout layoutDots;
    private ViewPager viewPager;

    private SliderPagerAdapter adapter;

//    int [] layoutIds = {R.layout.intro_slider_1, R.layout.intro_slider_2, R.layout.intro_slider_3, R.layout.intro_slider_4};

    private SliderPrefManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);

        changeStatusBarColor();

        manager = new SliderPrefManager(this);

        if (!manager.startSlider()){
            lunchMainScreen();
            return;
        }

        btnNext = findViewById(R.id.btn_next);
        btnSkip = findViewById(R.id.btn_skip);
        layoutDots = findViewById(R.id.LayoutDots);
        viewPager = findViewById(R.id.viewPager);

        adapter = new SliderPagerAdapter();

        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                showDots(position);

                if (position == adapter.getCount() - 1){
                    btnNext.setText(R.string.gotit);
                    btnSkip.setVisibility(View.GONE);
                }else {
                    btnNext.setText(R.string.next);
                    btnSkip.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == adapter.getCount()-1){
                    manager.setStartSlider(false);
                    lunchMainScreen();
                }else{
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lunchMainScreen();
            }
        });

        showDots(viewPager.getCurrentItem());
    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    private void lunchMainScreen(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showDots(int pageNumber) {
        TextView[] dots = new TextView[adapter.getCount()];
        layoutDots.removeAllViews();
        for (int i = 0 ; i < dots.length ; i++){
            dots[i] = new TextView(IntroSliderActivity.this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
            dots[i].setTextColor(ContextCompat.getColor(this,(i == pageNumber ? R.color.dot_active : R.color.dot_inactive)));
            layoutDots.addView(dots[i]);
        }
    }


    public class SliderPagerAdapter extends PagerAdapter{



        String[] slideTitles;
        String[] slideDescriptions;
        int[] backgroundIds = {R.color.slide_1_bg_color, R.color.slide_2_bg_color, R.color.slide_3_bg_color, R.color.slide_4_bg_color};
        int[] imageResources = {R.drawable.ic_food, R.drawable.ic_movie, R.drawable.ic_discount, R.drawable.ic_travel};

        public SliderPagerAdapter(){
            slideTitles = getResources().getStringArray(R.array.slide_titles);
            slideDescriptions = getResources().getStringArray(R.array.slide_descriptions);
        }



        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            View view = LayoutInflater.from(IntroSliderActivity.this).inflate(R.layout.slide, container, false);
            view.findViewById(R.id.bgLayout).setBackgroundColor(ContextCompat.getColor(IntroSliderActivity.this, backgroundIds[position]));
            ((ImageView)view.findViewById(R.id.slide_image)).setImageResource(imageResources[position]);
            ((TextView)view.findViewById(R.id.slide_title)).setText(slideTitles[position]);
            ((TextView)view.findViewById(R.id.slide_description)).setText(slideDescriptions[position]);
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return backgroundIds.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

}
