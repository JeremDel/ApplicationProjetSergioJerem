package com.example.applicationprojetsergiojerem.exo;

import android.app.Application;

import com.example.applicationprojetsergiojerem.exo.database.repository.ExcursionRepository;
import com.example.applicationprojetsergiojerem.exo.database.repository.GuideRepository;

public class BaseApp extends Application {

    public GuideRepository getGuideRepository(){
        return GuideRepository.getInstance();
    }

    public ExcursionRepository getExcursionRepository(){
        return ExcursionRepository.getInstance();
    }
}
