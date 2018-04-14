package com.fourthyearproject.robsrecipes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Home activity that users can navigate around the application from. This activity starts the async tasks that
//download the json files that store the ingredients and recipes. It stores the users inventory and recipe match
//which holds information regarding the users inventory and the recipes they can make with the ingredients they have.

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DownloadIngredients.AsyncResponse, DownloadRecipes.AsyncResponse {

    //Create fields for the Async task to download recipe and ingredient jason files
    DownloadIngredients getIngredientJson = new DownloadIngredients();
    DownloadRecipes getRecipeJson = new DownloadRecipes();

    //Lists to store all the ingredients and recipes downloaded
    static ArrayList<HashMap<String, String>> listIngredients = new ArrayList<>();
    static ArrayList<HashMap<String, List<String>>> listRecipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set the delegate for both async tasks to this activity
        getIngredientJson.delegate = this;
        getRecipeJson.delegate = this;

        //Execute the two async tasks to download all the data
        getRecipeJson.execute();
        getIngredientJson.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Navigation side bar which loads the correct activity when the link is clicked
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Context context = getApplicationContext();
            Intent intent = new Intent(context, ScannerActivity.class);
            context.startActivity(intent);
        } else if (id == R.id.nav_search) {
            Context context = getApplicationContext();
            Intent intent = new Intent(context, IngredientsActivity.class);
            context.startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Context context = getApplicationContext();
            Intent intent = new Intent(context, UserDetailsActivity.class);
            context.startActivity(intent);
        } else if (id == R.id.nav_inventory) {
            Context context = getApplicationContext();
            Intent intent = new Intent(context, InventoryActivity.class);
            context.startActivity(intent);
        } else if (id == R.id.nav_recipe) {
            Toast.makeText(getApplicationContext(), "Currently under development", Toast.LENGTH_LONG);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Method to set the ingredient list to the downloaded json of ingredients
    @Override
    public void onIngredientSuccess(ArrayList<HashMap<String, String>> response) {
        listIngredients.addAll(response);
    }

    //Method to set the recipes list to the downloaded json of recipes
    @Override
    public void onRecipeSuccess(ArrayList<HashMap<String, List<String>>> response) {
        listRecipes.addAll(response);
    }
}
