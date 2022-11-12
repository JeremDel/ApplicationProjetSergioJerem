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

    @Query("SELECT * FROM Excursion")
    public abstract LiveData<List<Excursion>> getAllExcursions();

    @Query("SELECT * FROM Excursion WHERE id = :id")
    public abstract LiveData<Excursion> getExcursionById(int id);


    @Query("SELECT * FROM Excursion WHERE guide = :guideId")
    public abstract LiveData<List<Excursion>> getExcursionsByGuide(int guideId);

    // -- Insert --

    @Insert
    public abstract long insert(Excursion excursion);

    // -- Update --

    @Update
    public abstract void update(Excursion excursion);

    // -- Delete --

    @Delete
    public abstract void delete(Excursion excursion);

    @Query("DELETE FROM Excursion")
    public abstract void deleteAll();
}
