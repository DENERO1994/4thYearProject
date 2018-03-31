package com.fourthyearproject.robsrecipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.http.HttpClient;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MyActivity";

    Button click;
    public static TextView data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click = (Button) findViewById(R.id.fetchData);
        data = (TextView) findViewById(R.id.dataView);
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

    public void getData(View view)
    {
        FetchData process = new FetchData();
        process.execute();
    }
}
