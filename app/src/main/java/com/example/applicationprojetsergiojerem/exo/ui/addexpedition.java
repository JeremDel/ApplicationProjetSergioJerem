package com.example.applicationprojetsergiojerem.exo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.applicationprojetsergiojerem.R;

public class addexpedition extends AppCompatActivity {

     private Button AsauveExped, BsupprimerExped;


     @Override
     protected void onCreate(Bundle savedInstanceState) {

         if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
             setTheme(R.style.DarkTheme);

         }else{
             setTheme(R.style.AppTheme);
         }

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
         Intent intent = new Intent(addexpedition.this, ExpeditionActivity.class);
         startActivity(intent);
     }
 }
