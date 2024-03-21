package com.jordanbrakefield.vacationplanner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jordanbrakefield.vacationplanner.entities.Excursion;

import java.util.List;

// Task B3 database component to add, modify and delete data

//Task B6, ROOM persistence library provides security measures
// to prevent SQL injection and also includes parameterization

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM EXCURSIONS ORDER BY excursionID ASC")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM EXCURSIONS WHERE vacationID=:vaca ORDER BY excursionID ASC")
    List<Excursion> getAssociatedExcursions(int vaca);
}
