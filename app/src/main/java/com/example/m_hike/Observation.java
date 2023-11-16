package com.example.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class Observation extends AppCompatActivity {

    Button backObservation,createObservation;
    ListView ListViewObservation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation);

        Intent i = getIntent();
        long id = i.getLongExtra("HikeId",0);


        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<ObservationModel> listObservation = db.getobservationData(id);

        ObservationAdapter customAdapter = new ObservationAdapter(listObservation);

        customAdapter.notifyDataSetChanged();

        ListView ListViewObservation = findViewById(R.id.ListViewObservation);
        ListViewObservation.setAdapter(customAdapter);

        ListViewObservation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long observationId) {
                Intent i = new Intent(Observation.this,UpdateObservation.class);

                i.putExtra("ObservationId",observationId);
                i.putExtra("HikeId",id);

                if(observationId != 0) {
                   ObservationModel observationModel = db.getObservationById(observationId);
                   i.putExtra("observation",observationModel.observation);
                   i.putExtra("dateTime",observationModel.dateTime);
                }
                startActivity(i);
            }
        });




        backObservation = (Button) findViewById(R.id.backObservation);
        backObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Observation.this, HikeDetail.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });


        createObservation = (Button) findViewById(R.id.createObservation);
        createObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Observation.this, CreateObservation.class);
                i.putExtra("HikeId",id);
                startActivity(i);
            }
        });


    }
}