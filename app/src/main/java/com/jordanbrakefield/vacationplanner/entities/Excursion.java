package com.jordanbrakefield.vacationplanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.jordanbrakefield.vacationplanner.dao.DateConverter;

import java.util.Date;

@Entity(tableName = "excursions")
public class Excursion {

    public Excursion(int excursionID, String excursionTitle, Date excursionDate, int vacationID) {
        this.excursionID = excursionID;
        this.excursionTitle = excursionTitle;
        this.excursionDate = excursionDate;
        this.vacationID = vacationID;
    }

    @PrimaryKey(autoGenerate = true)
    private int excursionID;

    private String excursionTitle;

    @TypeConverters({DateConverter.class})
    private Date excursionDate;

    private int vacationID;

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public String getExcursionTitle() {
        return excursionTitle;
    }

    public void setExcursionTitle(String excursionTitle) {
        this.excursionTitle = excursionTitle;
    }

    public Date getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(Date excursionDate) {
        this.excursionDate = excursionDate;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }
}




