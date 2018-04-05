package com.fourthyearproject.robsrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class IngredientDetailsActivity extends AppCompatActivity {

    private TextView brand;
    private TextView name;
    private TextView weight;
    private TextView barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_details);

        brand = (TextView) findViewById(R.id.brand);
        name = (TextView) findViewById(R.id.name);
        weight = (TextView) findViewById(R.id.weight);
        barcode = (TextView) findViewById(R.id.barcode);

        Intent intent = getIntent();

        if(intent != null)
        {
            brand.setText(intent.getStringExtra(FetchData.KEY_BRAND));
            name.setText(intent.getStringExtra(FetchData.KEY_NAME));
            weight.setText(intent.getStringExtra(FetchData.KEY_WEIGHT));
            barcode.setText(intent.getStringExtra(FetchData.KEY_BARCODE));
        }
    }
}
