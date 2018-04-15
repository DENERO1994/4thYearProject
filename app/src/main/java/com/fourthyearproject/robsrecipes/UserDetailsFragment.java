package com.fourthyearproject.robsrecipes;

import android.content.AsyncQueryHandler;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsClient;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;
import com.fourthyearproject.robsrecipes.data.UserDetails;
import com.fourthyearproject.robsrecipes.data.UserDetailsContentContract;

/**
 * A fragment representing a single User detail screen.
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
    EditText editFirstName;
    EditText editSurname;

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
    // Constants used for async data operations
    private static final int QUERY_TOKEN = 1001;
    private static final int UPDATE_TOKEN = 1002;
    private static final int INSERT_TOKEN = 1003;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the ContentResolver
        contentResolver = getContext().getContentResolver();

        // Unbundle the arguments if any.  If there is an argument, load the data from
        // the content resolver aka the content provider.
        Bundle arguments = getArguments();
        mItem = new UserDetails();
        if (arguments != null && arguments.containsKey(ARG_ITEM_ID)) {
            String itemId = getArguments().getString(ARG_ITEM_ID);
            itemUri = UserDetailsContentContract.UserDetails.uriBuilder(itemId);


            // Replace local cursor methods with async query handling
            AsyncQueryHandler queryHandler = new AsyncQueryHandler(contentResolver) {
                @Override
                protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                    super.onQueryComplete(token, cookie, cursor);
                    cursor.moveToFirst();
                    mItem = UserDetails.fromCursor(cursor);
                    isUpdate = true;

                    editFirstName.setText(mItem.getFirstName());
                    editSurname.setText(mItem.getSurname());
                }
            };
            queryHandler.startQuery(QUERY_TOKEN, null, itemUri, UserDetailsContentContract.UserDetails.PROJECTION_ALL, null, null, null);


        } else {
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
        if (!mItem.getFirstName().equals(editFirstName.getText().toString().trim())) {
            mItem.setFirstName(editFirstName.getText().toString().trim());
            isUpdated = true;
        }
        if (!mItem.getSurname().equals(editSurname.getText().toString().trim())) {
            mItem.setSurname(editSurname.getText().toString().trim());
            isUpdated = true;
        }

        // Replace local cursor methods with an async query handler
        // Convert to ContentValues and store in the database.
        if (isUpdated) {
            ContentValues values = mItem.toContentValues();

            AsyncQueryHandler queryHandler = new AsyncQueryHandler(contentResolver) {
                @Override
                protected void onInsertComplete(int token, Object cookie, Uri uri) {
                    super.onInsertComplete(token, cookie, uri);
                    Log.d("UserDetailsFragment", "insert completed");
                }

                @Override
                protected void onUpdateComplete(int token, Object cookie, int result) {
                    super.onUpdateComplete(token, cookie, result);
                    Log.d("UserDetailsFragment", "update completed");
                }
            };
            if (isUpdate) {
                queryHandler.startUpdate(UPDATE_TOKEN, null, itemUri, values, null, null);
            } else {
                queryHandler.startInsert(INSERT_TOKEN, null, UserDetailsContentContract.UserDetails.CONTENT_URI, values);
                //isUpdate = true;    // Anything from now on is an update

                // Send Custom Event to Amazon Pinpoint
                final AnalyticsClient mgr = AWSProvider.getInstance()
                        .getPinpointManager()
                        .getAnalyticsClient();
                final AnalyticsEvent evt = mgr.createEvent("AddDetails")
                        .withAttribute("userDetailsId", mItem.getUserDetailsId());
                mgr.recordEvent(evt);
                mgr.submitEvents();
            }
        }
    }

    /**
     * Returns the current user.
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
        editFirstName = (EditText) rootView.findViewById(R.id.edit_first_name);
        editSurname = (EditText) rootView.findViewById(R.id.edit_surname);

        editFirstName.setText(mItem.getFirstName());
        editSurname.setText(mItem.getSurname());

        return rootView;
    }
}
