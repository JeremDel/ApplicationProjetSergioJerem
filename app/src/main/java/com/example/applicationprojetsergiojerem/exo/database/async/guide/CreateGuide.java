package com.example.applicationprojetsergiojerem.exo.database.async.guide;

import android.app.Application;
import android.os.AsyncTask;

import com.example.applicationprojetsergiojerem.exo.BaseApp;
import com.example.applicationprojetsergiojerem.exo.database.AppDatabase;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

public class CreateGuide extends AsyncTask<Guide, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    /**
     * Constructeur
     * @param application
     * @param callback
     */
    public CreateGuide(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }

    /**
     * Insère les guides passées en paramètres dans la base de données
     * @param params Guides à insérer dans la db
     * @return
     */
    @Override
    protected Void doInBackground(Guide... params){
        try{
            for(Guide guide : params){
                ((BaseApp)application).getDatabase().guideDAO().insert(guide);
            }
        }
        catch (Exception e){
            exception = e;
            throw e;
        }

        return null;
    }

    /**
     * Log en fonction de si tout s'est bien passé ou pas
     * @param aVoid
     */
    @Override
    protected void onPostExecute(Void aVoid){
        if(callback != null){
            if (exception == null)
                callback.onSuccess();
        } else {
            callback.onFailure(exception);
        }
    }
}
