package com.fourthyearproject.robsrecipes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourthyearproject.robsrecipes.data.Ingredient;

import java.util.HashMap;

public class IngredientDetailsActivity extends AppCompatActivity implements DownloadImage.AsyncResponse{

    HashMap<String, String> ingredientHash = new HashMap<>();
    DownloadImage getImage = new DownloadImage();
    ImageView itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_details);

        TextView brand = (TextView) findViewById(R.id.brand);
        TextView name = (TextView) findViewById(R.id.name);
        TextView weight = (TextView) findViewById(R.id.weight);
        TextView barcode = (TextView) findViewById(R.id.barcode);
        final Button addItem = (Button) findViewById(R.id.addItem);
        itemImage = (ImageView) findViewById(R.id.itemImage);

        Intent intent = getIntent();

        if(intent != null)
        {
            brand.append(intent.getStringExtra(DownloadIngredients.KEY_BRAND));
            name.append(intent.getStringExtra(DownloadIngredients.KEY_NAME));
            weight.append(intent.getStringExtra(DownloadIngredients.KEY_WEIGHT));
            barcode.append(intent.getStringExtra(DownloadIngredients.KEY_BARCODE));

            ingredientHash.put(DownloadIngredients.KEY_ID, intent.getStringExtra(DownloadIngredients.KEY_ID));
            ingredientHash.put(DownloadIngredients.KEY_BRAND, intent.getStringExtra(DownloadIngredients.KEY_BRAND));
            ingredientHash.put(DownloadIngredients.KEY_BARCODE, intent.getStringExtra(DownloadIngredients.KEY_BARCODE));
            ingredientHash.put(DownloadIngredients.KEY_NAME, intent.getStringExtra(DownloadIngredients.KEY_NAME));
            ingredientHash.put(DownloadIngredients.KEY_IMAGE, intent.getStringExtra(DownloadIngredients.KEY_IMAGE));
            ingredientHash.put(DownloadIngredients.KEY_WEIGHT, intent.getStringExtra(DownloadIngredients.KEY_WEIGHT));

            getImage.delegate = this;
            getImage.execute(ingredientHash.get(DownloadIngredients.KEY_IMAGE));

            if(InventoryActivity.inventoryList.isEmpty())
            {
                addItem.setEnabled(true);
            }
            else
            {
                for(HashMap<String, String> i : InventoryActivity.inventoryList)
                {
                    if(i.equals(ingredientHash))
                    {
                        addItem.setEnabled(false);
                        break;
                    }
                    else
                    {
                        addItem.setEnabled(true);
                    }
                }
            }
        }

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InventoryActivity.inventoryList.add(ingredientHash);
                Toast.makeText(IngredientDetailsActivity.this, "Added to My Inventory", Toast.LENGTH_LONG).show();
                addItem.setEnabled(false);
            }
        });
    }

    public void onSuccess(Bitmap response) {
        itemImage.setImageBitmap(response);
    }
}
