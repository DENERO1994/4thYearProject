package com.fourthyearproject.robsrecipes;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

//Async task that runs in the background and downloads the ingredients form a json file stored in s3

public class DownloadIngredients extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>{

    private String data = "";
    private String line = "";

    //Delegate that allows data to be passed to the Home activity
    public AsyncResponse delegate = null;

    //Constants that store the tags of each element of the json file
    public static final String KEY_ID = "id";
    public static final String KEY_BRAND = "brand";
    public static final String KEY_NAME = "name";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_BARCODE = "barcode";
    public static final String KEY_IMAGE = "image";

    @Override
    protected  ArrayList<HashMap<String, String>> doInBackground(Void... voids) {

        //Declare hash map of ingredients
        final ArrayList<HashMap<String, String>> ingredients = new ArrayList<>();

        try {
            //URL to the json file stored in s3
            URL url = new URL("https://s3-eu-west-1.amazonaws.com/projectrecipeapp/ingredients.json");

            //Open up a connection to the url and read in the contents into a buffered reader for parsing
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while(line != null){
                line = bufferedReader.readLine();
                data += line;
            }

            //JSON array to store the data being passed in
            JSONArray JA = new JSONArray(data);

            //Loop through the JSON array to add contents to the ingredients list
            for(int i = 0; i < JA.length(); i++)
            {
                //Create a new object for each element of the array
                JSONObject JO = (JSONObject) JA.get(i);

                //get each of the values from the tags (i.e. value of "id" into String id)
                String id = JO.getString(KEY_ID);
                String brand = JO.getString(KEY_BRAND);
                String name = JO.getString(KEY_NAME);
                String weight = JO.getString(KEY_WEIGHT);
                String barcode = JO.getString(KEY_BARCODE);
                String image = JO.getString(KEY_IMAGE);

                //Create new hash map to store the object
                HashMap<String, String> map = new HashMap<>();

                //add the string values to the hash map
                map.put(KEY_ID, id);
                map.put(KEY_BRAND, brand);
                map.put(KEY_NAME, name);
                map.put(KEY_WEIGHT, weight);
                map.put(KEY_BARCODE, barcode);
                map.put(KEY_IMAGE, image);

                //add the map to the list of ingredients
                ingredients.add(map);
            }
        } catch (IOException|JSONException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    //interface that allows access to the data from the home activity
    //(abstract method implemented in this activity)
    public interface AsyncResponse {
        void onIngredientSuccess(ArrayList<HashMap<String, String>> response);
    }

    //When data has been successfully downloaded, call the success method which invokes
    //the abstract method in home activity
    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
        super.onPostExecute(result);
        delegate.onIngredientSuccess(result);
    }
}