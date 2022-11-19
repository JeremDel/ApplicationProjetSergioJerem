package com.example.applicationprojetsergiojerem.exo.database.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private final String url;
    private final ImageView imageView;

    /**
     * Constructor
     * @param url Url de l'image à obtenir
     * @param imageView ImageView où insérer l'image convertie en BitMap
     */
    public ImageLoadTask(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }

    /**
     * Crée une connection à l'url passée en paramètre et le décode en bitmap
     * @param params
     * @return
     */
    @Override
    protected Bitmap doInBackground(Void... params){
        try{
            URL urlConnection = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) urlConnection.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream is = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Assigne l'image dans l'ImageView
     * @param result Image en Bitmap
     */
    @Override
    protected void onPostExecute(Bitmap result){
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
    }
}
