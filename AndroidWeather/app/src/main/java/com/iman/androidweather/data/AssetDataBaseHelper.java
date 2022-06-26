package com.iman.androidweather.data;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AssetDataBaseHelper {

    private Context context;
    private String dbName = "db_city.db";


    public AssetDataBaseHelper(Context context){
        this(context, "db_city.db");
    }

    public AssetDataBaseHelper(Context context, String dbName){
        this.context = context;
        this.dbName = dbName;
    }

    public void checkDb(){
        File dbFile = context.getDatabasePath(dbName);
        if (!dbFile.exists()) {
            try {
                copyDatabase(dbFile);
                Log.i("AssetDataBaseHelper", "database copied");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database!", e);
            }
        }
    }

    private void copyDatabase(File dbFile) throws IOException {
        InputStream is = context.getAssets().open(dbName);
        dbFile.getParentFile().mkdirs();
        OutputStream os = new FileOutputStream(dbFile);

        int len = 0;
        byte[] buffer = new byte[1024];

        while ((len = is.read(buffer)) > 0){
            os.write(buffer, 0, len);
        }

        os.flush();
        os.close();
        is.close();
    }

}
