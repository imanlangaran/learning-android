package com.iman.filedownload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private static final int PERM_REQ_CODE = 1234;

    public static final String DOWNLOAD_DIR = "myDownload";

    EditText input_url;

    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input_url = findViewById(R.id.input_url);
        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setProgress(0);
        pDialog.setMax(100);
        pDialog.setTitle("downloading ...");


        findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url_to_download = input_url.getText().toString().trim();
                if (url_to_download.isEmpty()) {
                    input_url.setError("inter file url to download");
                    input_url.requestFocus();
                    return;
                }

                new DownloadTask().execute(url_to_download);

            }
        });
        checkPerms();
    }

    private void checkPerms() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERM_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERM_REQ_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "closing app...", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    class DownloadTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            if (!pDialog.isShowing()){
                pDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {//param[0] : url_to_download
            try {
                URL url = new URL(params[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                long lengthFile = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    lengthFile = connection.getContentLengthLong();
                }

                Log.i("TAGGGG", String.valueOf(lengthFile));

                BufferedInputStream input = new BufferedInputStream(url.openStream(), 8 * 1024);
                String fileName = params[0].substring(params[0].lastIndexOf("/") + 1);
                File outputFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DOWNLOAD_DIR, fileName);
                if (outputFile.exists()){
                    String ext = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")+1) : "";
                    String name = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;
                    outputFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DOWNLOAD_DIR, name + "-2" + ext);
                }
                if (!outputFile.getParentFile().exists()){
                    outputFile.getParentFile().mkdirs();
                }
                OutputStream output = new FileOutputStream(outputFile);
                byte[] buffer = new byte[1024];
                int count = 0;
                long downloaded = 0;
                while ((count = input.read(buffer))!= -1){
                    downloaded += count;
                    publishProgress(String.valueOf(downloaded));
//                    Log.i("TAG", String.valueOf(lengthFile));
//                    Log.i("TAG", String.valueOf(downloaded));
                    output.write(buffer, 0, count);
                }
                output.flush();
                return outputFile.getAbsolutePath();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();

            if (result == null){
                Toast.makeText(MainActivity.this, "download failed", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            pDialog.setProgress(Integer.valueOf(values[0]));
        }
    }


}
