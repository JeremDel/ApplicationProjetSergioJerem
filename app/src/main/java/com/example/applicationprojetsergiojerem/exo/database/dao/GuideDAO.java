package com.example.applicationprojetsergiojerem.exo.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;

import java.util.List;

@Dao
public abstract class GuideDAO {

    // -- Base queries --

    /**
     * Recupère tous les guides de la db
     * @return Liste de guides en LiveData
     */
    @Query("SELECT * FROM Guide")
    public abstract LiveData<List<Guide>> getAllGuides();

    /**
     * Recupère les guides (le guide) avec l'id passé en paramètre
     * @param id Id du guide à recupérer
     * @return Guide en LiveData
     */
    @Query("SELECT * FROM Guide WHERE id = :id")
    public abstract LiveData<Guide> getGuideById(int id);


    // -- Insert --

    /**
     * Insère un guide dans la db
     * @param guide Guide à insérer dans la db
     * @return
     */
    @Insert
    public abstract long insert(Guide guide);


    // -- Update --

    /**
     * Met un guide à jour dans la db
     * @param guide Objet guide avec les infos misses à jour
     */
    @Update
    public abstract void update(Guide guide);


    // -- Delete --

    /**
     * Efface un guide passé en paramètre de la db
     * @param guide Guide à effacer
     */
    @Delete
    public abstract void delete(Guide guide);

    /**
     * Efface TOUS les guides de la db
     */
    @Query("DELETE FROM Guide")
    public abstract void deleteAll();
}
