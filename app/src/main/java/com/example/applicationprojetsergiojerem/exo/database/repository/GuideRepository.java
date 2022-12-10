package com.example.applicationprojetsergiojerem.exo.database.repository;

import androidx.lifecycle.LiveData;

import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.database.firebase.GuideLiveData;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
     * @return Liste de guides en LiveData
     */
    public LiveData<List<Guide>> getAllGuides(){
        // TODO Add Jeremie's code
        return null; //((BaseApp) application).getDatabase().guideDAO().getAllGuides();
    }

    /**
     * Recupère un guide de la db
     * @param id Id du guide à recupérer
     * @return Guide en LiveData
     */
    public LiveData<Guide> getGuideById(final int id){
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("guides").child(String.valueOf(id));
        return new GuideLiveData(dbReference);
    }

    /**
     * Insère un guide dans la db
     * @param guide Guide à insérer
     * @param callback
     */
    public void insert(final Guide guide, OnAsyncEventListener callback){
        FirebaseDatabase.getInstance().getReference("guides").push().setValue(guide, (dbErr, dbRef) -> {
           if (dbErr != null)
               callback.onFailure(dbErr.toException());
           else
               callback.onSuccess();
        });
    }

    /**
     * Met un guide à jour dans la db
     * @param guide Objet guide avec les infos à jour
     * @param callback
     */
    public void update(final Guide guide, OnAsyncEventListener callback){
        FirebaseDatabase.getInstance().getReference("guides").updateChildren(guide.toMap(), (dbErr, dbRef) -> {
            if (dbErr != null)
                callback.onFailure(dbErr.toException());
            else
                callback.onSuccess();
        });
    }

    /**
     * Efface un guide de la db
     * @param guide Guide à effacer
     * @param callback
     * @param application
     */
    public void delete(final Guide guide, OnAsyncEventListener callback){
        FirebaseDatabase.getInstance().getReference("guides").child(String.valueOf(guide.getId())).removeValue((dbErr, dbRef) -> {
            if (dbErr != null)
                callback.onFailure(dbErr.toException());
            else
                callback.onSuccess();
        });
    }
}
