package com.fourthyearproject.robsrecipes.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.fourthyearproject.robsrecipes.AWSProvider;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.UUID;

/**
 * The UserDetails model
 *
 * _id      The internal ID - only relevant to the current device
 * userDetailsId   The global ID - should be unique globally
 * firstName    The users first name
 * surname  The users surname
 */
public class UserDetails {
    private long id = -1;
    private String userDetailsId;
    private String firstName;
    private String surname;

    /**
     * Create a new User from a Cursor object.  This version provides default values for
     * any information that is missing; hopefully, this ensures that the method never crashes
     * the app.
     *
     * @param c the cursor to read from - it must be set to the right position.
     * @return the user stored in the cursor.
     */
    public static UserDetails fromCursor(Cursor c) {
        UserDetails userDetails = new UserDetails();

        userDetails.setId(getLong(c, UserDetailsContentContract.UserDetails._ID, -1));
        userDetails.setUserDetailsId(getString(c, UserDetailsContentContract.UserDetails.USERDETAILSID, ""));
        userDetails.setFirstName(getString(c, UserDetailsContentContract.UserDetails.FIRSTNAME, ""));
        userDetails.setSurname(getString(c, UserDetailsContentContract.UserDetails.SURNAME, ""));

        return userDetails;
    }

    /**
     * Read a string from a key in the cursor
     *
     * @param c the cursor to read from
     * @param col the column key
     * @param defaultValue the default value if the column key does not exist in the cursor
     * @return the value of the key
     */
    private static String getString(Cursor c, String col, String defaultValue) {
        if (c.getColumnIndex(col) >= 0) {
            return c.getString(c.getColumnIndex(col));
        } else {
            return defaultValue;
        }
    }

    /**
     * Read a long value from a key in the cursor
     *
     * @param c the cursor to read from
     * @param col the column key
     * @param defaultValue the default value if the column key does not exist in the cursor
     * @return the value of the key
     */
    private static long getLong(Cursor c, String col, long defaultValue) {
        if (c.getColumnIndex(col) >= 0) {
            return c.getLong(c.getColumnIndex(col));
        } else {
            return defaultValue;
        }
    }

    /**
     * Create a new blank user
     */
    public UserDetails() {
        setUserDetailsId(AWSProvider.getInstance().getIdentityManager().getCachedUserID());
        setFirstName("");
        setSurname("");
    }

    /**
     * Returns the internal ID
     * @return the internal ID
     */
    public long getId() { return id; }

    /**
     * Sets the internal ID
     * @param id the new internal ID
     */
    public void setId(long id) { this.id = id;}

    /**
     * Returns the userDetailsId
     * @return the userDetails ID
     */
    public String getUserDetailsId() { return userDetailsId; }

    /**
     * Sets the userDetailsId
     * @param userDetailsId the new userDetails ID
     */
    public void setUserDetailsId(String userDetailsId) {
        this.userDetailsId = userDetailsId;
    }

    /**
     * Returns the first name
     * @return the first name
     */
    public String getFirstName() { return firstName; }

    /**
     * Sets the first name
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**a
     * Returns the surname
     * @return the surname
     */
    public String getSurname() { return surname; }

    /**
     * Sets the surname
     * @param surname the surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Updates the note
     * @param firstName the new first name
     * @param surname the new surname
     */
    public void updateUserDetails(String firstName, String surname) {
        setFirstName(firstName);
        setSurname(surname);
    }

    /**
     * The string version of the class
     * @return the class unique descriptor
     */
    @Override
    public String toString() {
        return String.format("[userDetails#%s] %s", userDetailsId, firstName);
    }

    /**
     * Return the ContentValue object for this record.  This should not include the _id
     * field as it is stored for us.
     */
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(UserDetailsContentContract.UserDetails.USERDETAILSID, userDetailsId);
        values.put(UserDetailsContentContract.UserDetails.FIRSTNAME, firstName);
        values.put(UserDetailsContentContract.UserDetails.SURNAME, surname);

        return values;
    }
}
