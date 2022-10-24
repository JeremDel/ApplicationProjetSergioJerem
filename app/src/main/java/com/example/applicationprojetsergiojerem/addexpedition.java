package com.example.applicationprojetsergiojerem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

class activity_addexpedition extends AppCompatActivity {

    private Button AsauveExped, BsupprimerExped;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addexpedition);

        AsauveExped = (Button) findViewById(R.id.sauveExped);
        BsupprimerExped = (Button) findViewById(R.id.supprimerExped);

        AsauveExped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToExpedition();
            }
        });

        BsupprimerExped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToExpedition();
            }
        });
    }

        private void goToExpedition(){
            Intent intent = new Intent(activity_addexpedition.this, ExpeditionActivity.class);
            startActivity(intent);
    }
    }

