package com.example.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class SearchHike extends AppCompatActivity {

    Button backButton;
    EditText searchText;
    ListView listViewSearchHike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hike);

        Intent i = getIntent();
        String search = i.getStringExtra("searchText");

        Log.d("searchName",search);
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<MHike> listHikeManger = db.getData(search);

        MHikeListAdapter customAdapter = new MHikeListAdapter(listHikeManger);

        customAdapter.notifyDataSetInvalidated();
        customAdapter.notifyDataSetChanged();

        ListView listViewSearchHike = findViewById(R.id.ListViewSearchHike);
        listViewSearchHike.setAdapter(customAdapter);

        listViewSearchHike.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchHike.this, HikeDetail.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        backButton = (Button) findViewById(R.id.Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}