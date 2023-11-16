package com.example.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HikeDetail extends AppCompatActivity {

    private TextView name,location,dateHike,parking,hikeLength,levelDifficult,description;
    private Button delete,observation,backHikeDetail,updateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_detail);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        TextView name = (TextView) findViewById(R.id.name3);
        TextView location = (TextView) findViewById(R.id.location3);
        TextView dateHike = (TextView) findViewById(R.id.dateHike3);
        TextView parking = (TextView) findViewById(R.id.parking3);
        TextView hikeLength = (TextView) findViewById(R.id.hikeLength3);
        TextView levelDifficult = (TextView) findViewById(R.id.levelDifficult3);
        TextView description = (TextView) findViewById(R.id.description3);

        Intent i = getIntent();
        long id = i.getLongExtra("id",0);
        if(id != 0)
        {
            MHike hikeDetail = db.getHikeDetailByID(id);
            name.setText("Name " + hikeDetail.name);
            location.setText("Location " + hikeDetail.location);
            dateHike.setText("Date " + hikeDetail.dateOfHike);
            if(hikeDetail.parkingAvailable.toUpperCase().equals("TRUE")) {
                parking.setText("Parking Available");
            }else {
                parking.setText("Parking None Available");
            }
            hikeLength.setText("Hike Length " + hikeDetail.lengthOfHike);
            levelDifficult.setText("Level Of Difficult " + hikeDetail.levelOfDifficult);
            description.setText("Description " + hikeDetail.description);

        }
        delete = (Button) findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteHikeDetailByID(id);
                Intent i = new Intent(HikeDetail.this, MainActivity.class);
                startActivity(i);
            }
        });

        backHikeDetail = (Button) findViewById(R.id.backHikeDetailt);
        backHikeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HikeDetail.this, MainActivity.class);
                startActivity(i);
            }
        });
        observation =  (Button) findViewById(R.id.observation);
        observation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HikeDetail.this, Observation.class);
                i.putExtra("HikeId",id);
                startActivity(i);
            }
        });
        updateButton =  (Button) findViewById(R.id.Update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HikeDetail.this, UpdateMHike.class);
                i.putExtra("id",id);
                if(id != 0) {
                    MHike hikeDetail = db.getHikeDetailByID(id);
                    i.putExtra("name", hikeDetail.name);
                    i.putExtra("location", hikeDetail.location);
                    i.putExtra("dateOfHike", hikeDetail.dateOfHike);
                    i.putExtra("parkingAvailable", hikeDetail.parkingAvailable);
                    i.putExtra("lengthOfHike", hikeDetail.lengthOfHike);
                    i.putExtra("levelOfDifficult", hikeDetail.levelOfDifficult);
                    i.putExtra("description", hikeDetail.description);
                }
                startActivity(i);
            }
        });
    }
}