package com.example.applicationprojetsergiojerem.exo.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;

import java.util.List;

@Dao
public abstract class ExcursionDAO {

    // -- Base queries --

    /**
     * Recupère toutes les excursions de la db
     * @return Liste d'excursions en LiveData
     */
    @Query("SELECT * FROM Excursion")
    public abstract LiveData<List<Excursion>> getAllExcursions();

    /**
     * Recupère les excursions (l'excursion) avec l'id passé en paramètre
     * @param id Id de l'excursion à recupérer
     * @return Excursion en LiveData
     */
    @Query("SELECT * FROM Excursion WHERE id = :id")
    public abstract LiveData<Excursion> getExcursionById(int id);

    /**
     * Recupère toutes les excursions d'un guide
     * @param guideId Guide dont on veut recupérer les excursions
     * @return List d'excursions du guide en LiveData
     */
    @Query("SELECT * FROM Excursion WHERE guide = :guideId")
    public abstract LiveData<List<Excursion>> getExcursionsByGuide(int guideId);

    // -- Insert --

    /**
     * Insère une excursion dans la db
     * @param excursion Excursion à insérer dans la db
     * @return
     */
    @Insert
    public abstract long insert(Excursion excursion);

    // -- Update --

    /**
     * Met une excursion à jour dans la db
     * @param excursion Objet excursion avec les infos misses à jour
     */
    @Update
    public abstract void update(Excursion excursion);

    // -- Delete --

    /**
     * Efface une excursion passée en paramètre de la db
     * @param excursion Excursion à effacer
     */
    @Delete
    public abstract void delete(Excursion excursion);

    /**
     * Efface TOUTES les excursions de la db
     */
    @Query("DELETE FROM Excursion")
    public abstract void deleteAll();
}
