package com.example.mac.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class GoogleSearch extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mImageButton;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_search);
        mImageButton = (ImageButton)findViewById(R.id.imageButton);
        mEditText = (EditText)findViewById(R.id.editText);
        mImageButton.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_contact à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_navig, menu);

        return true;
    }

    private void retour(){

        System.exit(0);
    }

    private void contact(){
        Intent intent= new Intent(GoogleSearch.this,contact.class);
        startActivity(intent);
    }



    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_retour:
                retour();
                return true;
            case R.id.action_contact:
               contact();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

//ouvre un navigateur et lance la recherche sur google
    @Override
    public void onClick(View view) {
        if (view == mImageButton) {
            final String requete = "http://www.google.fr/search?q=" + mEditText.getText();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(requete));
            startActivity(intent);
        }
    }
}
