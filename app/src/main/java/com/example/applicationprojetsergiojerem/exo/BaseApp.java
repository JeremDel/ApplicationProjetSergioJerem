package com.example.applicationprojetsergiojerem.exo;

import android.app.Application;

public class BaseApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
    }

    // TODO add getDatabase(), getAccountRepository(), getClientRepository() versions with the snowshoes
}
