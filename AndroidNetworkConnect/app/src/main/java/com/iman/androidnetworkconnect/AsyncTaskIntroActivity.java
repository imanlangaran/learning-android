package com.iman.androidnetworkconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsyncTaskIntroActivity extends AppCompatActivity {

    private static final String SAMPLE_URL = "https://langaran.000webhostapp.com/mysite/flowers_json.json";


    public static int counter = 0;

    List<AsyncTask> tasks = new ArrayList<>();

    TextView tv;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_intro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv = findViewById(R.id.tv);
        tv.setMovementMethod(new ScrollingMovementMethod());

        pb = findViewById(R.id.progressBar);

        findViewById(R.id.btn_intro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TaskIntro("task #" + ++counter)
//                        .execute("bank", "hospital", "shopping")
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "1", "2", "3", "4");
            }
        });

        findViewById(R.id.btn_get_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TaskGetData().execute(SAMPLE_URL);
            }
        });
    }


    public class TaskIntro extends AsyncTask<String, String, String>{

        private String taskName;
        Random random;
        int time;

        TaskIntro (String taskName){
            this.taskName = taskName;
            random = new Random();
            time = random.nextInt(750) + 750;
        }

        @Override
        protected void onPreExecute() {
            tv.append("< " + taskName + " >   start! time = " + time + "\n");
            tasks.add(this);
            if (!tasks.isEmpty())   pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            for (String p : params) {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress("< " + taskName + " >   Progress : " + p + "\n");
            }

            return "< " + taskName + " >   done\n";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            tv.append(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            tv.append(result);
            tasks.remove(this);
            if (tasks.isEmpty())   pb.setVisibility(View.INVISIBLE);

        }
    }

    public class TaskGetData extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {

            tasks.add(this);
            if (!tasks.isEmpty())   pb.setVisibility(View.VISIBLE);
            tv.append("Getting Data ...\n\n");

        }

        @Override
        protected String doInBackground(String... strings) {
            return MyHttpUtils.getDataHttpClient(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            tasks.remove(this);
            if (tasks.isEmpty())    pb.setVisibility(View.INVISIBLE);
            tv.append(s + "\n");
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Reset").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                tv.setText("");
                counter = 0;
                tasks.clear();
                pb.setVisibility(View.INVISIBLE);
                return false;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }
}
