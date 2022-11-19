package com.example.applicationprojetsergiojerem.exo.database.async.excursion;

import android.app.Application;
import android.os.AsyncTask;

import com.example.applicationprojetsergiojerem.exo.BaseApp;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

public class DeleteExcursion extends AsyncTask<Excursion, Void, Void> {
    private Application application;
    private Exception exception;
    private OnAsyncEventListener callback;

    /**
     * Constructeur
     * @param application
     * @param callback
     */
    public DeleteExcursion(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }

    /**
     * Éfface les excursions passées en paramètres de la base de données
     * @param params Excursions à éffacer de la db
     * @return
     */
    @Override
    protected Void doInBackground(Excursion... params){
        try {
            for (Excursion excursion : params) {
                ((BaseApp) application).getDatabase().excursionDAO().delete(excursion);
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
        if(callback != null){
            if (exception == null)
                callback.onSuccess();
        } else {
            callback.onFailure(exception);
        }
    }
}
