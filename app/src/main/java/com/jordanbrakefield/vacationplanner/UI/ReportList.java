package com.jordanbrakefield.vacationplanner.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jordanbrakefield.vacationplanner.R;
import com.jordanbrakefield.vacationplanner.database.Repository;
import com.jordanbrakefield.vacationplanner.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportList extends AppCompatActivity {

    private Repository repository;
    private ReportAdapter reportAdapter;
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    final Calendar myCalendarVacation = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDatePicker;
    EditText editStartDate;
    Date startDate = new Date();
    TextView reportTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        editStartDate = findViewById(R.id.startdatetext);

        Intent intent = getIntent();
        startDate.setTime(intent.getLongExtra("startDate", VacationDetails.todaysDate.getTime()));

        editStartDate.setText(sdf.format(startDate));
        reportTimestamp = findViewById(R.id.reporttimestamp);

        startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarVacation.set(Calendar.YEAR, year);
                myCalendarVacation.set(Calendar.MONTH, month);
                myCalendarVacation.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStartOne();

                long currentTimeMillis = System.currentTimeMillis();
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
                String timestamp = dateTimeFormat.format(new Date(currentTimeMillis));
                reportTimestamp.setText("Report generated on " + timestamp);

                handleFilteredButtonClick(myCalendarVacation.getTime());

            }
        };

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
                new DatePickerDialog(ReportList.this, startDatePicker, myCalendarVacation
                        .get(Calendar.YEAR), myCalendarVacation.get(Calendar.MONTH), myCalendarVacation.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        RecyclerView reportRecyclerView = findViewById(R.id.reportrecyclerview);
        repository = new Repository(getApplication());
        reportAdapter = new ReportAdapter(this);
        reportRecyclerView.setAdapter(reportAdapter);
        reportRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void updateLabelStartOne() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        editStartDate.setText(sdf.format((myCalendarVacation.getTime())));
    }

    private void handleFilteredButtonClick(Date selectedDate){
        List<Vacation> filteredVacations = new ArrayList<>();
        for (Vacation vacation : repository.getmAllVacations()){
            if(isSameDate(vacation.getStartDate(), selectedDate)){
                filteredVacations.add(vacation);
            }
        }
        if (filteredVacations.isEmpty()){
            reportAdapter.setVacations(new ArrayList<>());
            Toast.makeText(this, "No vacations match the selected date", Toast.LENGTH_SHORT).show();
        }
        else{
            reportAdapter.setVacations(filteredVacations);
        }

    }

    private Boolean isSameDate(Date date1, Date date2){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)&&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)&&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
}
