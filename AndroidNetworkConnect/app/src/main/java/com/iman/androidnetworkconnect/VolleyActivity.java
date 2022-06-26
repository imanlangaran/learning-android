package com.iman.androidnetworkconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VolleyActivity extends AppCompatActivity {

    public static final String URI_SHOW_PARAMS = "https://langaran.000webhostapp.com/showparams.php";

    RequestQueue requestQueue;

    ProgressDialog pDialog;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);


        requestQueue = Volley.newRequestQueue(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Wait ...");
        pDialog.setMessage("");
        pDialog.setCancelable(true);

        imageView = findViewById(R.id.imageview);
    }

    ;


    private void sendParamsGet() {
        Map<String, String> params = new HashMap<>();
        params.put("City", "Tehran");
        params.put("Country", "Iran");
        String uri = URI_SHOW_PARAMS + "?" + MyHttpUtils.encodeParameters(params);


        StringRequest request = new StringRequest(
                Request.Method.GET,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        new AlertDialog.Builder(VolleyActivity.this)
                                .setTitle("Response ")
                                .setMessage(response)
                                .setPositiveButton("OK", null)
                                .show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        new AlertDialog.Builder(VolleyActivity.this)
                                .setTitle("Error")
                                .setMessage(error.getMessage())
                                .setPositiveButton("OK", null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
        );


        pDialog.show();
        requestQueue.add(request);

    }


    private void sendParamsPost() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URI_SHOW_PARAMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        new AlertDialog.Builder(VolleyActivity.this)
                                .setTitle("Response ")
                                .setMessage(response)
                                .setPositiveButton("OK", null)
                                .show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        new AlertDialog.Builder(VolleyActivity.this)
                                .setTitle("Error")
                                .setMessage(error.getMessage())
                                .setPositiveButton("OK", null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("firstname", "iman");
                params.put("lastname", "langaran");
                return params;
            }
        };


        pDialog.show();
        requestQueue.add(request);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("GET").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                sendParamsGet();
                return false;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add("POST").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                sendParamsPost();
                return false;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add("Image").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                loadImage();
                return false;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    private void loadImage() {
        String url = "https://langaran.000webhostapp.com/person4.png";
        ImageRequest request = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Toast.makeText(VolleyActivity.this, "DONE!", Toast.LENGTH_SHORT).show();
                        imageView.setImageBitmap(response);
                    }
                }, 200, 200, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VolleyActivity.this, "ERROR : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(request);
    }


}
