package com.fourthyearproject.robsrecipes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//This activity's purpose was to display the possible recipes that a user could make based on the
//contents of their inventory. (It is a working progress as I could not get the logic working)

public class RecipeActivity extends AppCompatActivity{

    private TextView textView;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

//        Button search = (Button) findViewById(R.id.addIngredients);
        textView = (TextView) findViewById(R.id.textView);

        List<String> names = new ArrayList<>();
        List<String> names2 = new ArrayList<>();
        List<HashMap<String, List<String>>> recipeMatch = new ArrayList<>();

        if(!InventoryActivity.inventoryList.isEmpty())
        {
            for(HashMap<String, String> ingredient : InventoryActivity.inventoryList)
            {
                names.add(ingredient.get(DownloadIngredients.KEY_NAME));
            }

            for(HashMap<String, List<String>> recipe : HomeActivity.listRecipes)
            {
                for(String s : recipe.get("ingredients"))
                {
                    names2.add(s.substring(8, s.length() - 1));
                }

                if(names.contains(names2))
                {
                    textView.append(recipe.toString());
                }
            }


//            listView = (ListView) findViewById(R.id.list_view);
//            listView.setOnItemClickListener(this);
//
//            listAdapter = new SimpleAdapter(
//                    RecipeActivity.this,
//                    recipeMatch,
//                    R.layout.list_item,
//                    new String []{"name", "cookingTime"},
//                    new int []{R.id.brand, R.id.name}
//            );
//            listView.setAdapter(listAdapter);
        }
    }

    public void getIngredients(View view)
    {
        Context context = view.getContext();
        Intent intent = new Intent(context, IngredientsActivity.class);
        context.startActivity(intent);
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Context context = view.getContext();
//        Intent intent = new Intent(context, IngredientDetailsActivity.class);
//        intent.putExtra(DownloadIngredients.KEY_ID, HomeActivity.listIngredients.get(position).get(DownloadIngredients.KEY_ID));
//        intent.putExtra(DownloadIngredients.KEY_BRAND, HomeActivity.listIngredients.get(position).get(DownloadIngredients.KEY_BRAND));
//        intent.putExtra(DownloadIngredients.KEY_NAME, HomeActivity.listIngredients.get(position).get(DownloadIngredients.KEY_NAME));
//        intent.putExtra(DownloadIngredients.KEY_WEIGHT, HomeActivity.listIngredients.get(position).get(DownloadIngredients.KEY_WEIGHT));
//        intent.putExtra(DownloadIngredients.KEY_BARCODE, HomeActivity.listIngredients.get(position).get(DownloadIngredients.KEY_BARCODE));
//        intent.putExtra(DownloadIngredients.KEY_IMAGE, HomeActivity.listIngredients.get(position).get(DownloadIngredients.KEY_IMAGE));
//        context.startActivity(intent);
//    }
}
