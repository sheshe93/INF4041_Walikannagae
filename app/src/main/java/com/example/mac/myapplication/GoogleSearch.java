package com.example.mac.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class GoogleSearch extends Activity implements View.OnClickListener {

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

    @Override
    public void onClick(View view) {
        if (view == mImageButton) {
            final String requete = "http://www.google.fr/search?q=" + mEditText.getText();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(requete));
            startActivity(intent);
        }
    }
}
