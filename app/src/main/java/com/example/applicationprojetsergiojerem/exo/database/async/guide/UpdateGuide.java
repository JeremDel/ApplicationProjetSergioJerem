package com.example.applicationprojetsergiojerem.exo.database.async.guide;

import android.app.Application;
import android.os.AsyncTask;

import com.example.applicationprojetsergiojerem.exo.BaseApp;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

public class UpdateGuide extends AsyncTask<Guide, Void, Void> {
    private Application application;
    private Exception exception;
    private OnAsyncEventListener callback;

    /**
     * Constructeur
     * @param application
     * @param callback
     */
    public UpdateGuide(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }

    /**
     * Mise à jour des guides passées en paramètres dans la base de données
     * @param params Guides à mettre à jour
     * @return
     */
    @Override
    protected Void doInBackground(Guide... params){
        try{
            for(Guide guide : params){
                ((BaseApp) application).getDatabase().guideDAO().update(guide);
            }
        } catch (Exception e){
            exception = e;
        }

        return null;
    }

    /**
     * Log en fonction de si tout s'est bien passé ou pas
     * @param aVoid
     */
    @Override
    protected void onPostExecute(Void aVoid){
        if (callback != null){
            if (exception == null)
                callback.onSuccess();
        } else{
            callback.onFailure(exception);
        }
    }
}
