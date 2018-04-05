package com.fourthyearproject.robsrecipes.data;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBDocument;

import java.io.Serializable;

/**
 * Created by robfi on 01/04/2018.
 */
@DynamoDBDocument
public class Inventory implements Serializable{

    private String id;
    private String brand;
    private String name;
    private int weight;
    private String barcode;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getBrand(){
        return brand;
    }

    public void setBrand(String brand){
        this.brand = brand;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getWeight(){
        return weight;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public String getBarcoded(){
        return barcode;
    }

    public void setBarcode(String barcode){
        this.barcode = barcode;
    }
}
