package com.example.mac.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public ListView list;
    public ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


//bouton liste contact
    public void buttonClick(View V) {
     //   Toast.makeText(getApplicationContext(), getString(R.string.msg), Toast.LENGTH_LONG).show();
        Intent intent= new Intent(MainActivity.this,contact.class);
        startActivity(intent);

    }

//bouton recher internet
    public void internetButton(View V) {
         Intent intent= new Intent(MainActivity.this,GoogleSearch.class);
            startActivity(intent);

    }




}
