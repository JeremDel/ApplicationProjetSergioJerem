package com.example.applicationprojetsergiojerem.exo.database.async.excursion;

import android.app.Application;
import android.os.AsyncTask;

import com.example.applicationprojetsergiojerem.exo.BaseApp;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

public class CreateExcursion extends AsyncTask<Excursion, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateExcursion(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Excursion... params){
        try{
            for(Excursion excursion : params){
                ((BaseApp) application).getDatabase().excursion().insert(excursion);
            }
        }
        catch (Exception e){
            exception = e;
        }

        return null;
    }

    @Override
    protected void onPOstExecute(Void aVoid){
        if (callback != null){
            if (exception == null){
                callback.onSuccess();
            }
        } else {
            callback.onFailure(exception);
        }
    }
}
