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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by robfi on 31/03/2018.
 */

public class FetchData extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>{

    String data = "";
    String line = "";

    public AsyncResponse delegate = null;

    public static final String KEY_ID = "id";
    public static final String KEY_BRAND = "brand";
    public static final String KEY_NAME = "name";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_BARCODE = "barcode";

    @Override
    protected  ArrayList<HashMap<String, String>> doInBackground(Void... voids) {

        final ArrayList<HashMap<String, String>> ingredients = new ArrayList<>();

        try {
            URL url = new URL("https://s3-eu-west-1.amazonaws.com/projectrecipeapp/ingredient.json");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray JA = new JSONArray(data);

            for(int i = 0; i < JA.length(); i++)
            {
                JSONObject JO = (JSONObject) JA.get(i);

                String id = JO.getString(KEY_ID);
                String brand = JO.getString(KEY_BRAND);
                String name = JO.getString(KEY_NAME);
                String weight = JO.getString(KEY_WEIGHT);
                String barcode = JO.getString(KEY_BARCODE);

                HashMap<String, String> map = new HashMap<>();

                map.put(KEY_ID, id);
                map.put(KEY_BRAND, brand);
                map.put(KEY_NAME, name);
                map.put(KEY_WEIGHT, weight);
                map.put(KEY_BARCODE, barcode);

                ingredients.add(map);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    public interface AsyncResponse {
        void onSuccess(ArrayList<HashMap<String, String>> response);
    }

    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
        super.onPostExecute(result);
        delegate.onSuccess(result);
    }
}