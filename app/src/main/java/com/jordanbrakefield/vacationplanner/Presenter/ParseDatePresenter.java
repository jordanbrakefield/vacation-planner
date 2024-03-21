package com.jordanbrakefield.vacationplanner.Presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ParseDatePresenter {
    public ParseDatePresenter(){

    }

    public Date parseDate(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        try{
            return sdf.parse(dateString);
        }catch(ParseException e){
            e.printStackTrace();
            return new Date();
        }


    }

}
