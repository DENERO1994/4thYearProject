package com.fourthyearproject.robsrecipes;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

/**
 * An activity representing a single User profile details screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * details are presented side-by-side with a list of items
 * in a { UserDetailsListActivity}.
 */
public class UserDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        setSupportActionBar((Toolbar) findViewById(R.id.details_toolbar));

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey(UserDetailsFragment.ARG_ITEM_ID)) {
                String userDetailId = extras.getString(UserDetailsFragment.ARG_ITEM_ID);
                arguments.putString(UserDetailsFragment.ARG_ITEM_ID, userDetailId);
            }
            UserDetailsFragment fragment = new UserDetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.user_details_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}