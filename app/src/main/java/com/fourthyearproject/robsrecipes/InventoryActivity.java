package com.fourthyearproject.robsrecipes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.fourthyearproject.robsrecipes.data.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    ListAdapter listAdapter;

    static List<HashMap<String, String>> inventoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);

        listAdapter = new SimpleAdapter(
                InventoryActivity.this,
                inventoryList,
                R.layout.list_item,
                new String []{"brand", "name"},
                new int []{R.id.brand, R.id.name}
        );
        listView.setAdapter(listAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context context = view.getContext();
        Intent intent = new Intent(context, IngredientDetailsActivity.class);
        intent.putExtra(DownloadIngredients.KEY_ID, inventoryList.get(position).get(DownloadIngredients.KEY_ID));
        intent.putExtra(DownloadIngredients.KEY_BRAND, inventoryList.get(position).get(DownloadIngredients.KEY_BRAND));
        intent.putExtra(DownloadIngredients.KEY_NAME, inventoryList.get(position).get(DownloadIngredients.KEY_NAME));
        intent.putExtra(DownloadIngredients.KEY_WEIGHT, inventoryList.get(position).get(DownloadIngredients.KEY_WEIGHT));
        intent.putExtra(DownloadIngredients.KEY_BARCODE, inventoryList.get(position).get(DownloadIngredients.KEY_BARCODE));
        intent.putExtra(DownloadIngredients.KEY_IMAGE, inventoryList.get(position).get(DownloadIngredients.KEY_IMAGE));
        intent.putExtra("position", position);
        context.startActivity(intent);
    }
}
