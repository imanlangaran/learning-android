package com.iman.drawchart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RelativeLayout chartLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chartLayout = findViewById(R.id.chartLayout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("line chart").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                chartLayout.removeAllViews();
                drawLineChart();
                return true;
            }
        });

        menu.add("pie chart").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                chartLayout.removeAllViews();
                drawPieChart();
                return true;
            }
        });

        menu.add("radar chart").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                chartLayout.removeAllViews();
                drawRadarChart();
                return true;
            }
        });

        menu.add("clean").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                chartLayout.removeAllViews();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void drawRadarChart() {
        RadarChart chart = new RadarChart(this);
        chart.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        chartLayout.addView(chart);

        List<RadarEntry> entries1 = new ArrayList<>();
        List<RadarEntry> entries2 = new ArrayList<>();

        int max = 80;
        int min = 20;

        for (int i = 0 ; i < 6 ; i++){
            entries1.add(new RadarEntry((float) (min + Math.random() * (max * min))));
            entries2.add(new RadarEntry((float) (min + Math.random() * (max * min))));
        }

        RadarDataSet ds1 = new RadarDataSet( entries1, "set1");
        RadarDataSet ds2 = new RadarDataSet( entries2, "set2");

        ds1.setColor(Color.GRAY);
        ds1.setFillColor(ds1.getColor());
        ds1.setDrawFilled(true);
        ds1.setFillAlpha(170);


        ds2.setColor(Color.GREEN);
        ds2.setFillColor(ds2.getColor());
        ds2.setDrawFilled(true);
        ds2.setFillAlpha(170);

        chart.animateXY(1500, 1500);
        chart.setData(new RadarData(ds1, ds2));

    }

    private void drawPieChart() {
        PieChart chart = new PieChart(this);
        chart.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        chartLayout.addView(chart);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(4f, "c0"));
        entries.add(new PieEntry(9f, "c1"));
        entries.add(new PieEntry(12f, "c2"));
        entries.add(new PieEntry(14f, "c3"));
        entries.add(new PieEntry(6f, "c4"));
        entries.add(new PieEntry(2f, "c5"));

        PieDataSet dataSet = new PieDataSet(entries, "label");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setDrawValues(false);
        chart.animateXY(1000, 1000);
        chart.setData(new PieData(dataSet));
    }


    private void drawLineChart(){
        LineChart chart = new LineChart(this);
        chart.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        chartLayout.addView(chart);

        List<Entry> entries1 = new ArrayList<>();
        for (int i = 0 ; i < 40 ; i++){
            float x = (float) (i * 2 * Math.PI/40);
            entries1.add(new Entry(x, (float) Math.sin(x)));
        }

        LineDataSet dataSet1 = new LineDataSet(entries1, "sin");
        dataSet1.setColor(Color.BLUE);
        dataSet1.setDrawCircles(false);
        dataSet1.setLineWidth(3f);
        dataSet1.setDrawValues(false);


        List<Entry> entries2 = new ArrayList<>();
        for (int i = 0 ; i < 40 ; i++){
            float x = (float) (i * 2 * Math.PI / 40);
            entries2.add(new Entry(x, (float) (x * Math.cos(x))));
        }
        LineDataSet dataSet2 = new LineDataSet(entries2, "x cos");
        dataSet2.setColor(Color.RED);
        dataSet2.setLineWidth(2.5f);
        dataSet2.setDrawValues(false);
        dataSet2.setDrawCircles(false);



        chart.setData(new LineData(dataSet1, dataSet2));
        chart.animateXY(1000, 1000);
        chart.invalidate();
    }

}
