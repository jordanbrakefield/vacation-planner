package com.jordanbrakefield.vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jordanbrakefield.vacationplanner.R;

public class MainActivity extends AppCompatActivity implements PrintToConsole {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.getStartedButton);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VacationList.class);
                intent.putExtra("test", "Information sent");
                startActivity(intent);
            }
        });

        printToConsole();
    }

    //    Task B1, polymorphism
    @Override
    public void printToConsole(){
        System.out.println("Entering MainActivity");
    }
}