package com.example.applicationprojetsergiojerem.exo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applicationprojetsergiojerem.R;

public class ExpeditionActivity extends AppCompatActivity {

    private ImageButton button;
    private ImageView imageMuveran, image7lacs, imageWalser;
    private TextView titreMuveran, textMuveran, titre7lacs, text7lacs, titreWalser, textWalser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_expedition);

        button = (ImageButton) findViewById(R.id.ExpeditionAdd);
        imageMuveran = (ImageView) findViewById(R.id.imageMuveran);
        image7lacs = (ImageView) findViewById(R.id.image7lacs);
        imageWalser = (ImageView) findViewById(R.id.imageWalser);
        titreMuveran = (TextView) findViewById(R.id.titreMuveran);
        titre7lacs = (TextView) findViewById(R.id.titre7lacs);
        titreWalser = (TextView) findViewById(R.id.titreWalser);


        imageMuveran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpeditionActivity.this, Muveran.class);
                startActivity(intent);
            }
        });

        titreMuveran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpeditionActivity.this, Muveran.class);
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpeditionActivity.this, addexpedition.class);
                startActivity(intent);
            }
        });
    }

}