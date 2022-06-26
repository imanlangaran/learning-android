package com.iman.jsonxml;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btn_xml_pp, btn_xml_jdom, btn_json, btn_tour;
    private ListView listView;

    private List<Flower> flowers;
    private ArrayAdapter<Flower> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_xml_pp = findViewById(R.id.btn_xml_pp);
        btn_xml_jdom = findViewById(R.id.btn_xml_jdom);
        btn_json = findViewById(R.id.btn_json);
        btn_tour = findViewById(R.id.btn_tours);
        listView = findViewById(R.id.list);

        btn_xml_pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flowers = new FlowerXmlPullParser(MainActivity.this).parseXml();
                Toast.makeText(MainActivity.this, "XMLPP : Returned " + flowers.size() + " items.", Toast.LENGTH_SHORT).show();
                refreshDisplay();

            }
        });

        btn_xml_jdom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flowers = new FlowerXmlJdomParser(MainActivity.this).parseXml();
                Toast.makeText(MainActivity.this, "XMLJdom : Returned " + flowers.size() + " items.", Toast.LENGTH_SHORT).show();
                refreshDisplay();
            }
        });

        btn_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream input = getResources().openRawResource(R.raw.flowers_json);
                flowers = FlowerJsonParser.parseJson(input);
                Toast.makeText(MainActivity.this, "JSON : Returned : " + flowers.size() + " items.", Toast.LENGTH_SHORT).show();
                refreshDisplay();
            }
        });

        btn_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream input = getResources().openRawResource(R.raw.tours);
                List<Tour> tours = new TourJdomParser(input).parseXml();
                Toast.makeText(MainActivity.this, "TourXmlParser : Returned : " + tours.size() + " items.", Toast.LENGTH_SHORT).show();
                ArrayAdapter<Tour> tourArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, tours);
                listView.setAdapter(tourArrayAdapter);
            }
        });
    }


    private void refreshDisplay(){
        if (flowers == null){
            flowers = new ArrayList<>();
        }
        adapter = new FlowerAdapter(this, flowers);
        listView.setAdapter(adapter);
    }
}
