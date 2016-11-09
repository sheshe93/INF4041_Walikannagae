package com.example.mac.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class contact extends AppCompatActivity {

    ListView mListView;

    String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mListView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(contact.this,android.R.layout.simple_list_item_1, prenoms);
        mListView.setAdapter(adapter);

    }
}
