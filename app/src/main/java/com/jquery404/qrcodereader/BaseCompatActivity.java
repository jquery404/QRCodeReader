package com.jquery404.qrcodereader;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Faisal on 8/14/17.
 */

public abstract class BaseCompatActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}

