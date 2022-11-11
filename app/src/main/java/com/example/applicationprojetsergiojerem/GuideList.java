package com.example.applicationprojetsergiojerem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GuideList extends AppCompatActivity {

    private ImageView ivGuideIcon;
    private TextView tvGuideName, tvGuideDescription;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.guides_list);

        //ivGuideIcon = (ImageView) findViewById(R.id.guideIcon);
        tvGuideDescription = (TextView) findViewById(R.id.guideDescription);
        tvGuideName = (TextView) findViewById(R.id.guideName);
        addButton = (FloatingActionButton) findViewById(R.id.add);

        ivGuideIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGuideView();
            }
        });

        tvGuideName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGuideView();
            }
        });

        tvGuideDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGuideView();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideList.this, AddGuide.class);
                startActivity(intent);
            }
        });
    }

    private void goToGuideView(){
        Intent intent = new Intent(GuideList.this, GuideDetails.class);
        startActivity(intent);
    }
}
