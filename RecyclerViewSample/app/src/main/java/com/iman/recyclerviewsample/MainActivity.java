package com.iman.recyclerviewsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<MyFriends> myFriendsList;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);

        prepareData();

        showData();
    }

    private void showData() {
        adapter = new MyAdapter(myFriendsList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void prepareData() {
        if (myFriendsList == null){
            myFriendsList = new ArrayList<>();
        }else {
            myFriendsList.clear();
        }

        myFriendsList.add(new MyFriends("Ali", "Mashhad"));
        myFriendsList.add(new MyFriends("Mohammad", "Isfahan"));
        myFriendsList.add(new MyFriends("Arman", "Boushehr"));
        myFriendsList.add(new MyFriends("Akbar", "Sanandaj"));
        myFriendsList.add(new MyFriends("Kia", "Golbahar"));
        myFriendsList.add(new MyFriends("Yousef", "Torghabeh"));
        myFriendsList.add(new MyFriends("Rasoul" ,"Nushabur"));
        myFriendsList.add(new MyFriends("Ali", "Mashhad"));
        myFriendsList.add(new MyFriends("Mohammad", "Isfahan"));
        myFriendsList.add(new MyFriends("Arman", "Boushehr"));
        myFriendsList.add(new MyFriends("Akbar", "Sanandaj"));
        myFriendsList.add(new MyFriends("Kia", "Golbahar"));
        myFriendsList.add(new MyFriends("Yousef", "Torghabeh"));
        myFriendsList.add(new MyFriends("Rasoul" ,"Nushabur"));
        myFriendsList.add(new MyFriends("Ali", "Mashhad"));
        myFriendsList.add(new MyFriends("Mohammad", "Isfahan"));
        myFriendsList.add(new MyFriends("Arman", "Boushehr"));
        myFriendsList.add(new MyFriends("Akbar", "Sanandaj"));
        myFriendsList.add(new MyFriends("Kia", "Golbahar"));
        myFriendsList.add(new MyFriends("Yousef", "Torghabeh"));
        myFriendsList.add(new MyFriends("Rasoul" ,"Nushabur"));
        myFriendsList.add(new MyFriends("Ali", "Mashhad"));
        myFriendsList.add(new MyFriends("Mohammad", "Isfahan"));
        myFriendsList.add(new MyFriends("Arman", "Boushehr"));
        myFriendsList.add(new MyFriends("Akbar", "Sanandaj"));
        myFriendsList.add(new MyFriends("Kia", "Golbahar"));
        myFriendsList.add(new MyFriends("Yousef", "Torghabeh"));
        myFriendsList.add(new MyFriends("Rasoul" ,"Nushabur"));
        myFriendsList.add(new MyFriends("Ali", "Mashhad"));
        myFriendsList.add(new MyFriends("Mohammad", "Isfahan"));
        myFriendsList.add(new MyFriends("Arman", "Boushehr"));
        myFriendsList.add(new MyFriends("Akbar", "Sanandaj"));
        myFriendsList.add(new MyFriends("Kia", "Golbahar"));
        myFriendsList.add(new MyFriends("Yousef", "Torghabeh"));
        myFriendsList.add(new MyFriends("Rasoul" ,"Nushabur"));
    }
}
