package com.fourthyearproject.robsrecipes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;

public class DownloadImage extends AsyncTask<String, Void, Bitmap>{

    public AsyncResponse delegate = null;

    @Override
    protected Bitmap doInBackground(String... strings) {
        String image = strings[0];

        Bitmap bitmap = null;
        try {
            InputStream input = new java.net.URL(image).openStream();

            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public interface AsyncResponse {
        void onSuccess(Bitmap response);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        delegate.onSuccess(result);
    }
}
