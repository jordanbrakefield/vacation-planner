package com.jordanbrakefield.vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jordanbrakefield.vacationplanner.R;
import com.jordanbrakefield.vacationplanner.database.Repository;
import com.jordanbrakefield.vacationplanner.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationList extends AppCompatActivity implements PrintToConsole {
private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);
        FloatingActionButton fab = findViewById((R.id.floatingActionButton2));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
                System.out.println("clicking + button");
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getmAllVacations();
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);

        printToConsole();
    }

//    Task B1, polymorphism
    @Override
    public void printToConsole(){
        System.out.println("Entering VacationList");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        List<Vacation> allVacations = repository.getmAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
//        if(item.getItemId() == R.id.sample){
//            repository=new Repository(getApplication());
//            //Toast.makeText(VacationList.this, "put in sample data", Toast.LENGTH_LONG).show();
//            Vacation vacation = new Vacation(0, "Panama", "The Panama Inn", parseDate("11/28/2024"), parseDate("12/10/2024"));
//            repository.insert(vacation);
//            Excursion excursion = new Excursion(0,"Panama Locks", parseDate("11/29/2024"), 1);
//            repository.insert(excursion);
//             vacation = new Vacation(0, "Costa Rica", "The Costa Ric'inn",parseDate("12/11/2024"), parseDate("12/15/2024") );
//            repository.insert(vacation);
//             excursion = new Excursion(0,"zip lining", parseDate("11/29/2024"), 1);
//            repository.insert(excursion);
//            return true;
//        }
        if(item.getItemId() == android.R.id.home){
            this.finish();
//            Intent intent = new Intent(VacationList.this, VacationDetails.class);
//            startActivity(intent);
            return true;
        }

        if(item.getItemId() == R.id.reportview){
            Intent intent = new Intent(VacationList.this, ReportList.class);
            intent.putExtra("test", "Information sent");
            startActivity(intent);
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