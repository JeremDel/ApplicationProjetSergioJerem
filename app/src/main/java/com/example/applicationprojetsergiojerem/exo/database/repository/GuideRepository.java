package com.example.applicationprojetsergiojerem.exo.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.applicationprojetsergiojerem.exo.BaseApp;
import com.example.applicationprojetsergiojerem.exo.database.async.guide.CreateGuide;
import com.example.applicationprojetsergiojerem.exo.database.async.guide.DeleteGuide;
import com.example.applicationprojetsergiojerem.exo.database.async.guide.UpdateGuide;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

import java.util.List;

public class GuideRepository {

    private static GuideRepository instance;

    private GuideRepository(){

    }

    public static GuideRepository getInstance(){
        if (instance == null){
            synchronized (GuideRepository.class){
                if (instance == null)
                    instance = new GuideRepository();
            }
        }

        return instance;
    }

    public LiveData<List<Guide>> getAllGuides(Application application){
        return ((BaseApp) application).getDatabase().guideDAO().getAllGuides();
    }

    public LiveData<Guide> getGuideById(final int id, Application application){
        return ((BaseApp) application).getDatabase().guideDAO().getGuideById(id);
    }

    public void insert(final Guide guide, OnAsyncEventListener callback, Application application){
        new CreateGuide(application, callback).execute(guide);
    }

    public void update(final Guide guide, OnAsyncEventListener callback, Application application){
        new UpdateGuide(application, callback).execute(guide);
    }

    public void delete(final Guide guide, OnAsyncEventListener callback, Application application){
        new DeleteGuide(application, callback).execute(guide);
    }
}
