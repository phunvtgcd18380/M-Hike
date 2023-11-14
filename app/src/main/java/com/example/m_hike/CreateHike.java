package com.example.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

public class CreateHike extends AppCompatActivity {

    private String parking;
    private DatePickerDialog datePickerDialog;
    private Button dateButton, saveButton;

    private CheckBox checkBoxParking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hike);
        initDatePicker();
        dateButton = (Button) findViewById(R.id.dateButton);
        dateButton.setText(getTodayDate());
        saveData();
    }

    private void saveData(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        checkBoxParking = findViewById(R.id.checkBoxParking);
        parking = "false";
        boolean pass = true;
        if(checkBoxParking.isChecked()) {
            parking = "true";
        }


        EditText nameEditText = (EditText)  findViewById(R.id.editTextName);
        EditText locationEditText = (EditText)  findViewById(R.id.editTextLocation);
        EditText lengthOfHikeEditText = (EditText)  findViewById(R.id.editTextLengthOfHike);
        EditText levelOfDifficultEditText = (EditText)  findViewById(R.id.editTextLevelOfDifficult);
        EditText descriptionEditText = (EditText)  findViewById(R.id.editTextDescription);

            saveButton = (Button) findViewById(R.id.save);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                        finish();
                        db.insertData(name, location, dateOfHike, parking, lengthOfHike, levelOfDifficult, description);
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
        return  day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}