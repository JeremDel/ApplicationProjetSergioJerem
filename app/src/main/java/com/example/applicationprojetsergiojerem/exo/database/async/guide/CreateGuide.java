package com.example.applicationprojetsergiojerem.exo.database.async.guide;

import android.app.Application;
import android.os.AsyncTask;

import com.example.applicationprojetsergiojerem.exo.BaseApp;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

public class CreateGuide extends AsyncTask<Guide, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateGuide(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Guide... params){
        try{
            for(Guide guide : params){
                ((BaseApp) application).getDatabase().guide().insert(guide);
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
