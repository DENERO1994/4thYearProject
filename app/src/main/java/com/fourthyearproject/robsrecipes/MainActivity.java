package com.fourthyearproject.robsrecipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void editProfile(View view)
    {
        Context context = view.getContext();
        Intent intent = new Intent(context, UserDetailsActivity.class);
        context.startActivity(intent);
    }

    public void scanItem(View view)
    {
        Context context = view.getContext();
        Intent intent = new Intent(context, ScannerActivity.class);
        context.startActivity(intent);
    }
}
