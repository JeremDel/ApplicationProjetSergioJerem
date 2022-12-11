package com.example.applicationprojetsergiojerem.exo.database.repository;

import androidx.lifecycle.LiveData;

import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.database.firebase.GuideListLiveData;
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
        DatabaseReference dbReference = FirebaseDatabase.getInstance("https://snowshoestouring-default-rtdb.europe-west1.firebasedatabase.app/").getReference("guides");
        return new GuideListLiveData(dbReference);
    }

    /**
     * Recupère un guide de la db
     * @param id Id du guide à recupérer
     * @return Guide en LiveData
     */
    public LiveData<Guide> getGuideById(final String id){
        DatabaseReference dbReference = FirebaseDatabase.getInstance("https://snowshoestouring-default-rtdb.europe-west1.firebasedatabase.app/").getReference("guides").child(String.valueOf(id));
        return new GuideLiveData(dbReference);
    }

    /**
     * Insère un guide dans la db
     * @param guide Guide à insérer
     * @param callback
     */
    public void insert(final Guide guide, OnAsyncEventListener callback){
        DatabaseReference dbRef = FirebaseDatabase.getInstance("https://snowshoestouring-default-rtdb.europe-west1.firebasedatabase.app/").getReference("guides");
        String key = dbRef.push().getKey();
        FirebaseDatabase.getInstance("https://snowshoestouring-default-rtdb.europe-west1.firebasedatabase.app/").getReference("guides").child(key).setValue(guide, (dbErr, dbReference) -> {
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
        FirebaseDatabase.getInstance("https://snowshoestouring-default-rtdb.europe-west1.firebasedatabase.app/").getReference("guides").child(guide.getId()).updateChildren(guide.toMap(), (dbErr, dbRef) -> {
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
     */
    public void delete(final Guide guide, OnAsyncEventListener callback){
        FirebaseDatabase.getInstance("https://snowshoestouring-default-rtdb.europe-west1.firebasedatabase.app/").getReference("guides").child(guide.getId()).removeValue((dbErr, dbRef) -> {
            if (dbErr != null)
                callback.onFailure(dbErr.toException());
            else
                callback.onSuccess();
        });
    }
}
