package com.iman.prefrenses;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TextView tvfirstName, tvlastName, tvage;
    private EditText etFirstName, etLastName, etAge;
    private Button save, load;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        preferences = getPreferences(MODE_PRIVATE);
        preferences = getSharedPreferences("custom", MODE_PRIVATE);

        initViews();
    }

    private void initViews() {

        tvfirstName = findViewById(R.id.TXT_firstName);
        tvlastName = findViewById(R.id.TXT_lastName);
        tvage = findViewById(R.id.TXT_age);
        etFirstName = findViewById(R.id.EDIT_firstName);
        etLastName = findViewById(R.id.EDIT_lastName);
        etAge = findViewById(R.id.EDIT_age);
        save = findViewById(R.id.BTN_save);
        load = findViewById(R.id.BTN_load);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstname = etFirstName.getText().toString().trim();
                String lastname = etLastName.getText().toString().trim();
                String age = etAge.getText().toString().trim();

                if (!firstname.isEmpty() && !lastname.isEmpty() && !age.isEmpty()) {

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("firstname", firstname);
                    editor.putString("lastname", lastname);
                    editor.putInt("age", Integer.parseInt(age));
                    editor.apply();
                    Toast.makeText(SecondActivity.this, "saved", Toast.LENGTH_SHORT).show();

                }else
                    Toast.makeText(SecondActivity.this, "not saved \nfill the parameters", Toast.LENGTH_SHORT).show();

            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = preferences.getString("firstname", "NOT-FOUND");
                String lastName = preferences.getString("lastname", "NOT-FOUND");
                int age = preferences.getInt("age", -1);

                new AlertDialog.Builder(SecondActivity.this)
                        .setTitle("prefrences")
                        .setMessage("first name : " + firstName + "\n" + "last name : " + lastName + "\n" + "age : " + (age == -1 ? "NOT-FOUND" : age))
                        .show();
            }
        });

    }
}
