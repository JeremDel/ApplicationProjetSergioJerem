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

    @Query("SELECT * FROM Guide")
    public abstract LiveData<List<Guide>> getAllGuides();

    @Query("SELECT * FROM Guide WHERE id = :id")
    public abstract LiveData<Guide> getGuideById(int id);


    // -- Insert --

    @Insert
    public abstract long insert(Guide guide);


    // -- Update --

    @Update
    public abstract void update(Guide guide);


    // -- Delete --

    @Delete
    public abstract void delete(Guide guide);

    @Query("DELETE FROM Guide")
    public abstract void deleteAll();
}
