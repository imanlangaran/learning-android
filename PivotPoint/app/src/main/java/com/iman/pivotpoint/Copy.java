package com.iman.pivotpoint;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;




public class Copy extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String text = getIntent().getStringExtra("text");
        if (text != null) {
            copyToClipboard(text);
//            Snackbar.make(view, "Data Copied To Clipboard.", Snackbar.LENGTH_LONG).show();
            Toast.makeText(this, "Data Copied To Clipboard", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(null, text);
        clipboard.setPrimaryClip(clip);
    }
}
