package com.fourthyearproject.robsrecipes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, FetchData.AsyncResponse {

    private ListView listView;
    FetchData getJson = new FetchData();
    ArrayList<HashMap<String, String>> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);

        getJson.delegate = this;
        getJson.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context context = view.getContext();
        Intent intent = new Intent(context, IngredientDetailsActivity.class);
        intent.putExtra(FetchData.KEY_ID, listData.get(position).get(FetchData.KEY_ID));
        intent.putExtra(FetchData.KEY_BRAND, listData.get(position).get(FetchData.KEY_BRAND));
        intent.putExtra(FetchData.KEY_NAME, listData.get(position).get(FetchData.KEY_NAME));
        intent.putExtra(FetchData.KEY_WEIGHT, listData.get(position).get(FetchData.KEY_WEIGHT));
        intent.putExtra(FetchData.KEY_BARCODE, listData.get(position).get(FetchData.KEY_BARCODE));
        context.startActivity(intent);
    }

    @Override
    public void onSuccess(ArrayList<HashMap<String, String>> response) {
        listData.addAll(response);

        ListAdapter listAdapter = new SimpleAdapter(
                InventoryActivity.this,
                response,
                R.layout.list_item,
                new String []{"brand", "name"},
                new int []{R.id.brand, R.id.name}
        );
        listView.setAdapter(listAdapter);
    }
}
