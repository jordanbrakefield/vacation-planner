package com.jordanbrakefield.vacationplanner.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jordanbrakefield.vacationplanner.Presenter.DateValidatorPresenter;
import com.jordanbrakefield.vacationplanner.Presenter.ParseDatePresenter;
import com.jordanbrakefield.vacationplanner.R;
import com.jordanbrakefield.vacationplanner.database.Repository;
import com.jordanbrakefield.vacationplanner.entities.Excursion;
import com.jordanbrakefield.vacationplanner.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    int vacationID;
    int excursionID;

    String title;
    Date excursionDate = new Date();

    EditText editTitle;
    EditText editExcursionDate;

    Repository repository;

    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    DatePickerDialog.OnDateSetListener excursionDatePicker;
    final Calendar myCalenderExcursion = Calendar.getInstance();
    Excursion excursionToDelete;

    Date vacationStart = new Date();
    Date vacationEnd = new Date();
    private com.jordanbrakefield.vacationplanner.Presenter.ParseDatePresenter ParseDatePresenter;
    public static final Date todaysDate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);
        repository = new Repository(getApplication());
        ParseDatePresenter = new ParseDatePresenter();

        editTitle = findViewById(R.id.excursiontitletext);
        editExcursionDate = findViewById(R.id.datetext);

        Intent intent = getIntent();
        vacationID = intent.getIntExtra("vacationID", -1);
        excursionID = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("title");

        excursionDate.setTime(intent.getLongExtra("date", ExcursionDetails.todaysDate.getTime()));
//        getVacationDate();
        editTitle.setText(title);
        editExcursionDate.setText(sdf.format(excursionDate));


//        search functionality with multiple rows and displays
        excursionDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalenderExcursion.set(Calendar.YEAR, year);
                myCalenderExcursion.set(Calendar.MONTH, month);
                myCalenderExcursion.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();

            }
        };


        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


//        search functionality with multiple rows and displays
        editExcursionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editExcursionDate.getText().toString();
                if (info.equals("")) {
                    info = "01/01/2024";
                }
                try {
                    myCalenderExcursion.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                    ;
                }
                new DatePickerDialog(ExcursionDetails.this, excursionDatePicker, myCalenderExcursion
                        .get(Calendar.YEAR), myCalenderExcursion.get(Calendar.MONTH), myCalenderExcursion.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editExcursionDate.setText(sdf.format((myCalenderExcursion.getTime())));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }


        if (item.getItemId() == R.id.excursionsave) {

            final boolean shouldConsumeEvent = true;

            //get the excursion date
            String dateString = editExcursionDate.getText().toString();
            Date date = parseDate(dateString);

            Vacation currentVacation = null;

            //get the current vacation id
            for (Vacation vaca : repository.getmAllVacations()) {
                if (vaca.getVacationID() == vacationID) {
                    currentVacation = vaca;
                    break;
                }
            }


            //get vacation start and end date
            Date startDate = currentVacation.getStartDate();
            Date endDate = currentVacation.getEndDate();



            DateValidatorPresenter dateValidatorPresenter = new DateValidatorPresenter(this);
            boolean isValidDate = DateValidatorPresenter.isOccursDuringTrip(date, startDate, endDate);

//            validate if an excursion is after the start date and before the end date
            if (!isValidDate) {
                dateValidatorPresenter.displayDateValidationErrorNotification();
                return shouldConsumeEvent; // Exit if date is invalid
            }



            System.out.println(excursionID);
            Excursion excursion = new Excursion(excursionID, editTitle.getText().toString(), parseDate(editExcursionDate.getText().toString()), vacationID);
            ;
            if (excursionID == 0) {
                repository.insert(excursion);
            } else {
                excursion = new Excursion(excursionID, editTitle.getText().toString(), parseDate(editExcursionDate.getText().toString()), vacationID);
                repository.update(excursion);
            }
            Toast.makeText(this, "excursion saved", Toast.LENGTH_LONG).show();
            this.finish();
        }


        if (item.getItemId() == R.id.excursiondelete) {

            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getExcursionID() == excursionID) {
                    excursionToDelete = excursion;
                    break;
                }
            }

            if (excursionToDelete != null) {
                repository.delete(excursionToDelete);
                Toast.makeText(ExcursionDetails.this, excursionToDelete.getExcursionTitle() + " deleted", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                Toast.makeText(ExcursionDetails.this, "Excursion not found", Toast.LENGTH_LONG).show();
            }

        }

        if (item.getItemId() == R.id.notify) {
            String dateFromScreen = editExcursionDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
            String dynamicSentence = "Excursion " + editTitle.getText().toString() + " is today!";
            intent.putExtra("key", dynamicSentence);

            PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            //set alarm even when app is turned off
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }

        return true;
    }

    public Date parseDate(String dateString) {
        return ParseDatePresenter.parseDate(dateString);

    }




}