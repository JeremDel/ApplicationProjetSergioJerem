package com.example.applicationprojetsergiojerem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    Button bton_expedition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bton_expedition = (Button)findViewById(R.id.buttonExpedition);

        //bouton pour passer de la fenêtre de titre à la fenête expédition

        bton_expedition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExpeditionActivity.class);
                startActivity(intent);
            }
        });
    }

}