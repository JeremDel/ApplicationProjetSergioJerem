package com.example.applicationprojetsergiojerem.exo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.applicationprojetsergiojerem.R;

public class Login extends AppCompatActivity {

    private Button Aregister_button, Bsign_in_button;

    /**
     * Création de la page permettant au guide de se connecter à son compte.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);

        }else{
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Aregister_button = (Button) findViewById(R.id.register_button);
        Bsign_in_button = (Button) findViewById(R.id.sign_in_button);

        Aregister_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Gère le bouton amenant sur la page permettant d'enregistrer un nouveau compte
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        /**
         * Permet de se connecter
         */
        Bsign_in_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
