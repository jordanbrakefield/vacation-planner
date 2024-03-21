package com.jordanbrakefield.vacationplanner.Presenter;

import android.content.Context;
import android.widget.Toast;

import java.util.Date;

public class DateValidatorPresenter {
    private Context context;

    public DateValidatorPresenter(Context context){
        this.context = context;
    }




    public static boolean isOccursDuringTrip(final Date date,final Date startDate, final Date endDate){

        return !(date.before(startDate) || date.after(endDate));
    }

    private static String getDoesNotOccurDuringTripDisplayMessage = "Excursion date must be within vacation timeframe";

    public void displayDateValidationErrorNotification(){
        Toast.makeText(context, getDoesNotOccurDuringTripDisplayMessage, Toast.LENGTH_LONG).show();
    }

}

