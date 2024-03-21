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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jordanbrakefield.vacationplanner.R;
import com.jordanbrakefield.vacationplanner.database.Repository;
import com.jordanbrakefield.vacationplanner.entities.Excursion;
import com.jordanbrakefield.vacationplanner.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    int vacationID;
    String title;
    String hotelName;
    Date startDate = new Date();
    Date endDate = new Date();
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;
    Vacation currentVacation;
    int numExcursions;


    final Calendar myCalendarVacation = Calendar.getInstance();
    EditText editTitle;
    EditText editHotel;
    EditText editStartDate;
    EditText editEndDate;
    Repository repository;
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    public static final Date todaysDate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab = findViewById((R.id.floatingActionButton));

        editTitle = findViewById(R.id.titletext);
        editHotel = findViewById(R.id.hoteltext);
        editStartDate = findViewById(R.id.startdatetext);
        editEndDate = findViewById(R.id.enddatetext);

        //get intents
        Intent intent = getIntent();
        vacationID = intent.getIntExtra("id", -1);
        title = intent.getStringExtra("title");
        hotelName = intent.getStringExtra("hotelName");

        startDate.setTime(intent.getLongExtra("startDate", VacationDetails.todaysDate.getTime()));
        endDate.setTime(intent.getLongExtra("endDate", VacationDetails.todaysDate.getTime()));


        editTitle.setText(title);
        editHotel.setText(hotelName);
        editStartDate.setText(sdf.format(startDate));
        editEndDate.setText(sdf.format(endDate));



//        search functionality with multiple rows and displays
        startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarVacation.set(Calendar.YEAR, year);
                myCalendarVacation.set(Calendar.MONTH, month);
                myCalendarVacation.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStartOne();

            }
        };

//        search functionality with multiple rows and displays
        endDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarVacation.set(Calendar.YEAR, year);
                myCalendarVacation.set(Calendar.MONTH, month);
                myCalendarVacation.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStartTwo();

            }
        };



        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


