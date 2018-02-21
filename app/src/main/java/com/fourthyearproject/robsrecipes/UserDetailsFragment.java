package com.fourthyearproject.robsrecipes;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fourthyearproject.robsrecipes.data.UserDetails;
import com.fourthyearproject.robsrecipes.data.UserDetailsContentContract;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * A fragment representing a single Note detail screen.
 * This fragment is either contained in a {@link}
 * in two-pane mode (on tablets) or a {@link }
 * on handsets.
 */
public class UserDetailsFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "userDetailsId";

    /**
     * The dummy content this fragment is presenting.
     */
    private UserDetails mItem;
    private Uri itemUri;

    /**
     * Content Resolver
     */
    private ContentResolver contentResolver;

    /**
     * Is this an insert or an update?
     */
    private boolean isUpdate;

    /**
     * The component bindings
     */
    EditText editTitle;
    EditText editContent;

    /**
     * The timer for saving the record back to SQL
     */
    Handler timer = new Handler();
    Runnable timerTask = new Runnable() {
        @Override
        public void run() {
            saveData();                             // Save the data
            timer.postDelayed(timerTask, 5000);     // Every 5 seconds
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserDetailsFragment() {
    }

    /**
     * Lifecycle event handler - called when the fragment is created.
     * @param savedInstanceState the saved state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the ContentResolver
        contentResolver = getContext().getContentResolver();

        // Unbundle the arguments if any.  If there is an argument, load the data from
        // the content resolver aka the content provider.
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARG_ITEM_ID)) {
            String itemId = getArguments().getString(ARG_ITEM_ID);
            itemUri = UserDetailsContentContract.UserDetails.uriBuilder(itemId);
            Cursor data = contentResolver.query(itemUri, UserDetailsContentContract.UserDetails.PROJECTION_ALL, null, null, null);
            if (data != null) {
                data.moveToFirst();
                mItem = UserDetails.fromCursor(data);
                isUpdate = true;
            }
        } else {
            mItem = new UserDetails();
            isUpdate = false;
        }

        // Start the timer for the delayed start
        timer.postDelayed(timerTask, 5000);
    }

    /**
     * Lifecycle event handler - called when the fragment is paused.  Use this to do any
     * saving of data as it is the last opportunity to reliably do so.
     */
    @Override
    public void onPause() {
        super.onPause();
        timer.removeCallbacks(timerTask);
        saveData();
    }

    /**
     * Save the data from the form back into the database.
     */
    private void saveData() {
        // Save the edited text back to the item.
        boolean isUpdated = false;
        if (!mItem.getFirstName().equals(editTitle.getText().toString().trim())) {
            mItem.setFirstName(editTitle.getText().toString().trim());
            isUpdated = true;
        }
        if (!mItem.getSurname().equals(editContent.getText().toString().trim())) {
            mItem.setSurname(editContent.getText().toString().trim());
            isUpdated = true;
        }

        // Convert to ContentValues and store in the database.
        if (isUpdated) {
            ContentValues values = mItem.toContentValues();
            if (isUpdate) {
                contentResolver.update(itemUri, values, null, null);
            } else {
                itemUri = contentResolver.insert(UserDetailsContentContract.UserDetails.CONTENT_URI, values);
                isUpdate = true;    // Anything from now on is an update
                itemUri = UserDetailsContentContract.UserDetails.uriBuilder(mItem.getUserDetailsId());
            }
        }
    }

    /**
     * Returns the current note.
     * @return the current data
     */
    public UserDetails getUserDetails() {
        return mItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get a reference to the root view
        View rootView = inflater.inflate(R.layout.user_details, container, false);

        // Update the text in the editor
        editTitle = (EditText) rootView.findViewById(R.id.edit_first_name);
        editContent = (EditText) rootView.findViewById(R.id.edit_surname);

        editTitle.setText(mItem.getFirstName());
        editContent.setText(mItem.getSurname());

        return rootView;
    }
}
