package com.example.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Time;

public class CreateObservation extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, saveButton, backButton,timeButton;

    private CheckBox checkBoxParking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_observation);

        Intent i = getIntent();
        long hikeId = i.getLongExtra("HikeId",0);

        initDatePicker();
        dateButton = (Button) findViewById(R.id.dateObservation);
        dateButton.setText(getTodayDate());

        initTimePicker();
        timeButton = (Button) findViewById(R.id.TimeObservation);
        timeButton.setText(getCurrentTime());

        backButton = (Button) findViewById(R.id.backObservationCreate);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateObservation.this, Observation.class);
                i.putExtra("HikeId",hikeId);
                startActivity(i);
            }
        });


        saveData(hikeId);
    }

    private void saveData(long hikeId){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());


        EditText observation = (EditText)  findViewById(R.id.ObservationEditName);

        saveButton = (Button) findViewById(R.id.saveObservation);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String observationName = observation.getText().toString();
                String dateObservation = dateButton.getText().toString();
                String timeObservation = timeButton.getText().toString();

                String dateTimeObservation = dateObservation + " " + timeObservation;

                boolean pass = true;
                if (observationName.trim().equals(""))
                {
                    observation.setError( "observation is required!" );
                    pass = false;
                }
                if(pass == true)
                {
                    Intent i = new Intent(CreateObservation.this, Observation.class);
                    i.putExtra("HikeId",hikeId);
                    startActivity(i);
                    db.insertObservationData(observationName, dateTimeObservation, hikeId);
                }
            }
        });
    }

    private String getTodayDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return  day + "/" + month + "/" + year;
    }

    private String getCurrentTime()
    {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        return makeTimeString(hour, minute);
    }
    private void initTimePicker()
    {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = makeTimeString(hourOfDay, minute);
                timeButton.setText(time);
            }
        };

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        timePickerDialog = new TimePickerDialog (this, timeSetListener, hour, minute, true);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeTimeString(int hour, int minute)
    {
        return  hour + ":" + minute;
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
    public void openTimePicker(View view)
    {
        timePickerDialog.show();
    }
}