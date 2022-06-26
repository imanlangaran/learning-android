package com.iman.fragment1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentStudentList.CallBacks{

    FragmentStudentList fragmentStudentList;
    boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentStudentList = new FragmentStudentList();
        getSupportFragmentManager().beginTransaction().replace(R.id.list_fragment_container, fragmentStudentList).commit();
        isTwoPane =  findViewById(R.id.list_fragment_container).getTag().equals("large");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Dimensions").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showDimensions();
                return false;
            }
        });

        menu.add("Setting").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void showDimensions() {
        ScreenUtility screenUtility = new ScreenUtility(this);

        String msg = "width : " + screenUtility.getDpWight() + " , height : " +screenUtility.getDpHeight();

        new AlertDialog.Builder(this)
                .setTitle("Dimensions")
                .setMessage(msg)
                .show();
    }


    @Override
    public void onItemSelected(Student student) {
//        Toast.makeText(this, "<Activity> Student name : "+ student.toString(), Toast.LENGTH_SHORT).show();
        FragmentStudentDetails details = FragmentStudentDetails.newInstance(student.id, student.name, student.score);

        if (isTwoPane){
            getSupportFragmentManager().beginTransaction().replace(R.id.detailsFragment_container, details).commit();
        }else {
//            startActivity(new Intent(this, StudentDetailsActivity.class).putExtra("student", student));
            details.show(getSupportFragmentManager(), "student_detail");
        }

    }
}
