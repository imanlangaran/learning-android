package com.iman.file;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText input_fileName, input_content;
    private Button btnWriteFile, btnReadFile;
    private TextView tv;
    private CheckBox external;

    private File extDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_fileName = findViewById(R.id.edt_fileName);
        input_content = findViewById(R.id.edt_content);
        btnWriteFile = findViewById(R.id.btn_writeFile);
        btnReadFile = findViewById(R.id.btn_readFile);
        tv = findViewById(R.id.tv);
        external = findViewById(R.id.cb_external);

        btnWriteFile.setOnClickListener(this);
        btnReadFile.setOnClickListener(this);

        extDir = Environment.getExternalStorageDirectory();


        try {
            tv.setText("internal : " + getFilesDir().getCanonicalPath() + "\n");
            tv.append("external : " + Environment.getExternalStorageDirectory().getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {


        File dir = new File(getFilesDir(), "myDir");
        if (external.isChecked()){
//            if (!checkState())  return;
            dir = new File(extDir, "myDir");
        }

        String fileName = input_fileName.getText().toString().trim();
        String content = input_content.getText().toString().trim();
        fileName = fileName.replace(" ", "_");
        if (v.getId() == btnWriteFile.getId()){
            if (fileName.isEmpty()){
                input_fileName.setError("wrong fileName...");
                input_fileName.requestFocus();
                return;
            }
            if (content.isEmpty()){
                input_content.setError("no content");
                input_content.requestFocus();
                return;
            }

            writeFile(dir, fileName, content);
        }else if (v.getId() == btnReadFile.getId()){
            if (fileName.isEmpty()){
                input_fileName.setError("wrong fileName...");
                input_fileName.requestFocus();
                return;
            }
            String fileContent = readFile(dir, fileName);
            input_content.setText(fileContent);
        }
    }

    private String readFile(File dir, String fileName) {
        File file = new File(dir, fileName);
        if (!file.exists()) {
            Toast.makeText(this, "file not found", Toast.LENGTH_SHORT).show();
            return "";
        }


        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            StringBuilder sb= new StringBuilder();
            while (bis.available() != 0){
                sb.append((char)bis.read());
            }
            bis.close();
            fis.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    private void writeFile(File dir, String fileName, String content) {

        if (!dir.exists()) dir.mkdirs();

        File file = new File(dir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(this, "file created", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private boolean checkState(){
        String state = Environment.getExternalStorageState();
        if (state == Environment.MEDIA_MOUNTED){
            return true;
        }else if (state == Environment.MEDIA_MOUNTED_READ_ONLY){
            Toast.makeText(this, "read-only external storage", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "not accessed", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

}
