package com.fourthyearproject.robsrecipes.data;

/**
 * Created by robfi on 04/04/2018.
 */

import java.util.ArrayList;
import java.util.List;

public class IngredientData {

    private List<Inventory> inventory = new ArrayList<Inventory>();

    public List<Inventory> getInventory()
    {
        return inventory;
    }

}