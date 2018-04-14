package com.fourthyearproject.robsrecipes.data;

import java.util.List;

//Class representing the recipe objects

public class Recipe {
    private String id;
    private String name;
    private String cookingTime; //Stored as minutes
    private List<Ingredient> ingredients; //List of ingredient objects

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
