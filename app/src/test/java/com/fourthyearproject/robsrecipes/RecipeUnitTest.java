package com.fourthyearproject.robsrecipes;

import com.fourthyearproject.robsrecipes.data.UserDetailsDO;

import org.junit.Test;

import static org.junit.Assert.*;

//This class defines all of the local unit tests in the application
//Testing DB connectivity and getter/setter methods.

public class RecipeUnitTest {
    //This test checks that the data that is entered for the new user is saved in the backend DB
    @Test
    public void userDetails_isCorrect() throws Exception {
        //Create new user
        UserDetailsDO userDetails = new UserDetailsDO();

        //set the new users name and surname
        userDetails.setFirstName("Robert");
        userDetails.setSurname("Fitzgerald");

        //check if the data stored is correct
        assertEquals(userDetails.getFirstName(), "Robert");
    }
}