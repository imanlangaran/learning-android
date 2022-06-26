package com.iman.sqlitedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlowerDbHelper flowerDbHelper;
    private ListView listView;
    private SearchView searchView;
    List<Flower> flowers = new ArrayList<>();

    private FlowerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.search_view);

        refreshDisplay();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "Submit", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                flowerDbHelper.getFlower(flowers, Flower.KEY_NAME + " LIKE '%" + newText + "%'", null);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        flowerDbHelper = new FlowerDbHelper(this);


        SQLiteDatabase db = flowerDbHelper.getWritableDatabase();
        Log.i("database", "database opened.");
        if (db != null && db.isOpen()) {
            db.close();
            Log.i("database", "database closed.");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        menu.add("import flowers_json.json").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                importJson();
                flowers = flowerDbHelper.getAllFlowers();
                refreshDisplay();
                return false;
            }
        });
        menu.add("Get All Flowers").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                flowers = flowerDbHelper.getAllFlowers();
                refreshDisplay();
//                Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        menu.add("Fancy").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                flowers = flowerDbHelper.getFlower(null, null, Flower.KEY_PRICE + " DESC");
                refreshDisplay();
                return false;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add("Cheap").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                flowers = flowerDbHelper.getFlower(null, null, Flower.KEY_PRICE + " ASC");
                refreshDisplay();
                return false;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    private void importJson() {
        InputStream input = getResources().openRawResource(R.raw.flowers_json);
        List<Flower> flowers = FlowerJsonParser.parseJson(input);
        Log.i("JsonParser", "FlowerJsonParser : Returned " + flowers.size() + " items.");
        for (Flower flower : flowers) {
            flowerDbHelper.insertFlower(flower);
        }
    }

    private void refreshDisplay() {

        adapter = new FlowerAdapter(this, flowers);
        listView.setAdapter(adapter);
        Toast.makeText(this, "ListView Updated \n" + flowers.size() + " items", Toast.LENGTH_SHORT).show();

    }
}
