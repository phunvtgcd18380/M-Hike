package com.example.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

public class UpdateMHike extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton, saveButton, backButton;

    private CheckBox checkBoxParking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mhike);

        Intent i = getIntent();
        long id = i.getLongExtra("id",0);

        initDatePicker();
        backButton = (Button) findViewById(R.id.backHikeDetail);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateMHike.this, HikeDetail.class);
                i.putExtra("ịd",id);
                startActivity(i);
            }
        });
        String name = i.getStringExtra("name");
        String location = i.getStringExtra("location");
        String dateOfHike = i.getStringExtra("dateOfHike");
        String parkingAvailable = i.getStringExtra("parkingAvailable");
        String lengthOfHike = i.getStringExtra("lengthOfHike");
        String levelOfDifficult = i.getStringExtra("levelOfDifficult");
        String description = i.getStringExtra("description");


        EditText nameEditText = (EditText)  findViewById(R.id.NameEditNameUpdate);
        EditText locationEditText = (EditText)  findViewById(R.id.editTextLocationUpdate);
        EditText lengthOfHikeEditText = (EditText)  findViewById(R.id.editTextLengthOfHikeUpdate);
        EditText levelOfDifficultEditText = (EditText)  findViewById(R.id.editTextLevelOfDifficultUpdate);
        EditText descriptionEditText = (EditText)  findViewById(R.id.editTextDescriptionUpdate);

        dateButton = (Button) findViewById(R.id.dateButtonUpdate);
        dateButton.setText(dateOfHike);

        nameEditText.setText(name);
        locationEditText.setText(location);
        lengthOfHikeEditText.setText(lengthOfHike);
        levelOfDifficultEditText.setText(levelOfDifficult);
        descriptionEditText.setText(description);

        if(parkingAvailable.toUpperCase().equals("TRUE")) {
            checkBoxParking = (CheckBox) findViewById(R.id.checkBoxParkingUpdate);
            checkBoxParking.setChecked(true);
        }
        else {
            checkBoxParking = (CheckBox) findViewById(R.id.checkBoxParkingUpdate);
            checkBoxParking.setChecked(false);
        }
        saveData(id);
    }

    private void saveData(long id){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        EditText nameEditText = (EditText)  findViewById(R.id.NameEditNameUpdate);
        EditText locationEditText = (EditText)  findViewById(R.id.editTextLocationUpdate);
        EditText lengthOfHikeEditText = (EditText)  findViewById(R.id.editTextLengthOfHikeUpdate);
        EditText levelOfDifficultEditText = (EditText)  findViewById(R.id.editTextLevelOfDifficultUpdate);
        EditText descriptionEditText = (EditText)  findViewById(R.id.editTextDescriptionUpdate);

        saveButton = (Button) findViewById(R.id.update);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxParking = findViewById(R.id.checkBoxParkingUpdate);
                String parking = "false";
                if(checkBoxParking.isChecked()) {
                    parking = "true";
                }
                String name = nameEditText.getText().toString();
                String location = locationEditText.getText().toString();
                String dateOfHike = dateButton.getText().toString();
                String lengthOfHike = lengthOfHikeEditText.getText().toString();
                String levelOfDifficult = levelOfDifficultEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                boolean pass = true;
                if (name.trim().equals(""))
                {
                    nameEditText.setError( "name is required!" );
                    pass = false;
                }
                if (location.trim().equals(""))
                {
                    locationEditText.setError( "location is required!" );
                    pass = false;
                }
                if (lengthOfHike.trim().equals(""))
                {
                    lengthOfHikeEditText.setError( "length Of Hike is required!" );
                    pass = false;
                }
                if (levelOfDifficult.trim().equals(""))
                {
                    levelOfDifficultEditText.setError( "level Of Difficult is required!" );
                    pass = false;
                }
                if(pass == true)
                {
                    Intent i = new Intent(UpdateMHike.this, HikeDetail.class);
                    i.putExtra("id",id);
                    startActivity(i);
                    db.UpdateHikeById(id,name, location, dateOfHike, parking, lengthOfHike, levelOfDifficult, description);
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

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}