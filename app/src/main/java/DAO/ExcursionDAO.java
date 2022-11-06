package DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import Entities.Excursion;

@Dao
public abstract class ExcursionDAO {

    // -- Base queries --

    @Query("SELECT * FROM Excursion")
    public abstract LiveData<Excursion> getAllExcursions();

    @Query("SELECT * FROM Excursion WHERE id = :id")
    public abstract LiveData<Excursion> getExcursionById(int id);

    @Query("SELECT * FROM Excursion WHERE guide = :guideId")
    public abstract LiveData<Excursion> getExcursionsByGuide(int guideId);

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
