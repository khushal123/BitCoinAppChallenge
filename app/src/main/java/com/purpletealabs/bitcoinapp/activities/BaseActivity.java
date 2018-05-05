package com.purpletealabs.bitcoinapp.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public void showSnackBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}
