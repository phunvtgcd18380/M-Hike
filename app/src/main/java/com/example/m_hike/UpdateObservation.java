package com.example.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class UpdateObservation extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, saveButton, backButton,timeButton,deleteButton;

    private CheckBox checkBoxParking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_observation);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        Intent i = getIntent();
        long ObservationId = i.getLongExtra("ObservationId",0);
        long hikeId = i.getLongExtra("HikeId",0);
        String observationText = i.getStringExtra("observation");
        String dateTime = i.getStringExtra("dateTime");


        initDatePicker();
        dateButton = (Button) findViewById(R.id.dateObservationUpdate);

        dateButton.setText(dateTime.trim().split("\s")[0]);

        initTimePicker();
        timeButton = (Button) findViewById(R.id.TimeObservationUpdate);

        timeButton.setText(dateTime.trim().split("\s")[1]);

        EditText observation = (EditText)  findViewById(R.id.ObservationEditNameUpdate);

        observation.setText(observationText);

        deleteButton = (Button) findViewById((R.id.deleteObservation));

        deleteButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateObservation.this, Observation.class);
                i.putExtra("HikeId",hikeId);
                db.deleteObservationByID(ObservationId);
                startActivity(i);
            }
        }));

        backButton = (Button) findViewById(R.id.backObservationUpdate);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateObservation.this, Observation.class);
                i.putExtra("HikeId",hikeId);
                startActivity(i);
            }
        });


        saveData(ObservationId,hikeId);
    }

    private void saveData(long ObservationId, long hikeId){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());


        EditText observation = (EditText)  findViewById(R.id.ObservationEditNameUpdate);

        saveButton = (Button) findViewById(R.id.saveObservationUpdate);

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
                    Intent i = new Intent(UpdateObservation.this, Observation.class);
                    i.putExtra("HikeId",hikeId);
                    startActivity(i);
                    db.UpdateObservationById(ObservationId,observationName, dateTimeObservation, hikeId);
                }
            }
        });
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