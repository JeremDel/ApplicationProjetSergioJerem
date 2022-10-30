package com.example.applicationprojetsergiojerem.exo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applicationprojetsergiojerem.R;

public class Register extends AppCompatActivity {

    private Button AregisterNewAccompte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        AregisterNewAccompte = (Button) findViewById(R.id.registerNewAccompte);

        AregisterNewAccompte.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }
}