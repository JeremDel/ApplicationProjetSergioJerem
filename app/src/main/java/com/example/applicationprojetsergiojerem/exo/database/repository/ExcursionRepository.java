package com.example.applicationprojetsergiojerem.exo.database.repository;

import android.app.Application;

import com.example.applicationprojetsergiojerem.exo.BaseApp;
import com.example.applicationprojetsergiojerem.exo.database.async.excursion.CreateExcursion;
import com.example.applicationprojetsergiojerem.exo.database.async.excursion.DeleteExcursion;
import com.example.applicationprojetsergiojerem.exo.database.async.excursion.UpdateExcursion;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

import java.util.List;


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

    public List<Excursion> getAllExcursions(Application application){
        return ((BaseApp) application).getDatabase().excursionDao().getAllExcursions();
    }

    public List<Excursion> getExcursionsByGuide(final int guideId, Application application){
        return ((BaseApp) application).getDatabase().excursionDao().getExcursionsByGuide(guideId);
    }

    public void insert(final Excursion excursion, OnAsyncEventListener callback, Application application){
        new CreateExcursion(application, callback).execute(excursion);
    }

    public void update(final Excursion excursion, OnAsyncEventListener callback, Application application){
        new UpdateExcursion(application, callback).execute(excursion);
    }

    public void delete(final Excursion excursion, OnAsyncEventListener callback, Application application){
        new DeleteExcursion(application, callback).execute(excursion);
    }
}
