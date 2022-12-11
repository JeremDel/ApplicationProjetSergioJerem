package com.example.applicationprojetsergiojerem.exo.database.repository;

import androidx.lifecycle.LiveData;

import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.firebase.ExcursionListLiveData;
import com.example.applicationprojetsergiojerem.exo.database.firebase.ExcursionLiveData;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class ExcursionRepository {

    // URL de la base de données Firebase
    private static final String FIREBASE_DB_URL = "https://snowshoestouring-default-rtdb.europe-west1.firebasedatabase.app/";

    // Instance unique du repository
    private static ExcursionRepository instance;

    // Constructeur privé (singleton)
    private ExcursionRepository() {
    }

    /**
     * Méthode de base des singleton pour obtenir une instance du repository
     *
     * @return Instance du repository
     */
    public static ExcursionRepository getInstance() {
        if (instance == null) {
            synchronized (ExcursionRepository.class) {
                if (instance == null)
                    instance = new ExcursionRepository();
            }
        }

        return instance;
    }

    /**
     * Recupère une excursion de la db
     *
     * @param id Id de l'excursion à recupérer
     * @return Excursion en LiveData
     */
    public LiveData<Excursion> getExcursion(final String id) {
        DatabaseReference dbReference = FirebaseDatabase.getInstance("https://snowshoestouring-default-rtdb.europe-west1.firebasedatabase.app/").getReference("excursions").child(String.valueOf(id));
        return new ExcursionLiveData(dbReference);
    }

    /**
     * Recupère toutes les excursions de la db
     *
     * @return Liste d'excursions de la db en LiveData
     */
    public LiveData<List<Excursion>> getAllExcursions(){

        FirebaseDatabase db = FirebaseDatabase.getInstance(FIREBASE_DB_URL);
        DatabaseReference ref = db.getReference("excursions");
        // Retourne une instance d'ExcursionListLiveData pour récupérer toutes les excursions de la base de données
        return new ExcursionListLiveData(ref);
    }

    /**
     * Insère une excursion dans la db
     *
     * @param excursion Excursion à insérer dans la db
     * @param callback
     */
    public void insert(final Excursion excursion, OnAsyncEventListener callback){

        FirebaseDatabase db = FirebaseDatabase.getInstance(FIREBASE_DB_URL);
        DatabaseReference ref = db.getReference("excursions");

        String excursionId = ref.push().getKey();

        ref.child(excursionId).setValue(excursion, (dbErr, dbRef) -> {

            if (dbErr != null)

                callback.onFailure(dbErr.toException());
            else

                callback.onSuccess();
        });
    }

    /**
     * Met une excursion à jour dans la db
     *
     * @param excursion Objet excursion avec les infos à jour
     * @param callback
     */
    public void update(final Excursion excursion, OnAsyncEventListener callback){

        FirebaseDatabase db = FirebaseDatabase.getInstance(FIREBASE_DB_URL);
        DatabaseReference ref = db.getReference("excursions").child(excursion.getId());

        ref.updateChildren(excursion.toMap(), (dbErr, dbRef) -> {

            if (dbErr != null)

                callback.onFailure(dbErr.toException());
            else

                callback.onSuccess();
        });
    }

    /**
     * Efface une excursion de la db
     *
     * @param excursion Excursion à effacer
     * @param callback
     */
    public void delete(final Excursion excursion, OnAsyncEventListener callback) {

        FirebaseDatabase db = FirebaseDatabase.getInstance(FIREBASE_DB_URL);
        DatabaseReference ref = db.getReference("excursions").child(excursion.getId());


        ref.removeValue((dbErr, dbRef) -> {

            if (dbErr != null)

                callback.onFailure(dbErr.toException());
            else

                callback.onSuccess();
        });
    }
}