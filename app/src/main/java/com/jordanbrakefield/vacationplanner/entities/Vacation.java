package com.jordanbrakefield.vacationplanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.jordanbrakefield.vacationplanner.dao.DateConverter;

import java.util.Date;


@Entity(tableName = "vacations")
public class Vacation {

    public Vacation(int vacationID, String vacationTitle, String hotelName, Date startDate, Date endDate) {
        this.vacationID = vacationID;
        this.vacationTitle = vacationTitle;
        this.hotelName = hotelName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @PrimaryKey(autoGenerate = true)
    private int vacationID;

    private String vacationTitle;

    private String hotelName;

    @TypeConverters({DateConverter.class})
    private Date startDate;

    @TypeConverters({DateConverter.class})
    private Date endDate;


    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getVacationTitle() {
        return vacationTitle;
    }

    public void setVacationTitle(String vacationTitle) {
        this.vacationTitle = vacationTitle;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
