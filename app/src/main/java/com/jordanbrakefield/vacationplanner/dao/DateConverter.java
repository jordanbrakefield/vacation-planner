package com.jordanbrakefield.vacationplanner.dao;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {


    private static final String DATE_FORMAT = "MM/dd/yy";

    @TypeConverter
    public static Date fromString(String value){
        try{
            return new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(value);
        } catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    @TypeConverter
    public static String dateToString(Date date){
        return date == null ? null : new SimpleDateFormat(DATE_FORMAT, Locale.US).format(date);
    }
}
