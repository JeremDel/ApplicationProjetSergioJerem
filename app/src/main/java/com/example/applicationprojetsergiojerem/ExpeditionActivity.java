package com.example.applicationprojetsergiojerem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ExpeditionActivity extends AppCompatActivity {

    private ImageButton button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expedition);

        button = (ImageButton)findViewById(R.id.ExpeditionAdd);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpeditionActivity.this, addexpedition.class);
                startActivity(intent);
            }
        });
    }

}