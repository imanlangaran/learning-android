package com.iman.androidnetworkconnect;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HttpParamsActivity extends AppCompatActivity {

    public static final String URI_SHOW_PARAMS = "https://langaran.000webhostapp.com/showparams.php";

    private ProgressBar pb;
    private TextView tv;
    private List<AsyncTask> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_params);
        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        tv = findViewById(R.id.tv);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("GET").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MyHttpUtils.RequestData requestData = new MyHttpUtils.RequestData(URI_SHOW_PARAMS, "GET");
                requestData.setParameter("name", "iman");
                requestData.setParameter("code", "123456");
                requestData.setParameter("msg", "hlo");

                new MyTask().execute(requestData);

                return false;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add("POST").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MyHttpUtils.RequestData requestData = new MyHttpUtils.RequestData(URI_SHOW_PARAMS, "POST");
                requestData.setParameter("firstName", "iman");
                requestData.setParameter("LastName", "langaran");
                requestData.setParameter("Age", "20");

                new MyTask().execute(requestData);

                return false;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    public class MyTask extends AsyncTask<MyHttpUtils.RequestData, Void, String>{

        @Override
        protected void onPreExecute() {
            tasks.add(this);
            if (!tasks.isEmpty()) pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(MyHttpUtils.RequestData... params) {
            MyHttpUtils.RequestData reqData = params[0];

            return MyHttpUtils.getDataHttpUrlConnection(reqData);
        }

        @Override
        protected void onPostExecute(String s) {
            tv.setText(s);
            tasks.remove(this);
            if (tasks.isEmpty()) pb.setVisibility(View.INVISIBLE);
        }
    }

}
