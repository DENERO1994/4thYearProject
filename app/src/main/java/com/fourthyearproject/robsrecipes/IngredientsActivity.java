package com.fourthyearproject.robsrecipes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class IngredientsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private ListView listView;
    private Spinner spinner;
    ListAdapter listAdapter;
    private static final String[]choices = {"Sort by Name", "Sort by Brand"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);

        listAdapter = new SimpleAdapter(
                IngredientsActivity.this,
                HomeActivity.listIngredients,
                R.layout.list_item,
                new String []{"brand", "name"},
                new int []{R.id.brand, R.id.name}
        );
        listView.setAdapter(listAdapter);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(IngredientsActivity.this,
                android.R.layout.simple_spinner_item, choices);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context context = view.getContext();
        Intent intent = new Intent(context, IngredientDetailsActivity.class);
        intent.putExtra(DownloadIngredients.KEY_ID, HomeActivity.listIngredients.get(position).get(DownloadIngredients.KEY_ID));
        intent.putExtra(DownloadIngredients.KEY_BRAND, HomeActivity.listIngredients.get(position).get(DownloadIngredients.KEY_BRAND));
        intent.putExtra(DownloadIngredients.KEY_NAME, HomeActivity.listIngredients.get(position).get(DownloadIngredients.KEY_NAME));
        intent.putExtra(DownloadIngredients.KEY_WEIGHT, HomeActivity.listIngredients.get(position).get(DownloadIngredients.KEY_WEIGHT));
        intent.putExtra(DownloadIngredients.KEY_BARCODE, HomeActivity.listIngredients.get(position).get(DownloadIngredients.KEY_BARCODE));
        intent.putExtra(DownloadIngredients.KEY_IMAGE, HomeActivity.listIngredients.get(position).get(DownloadIngredients.KEY_IMAGE));
        context.startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(position) {

            //Sorts ingredients by their name in ascending order
            case 0:
                Collections.sort(HomeActivity.listIngredients, new Comparator<HashMap<String, String>>() {
                    @Override
                    public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
                        return o1.get("name").compareTo(o2.get("name"));
                    }
                });

                //update the view to show new order
                listAdapter = new SimpleAdapter(
                        IngredientsActivity.this,
                        HomeActivity.listIngredients,
                        R.layout.list_item,
                        new String []{"brand", "name"},
                        new int []{R.id.brand, R.id.name}
                );
                listView.setAdapter(listAdapter);
                break;

            //Sorts ingredients by their brand in ascending order
            case 1:
                Collections.sort(HomeActivity.listIngredients, new Comparator<HashMap<String, String>>() {
                    @Override
                    public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
                        return o1.get("brand").compareTo(o2.get("brand"));
                    }
                });

                //update the view to show new order
                listAdapter = new SimpleAdapter(
                        IngredientsActivity.this,
                        HomeActivity.listIngredients,
                        R.layout.list_item,
                        new String []{"brand", "name"},
                        new int []{R.id.brand, R.id.name}
                );
                listView.setAdapter(listAdapter);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}