package com.fourthyearproject.robsrecipes.data;

import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;

/**
 * Per the official Android documentation, this class defines all publically available
 * elements, like the authority, the content URIs, columns, and content types for each
 * element
 */
public class UserDetailsContentContract {
    /**
     * The authority of the notes content provider - this must match the authority
     * specified in the AndroidManifest.xml provider section
     */
    public static final String AUTHORITY = "com.fourthyearproject.robsrecipes.provider";

    /**
     * The content URI for the top-level notes authority
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /**
     * Constants for the Notes table
     */
    public static final class UserDetails implements BaseColumns {
        /**
         * The Table Name
         */
        public static final String TABLE_NAME = "userDetails";

        /**
         * The internal ID
         */
        public static final String _ID = "id";

        /**
         * The noteId field
         */
        public static final String USERDETAILSID = "userDetailsId";

//        /**
//         * The inventory field
//         */
//        public static final String INVENTORY = "inventory";

        /**
         * The title field
         */
        public static final String FIRSTNAME = "firstName";

        /**
         * The content field
         */
        public static final String SURNAME = "surname";

        /**
         * The directory base-path
         */
        public static final String DIR_BASEPATH = "userDetails";

        /**
         * The items base-path
         */
        public static final String ITEM_BASEPATH = "userDetails/*";

        /**
         * The SQLite database command to create the table
         */
        public static final String CREATE_SQLITE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + _ID + " INTEGER PRIMARY KEY, "
                        + USERDETAILSID + " TEXT UNIQUE NOT NULL, "
//                        + INVENTORY + "TEXT NOT NULL DEFAULT"
                        + FIRSTNAME + " TEXT NOT NULL DEFAULT '', "
                        + SURNAME + " TEXT NOT NULL DEFAULT '') ";

        /**
         * The content URI for this table
         */
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(UserDetailsContentContract.CONTENT_URI, TABLE_NAME);

        /**
         * The mime type of a directory of items
         */
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.fourthyearproject.robsrecipes";

        /**
         * The mime type of a single item
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.fourthyearproject.robsrecipes";

        /**
         * A projection of all columns in the items table
         */
        public static final String[] PROJECTION_ALL = {
                _ID,
                USERDETAILSID,
//                INVENTORY,
                FIRSTNAME,
                SURNAME
        };

        /**
         * The default sort order (SQLite syntax)
         */
        public static final String SORT_ORDER_DEFAULT = FIRSTNAME + " ASC";

        /**
         * Build a URI for the provided note
         * @param userDetailsId the ID of the provided note
         * @return the URI of the provided note
         */
        public static Uri uriBuilder(String userDetailsId) {
            Uri item = new Uri.Builder()
                    .scheme("content")
                    .authority(UserDetailsContentContract.AUTHORITY)
                    .appendPath(UserDetailsContentContract.UserDetails.DIR_BASEPATH)
                    .appendPath(userDetailsId)
                    .build();
            return item;
        }
    }
}