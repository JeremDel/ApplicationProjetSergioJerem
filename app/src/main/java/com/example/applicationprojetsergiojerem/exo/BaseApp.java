package com.example.applicationprojetsergiojerem.exo;

import android.app.Application;

import com.example.applicationprojetsergiojerem.exo.database.AppDatabase;
import com.example.applicationprojetsergiojerem.exo.database.repository.ExcursionRepository;
import com.example.applicationprojetsergiojerem.exo.database.repository.GuideRepository;

public class BaseApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
    }

    // TODO add getDatabase(), getAccountRepository(), getClientRepository() versions with the snowshoes
    public AppDatabase getDatabase(){
        return AppDatabase.getInstance(this);
    }

    public GuideRepository getGuideRepository(){
        return GuideRepository.getInstance();
    }

    public ExcursionRepository getExcursionRepository(){
        return ExcursionRepository.getInstance();
    }
}
