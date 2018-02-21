package com.fourthyearproject.robsrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

public class UserDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey(UserDetailsFragment.ARG_ITEM_ID)) {
                String userDetailsId = extras.getString(UserDetailsFragment.ARG_ITEM_ID);
                arguments.putString(UserDetailsFragment.ARG_ITEM_ID, userDetailsId);
            }
            UserDetailsFragment fragment = new UserDetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.user_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       // int id = item.getItemId();
        //if (id == android.R.id.home) {
          //  navigateUpTo(new Intent(this, UserDetailsActivity.class));
            //return true;
        //}
        return super.onOptionsItemSelected(item);
    }
}