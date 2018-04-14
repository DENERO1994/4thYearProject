package com.fourthyearproject.robsrecipes.data;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = "robsrecipes-mobilehub-9460472-userDetails")

public class UserDetailsDO {
    private String _userId;
    private String _userDetailsId;
    private String _firstName;
//    private List<Ingredient> _ingredients;
    private String _surname;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "userDetailsId")
    @DynamoDBAttribute(attributeName = "userDetailsId")
    public String getUserDetailsId() {
        return _userDetailsId;
    }

    public void setUserDetailsId(final String _userDetailsId) {
        this._userDetailsId = _userDetailsId;
    }
    @DynamoDBAttribute(attributeName = "firstName")
    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(final String _firstName) {
        this._firstName = _firstName;
    }

//    @DynamoDBAttribute(attributeName = "inventory")
//    public List<Ingredient> getIngredients() {
//        return _ingredients;
//    }
//
//    public void setIngredients(final List<Ingredient> _ingredients) {
//        this._ingredients = _ingredients;
//    }
    @DynamoDBAttribute(attributeName = "surname")
    public String getSurname() {
        return _surname;
    }

    public void setSurname(final String _surname) {
        this._surname = _surname;
    }

}
