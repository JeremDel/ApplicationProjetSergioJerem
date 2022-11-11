package com.example.applicationprojetsergiojerem;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddGuide extends AppCompatActivity {

    FloatingActionButton close, done;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_guide);

        close = (FloatingActionButton) findViewById(R.id.nope);
        done = (FloatingActionButton) findViewById(R.id.yep);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                // TODO replace finish() by insert into db and then finish()
            }
        });
    }
}
