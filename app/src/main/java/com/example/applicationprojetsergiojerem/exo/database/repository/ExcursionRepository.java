package com.example.applicationprojetsergiojerem.exo.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

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

    /**
     * Méthode de base des singleton
     * @return
     */
    public static ExcursionRepository getInstance(){
        if (instance == null) {
            synchronized (ExcursionRepository.class){
                if (instance == null)
                    instance = new ExcursionRepository();
            }
        }

        return instance;
    }

    /**
     * Recupère une excursion de la db
     * @param id Id de l'excursion à recupérer
     * @param application
     * @return Excursion en LiveData
     */
    public LiveData<Excursion> getExcursion(final int id, Application application){
        return ((BaseApp) application).getDatabase().excursionDAO().getExcursionById(id);
    }

    /**
     * Recupère toutes les excursions de la db
     * @param application
     * @return Liste d'excursions de la db en LiveData
     */
    public LiveData<List<Excursion>> getAllExcursions(Application application){
        return ((BaseApp) application).getDatabase().excursionDAO().getAllExcursions();
    }

    /**
     * Recupère toutes les excursions d'un guide de la db
     * @param guideId Id du guide dont on veut recupérer les excursions
     * @param application
     * @return Liste des excursions d'un guide en LiveData
     */
    public LiveData<List<Excursion>> getExcursionsByGuide(final int guideId, Application application){
        return ((BaseApp) application).getDatabase().excursionDAO().getExcursionsByGuide(guideId);
    }

    /**
     * Insère une excursion dans la db
     * @param excursion Excursion à insérer dans la db
     * @param callback
     * @param application
     */
    public void insert(final Excursion excursion, OnAsyncEventListener callback, Application application){
        new CreateExcursion(application, callback).execute(excursion);
    }

    /**
     * Met une excursion à jour dans la db
     * @param excursion Objet excursion avec les infos à jour
     * @param callback
     * @param application
     */
    public void update(final Excursion excursion, OnAsyncEventListener callback, Application application){
        new UpdateExcursion(application, callback).execute(excursion);
    }

    /**
     * Efface une excursion de la db
     * @param excursion Excursion à effacer
     * @param callback
     * @param application
     */
    public void delete(final Excursion excursion, OnAsyncEventListener callback, Application application){
        new DeleteExcursion(application, callback).execute(excursion);
    }
}
