package com.example.applicationprojetsergiojerem.exo.database.repository;

import android.app.Application;

import com.example.applicationprojetsergiojerem.exo.BaseApp;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;

import java.util.List;

import kotlin.jvm.Synchronized;

public class ExcursionRepository {

    private static ExcursionRepository instance;

    private ExcursionRepository(){

    }


    public static ExcursionRepository getInstance(){
        if (instance == null) {
            synchronized (ExcursionRepository.class){
                if (instance == null)
                    instance = new ExcursionRepository();
            }
        }

        return instance;
    }

    public List<Excursion> getExcursion(final int id, Application application){
        return ((BaseApp) application).getDatabase().excursionDao().getExcursionById(id);
    }
}
