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

    /**
     * Méthode de base des singleton
     * @return
     */
    public static GuideRepository getInstance(){
        if (instance == null){
            synchronized (GuideRepository.class){
                if (instance == null)
                    instance = new GuideRepository();
            }
        }

        return instance;
    }

    /**
     * Recupère tous les guides de la db
     * @param application
     * @return Liste de guides en LiveData
     */
    public LiveData<List<Guide>> getAllGuides(Application application){
        return ((BaseApp) application).getDatabase().guideDAO().getAllGuides();
    }

    /**
     * Recupère un guide de la db
     * @param id Id du guide à recupérer
     * @param application
     * @return Guide en LiveData
     */
    public LiveData<Guide> getGuideById(final int id, Application application){
        return ((BaseApp) application).getDatabase().guideDAO().getGuideById(id);
    }

    /**
     * Insère un guide dans la db
     * @param guide Guide à insérer
     * @param callback
     * @param application
     */
    public void insert(final Guide guide, OnAsyncEventListener callback, Application application){
        new CreateGuide(application, callback).execute(guide);
    }

    /**
     * Met un guide à jour dans la db
     * @param guide Objet guide avec les infos à jour
     * @param callback
     * @param application
     */
    public void update(final Guide guide, OnAsyncEventListener callback, Application application){
        new UpdateGuide(application, callback).execute(guide);
    }

    /**
     * Efface un guide de la db
     * @param guide Guide à effacer
     * @param callback
     * @param application
     */
    public void delete(final Guide guide, OnAsyncEventListener callback, Application application){
        new DeleteGuide(application, callback).execute(guide);
    }
}
