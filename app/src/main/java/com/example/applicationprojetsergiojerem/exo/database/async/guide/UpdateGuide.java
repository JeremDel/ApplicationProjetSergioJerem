package com.example.applicationprojetsergiojerem.exo.database.async.guide;

import android.app.Application;
import android.os.AsyncTask;

import com.example.applicationprojetsergiojerem.exo.BaseApp;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;

public class UpdateGuide extends AsyncTask<Guide, Void, Void> {
    private Application application;
    private Exception exception;
    private OnAsyncEventListener callback;

    public UpdateGuide(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Guide... params){
        try{
            for(Guide guide : params){
                ((BaseApp) application).getDatabase().guide().update(guide);
            }
        } catch (Exception e){
            exception = e;
        }

        return null;
    }


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