//        search functionality with multiple rows and displays
        editStartDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Date date;
                String info = editStartDate.getText().toString();
                if(info.equals("")){
                    info = "01/01/2024";
                }
                try{
                    myCalendarVacation.setTime(sdf.parse(info));
                }
                catch(ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, startDatePicker, myCalendarVacation
                        .get(Calendar.YEAR), myCalendarVacation.get(Calendar.MONTH), myCalendarVacation.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

//        search functionality with multiple rows and displays
        editEndDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Date date;
                String info = editEndDate.getText().toString();
                if(info.equals("")){
                    info = "01/01/2024";
                }
                try{
                    myCalendarVacation.setTime(sdf.parse(info));
                }
                catch(ParseException e){
                    e.printStackTrace();;
                }
                new DatePickerDialog(VacationDetails.this, endDatePicker, myCalendarVacation
                        .get(Calendar.YEAR), myCalendarVacation.get(Calendar.MONTH), myCalendarVacation.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID", vacationID);
                startActivity(intent);
                System.out.println("clicking + button");
                System.out.println(vacationID);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> associatedExcursions = new ArrayList<>();
        for(Excursion e : repository.getAllExcursions()){
            if(e.getVacationID() == vacationID){
                associatedExcursions.add(e);
            }
        }
        excursionAdapter.setExcursions(associatedExcursions);
    }

    @Override
    protected void onResume(){
        super.onResume();
        List<Excursion> associatedExcursions = repository.getAssociatedExcursions(vacationID);
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(associatedExcursions);
    }


    private void updateLabelStartOne() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


       editStartDate.setText(sdf.format((myCalendarVacation.getTime())));
    }

    private void updateLabelStartTwo() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

       editEndDate.setText(sdf.format((myCalendarVacation.getTime())));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    private List<String> getExcursionTitlesForVacation(int vacationID) {
        List<String> excursionTitles = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationID() == vacationID) {
                excursionTitles.add(e.getExcursionTitle());
            }
        }
        return excursionTitles;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            System.out.println("back button");
            this.finish();
        }

        if (item.getItemId() == R.id.vacationsave) {
            Vacation vacation;

            String startDateString = editStartDate.getText().toString();
            String endDateString = editEndDate.getText().toString();
            Date startDate = parseDate(startDateString);
            Date endDate = parseDate(endDateString);

            if (endDate.before(startDate)) {
                Toast.makeText(VacationDetails.this, "End date can not be before the start date", Toast.LENGTH_LONG).show();
                return true;
            }

            if (vacationID == -1) {
                if (repository.getmAllVacations().size() == 0) {
                    vacationID = 1;
                    //Assign vacation ID to 1 and then add the vacation object to repo
                    vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), parseDate(editStartDate.getText().toString()), parseDate(editEndDate.getText().toString()));
                    repository.insert(vacation);
                    this.finish();

                } else {
                    //figure out next available vaca ID and then save the vaca object
                    vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationID() + 1;
                    vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), parseDate(editStartDate.getText().toString()), parseDate(editEndDate.getText().toString()));
                    repository.insert(vacation);
                    System.out.println("insert");

                    this.finish();
                }

            } else {
                //update the vacation
                vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), parseDate(editStartDate.getText().toString()), parseDate(editEndDate.getText().toString()));
                repository.update(vacation);
                System.out.println("update");
                this.finish();
            }
        }

        if (item.getItemId() == R.id.vacationdelete) {
            for (Vacation vaca : repository.getmAllVacations()) {
                if (vaca.getVacationID() == vacationID) {
                    currentVacation = vaca;
                }
            }
            numExcursions = 0;
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getVacationID() == vacationID) {
                    ++numExcursions;
                }
            }
            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationTitle() + " deleted", Toast.LENGTH_LONG).show();
                VacationDetails.this.finish();
            } else {
                Toast.makeText(VacationDetails.this, "Can't delete a vacation that is associated with excursions", Toast.LENGTH_LONG).show();
            }
            System.out.println("delete");
        }

        if(item.getItemId() == R.id.vacationNotify){
            String startDateFromScreen = editStartDate.getText().toString();
            String endDateFromScreen = editEndDate.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
            Date startDate = null;
            try{
                startDate = sdf.parse(startDateFromScreen);
            }catch(ParseException e){
                e.printStackTrace();
            }
            Long startTrigger =startDate.getTime();

            Date endDate = null;

            try{
                endDate = sdf.parse(endDateFromScreen);
            }catch (ParseException e){
                e.printStackTrace();
            }
            Long endTrigger = endDate.getTime();

            Intent startIntent = new Intent(VacationDetails.this, MyReceiver.class);
            String startDynamicSentence = "Your vacation, " + editTitle.getText().toString() + " is starting today!";
            startIntent.putExtra("startingKey", startDynamicSentence);
            PendingIntent startSender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, startIntent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            startAlarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startTrigger, startSender);

            Intent endIntent = new Intent(VacationDetails.this, MyReceiver.class);
            String endDynamicSentence = "Your vacation, " + editTitle.getText().toString() + " is ending today!";
            endIntent.putExtra("endingKey", endDynamicSentence);
            PendingIntent endSender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, endIntent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            endAlarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, endTrigger, endSender);
        }

        if(item.getItemId() == R.id.share){
            Intent sentIntent = new Intent();

            List<String> excursionTitles = getExcursionTitlesForVacation(vacationID);

            String extraTitle = "Take a look at this getaway!";
            String sharedText = "Vacation Title: " + editTitle.getText().toString() +
                    "\nHotel: " + editHotel.getText().toString() + "\nTrip begins: " + editStartDate.getText().toString()
                    + "\nTrip Ends: " + editEndDate.getText().toString() + "\n";


            if(!excursionTitles.isEmpty()){
                sharedText += "\nExcursion(s) available:\n";
                for(String excursionTitle : excursionTitles){
                    sharedText += excursionTitle + "\n";
                }
            }

            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TEXT, sharedText);
            sentIntent.putExtra(Intent.EXTRA_TITLE, extraTitle);
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }
        return true;
    }

    private Date parseDate(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        try{
            return sdf.parse(dateString);
        }catch(ParseException e){
            e.printStackTrace();
            return new Date();
        }


    }



}