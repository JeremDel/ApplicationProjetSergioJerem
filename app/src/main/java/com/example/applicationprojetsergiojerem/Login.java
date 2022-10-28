package com.example.applicationprojetsergiojerem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applicationprojetsergiojerem.exo.ui.MainActivity;

public class Login extends AppCompatActivity {

    private Button Aregister_button, Bsign_in_button;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Aregister_button = (Button) findViewById(R.id.register_button);
        Bsign_in_button = (Button) findViewById(R.id.sign_in_button);

        Aregister_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        Bsign_in_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
