package com.example.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button createButton;
    ListView listViewAllHike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<MHike> listHikeManger = db.getData();

        MHikeListAdapter customAdapter = new MHikeListAdapter(listHikeManger);

        customAdapter.notifyDataSetInvalidated();
        customAdapter.notifyDataSetChanged();

        ListView listViewAllHike = findViewById(R.id.ListViewAllHike);
        listViewAllHike.setAdapter(customAdapter);

        listViewAllHike.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, HikeDetail.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        createButton = (Button) findViewById(R.id.create);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CreateHike.class);
                startActivity(i);
            }
        });
    }


}