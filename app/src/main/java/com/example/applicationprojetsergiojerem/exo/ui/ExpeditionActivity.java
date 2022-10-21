package com.example.applicationprojetsergiojerem.exo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.applicationprojetsergiojerem.R;

public class ExpeditionActivity extends AppCompatActivity {

    private Button button;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expedition);

        button = (Button) findViewById(R.id.imageButtonAdd);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addExpedition();
            }
        });
    }
    public void addExpedition(){
        Intent intent = new Intent(this, activity_addexpedition.class);
        startActivity(intent);
    }
}