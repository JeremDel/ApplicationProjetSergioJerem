package com.example.applicationprojetsergiojerem.exo.database.repository;

import android.app.Application;

import com.example.applicationprojetsergiojerem.exo.BaseApp;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;

import java.util.List;

public class GuideRepository {

    private static GuideRepository instance;

    private GuideRepository(){

    }

    public GuideRepository getInstance(){
        if (instance == null){
            synchronized (GuideRepository.class){
                if (instance == null)
                    instance = new GuideRepository();
            }
        }

        return instance;
    }

    public List<Guide> getAllGuides(Application application){
        return ((BaseApp) application).getDatabase().guideDao().getAllGuides();
    }

    public List<Guide> getGuideById(final int id, Application application){
        return ((BaseApp) application).getDatabase().guideDao().getGuideById(id);
    }

    // TODO add async methods
}
