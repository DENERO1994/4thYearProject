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
import java.util.List;

//Async task that runs in the background and downloads the recipes form a json file stored in s3

//Note this async task returns a list of hash maps. The reasoning for this is because there is an
//inner array within each json object meaning that some values in the hash map would be Strings and
//others List of strings. So by making all of the values be a list it gets around this issue (future
// fix will be too make it accept a generic type either a list or String)

public class DownloadRecipes extends AsyncTask<Void, Void, ArrayList<HashMap<String, List<String>>>>{

    private String data = "";
    private String line = "";

    //Delegate that allows data to be passed to the Home activity
    public AsyncResponse delegate = null;

    //Constants that store the tags of each element of the json file
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_COOKINGTIME = "cookingTime";
    public static final String KEY_INGREDIENTS = "ingredients";

    @Override
    protected  ArrayList<HashMap<String, List<String>>> doInBackground(Void... voids) {

        //Declare hash map of ingredients
        final ArrayList<HashMap<String, List<String>>> recipes = new ArrayList<>();

        try {
            //URL to the json file stored in s3
            URL url = new URL("https://s3-eu-west-1.amazonaws.com/projectrecipeapp/recipes.json");

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
                //Create new json object for each element in the json array
                JSONObject JO = (JSONObject) JA.get(i);

                //Create lists for each element
                List<String> id = new ArrayList<>();
                List<String> name = new ArrayList<>();
                List<String> cookingTime = new ArrayList<>();
                List<String> ingredients = new ArrayList<>();

                //Add elements to each list
                id.add(JO.getString(KEY_ID));
                name.add(JO.getString(KEY_NAME));
                cookingTime.add(JO.getString(KEY_COOKINGTIME));

                //create a new json array for the inner array "ingredients"
                JSONArray ingredientsJA = JO.getJSONArray(KEY_INGREDIENTS);

                //Inner loop that gets the inner JSON array "ingredients" from the recipe object
                for(int j = 0; j < ingredientsJA.length(); j++)
                {
                    //Add each ingredient to the list
                    ingredients.add(ingredientsJA.getJSONObject(j).toString());
                }

                //Create new hash map to store the object
                HashMap<String, List<String>> map = new HashMap<>();

                //add the elements to the map
                map.put(KEY_ID, id);
                map.put(KEY_NAME, name);
                map.put(KEY_COOKINGTIME, cookingTime);
                map.put(KEY_INGREDIENTS, ingredients);

                //add the map to the list of recipes
                recipes.add(map);
            }
        } catch (IOException|JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    //interface that allows access to the data from the home activity
    //(abstract method implemented in this activity)
    public interface AsyncResponse {
        void onRecipeSuccess(ArrayList<HashMap<String, List<String>>> response);
    }

    //When data has been successfully downloaded, call the success method which invokes
    //the abstract method in home activity
    @Override
    protected void onPostExecute(ArrayList<HashMap<String, List<String>>> result) {
        super.onPostExecute(result);
        delegate.onRecipeSuccess(result);
    }
}